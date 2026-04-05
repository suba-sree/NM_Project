# 🌾 AgriEdge Or-Mange Ltd — Salesforce-Driven Order Management System

> **Revolutionizing Agriculture with Cloud Technology** — A Salesforce CRM solution that streamlines order management, automates workflows, and empowers farmers with real-time tracking.

---

## PROJECT CREATION VIDEO: 
[GDrive Demo Link](https://drive.google.com/drive/folders/1ry1C0cEd_Zujrrsgp--yiY3bTIcuaRhc)

---

## Table of Contents

- [Abstract](#-abstract)
- [Introduction](#-introduction)
- [Objectives](#-objectives)
- [User Scenario](#-user-scenario)
- [System Architecture](#-system-architecture)
- [Getting Started](#-getting-started)
- [Data Management](#-data-management)
- [Data Security](#-data-security)
- [Automation & Apex Development](#-automation--apex-development)
- [Testing](#-testing)
- [Advantages & Limitations](#-advantages--limitations)
- [Conclusion](#-conclusion)

---

## Abstract

Agriculture remains one of the most critical sectors globally, yet many businesses still rely on manual systems for managing operations. This project proposes a **cloud-based solution using Salesforce** to streamline agricultural order management for AgriEdge Or-Mange Ltd.

The system integrates:
- Farmer data management
- Product inventory tracking
- Order processing
- Delivery tracking

By implementing automation tools such as **Apex Classes**, **Triggers**, and **Flows**, the solution improves efficiency, reduces operational errors, and ensures real-time decision-making capabilities.

---

## Introduction

Agriculture plays a crucial role in sustaining economies, especially in developing countries. However, traditional agricultural businesses face several challenges:

| Challenge | Impact |
|-----------|--------|
| Lack of centralized data management | Data silos and duplication |
| Inefficient order tracking | Delayed fulfillment |
| Poor farmer-supplier communication | Lost orders and disputes |
| Manual record-keeping errors | Financial inaccuracies |

**AgriEdge Or-Mange Ltd** addresses these challenges through a **digital transformation approach using Salesforce** — a scalable, secure, and customizable cloud platform that automates order management processes and ensures transparency.

---

## Objectives

- Design a **centralized system** for agricultural order management
- **Automate business processes** using Salesforce tools
- Improve **data accuracy** and reduce redundancy
- Enhance **customer (farmer) satisfaction**
- Enable **real-time monitoring** and reporting
- Ensure **data security** and controlled access

---

## User Scenario

### Actors Involved

| Actor | Role |
|-------|------|
| **Farmers** | Request agricultural products |
| **Sales Agents** | Create and manage orders |
| **Admin / Manager** | Verify, process, and oversee operations |

### Workflow

```
Farmer Request → Sales Agent Creates Order → System Records Details
      → Admin Verifies & Processes → Delivery Scheduled & Tracked
```

This structured workflow ensures **transparency and efficiency** across all operations.

---

## System Architecture

The system follows a **three-tier architecture**:

```
┌─────────────────────────────────────┐
│        Presentation Layer           │
│  Salesforce Lightning Interface     │
│  Dashboards · Forms · Reports       │
├─────────────────────────────────────┤
│        Application Layer            │
│  Apex Classes · Triggers · Flows    │
│  Process Builder · Workflow Rules   │
├─────────────────────────────────────┤
│           Data Layer                │
│  Standard & Custom Objects          │
│  Secure Cloud Storage               │
└─────────────────────────────────────┘
```

**Architecture Benefits:**

| Benefit | Description |
|---------|-------------|
| Scalability | Grows with business needs |
| Security | Enterprise-grade cloud security |
| High Availability | 99.9% uptime SLA |
| Easy Integration | Connect with third-party systems |

---

## Getting Started

### Salesforce Developer Account Setup

1. Visit [Salesforce Developer](https://developer.salesforce.com/) website
2. Register with your email and personal details
3. Verify your account via email link
4. Set a secure password and security question
5. Login to your **Salesforce Developer Dashboard**

> This provides access to all development tools required for this project.

---

## Data Management

### Custom Objects

| Object | Purpose | Key Fields |
|--------|---------|------------|
| **Farmer** | Stores farmer details | Name, Contact, Location |
| **Product** | Stores product information | Product Name, Price, Stock |
| **Order** | Records order transactions | Order Date, Quantity, Total Amount |
| **Delivery** | Tracks delivery status | Delivery Date, Status |

### Field Types Reference

| Field Type | Use Case |
|------------|----------|
| `Text` | Names and descriptions |
| `Number` | Quantities |
| `Date` | Order and delivery dates |
| `Picklist` | Status selection (Pending, Completed) |
| `Lookup` | Object relationships |
| `Formula` | Auto-calculated values (e.g., Total Price) |

### Relationships

- **One-to-Many:** One Farmer → Multiple Orders
- **Lookup:** Orders linked to Products

### Navigation (Tabs & App Manager)

- Each object has a dedicated **Tab** for quick navigation and data entry
- The **AgriEdge Management App** (built via App Manager) provides:
  - Centralized dashboard
  - Access to all objects
  - Custom branding and layout

---

## Data Security

### Role Hierarchy

```
        Admin
       (Full Access)
           │
       Manager
    (Limited Admin)
           │
      Sales Agent
    (Restricted Access)
```

### Profiles & Permissions

| Permission | Admin | Manager | Sales Agent |
|------------|:-----:|:-------:|:-----------:|
| Create | ✅ | ✅ | ✅ |
| Read | ✅ | ✅ | ✅ |
| Edit | ✅ | ✅ | ⚠️ Limited |
| Delete | ✅ | ❌ | ❌ |

### Field-Level Security

- Restrict editing of **price fields** for Sales Agents
- Hide **confidential data** from unauthorized roles
- Control **field visibility** per profile

---

## Automation & Apex Development

### Automation Tools Overview

| Tool | Purpose |
|------|---------|
| **Workflow Rules** | Automate simple tasks (e.g., email on order confirm) |
| **Process Builder** | Handle complex multi-step logic |
| **Flow Builder** | Create advanced user-driven automation |

---

### Apex Classes

#### `OrderTotalUpdater`

Automatically calculates and updates the **total amount** of an order based on its line items.

```apex
public class OrderTotalUpdater {
    public static void updateOrderTotal(Set<Id> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            return;
        }
        Map<Id, Decimal> orderTotals = new Map<Id, Decimal>();

        for (AggregateResult ar : [
            SELECT AgriEdge_Order__c orderId, SUM(Total_Price__c) totalAmount
            FROM AgriEdge_OrderItem__c 
            WHERE AgriEdge_Order__c IN :orderIds
            GROUP BY AgriEdge_Order__c
        ]) {
            orderTotals.put((Id) ar.get('orderId'), (Decimal) ar.get('totalAmount'));
        }

        List<AgriEdge_Order__c> ordersToUpdate = new List<AgriEdge_Order__c>();
        for (AgriEdge_Order__c order : [
            SELECT Id, Total_Amount__c, Payment_Status__c 
            FROM AgriEdge_Order__c 
            WHERE Id IN :orderIds
        ]) {
            order.Total_Amount__c = orderTotals.containsKey(order.Id) ? orderTotals.get(order.Id) : 0;
            order.Payment_Status__c = (order.Total_Amount__c > 0) ? 'Pending' : 'Paid';
            ordersToUpdate.add(order);
        }

        if (!ordersToUpdate.isEmpty()) {
            update ordersToUpdate;
        }
    }
}
```

---

#### `AgriEdgeOrderShipmentHelper`

Handles shipment lifecycle — creates, updates, or deletes shipment records based on payment and order status changes.

```apex
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
            } else if (order.Payment_Status__c == 'Pending') {
                updatedOrder.Order_Status__c = 'Processing';
                ordersToUpdate.add(updatedOrder);
            } else if (order.Payment_Status__c == 'Failed') {
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
                shipmentsToInsert.add(new AgriEdge_Shipment__c(
                    AgriEdge_Order__c = order.Id,
                    Tracking_Number__c = 'TEST_' + order.Id,
                    Status__c = 'Pending'
                ));
            } else if (order.Order_Status__c == 'Shipped' || order.Order_Status__c == 'Delivered') {
                if (existingShipments.containsKey(order.Id)) {
                    AgriEdge_Shipment__c s = existingShipments.get(order.Id);
                    s.Status__c = (order.Order_Status__c == 'Shipped') ? 'In Transit' : 'Delivered';
                    shipmentsToUpdate.add(s);
                }
            }
        }

        if (!ordersToUpdate.isEmpty())      update ordersToUpdate;
        if (!shipmentsToInsert.isEmpty())   insert shipmentsToInsert;
        if (!shipmentsToUpdate.isEmpty())   update shipmentsToUpdate;
        if (!orderItemsToDelete.isEmpty())  delete orderItemsToDelete;
        if (!shipmentsToDelete.isEmpty())   delete shipmentsToDelete;
    }
}
```

---

#### `AgriEdgeOrderTriggerHelper`

A **recursion guard** to prevent infinite trigger loops.

```apex
public class AgriEdgeOrderTriggerHelper {
    public static Boolean isTriggerExecuted = false;
}
```

---

#### `OrderEmailSender`

Sends automated **HTML email notifications** to customers when their payment status is updated.

```apex
public class OrderEmailSender {
    public static void sendOrderEmail(Set<Id> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) return;

        List<AgriEdge_Order__c> orders = [
            SELECT Id, Name, Total_Amount__c, Payment_Status__c, Order_Status__c, 
                   Customer__c, CreatedDate, Shipping_Address__c, Discounted_Total__c
            FROM AgriEdge_Order__c
            WHERE Id IN :orderIds
        ];

        Set<Id> accountIds = new Set<Id>();
        for (AgriEdge_Order__c order : orders) {
            if (order.Customer__c != null) accountIds.add(order.Customer__c);
        }

        Map<Id, List<String>> accountEmails = new Map<Id, List<String>>();
        for (Contact contact : [
            SELECT Email, AccountId FROM Contact WHERE AccountId IN :accountIds AND Email != null
        ]) {
            if (!accountEmails.containsKey(contact.AccountId)) {
                accountEmails.put(contact.AccountId, new List<String>());
            }
            accountEmails.get(contact.AccountId).add(contact.Email);
        }

        List<Messaging.SingleEmailMessage> emails = new List<Messaging.SingleEmailMessage>();
        for (AgriEdge_Order__c order : orders) {
            if (accountEmails.containsKey(order.Customer__c)) {
                String emailBody = 'Dear Customer,<br/><br/>'
                    + 'Your order has been updated:<br/><br/>'
                    + '<b>Order Name:</b> ' + order.Name + '<br/>'
                    + '<b>Order Status:</b> ' + order.Order_Status__c + '<br/>'
                    + '<b>Total Amount:</b> ' + order.Total_Amount__c + '<br/>'
                    + '<b>Payment Status:</b> ' + order.Payment_Status__c + '<br/>'
                    + '<b>Shipping Address:</b> ' + order.Shipping_Address__c + '<br/>'
                    + '<b>Created Date:</b> ' + order.CreatedDate + '<br/><br/>'
                    + '<b>Discounted Total:</b> ' + order.Discounted_Total__c + '<br/><br/>'
                    + 'Thank you for your business!<br/><br/>Best Regards,<br/>AgriEdge Team';

                Messaging.SingleEmailMessage email = new Messaging.SingleEmailMessage();
                email.setToAddresses(accountEmails.get(order.Customer__c));
                email.setSubject('Your Order Payment Status has been Updated');
                email.setHtmlBody(emailBody);
                emails.add(email);
            }
        }

        if (!emails.isEmpty()) Messaging.sendEmail(emails);
    }
}
```

---

### Triggers

#### `OrderItemTrigger`

Fires after **insert/update** on `AgriEdge_OrderItem__c` to keep order status and totals in sync.

```apex
trigger OrderItemTrigger on AgriEdge_OrderItem__c (after insert, after update) {
    Set<Id> orderIds = new Set<Id>();

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

---

#### `AgriEdgeOrderTrigger`

Main order trigger that handles **payment-driven status changes**, shipment creation, and cascade deletions.

```apex
trigger AgriEdgeOrderTrigger on AgriEdge_Order__c (after insert, after update) {
    if (AgriEdgeOrderTriggerHelper.isTriggerExecuted) return;
    AgriEdgeOrderTriggerHelper.isTriggerExecuted = true;

    List<AgriEdge_Order__c> relevantOrders = new List<AgriEdge_Order__c>();
    List<AgriEdge_Order__c> ordersToUpdate = new List<AgriEdge_Order__c>();
    List<Id> failedOrderIds = new List<Id>();

    for (AgriEdge_Order__c order : Trigger.new) {
        AgriEdge_Order__c oldOrder = Trigger.isUpdate ? Trigger.oldMap.get(order.Id) : null;

        Boolean paymentStatusChanged = (oldOrder == null || order.Payment_Status__c != oldOrder.Payment_Status__c);
        Boolean orderStatusChanged   = (oldOrder == null || order.Order_Status__c  != oldOrder.Order_Status__c);

        if (Trigger.isInsert || paymentStatusChanged || orderStatusChanged) {
            relevantOrders.add(order);
        }

        if (order.Payment_Status__c == 'Pending' && order.Order_Status__c != 'Processing') {
            ordersToUpdate.add(new AgriEdge_Order__c(Id = order.Id, Order_Status__c = 'Processing'));
        }

        if (order.Payment_Status__c == 'Failed') {
            ordersToUpdate.add(new AgriEdge_Order__c(Id = order.Id, Order_Status__c = 'Canceled'));
            failedOrderIds.add(order.Id);
        }
    }

    // ✅ Bulk DML outside loop
    if (!ordersToUpdate.isEmpty()) update ordersToUpdate;

    // ✅ Cascade delete on cancellation
    if (!failedOrderIds.isEmpty()) {
        List<AgriEdge_OrderItem__c> itemsToDelete    = [SELECT Id FROM AgriEdge_OrderItem__c WHERE AgriEdge_Order__c IN :failedOrderIds];
        List<AgriEdge_Shipment__c>  shipmentsToDelete = [SELECT Id FROM AgriEdge_Shipment__c  WHERE AgriEdge_Order__c IN :failedOrderIds];
        if (!itemsToDelete.isEmpty())    delete itemsToDelete;
        if (!shipmentsToDelete.isEmpty()) delete shipmentsToDelete;
    }

    // ✅ Delegate shipment processing to helper
    if (!relevantOrders.isEmpty()) {
        AgriEdgeOrderShipmentHelper.processOrderStatusChange(relevantOrders);
    }

    AgriEdgeOrderTriggerHelper.isTriggerExecuted = false;
}
```

---

#### `OrderPaymentStatusTriggers`

Sends email notifications when an order's payment status transitions to **"Paid"**.

```apex
trigger OrderPaymentStatusTriggers on AgriEdge_Order__c (after update) {
    Set<Id> updatedOrderIds = new Set<Id>();

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

---

## Testing

| Test Type | Scope | Goal |
|-----------|-------|------|
| **Unit Testing** | Individual Apex methods | Validate logic in isolation |
| **System Testing** | End-to-end workflow | Ensure complete integration |

> Salesforce requires **≥75% code coverage** for production deployments.

**Testing Best Practices:**
- Write `@isTest` annotated test classes for all Apex code
- Use `Test.startTest()` / `Test.stopTest()` to isolate governor limits
- Cover both positive and negative test scenarios

---

## Advantages & Limitations

### Advantages

| Benefit | Description |
|---------|-------------|
| Improved Efficiency | Automated workflows reduce manual effort |
| Accuracy | Reduced human errors via automation |
| Real-Time Tracking | Live order and delivery monitoring |
| Better Service | Faster response to farmer requests |
| Centralized Data | Single source of truth for all operations |

### Limitations

| Limitation | Notes |
|------------|-------|
| Internet Dependency | Requires stable connectivity |
| Learning Curve | Initial onboarding time needed |
| Implementation Cost | Salesforce licensing fees apply |

---

## Conclusion

The **AgriEdge Or-Mange Ltd** project demonstrates the transformative potential of Salesforce CRM in modernizing agricultural operations. By automating workflows and centralizing data, the system delivers:

- Enhanced business performance
- Faster, more reliable services for farmers
- Secure, role-based data management
- Intelligent automation via Apex & Flows

> *This solution marks a significant step toward sustainable and smart farming — bridging the gap between traditional agriculture and modern cloud technology.*

---

<div align="center">

**Built with ❤️ using Salesforce | AgriEdge Or-Mange Ltd**

![Salesforce](https://img.shields.io/badge/Salesforce-00A1E0?style=for-the-badge&logo=salesforce&logoColor=white)
![Apex](https://img.shields.io/badge/Apex-1797C0?style=for-the-badge&logo=salesforce&logoColor=white)
![Lightning](https://img.shields.io/badge/Lightning-032D60?style=for-the-badge&logo=salesforce&logoColor=white)

</div>
