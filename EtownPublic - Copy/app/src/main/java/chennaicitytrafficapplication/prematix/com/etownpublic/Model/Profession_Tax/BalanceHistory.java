package chennaicitytrafficapplication.prematix.com.etownpublic.model.Profession_Tax;

public class BalanceHistory {
    String District,Panchayat,TaxNo,FinanicalYear,HalfYear,Balance,Tax_Type;

    public BalanceHistory() {
    }

    public BalanceHistory(String district, String panchayat, String taxNo, String finanicalYear, String halfYear, String balance, String tax_Type) {
        District = district;
        Panchayat = panchayat;
        TaxNo = taxNo;
        FinanicalYear = finanicalYear;
        HalfYear = halfYear;
        Balance = balance;
        Tax_Type = tax_Type;
    }

    public String getTax_Type() {
        return Tax_Type;
    }

    public void setTax_Type(String tax_Type) {
        Tax_Type = tax_Type;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getPanchayat() {
        return Panchayat;
    }

    public void setPanchayat(String panchayat) {
        Panchayat = panchayat;
    }

    public String getTaxNo() {
        return TaxNo;
    }

    public void setTaxNo(String taxNo) {
        TaxNo = taxNo;
    }

    public String getFinanicalYear() {
        return FinanicalYear;
    }

    public void setFinanicalYear(String finanicalYear) {
        FinanicalYear = finanicalYear;
    }

    public String getHalfYear() {
        return HalfYear;
    }

    public void setHalfYear(String halfYear) {
        HalfYear = halfYear;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}
