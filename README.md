 Revolutionizing-Agriculture-with-AgriEdge-Or-Mange-Ltd-A-Salesforce-Driven-Order-Management-Solution
1. Abstract

Agriculture remains one of the most critical sectors globally, yet many agricultural businesses continue to rely on manual systems for managing operations. This project proposes a cloud-based solution using Salesforce to streamline agricultural order management for AgriEdge Or-Mange Ltd.

The system integrates farmer data, product inventory, order processing, and delivery tracking into a centralized platform. By implementing automation tools such as Apex, Triggers, and Flows, the solution improves efficiency, reduces operational errors, and ensures real-time decision-making capabilities. This project demonstrates how CRM technology can modernize agricultural supply chains and enhance productivity.

2. Introduction

Agriculture plays a crucial role in sustaining economies, especially in developing countries. However, traditional agricultural businesses face several challenges such as:

*Lack of centralized data management
*Inefficient order tracking
*Poor communication between farmers and suppliers
*Manual errors in record-keeping

To overcome these challenges, AgriEdge Or-Mange Ltd adopts a digital transformation approach using Salesforce. Salesforce provides a scalable, secure, and customizable cloud platform that enables businesses to manage operations efficiently.

This project focuses on designing and implementing a Salesforce-based system that automates order management processes, ensuring transparency and improved service delivery.

3. Objectives

The main objectives of this project are:

*To design a centralized system for agricultural order management
*To automate business processes using Salesforce tools
*To improve data accuracy and reduce redundancy
*To enhance customer (farmer) satisfaction
*To enable real-time monitoring and reporting
*To ensure data security and controlled access

4. User Scenario

The system is designed based on real-world agricultural workflows.

Actors Involved:
*Farmers
*Sales Agents
*Managers/Admin

Workflow:
*Farmers request agricultural products (fertilizers, seeds, etc.)
*Sales agents create orders in the system
*The system records product details, quantity, and pricing
*Admin verifies and processes the order
*Delivery is scheduled and tracked

This structured workflow ensures transparency and efficiency in operations.

5. System Architecture

The system follows a three-tier architecture:

1. Presentation Layer
Salesforce Lightning Interface
Provides user-friendly dashboards and forms
2. Application Layer
Apex Classes
Triggers
Automation tools (Flows, Process Builder)
3. Data Layer
Standard and Custom Objects
Stores all business data
Architecture Benefits:
Scalability
High availability
Secure cloud storage
Easy integration

6. Salesforce Credentials Setup

To begin development, a Salesforce Developer account is required.

Steps:
1.Visit Salesforce Developer website
2.Register with email and personal details
3.Verify account via email
4.Set password and security question
5.Login to Salesforce dashboard

This setup provides access to all development tools required for the project.

7. Data Management – Objects

Objects are the backbone of Salesforce data storage.

Custom Objects Created:
1. Farmer Object
Stores farmer details
Fields: Name, Contact, Location
2. Product Object
Stores product information
Fields: Product Name, Price, Stock
3. Order Object
Stores order transactions
Fields: Order Date, Quantity, Total Amount
4. Delivery Object
Tracks delivery details
Fields: Delivery Date, Status

8. Data Management – Tabs

Tabs provide easy access to objects.

Purpose:
*Simplify navigation
*Improve user experience
*Enable quick data entry

Each object is associated with a tab in the Salesforce interface.

9. Data Management – App Manager

The App Manager is used to create a custom application.

AgriEdge Management App Features:
*Centralized dashboard
*Access to all objects
*Custom branding and layout

This app acts as the main interface for users.

10. Fields & Relationships
Fields Explanation:

Fields store specific data values.

Examples:

Farmer Name → Text
Contact → Phone
Price → Currency
Order Date → Date
Relationships:
One-to-Many Relationship
One Farmer can have multiple Orders
Lookup Relationship
Orders are linked to Products

These relationships ensure proper data organization and integrity.

11. Field Types

Salesforce provides multiple field types to store different data formats:

