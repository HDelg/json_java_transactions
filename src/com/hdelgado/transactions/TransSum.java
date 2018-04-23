/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdelgado.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author HDelgado
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "user_id",
    "sum"
})
public class TransSum {

    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("sum")
    private Double sum;

    @JsonCreator
    public TransSum(@JsonProperty("user_id") Integer userId, @JsonProperty("sum") Double sum) {
        this.userId = userId;
        this.sum = sum;
    }

    @JsonProperty("user_id")
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("sum")
    public Double getSum() {
        return sum;
    }

    @JsonProperty("sum")
    public void setSum(Double sum) {
        this.sum = sum;
    }

}
