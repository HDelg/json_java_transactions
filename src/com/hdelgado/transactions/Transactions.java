/*
 * This application will take various input parameters and store data about transactions
 * The application should process the parameters and then exit.
 * 
 */
package com.hdelgado.transactions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HDelgado
 */
public class Transactions {    
    static BusinessHandler businessHandler = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String result;
        Integer userID = 0;
        
        try{
            if(args.length<2){
                
                Logger.getLogger(Transactions.class.getName()).log(Level.WARNING, "Class called without enough arguments.");
     
                System.out.println("Arguments required. Please see the options below.");
                System.out.println("ADD TRANSACTION:   ./Transactions <user_id> add <transaction_json>");
                System.out.println("SHOW TRANSACTION:  ./Transactions <user_id> <transaction_id>");
                System.out.println("LIST TRANSACTIONS: ./Transactions <user_id> list");
                System.out.println("SUM TRANSACTIONS:  ./Transactions <user_id> sum");
            }
            else{
                userID = Integer.parseInt(args[0]);
                switch(args[1]){
                    case "add": 
                        result = addTransaction(userID, args[2]);
                        break;
                    case "list":
                        result = listTransactions(userID);
                        break;
                    case "sum":
                        result = sumTransactions(userID);
                        break;
                    default:
                        result = showTransaction(userID, args[1]);
                }
            }
        }catch(NumberFormatException ex){
            System.out.println("Error. userID must be a numerical value.");
            Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
            System.out.println("Error while reading arguments.");
            Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
    
    /*
    * ADD TRANSACTION
    * ./Transactions <user_id> add <transaction_json>
    */
    private static String addTransaction(Integer userID, String transactionJSON){
        String result = Constants.OPERATION_FAIL;
        Logger.getLogger(Transactions.class.getName()).log(Level.FINEST, "Entering addTransaction with arguments: [user_id:{0}] and [transaction_json: {1}]", new Object[]{userID, transactionJSON});
     
        //override for testing
        //transactionJSON = "{ \"transaction_id\": \"2299ce24-9eaf-417f-82d6-e57f93777dc4\", \"amount\": 1.23, \"description\": \"Joes Tacos\", \"date\":\"2018-12-30\", \"user_id\": 345 }";
        //transactionJSON = "{ \"amount\": 1.23, \"description\": \"Joes Tacos\", \"date\":\"2018-12-30\", \"user_id\": 345 }";
        
        businessHandler = new BusinessHandler();
        result = businessHandler.saveTransaction(userID,transactionJSON);
        
        Logger.getLogger(Transactions.class.getName()).log(Level.FINE, "Result operation addTransaction for [userID:{0}] and [transactionJSON {1}]: [{2}] ", new Object[]{userID, transactionJSON, result});

        if(result.compareTo(Constants.OPERATION_SUCCESS)==0){
            System.out.println("Transaction saved successfully");
        }else{
            System.out.println("Transaction rejected");
        }
        return result;
    }
    
    
    /*
    * SHOW TRANSACTION
    * ./Transactions <user_id> <transaction_id>
    */
    private static String showTransaction(Integer userID, String transactionID){
        String result = null;
        
        Logger.getLogger(Transactions.class.getName()).log(Level.FINEST, "Entering showTransaction with arguments: [user_id:{0}] and [transaction_json: {1}]", new Object[]{userID, transactionID});
     
        businessHandler = new BusinessHandler();
        result = businessHandler.getSpecificTransaction(userID, transactionID);
        
        Logger.getLogger(Transactions.class.getName()).log(Level.FINE, "Result operation showTransaction for [userID:{0}] and [transactionID {1}]: [{2}] ", new Object[]{userID, transactionID, result});
        
        if(result == null){
            System.out.println("Transaction not found");
        }else{
            System.out.println(result);
        }
        return Constants.OPERATION_SUCCESS;
    }
    
    /*
    * LIST TRANSACTIONS
    * ./Transactions <user_id> list
    */
    private static String listTransactions(Integer userID){
        String result = null;
        
        Logger.getLogger(Transactions.class.getName()).log(Level.FINEST, "Entering showTransaction with arguments: [user_id:{0}]", userID);
        
        businessHandler = new BusinessHandler();
        result = businessHandler.getTransactionsFromUserID(userID);
        
        Logger.getLogger(Transactions.class.getName()).log(Level.FINE, "Result operation listTransactions for [userID:{0}]: [{1}]", new Object[]{userID, result});
        
        System.out.println(result);
        
        return Constants.OPERATION_SUCCESS;
    }
    
    /*
    * SUM TRANSACTIONS
    * ./Transaction <user_id> sum
    */
    private static String sumTransactions(Integer userID){
        String result = null;
        Logger.getLogger(Transactions.class.getName()).log(Level.FINEST, "Entering sumTransaction with arguments: [user_id:{0}]", userID);
     
        businessHandler = new BusinessHandler();
        result = businessHandler.getSumFromUserID(userID);
        
        System.out.println(result);
        
        return Constants.OPERATION_SUCCESS;
    }

}