*Text → Names and descriptions
*Number → Quantities
*Date → Order and delivery dates
*Picklist → Status selection (Pending, Completed)
*Lookup → Object relationships
*Formula →Auto-calculated values

12. Data Security – Roles

Roles define the hierarchy in an organization

Role Structure:
Admin (Full access)
Manager (Limited administrative control)
Sales Agent (Restricted access)

This hierarchy ensures proper control over data visibility.uto-calculated values

13. Data Security – Profiles

Profiles determine user permissions.

Permissions Include:
*Create, Read, Edit, Delete (CRUD)
*Access to objects
*Field visibility

Profiles ensure that users can only perform allowed operations.

14. Data Security – Users

Users represent individuals in the system.

User Setup Includes:
*Username
*Role assignment
*Profile assignment
Each user logs in and accesses the system based on permissions.

15. Field-Level Security

Field-level security protects sensitive information.

Examples:
Restrict editing of price fields
Hide confidential data from sales agents

This ensures data confidentiality and integrity.

16. Apex Class

Apex is a programming language used in Salesforce.

Purpose:
*Implement custom business logic
*Perform validations
*Automate calculations

17. Triggers

Triggers execute automatically when data changes.

Example Trigger:
When an order is created → reduce product stock
Benefits:
*Automation
*Data consistency
*Reduced manual intervention

18. Testing

Testing ensures system reliability.

Types of Testing:
*Unit Testing
*Tests individual components
*System Testing
*Tests complete system workflow
Importance:
Detect errors early
Ensure smooth functionality

19. Automation

Salesforce provides powerful automation tools:

1. Workflow Rules
Automate simple tasks
2. Process Builder
Handle complex logic
3. Flow Builder
Create advanced automation
Example:
Send notification when order is confirmed

20. Advantages
*Improved efficiency
*Reduced manual errors
*Real-time tracking
*Better customer service
*Centralized data management

22. Limitations
*Requires stable internet connection
*Initial learning curve
*Implementation cost

23. Conclusion

The AgriEdge Or-Mange Ltd project demonstrates the potential of Salesforce in transforming agricultural operations. By automating workflows and centralizing data, the system enhances efficiency, accuracy, and transparency.

This solution not only improves business performance but also supports farmers by providing faster and more reliable services. The integration of modern technology into agriculture marks a significant step toward sustainable and smart farming.



1. Create Another Apex Class Name is OrderTotalUpdater and Click Ok Button

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXeJBQIzeXu2AwjUN2nWXWiXY18yfPYD6zXm4GpV1CpKR_E5SgsGBP17RjWHTFdH-evYgGEc5Lz14g-xy8av2pqXN5l5mZz6dhDnoQolBCaieirTIscCzdoQu5k5oZJXadN0BE5cnw?key=ipCZHoYHuexRwUknKnl92gl8)

1. Inside the Apex Class Write The code

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXd62zQe1Akt5VcLx6S3D5n0C8_5Yd97VsJLML1pWDpSDJFvwr76fU9qi_qfx0lfzCtsqZInSckGUVpu4oEF42QUPoIw6NeFQqibHdr_VuBM1zgGHRm-9IZrOyAxnPFhSAdqZJr-4Q?key=ipCZHoYHuexRwUknKnl92gl8)

1. **Source Code:**

```javascript
public class OrderTotalUpdater {
    public static void updateOrderTotal(Set<Id> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            return;
        }
        // Map to store OrderId and its Total Price
        Map<Id, Decimal> orderTotals = new Map<Id, Decimal>();
        // Query all order items related to the given orders
        for (AggregateResult ar : [
            SELECT AgriEdge_Order__c orderId, SUM(Total_Price__c) totalAmount
            FROM AgriEdge_OrderItem__c 
            WHERE AgriEdge_Order__c IN :orderIds
            GROUP BY AgriEdge_Order__c
        ]) {
            orderTotals.put((Id) ar.get('orderId'), (Decimal) ar.get('totalAmount'));
        }
        List<AgriEdge_Order__c> ordersToUpdate = new List<AgriEdge_Order__c>();
        // Query orders that need to be updated
        for (AgriEdge_Order__c order : [
            SELECT Id, Total_Amount__c, Payment_Status__c 
            FROM AgriEdge_Order__c 
            WHERE Id IN :orderIds
        ]) {
            order.Total_Amount__c = orderTotals.containsKey(order.Id) ? orderTotals.get(order.Id) : 0;
            order.Payment_Status__c = (order.Total_Amount__c > 0) ? 'Pending' : 'Paid'; // If total > 0, set to Pending
            ordersToUpdate.add(order);
        }
        if (!ordersToUpdate.isEmpty()) {
            update ordersToUpdate;
        }
    }
}
```

