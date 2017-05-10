package com.android.nmobile.equityassurance.dto;

import java.io.Serializable;

/**
 * @author Dev Rupesh Saxena
 */

public class ConfirmOderDetails implements Serializable {
    private String referenceId;
    private String product;
    private String duration;
    private String amount;
    private String paymentOption;

    public ConfirmOderDetails(String referenceId, String product, String duration, String amount, String paymentOption) {
        this.referenceId = referenceId;
        this.product = product;
        this.duration = duration;
        this.amount = amount;
        this.paymentOption = paymentOption;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }
}
