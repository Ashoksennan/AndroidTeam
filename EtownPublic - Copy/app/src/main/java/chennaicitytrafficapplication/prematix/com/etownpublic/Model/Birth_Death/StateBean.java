package chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death;

/**
 * Created by Admin on 8/6/2018.
 */

public class StateBean {
    private int mStatecode;
    private String mStateName;

    public StateBean(int mStatecode, String mStateName) {
        this.mStatecode = mStatecode;
        this.mStateName = mStateName;
    }

    public int getmStatecode() {
        return mStatecode;
    }

    public void setmStatecode(int mStatecode) {
        this.mStatecode = mStatecode;
    }

    public String getmStateName() {
        return mStateName;
    }

    public void setmStateName(String mStateName) {
        this.mStateName = mStateName;
    }
}
