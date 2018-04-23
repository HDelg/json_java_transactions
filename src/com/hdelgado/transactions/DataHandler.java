/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdelgado.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HDelgado
 */
public class DataHandler implements StorageHandler {
    
    /**
     * Write transaction to storage
     * @param userID
     * @param transDef
     * @return String "Success" or "Fail"
     */
    @Override
    public String writeTransaction(Integer userID,TransDef transDef){
        File file = null;
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        String result=Constants.OPERATION_FAIL;
        ObjectMapper objectMapper = Mapper.getInstance();
        ObjectWriter objectWriter = objectMapper.writerFor(TransDef.class);
        
        try {
            file = new File(String.format(Constants.FILE_TRANSACTIONS,userID));
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File:"+file);
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File Exists?:"+file.exists());
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File is directory?:"+file.isDirectory());
            
            fileOutputStream = new FileOutputStream(file, true);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            
            bufferedWriter.write(objectWriter.writeValueAsString(transDef));
            bufferedWriter.newLine();
            
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        result=Constants.OPERATION_SUCCESS;
        return result;
    }
    
    /**
     * Retrieve a list of transactions searching by User ID
     * @param userID
     * @return a list of TransDef with the transactions found
     */
    @Override
    public ArrayList<TransDef> retrieveTransactionsFromUserID(Integer userID){
        File file = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String line=null;
        ArrayList<TransDef> result = null;
        TransDef transDef = null;
        ObjectMapper objectMapper = Mapper.getInstance();
        ObjectReader objectReader = objectMapper.readerFor(TransDef.class);
        
        try{
            file = new File(String.format(Constants.FILE_TRANSACTIONS,userID));
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File:"+file);
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File Exists?:"+file.exists());
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File is directory?:"+file.isDirectory());
            
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            
            result = new ArrayList<TransDef>();
            
            while((line=bufferedReader.readLine())!=null){
                transDef = objectReader.readValue(line);
                if(transDef.getUserId().compareTo(userID)==0){
                    result.add(transDef);
                }
            }
            //sort by date in descending order
            Collections.sort(result, Collections.reverseOrder(new TransDefComparator()));
            
        } catch (FileNotFoundException ex) { 
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Logger.getLogger(DataHandler.class.getName()).log(Level.FINE, "Retrieved {0} elements", result.size());
        return result;
    }
    
    /**
     * Search for the transaction specified through the arguments
     * @param userID
     * @param transactionID
     * @return a TransDef with the transaction (null if not found)
     */
    @Override
    public TransDef searchTransaction(Integer userID, String transactionID){
        File file = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String line=null;
        TransDef transDef, result = null;
        ObjectMapper objectMapper = Mapper.getInstance();
        ObjectReader objectReader = objectMapper.readerFor(TransDef.class);
        
        try{
            file = new File(String.format(Constants.FILE_TRANSACTIONS,userID));
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File:"+file);
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File Exists?:"+file.exists());
            Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "File is directory?:"+file.isDirectory());
            
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            
            while(result==null && (line=bufferedReader.readLine())!=null){
                transDef = objectReader.readValue(line);
                if(transDef.getUserId().compareTo(userID)==0 
                        && transDef.getTransactionId().compareTo(transactionID)==0){
                    result=transDef;
                    Logger.getLogger(DataHandler.class.getName()).log(Level.FINEST, "Found transaction!");
                }
            }
        } catch (FileNotFoundException ex) { 
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
}
