package com.ashok.etown2.model;

public class PaidHistory {
   private String receiptNo;
   private String receiptDate;
    private String finYear;
    private String payment;
    private String taxPaid;

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTaxPaid() {
        return taxPaid;
    }

    public void setTaxPaid(String taxPaid) {
        this.taxPaid = taxPaid;
    }

    public PaidHistory(String receiptNo, String receiptDate, String finYear, String payment, String taxPaid) {
        this.receiptNo = receiptNo;
        this.receiptDate = receiptDate;
        this.finYear = finYear;
        this.payment = payment;
        this.taxPaid = taxPaid;
    }
}
