/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdelgado.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HDelgado
 */
public class BusinessHandler {
    
    
    /**
     * Constructor
     */
    public BusinessHandler(){
        
    }
    
    /**
     * Save transaction
     * @param userID
     * @param transactionJSON
     * @return String "Success" or "Fail"
     */
    public String saveTransaction(Integer userID,String transactionJSON){
        String result=Constants.OPERATION_FAIL;
        DataHandler dataHandler = new DataHandler();
        ObjectMapper objectMapper = Mapper.getInstance();
        ObjectReader objectReader = objectMapper.readerFor(TransDef.class);
        
        try {    
            TransDef transDef = objectReader.readValue(transactionJSON);
            //Generate transaction ID
            transDef.setTransactionId(UUID.randomUUID().toString());
            
            Logger.getLogger(BusinessHandler.class.getName()).log(Level.FINEST, "Succesfully mapped:{0}", transDef.toString());
            
            result = dataHandler.writeTransaction(userID, transDef);
            
        } catch (IOException ex) {
            Logger.getLogger(BusinessHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Get all the transactions searching by User ID
     * @param userID
     * @return String with the JSON formatted results
     */
    public String getTransactionsFromUserID(Integer userID){
        ArrayList<TransDef> transDefs = null;
        String result = null;
        DataHandler dataHandler = new DataHandler();
        ObjectMapper objectMapper = Mapper.getInstance();
        ObjectWriter objectWriter = objectMapper.writerFor(ArrayList.class);
        
        try{
            transDefs = dataHandler.retrieveTransactionsFromUserID(userID);
            result = objectWriter.writeValueAsString(transDefs);
            
        } catch (IOException ex) {
            Logger.getLogger(BusinessHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    /**
     * Get the sum (Amounts) of all the transactions associated to the USER_ID
     * @param userID
     * @return String with the JSON formatted result
     */
    public String getSumFromUserID(Integer userID){
        ArrayList<TransDef> transDefs = null;
        String result = null;
        Double sum = new Double(0);
        TransSum transSum = null;
        ObjectMapper objectMapper = Mapper.getInstance();
        ObjectWriter objectWriter = objectMapper.writerFor(TransSum.class);
        DataHandler dataHandler = new DataHandler();
        
        try{
            transDefs = dataHandler.retrieveTransactionsFromUserID(userID);
            
            for (TransDef i: transDefs) {
                sum+=i.getAmount();
            }
            
            transSum = new TransSum(userID, roundDouble(sum,2));
            result = objectWriter.writeValueAsString(transSum);
            
        } catch (IOException ex) {
            Logger.getLogger(BusinessHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    /**
     * Round the received value
     * @param value
     * @param places
     * @return the value rounded 
     */
    private double roundDouble(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    /**
     * Get the transaction specified in the arguments
     * @param userID
     * @param transactionID
     * @return String with the JSON formatted result
     */
    public String getSpecificTransaction(Integer userID, String transactionID){
        DataHandler dataHandler = new DataHandler();
        String result=null;
        TransDef transDef = null;
        ObjectMapper objectMapper = Mapper.getInstance();
        ObjectWriter objectWriter = objectMapper.writerFor(TransDef.class);
        
        try{
            transDef = dataHandler.searchTransaction(userID, transactionID);
            
            if(transDef!=null){
                result = objectWriter.writeValueAsString(transDef);
            }else{
                result = "Transaction not found";
            }
        } catch (IOException ex) {
            Logger.getLogger(BusinessHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
