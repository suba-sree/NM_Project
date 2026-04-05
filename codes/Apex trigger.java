trigger AgriEdgeOrderTrigger on AgriEdge_Order__c (after insert, after update) {
    if (AgriEdgeOrderTriggerHelper.isTriggerExecuted) {
        return; // Prevent recursive execution
    }
    AgriEdgeOrderTriggerHelper.isTriggerExecuted = true;

    List<AgriEdge_Order__c> relevantOrders = new List<AgriEdge_Order__c>();
    List<AgriEdge_Order__c> ordersToUpdate = new List<AgriEdge_Order__c>();
    List<Id> failedOrderIds = new List<Id>();

    for (AgriEdge_Order__c order : Trigger.new) {
        AgriEdge_Order__c oldOrder = null;
        
        // Only access Trigger.oldMap for update events
        if (Trigger.isUpdate) {
            oldOrder = Trigger.oldMap.get(order.Id);
        }

        Boolean paymentStatusChanged = (oldOrder == null || order.Payment_Status__c != oldOrder.Payment_Status__c);
        Boolean orderStatusChanged = (oldOrder == null || order.Order_Status__c != oldOrder.Order_Status__c);

        if (Trigger.isInsert || paymentStatusChanged || orderStatusChanged) {
            relevantOrders.add(order);
        }

        // If Payment is Pending → Set Order Status to Processing
        if (order.Payment_Status__c == 'Pending' && order.Order_Status__c != 'Processing') {
            ordersToUpdate.add(new AgriEdge_Order__c(
                Id = order.Id,
                Order_Status__c = 'Processing'
            ));
        }

        // If Payment Failed → Set Order Status to Cancelled
        if (order.Payment_Status__c == 'Failed') {
            ordersToUpdate.add(new AgriEdge_Order__c(
                Id = order.Id,
                Order_Status__c = 'Canceled'
            ));
            failedOrderIds.add(order.Id);
        }
    }

    // ✅ Perform updates outside of the loop to optimize DML
    if (!ordersToUpdate.isEmpty()) {
        update ordersToUpdate;
    }

    // ✅ Delete related records if Order is Cancelled
    if (!failedOrderIds.isEmpty()) {
        List<AgriEdge_OrderItem__c> orderItemsToDelete = [SELECT Id FROM AgriEdge_OrderItem__c WHERE AgriEdge_Order__c IN :failedOrderIds];
        List<AgriEdge_Shipment__c> shipmentsToDelete = [SELECT Id FROM AgriEdge_Shipment__c WHERE AgriEdge_Order__c IN :failedOrderIds];

        if (!orderItemsToDelete.isEmpty()) {
            delete orderItemsToDelete;
        }
        if (!shipmentsToDelete.isEmpty()) {
            delete shipmentsToDelete;
        }
    }

    // ✅ Call Helper Class for Shipment Processing
    if (!relevantOrders.isEmpty()) {
        AgriEdgeOrderShipmentHelper.processOrderStatusChange(relevantOrders);
    }

    // Reset recursion flag
    AgriEdgeOrderTriggerHelper.isTriggerExecuted = false;
}
