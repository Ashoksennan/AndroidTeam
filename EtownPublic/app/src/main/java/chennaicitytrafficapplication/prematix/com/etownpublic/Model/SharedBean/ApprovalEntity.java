package chennaicitytrafficapplication.prematix.com.etownpublic.model.SharedBean;

public class ApprovalEntity {

    private String reqNo;
    private String reqDate;
    private String remarks;
    private String status;
    private String orderDate;
    private String approvalBy;

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(String approvalBy) {
        this.approvalBy = approvalBy;
    }

    public ApprovalEntity(String reqNo, String reqDate, String remarks, String status, String orderDate,
                          String approvalBy) {
        this.reqNo = reqNo;
        this.reqDate = reqDate;
        this.remarks = remarks;
        this.status = status;
        this.orderDate = orderDate;
        this.approvalBy = approvalBy;
    }
}