1. Create Trigger Class Call Apex Class OrderStatusUpdater  in Trigger

2. Click On File and Click New then Select Apex Trigger

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXcuJ0TsM5p1-o2j6MS4ZIPP7BYRPXD9eX8Dbfnnz8VU581kM5y-JFB3KhqsJeiZm6Z9TnpuTzg2b2ydCRdQAl1emX67ILTQGGoGkoU6xcD0bt4hi7Mju2bgVYWRIZ1-9dlxSmcHcw?key=ipCZHoYHuexRwUknKnl92gl8)

1. **Create an Apex trigger**

   * Name : OrderItemTrigger

   * Object : AgriEdge_OrderItem__C

   * Click Submit Button

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfLxwHagdRzx0xhVQ07FoNO-3zSz1qbteWby6IHnVXNmFO_gT9OLG5WBNWFzHzI34V9cNC-sVyX3D5YSlGvT1htwRX-ja-6-2GhE1vOcYGuXjWONBQl6Z5mSPWREwhVK6L9FWuq?key=ipCZHoYHuexRwUknKnl92gl8)

1. Write Trigger Code Inside the Trigger Class

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfI9t7N4T-I6rodGx27ALsEpIRfw4tm_nn2DbvAeGzHGUqi_fQWSvzMcBTXXYG4LA6uCBZw5QONkE9CLQcj9-oew4IFciBnliXB_05-0oq5UAriG5M1RcuWievkTNnywAdzeOSl?key=ipCZHoYHuexRwUknKnl92gl8)

1. **Source Code:**

```javascript
trigger OrderItemTrigger on AgriEdge_OrderItem__c (after insert, after update) {
    Set<Id> orderIds = new Set<Id>();

    // Collect Order IDs from inserted/updated OrderItem records
    for (AgriEdge_OrderItem__c orderItem : Trigger.new) {
        if (orderItem.AgriEdge_Order__c != null) {
            orderIds.add(orderItem.AgriEdge_Order__c);
        }
    }

    if (!orderIds.isEmpty()) {
        OrderStatusUpdater.updateOrderStatus(orderIds);
        OrderTotalUpdater.updateOrderTotal(orderIds);
    }
}
```

1. **Create Apex Class For Email**

   * Class Name : OrderEmailSender

   * Click Ok Button

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdGOzWeDbJLjhBt1jSAR4MU4Fjt2rnmloOAkIqJbNq0iKl2EGEXONz0tWAwcW2rU323fHMyDtzHskDGZUY2nC4_dpmUwpnZWgcdignTjM9G8FQS6MGrUR3IsKWQBH2K5PaYL0F0xA?key=ipCZHoYHuexRwUknKnl92gl8)

1. Inside the Class Write Apex Logic Code

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXf6wjDLKay35Oz5r8GtUEDTwXbfkBShsbC-0Huz1XcqqOIE-y0SzB25pUoB82LIWMk3aaJsT1iwtJIwRsdBRY3pcGd0yZWVDfRS_twjMCio9c3Sd-qx3rQPhL9CYp5PITWbK0iJPw?key=ipCZHoYHuexRwUknKnl92gl8)

1. **Source Code :**

