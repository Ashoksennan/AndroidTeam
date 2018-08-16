package chennaicitytrafficapplication.prematix.com.etownpublic.Model.Profession_Tax;

public class TrackOnlineFilingEntity {
    private String reqNo;
    private String reqDate;
    private String district;
    private String panchayat;
    private String finYear;
    private String name;
    private String assessmentType;
    private String tradeName;
    private String orgCode;
    private String orgName;
    private String taxSNo;
    private String tradeOrgName;
    private String taxNo;
    private String status;
    private String mobileNo;
    private String emailId;
    private String date;

    public String getReqNo() {
        return reqNo;
    }

    public String getReqDate() {
        return reqDate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(String panchayat) {
        this.panchayat = panchayat;
    }

    public String getFinYear() {
        return finYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getTaxSNo() {
        return taxSNo;
    }

    public String getTradeOrgName() {
        return tradeOrgName;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TrackOnlineFilingEntity(String reqNo, String reqDate, String district, String panchayat, String finYear,
                                   String name, String assessmentType, String tradeName, String orgCode, String orgName,
                                   String taxSNo, String tradeOrgName, String taxNo, String status, String mobileNo,
                                   String emailId, String date) {

        this.reqNo = reqNo;
        this.reqDate = reqDate;
        this.district = district;
        this.panchayat = panchayat;
        this.finYear = finYear;
        this.name = name;
        this.assessmentType = assessmentType;
        this.tradeName = tradeName;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.taxSNo = taxSNo;
        this.tradeOrgName = tradeOrgName;
        this.taxNo = taxNo;
        this.status = status;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.date = date;
    }
}

