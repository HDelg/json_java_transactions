/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdelgado.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 *
 * @author HDelgado
 */
public class TransDefs {
    
    @JsonProperty("transDefs")
    private ArrayList<TransDef> transDefs;

    /**
     * @return the transDefs
     */
    @JsonProperty("transDefs")
    public ArrayList<TransDef> getTransDefs() {
        return transDefs;
    }

    /**
     * @param transDefs the transDefs to set
     */
    @JsonProperty("transDefs")
    public void setTransDefs(ArrayList<TransDef> transDefs) {
        this.transDefs = transDefs;
    }
    
    @Override
    public String toString() {
        return "TransDefs{" + "transDefs=" + transDefs + '}';
    }
    
    
    
}
