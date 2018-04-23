# json_java_transactions
A java application to handle JSON transactions

Notes:
* Java application that can be run from the command line.
* The application store data on the file "Transactions.json" located under the folder where the application is.

Methods:
1) ADD TRANSACTION
  ./application <user_id> add <transaction_json>
Example to run from the command line:
  java -jar <path_to_jar>/Transactions.jar 345 add "{ \"amount\": 1.23, \"description\": \"Joes Tacos\", \"date\":\"2018-12-30\", \"user_id\": 345 }"

2) SHOW TRANSACTION
  ./application <user_id> <transaction_id>
Example to run from the command line:
  java -jar <path_to_jar>/Transactions.jar 345 824d1b22-ae33-43bd-bab4-3c1aa39da2b2

3) LIST TRANSACTIONS
  ./application <user_id> list
Example to run from the command line:
  java -jar <path_to_jar>/Transactions.jar 345 list
  
4) SUM TRANSACTIONS
  ./application <user_id> sum
Example to run from the command line:
  java -jar <path_to_jar>/Transactions.jar 345 sum

Alternatively add the following parameter to the java command to custom the logging:
-Djava.util.logging.config.file=<path_to_properties>/logging.properties 
