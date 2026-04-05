public with sharing class OrderTaskCreator {

    @InvocableMethod

    public static void createTaskForNewOrder(List<Id> orderIds) {

        if (orderIds == null || orderIds.isEmpty()) {

            return;

        }




        // Fetch users with Profile Name 'Platform 1'

        List<User> platformUsers = [SELECT Id FROM User WHERE Profile.Name = 'Platform 1' LIMIT 10];




        // If no users found, exit method gracefully

        if (platformUsers.isEmpty()) {

            System.debug('No users found with Platform 1 profile.');

            return;

        }




        List<Task> tasks = new List<Task>();




        for (Id orderId : orderIds) {

            for (User user : platformUsers) {

                Task newTask = new Task(

                    Subject = 'New Order Created',

                    Description = 'A new order has been created. Please create an Order Item record.',

                    WhatId = orderId,

                    OwnerId = user.Id,

                    Status = 'Not Started',

                    Priority = 'High'

                );

                tasks.add(newTask);

            }

        }




        if (!tasks.isEmpty()) {

            insert tasks;

        }

    }

}