package chennaicitytrafficapplication.prematix.com.etownpublic.Model;

public class Organisations {

    String orgName;
    int orgId;


    public Organisations(String orgName, int orgId) {
        this.orgName = orgName;
        this.orgId = orgId;
    }

    public String getorgName() {
        return orgName;
    }

    public void setorgName(String orgName) {
        this.orgName = orgName;
    }

    public int getorgId() {
        return orgId;
    }

    public void setorgId(int orgId) {
        this.orgId = orgId;
    }
}
