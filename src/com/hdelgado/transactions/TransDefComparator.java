/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdelgado.transactions;

import java.util.Comparator;

/**
 *
 * @author HDelgado
 */
public class TransDefComparator implements Comparator<TransDef> {

    @Override
    public int compare(TransDef o1, TransDef o2) {
        return o1.getDate().compareTo(o2.getDate());
        /*
        if(o1.getDate().compareTo(o2.getDate())>0){
            return 1;
        } else {
            return -1;
        }*/
    }
    
}
