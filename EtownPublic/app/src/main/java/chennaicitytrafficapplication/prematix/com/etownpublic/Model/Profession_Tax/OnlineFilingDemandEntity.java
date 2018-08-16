package chennaicitytrafficapplication.prematix.com.etownpublic.Model.Profession_Tax;

public class OnlineFilingDemandEntity {
    private String assessmentNo,assessmentName,doorNo,wardNo,tradeName,financialYear;
    private int demand,sno;


    public OnlineFilingDemandEntity(String assessmentNo, String assessmentName, String doorNo, String wardNo, String tradeName,
                                    String financialYear, int demand, int sno) {
        this.assessmentNo = assessmentNo;
        this.assessmentName = assessmentName;
        this.doorNo = doorNo;
        this.wardNo = wardNo;
        this.tradeName = tradeName;
        this.financialYear = financialYear;
        this.demand = demand;
        this.sno = sno;
    }

    public String getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(String assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }
}
