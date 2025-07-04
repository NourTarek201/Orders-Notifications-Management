# Orders-notifications-Management-System
## Requirements
The “Orders and Notifications Management” module provide the following features:

1. A list of all the products currently available for purchase should be displayed. Products are defined by a serial number, a name, a vendor, a category, and a price. Furthermore, a count of the remaining parts from each category should be available.
2. A customer should be able to create an account and put a specific balance using that account. Such balance would be utilized during future purchasing operations.
3. A customer can place a simple order, where such an order would include a single product or several products. Furthermore, a customer can make a compound order, where that order can include various orders headed to near-by locations, in addition to his own products, to lessen the shipping fees. For instance, a customer can make a compound order for himself and two of his friends, who all live in different locations in El-Dokki.
4. You should be able to list all the details of both simple and compound orders.
5. To place a compound order, the customer needs to pick his products, and his friends’ orders as well. Once the order is placed, the balance of each order is deducted from its corresponding customer.
6. After placing the order, the user can ship the order. For simple orders, when an order is shipped, its shipping fees are deducted from its customer’s account. For compound orders, when an order is shipped, its shipping fees are deducted from all the customers who participated in the compound order.
7. Notifications creation for various operations. The system manages those different notification "templates", and the languages these templates can be sent in, and of course the "placeholders" within the content of these templates to be replaced with "actual values".
ex: " Dear {x} , your booking of the {y} is confirmed. thanks for using our store :) " this would be the template, but when the system "sends" it to the user "Nabil" who bought the item "mobile charger" , the actual email would be
" Dear Nabil, your booking of the item mobile charger is confirmed, thanks for using our store :)"
So, the "management" of those notifications templates, their subjects, content, available languages, available channels ( should be: email, sms) , and placeholders would be the focus of this requirement. Your system should support at least two different notification templates for order
placement, and order shipment. Each of those templates should have different text, a different number of placeholders, where such templates types should be settled prior to launching the system.
8. For created notifications, you should implement a "notifications Queue", where you insert "notifications" that ARE TO BE SENT. You should be able to list the current content of that Queue.
Bonus part (Only the part highlighted in yellow is bonus):
9. Customers can cancel an order placement, or cancel only its shipping within a pre-configured automated duration. Such cancellation should update appropriately within the system.
10. After a configured time, messages are removed from the queue automatically to simulate that they were actually sent. Accordingly, the system should provide some live statistics to the overall software. The target of these statistics is to provide a vision about the notifications that are sent successfully:
• The most notified email-address/phone-number.
• The most sent notification template.
