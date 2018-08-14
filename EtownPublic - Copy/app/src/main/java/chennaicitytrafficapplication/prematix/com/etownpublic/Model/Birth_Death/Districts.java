package chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death;

/**
 * Created by Admin on 8/3/2018.
 */

public class Districts {
    private int mDistrictId;
    private String mDistrictName;

    public Districts(int mDistrictId, String mDistrictName) {
        this.mDistrictId = mDistrictId;
        this.mDistrictName = mDistrictName;
    }

    public int getmDistrictId() {
        return mDistrictId;
    }

    public void setmDistrictId(int mDistrictId) {
        this.mDistrictId = mDistrictId;
    }

    public String getmDistrictName() {
        return mDistrictName;
    }

    public void setmDistrictName(String mDistrictName) {
        this.mDistrictName = mDistrictName;
    }
}
