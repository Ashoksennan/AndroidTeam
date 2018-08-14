package chennaicitytrafficapplication.prematix.com.etownpublic.model.property.PaidHistory.AssessmentSearch;

public class AssessmentSearch_Pojo{

    String taxNo,finYear;
    int halfYear, balance;

    public AssessmentSearch_Pojo(String taxNo,String finYear,int halfYear,int balance) {
        this.taxNo=taxNo;
        this.finYear=finYear;
        this.halfYear=halfYear;
        this.balance=balance;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public int getHalfYear() {
        return halfYear;
    }

    public void setHalfYear(int halfYear) {
        this.halfYear = halfYear;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