```javascript
public class OrderEmailSender {
    public static void sendOrderEmail(Set<Id> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            return;
        }

        //  Include Shipping_Address__c and Discounted_Total__c in the query
        List<AgriEdge_Order__c> orders = [
            SELECT Id, Name, Total_Amount__c, Payment_Status__c, Order_Status__c, 
                   Customer__c, CreatedDate, Shipping_Address__c, Discounted_Total__c
            FROM AgriEdge_Order__c
            WHERE Id IN :orderIds
        ];

        // Collect Customer (Account) IDs
        Set<Id> accountIds = new Set<Id>();
        for (AgriEdge_Order__c order : orders) {
            if (order.Customer__c != null) {
                accountIds.add(order.Customer__c);
            }
        }

        // Query related Contacts of Customers (Accounts)
        Map<Id, List<String>> accountEmails = new Map<Id, List<String>>();
        for (Contact contact : [
            SELECT Email, AccountId FROM Contact WHERE AccountId IN :accountIds AND Email != null
        ]) {
            if (!accountEmails.containsKey(contact.AccountId)) {
                accountEmails.put(contact.AccountId, new List<String>());
            }
            accountEmails.get(contact.AccountId).add(contact.Email);
        }

        // Prepare Emails
        List<Messaging.SingleEmailMessage> emails = new List<Messaging.SingleEmailMessage>();

        for (AgriEdge_Order__c order : orders) {
            if (accountEmails.containsKey(order.Customer__c)) {
                List<String> toEmails = accountEmails.get(order.Customer__c);

                // Create Email Body
                String emailBody = 'Dear Customer,<br/><br/>'
                    + 'Your order has been updated with the following details:<br/><br/>'
                    + '<b>Order Name:</b> ' + order.Name + '<br/>'
                    + '<b>Order Status:</b> ' + order.Order_Status__c + '<br/>'
                    + '<b>Total Amount:</b> ' + order.Total_Amount__c + '<br/>'
                    + '<b>Payment Status:</b> ' + order.Payment_Status__c + '<br/>'
                    + '<b>Shipping Address:</b> ' + order.Shipping_Address__c + '<br/>'
                    + '<b>Created Date:</b> ' + order.CreatedDate + '<br/><br/>'
                    + '<b>Total Amount Paid (Including Discount):</b> ' + order.Discounted_Total__c + '<br/><br/>'
                    + 'Thank you for your business!<br/><br/>'
                    + 'Best Regards,<br/>Your Company Name';

                // Prepare Email
                Messaging.SingleEmailMessage email = new Messaging.SingleEmailMessage();
                email.setToAddresses(toEmails);
                email.setSubject('Your Order Payment Status has been Updated');
                email.setHtmlBody(emailBody);
                
                emails.add(email);
            }
        }

        if (!emails.isEmpty()) {
            Messaging.sendEmail(emails);
        }
    }
}
```

1. **Create an Apex Trigger**

   * Create Trigger Class

   * Class Name : OrderPaymentStatusTriggers

   * Object : AgriEdge_Order__c

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXcAtYuWRFwPGdgPnnARpyphbNDEOBqUiRUfX2b01rcuvwTmSIj8ZyV95Z_IlF6rwTETiqpjNmuKtbDHSdEtRfaHtEqjIrtHH4gmfHLB_3CCTtwvOiNU-Djvgur7wzubnatew_6Xnw?key=ipCZHoYHuexRwUknKnl92gl8)

1. Write Code Logic Inside the Trigger

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXeyq9Hrv0yJm7Y4YZzDd4N3x1NTNtT8OirEFEcXRrKf6oeX4Oi_zxVHlytHa8VN38HYf56M0uJQGEJYqLcitttQLSzTGt2nCSG1OBcbYORm8T_6DoPFWgadjRJAZk3eQi2rbNMrCg?key=ipCZHoYHuexRwUknKnl92gl8)

1. **Source Code :**

