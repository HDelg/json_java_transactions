/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdelgado.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;

/**
 * ObjectMapper Singleton
 * @author HDelgado
 */
public class Mapper {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }
    
    private Mapper(){
        
    }
    
    public static ObjectMapper getInstance() {
        return objectMapper;
    }
}
