/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdelgado.transactions;

import java.util.ArrayList;

/**
 *
 * @author HDelgado
 */
public interface StorageHandler {
    /**
     * Write transaction to storage
     * @param userID
     * @param transDef
     * @return String "Success" or "Fail"
     */
    public String writeTransaction(Integer userID,TransDef transDef);
    
    /**
     * Retrieve a list of transactions searching by User ID
     * @param userID
     * @return a list of TransDef with the transactions found
     */
    public ArrayList<TransDef> retrieveTransactionsFromUserID(Integer userID);
    
    /**
     * Search for the transaction specified through the arguments
     * @param userID
     * @param transactionID
     * @return a TransDef with the transaction (null if not found)
     */
    public TransDef searchTransaction(Integer userID, String transactionID);
    
}
