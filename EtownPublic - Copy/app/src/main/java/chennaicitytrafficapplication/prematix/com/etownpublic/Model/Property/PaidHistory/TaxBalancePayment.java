package chennaicitytrafficapplication.prematix.com.etownpublic.model.property.PaidHistory;

public class TaxBalancePayment {


    public TaxBalancePayment() {
    }

    public String  FinancialYear,ReceiptDate,PaymentMode  ;

    public String  TaxNo,ReceiptNo ,TaxPaid ;

    public TaxBalancePayment(String financialYear, String receiptDate, String paymentMode, String taxNo, String receiptNo, String taxPaid) {
        FinancialYear = financialYear;
        ReceiptDate = receiptDate;
        PaymentMode = paymentMode;
        TaxNo = taxNo;
        ReceiptNo = receiptNo;
        TaxPaid = taxPaid;
    }

    public String getFinancialYear() {
        return FinancialYear;
    }

    public void setFinancialYear(String financialYear) {
        FinancialYear = financialYear;
    }

    public String getReceiptDate() {
        return ReceiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        ReceiptDate = receiptDate;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getTaxNo() {
        return TaxNo;
    }

    public void setTaxNo(String taxNo) {
        TaxNo = taxNo;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getTaxPaid() {
        return TaxPaid;
    }

    public void setTaxPaid(String taxPaid) {
        TaxPaid = taxPaid;
    }
}
