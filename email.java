Source Code :

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