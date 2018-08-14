package chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death;

public class BirthAbstractWardWise_Pojo {

    int LiveBirth,StillBirth,Total;
    String RegYear;

    public BirthAbstractWardWise_Pojo(String regYear, int liveBirth, int stillBirth, int total) {
        RegYear = regYear;
        LiveBirth = liveBirth;
        StillBirth = stillBirth;
        Total = total;
    }


    public String getRegYear() {
        return RegYear;
    }

    public void setRegYear(String regYear) {
        RegYear = regYear;
    }

    public int getLiveBirth() {
        return LiveBirth;
    }

    public void setLiveBirth(int liveBirth) {
        LiveBirth = liveBirth;
    }

    public int getStillBirth() {
        return StillBirth;
    }

    public void setStillBirth(int stillBirth) {
        StillBirth = stillBirth;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