```javascript
trigger OrderPaymentStatusTriggers on AgriEdge_Order__c (after update) {
    Set<Id> updatedOrderIds = new Set<Id>();

    // Collect Orders where Payment_Status__c was changed to "Paid"
    for (AgriEdge_Order__c order : Trigger.new) {
        AgriEdge_Order__c oldOrder = Trigger.oldMap.get(order.Id);
        if (oldOrder.Payment_Status__c != 'Paid' && order.Payment_Status__c == 'Paid' && order.Customer__c != null) {
            updatedOrderIds.add(order.Id);
        }
    }

    if (!updatedOrderIds.isEmpty()) {
        OrderEmailSender.sendOrderEmail(updatedOrderIds);
    }
}
```

1. **Create Apex Class**

   * Class Name : AgriEdgeOrderShipmentHelper

   * Click Ok Button

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXe68_TxbmPsnSQzTLFgh5kYR1mSWAiiZVJnJX4kZyhtm3MSD-8AjWa8ubUvPWs7XVRqoLUMBqUBKK3Ol17qoqvMz6QDvNNAqn_P3Ity1-17efiX_-0EOOStppxn_aTSkntQnWYheg?key=ipCZHoYHuexRwUknKnl92gl8)

1. Write Logic inSide Apex Class

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXfDbOlzRPy4nZo4JaTyuY-sWo0CMiGQJNbPe-nF71CLsffX5cO3iULpUu3aS3kTXRqbUhWKytL6okM378LH95tnDyxYZy9f25PXVcWuY5kZoU2HArGsU03EhQRWdoJt27GTvsRKOA?key=ipCZHoYHuexRwUknKnl92gl8)

**Source Code :**

```javascript
public class AgriEdgeOrderShipmentHelper {
    public static void processOrderStatusChange(List<AgriEdge_Order__c> updatedOrders) {
        List<AgriEdge_Shipment__c> shipmentsToInsert = new List<AgriEdge_Shipment__c>();
        List<AgriEdge_Shipment__c> shipmentsToUpdate = new List<AgriEdge_Shipment__c>();
        List<AgriEdge_Order__c> ordersToUpdate = new List<AgriEdge_Order__c>();
        List<AgriEdge_OrderItem__c> orderItemsToDelete = new List<AgriEdge_OrderItem__c>();
        List<AgriEdge_Shipment__c> shipmentsToDelete = new List<AgriEdge_Shipment__c>();
        
        Set<Id> orderIds = new Set<Id>();

        for (AgriEdge_Order__c order : updatedOrders) {
            orderIds.add(order.Id);
        }

        Map<Id, AgriEdge_Shipment__c> existingShipments = new Map<Id, AgriEdge_Shipment__c>();
        for (AgriEdge_Shipment__c shipment : [
            SELECT Id, AgriEdge_Order__c, Status__c 
            FROM AgriEdge_Shipment__c 
            WHERE AgriEdge_Order__c IN :orderIds
        ]) {
            existingShipments.put(shipment.AgriEdge_Order__c, shipment);
        }

        Map<Id, List<AgriEdge_OrderItem__c>> existingOrderItems = new Map<Id, List<AgriEdge_OrderItem__c>>();
        for (AgriEdge_OrderItem__c orderItem : [
            SELECT Id, AgriEdge_Order__c 
            FROM AgriEdge_OrderItem__c 
            WHERE AgriEdge_Order__c IN :orderIds
        ]) {
            if (!existingOrderItems.containsKey(orderItem.AgriEdge_Order__c)) {
                existingOrderItems.put(orderItem.AgriEdge_Order__c, new List<AgriEdge_OrderItem__c>());
            }
            existingOrderItems.get(orderItem.AgriEdge_Order__c).add(orderItem);
        }

        for (AgriEdge_Order__c order : updatedOrders) {
            AgriEdge_Order__c updatedOrder = order.clone(false, true, false, false);
            updatedOrder.Id = order.Id;
            
            if (order.Payment_Status__c == 'Paid' && order.Order_Status__c != 'Delivered') {
                updatedOrder.Order_Status__c = 'Delivered';
                ordersToUpdate.add(updatedOrder);
            }
            else if (order.Payment_Status__c == 'Pending') {
                updatedOrder.Order_Status__c = 'Processing';
                ordersToUpdate.add(updatedOrder);
            }
            else if (order.Payment_Status__c == 'Failed') {
                updatedOrder.Order_Status__c = 'Canceled';
                ordersToUpdate.add(updatedOrder);
                
                if (existingOrderItems.containsKey(order.Id)) {
                    orderItemsToDelete.addAll(existingOrderItems.get(order.Id));
                }
                if (existingShipments.containsKey(order.Id)) {
                    shipmentsToDelete.add(existingShipments.get(order.Id));
                }
            }

            if (order.Order_Status__c == 'Processing' && !existingShipments.containsKey(order.Id)) {
                AgriEdge_Shipment__c newShipment = new AgriEdge_Shipment__c(
                    AgriEdge_Order__c = order.Id,
                    Tracking_Number__c = 'TEST_' + order.Id,
                    Status__c = 'Pending'
                );
                shipmentsToInsert.add(newShipment);
            }
            else if (order.Order_Status__c == 'Shipped' || order.Order_Status__c == 'Delivered') {
                if (existingShipments.containsKey(order.Id)) {
                    AgriEdge_Shipment__c shipmentToUpdate = existingShipments.get(order.Id);
                    shipmentToUpdate.Status__c = (order.Order_Status__c == 'Shipped') ? 'In Transit' : 'Delivered';
                    shipmentsToUpdate.add(shipmentToUpdate);
                }
            }
        }

        if (!ordersToUpdate.isEmpty()) {
            update ordersToUpdate;
        }
        if (!shipmentsToInsert.isEmpty()) {
            insert shipmentsToInsert;
        }
        if (!shipmentsToUpdate.isEmpty()) {
            update shipmentsToUpdate;
        }
        if (!orderItemsToDelete.isEmpty()) {
            delete orderItemsToDelete;
        }
        if (!shipmentsToDelete.isEmpty()) {
            delete shipmentsToDelete;
        }
    }
}
```

