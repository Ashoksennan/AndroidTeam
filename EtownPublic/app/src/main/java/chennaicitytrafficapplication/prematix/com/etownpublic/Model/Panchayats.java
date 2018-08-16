package chennaicitytrafficapplication.prematix.com.etownpublic.Model;

public class Panchayats {

    String panchayatName;
    int panchayatId;

    public Panchayats(String panchayatName, int panchayatId) {
        this.panchayatName = panchayatName;
        this.panchayatId = panchayatId;
    }

    public String getPanchayatName() {
        return panchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
    }

    public int getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(int panchayatId) {
        this.panchayatId = panchayatId;
    }
}
