package chennaicitytrafficapplication.prematix.com.etownpublic.model.Water_Tax;

public class Water_Assessment_Search_Pojo {

    String Taxno, FinancialYear;
    int QuarterYear, Balance;

    public Water_Assessment_Search_Pojo() {
    }

    public Water_Assessment_Search_Pojo(String taxno, String financialYear, int quarterYear, int balance) {
        Taxno = taxno;
        FinancialYear = financialYear;
        QuarterYear = quarterYear;
        Balance = balance;
    }

    public String getTaxno() {
        return Taxno;
    }

    public void setTaxno(String taxno) {
        Taxno = taxno;
    }

    public String getFinancialYear() {
        return FinancialYear;
    }

    public void setFinancialYear(String financialYear) {
        FinancialYear = financialYear;
    }

    public int getQuarterYear() {
        return QuarterYear;
    }

    public void setQuarterYear(int quarterYear) {
        QuarterYear = quarterYear;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }
}