1. Create Apex Class

   * Class Name : AgriEdgeOrderTriggerHelper

   * Click Ok Button

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXd-Egb-W0CqnqkSmPi5rXxV-wI29E0bZ02nSEWqMkuOzosk1vcnt7YC56jpJ18BacGdt__3nRkF2OvYSV1-Bhkj9qP8pWxbUHEEtUkS_TP_XsCxsEjgt-5zoSz8OuSDnl9KyFjutA?key=ipCZHoYHuexRwUknKnl92gl8)

1. Write Boolean Logic to Help In Trigger Class

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdDnMthrTeDsP1hshcQHWqfbLiC0BCoPA1Q5Ere_TsGCr1lY-ZuQyLXu0qrfxIkTIQzFCSTYcN7g0egTlC890eeUW9tkbQAYxXunugTScCadUr00sGhZDbgotWUIh2zTuvgKF1ODw?key=ipCZHoYHuexRwUknKnl92gl8)

**Source Code :**

```javascript
public class AgriEdgeOrderTriggerHelper {
    public static Boolean isTriggerExecuted = false;
}
```

1. **Create Trigger Class**

   * Class Name : AgriEdgeOrderTrigger

   * Object : AgriEdge_Order__c

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXcgXY_dG662jG05NM2PH1R8NlvaLwHLVHcJ7zTxqmgrQT4q1vSlewpQGSxej7Qoq_7Inaqp2Sr0b4NntUkM1eSDuQORYf5kwDdHoDXYIXeBBLwDzyP-3ePCLFsmli346TKsxdXaHA?key=ipCZHoYHuexRwUknKnl92gl8)

1. Create :Logic In Trigger Class

![BlockNote image](https://lh7-rt.googleusercontent.com/docsz/AD_4nXclXlEWUWHV5TlvuOcaWgezHLWUHxjYSVQpVT4ubnrHR4lK1fxv2jaM9C1RTLtYFppmG8xwalHpPOA559Eau0QimuZ93NehzFF-0Ch1guzs8yZEygGCxy5Pj6etyND4ZvDXxixJXA?key=ipCZHoYHuexRwUknKnl92gl8)

**Source Code :**

```javascript
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

