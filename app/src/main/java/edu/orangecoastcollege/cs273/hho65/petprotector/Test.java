package edu.orangecoastcollege.cs273.hho65.petprotector;

/**
 * Created by Huy Ho on 11/8/2016.
 */

public class Test {

    private String mName;
    private String mDetails;
    private String mPhone;


    public Test() {
    }

    public Test(String mName, String mDetails, String mPhone) {


        this.mName = mName;
        this.mDetails = mDetails;
        this.mPhone = mPhone;
    }




    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmName() {
        return mName;
    }

    public String getmDetails() {
        return mDetails;
    }

    public String getmPhone() {
        return mPhone;
    }
}
