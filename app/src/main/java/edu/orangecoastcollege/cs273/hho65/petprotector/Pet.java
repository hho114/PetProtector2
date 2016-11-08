package edu.orangecoastcollege.cs273.hho65.petprotector;

import android.net.Uri;

/**
 * **
 * The <code>Pet</code> class maintains information about a pet,
 * including its id number, name, detail, image uri.
 *
 * Created by Huy Ho on 11/5/2016.
 */

public class Pet {

    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageURI;


    public Pet() {
    }

    public Pet(String mName, String mDetails, String mPhone) {
        this.mName = mName;
        this.mDetails = mDetails;
        this.mPhone = mPhone;
    }

    /**
     * Creates a new <code>Game</code> from its id, name, detail, phone number and image uri.
     * @param mId The Id
     * @param mName The pet name
     * @param mDetails The pet's detail
     * @param mImageURI The Image Uri
     * @param mPhone The phone number of owner
     */
    public Pet(int mId, String mName, String mDetails, String mPhone, Uri mImageURI) {
        this.mId = mId;
        this.mName = mName;
        this.mDetails = mDetails;
        this.mPhone = mPhone;
        this.mImageURI = mImageURI;
    }
    /**
     * Creates a new <code>Game</code> from its name, detail, phone number and image uri.
     * @param mName The pet name
     * @param mDetails The pet's detail
     * @param mImageURI The Image Uri
     * @param mPhone The phone number of owner
     */
    public Pet(String mName, String mDetails, String mPhone, Uri mImageURI) {
        this.mName = mName;
        this.mDetails = mDetails;
        this.mPhone = mPhone;
        this.mImageURI = mImageURI;
    }
    /**
     * Gets the detail of the <code>Game</code>.
     * @return The detail
     */
    public String getmDetails() {
        return mDetails;
    }
    /**
     * Gets the unique id of the <code>Pet</code>.
     * @return The unique id
     */
    public int getmId() {
        return mId;
    }
    /**
     * Gets the Image Uri of the <code>Pet</code>.
     * @return The image uri
     */
    public Uri getmImageURI() {
        return mImageURI;
    }
    /**
     * Gets the name of the <code>Pet</code>.
     * @return The pet's name
     */
    public String getmName() {
        return mName;
    }

    /**
     * Gets the phone number <code>Game</code>.
     * @return The phone number
     */
    public String getmPhone() {
        return mPhone;
    }

    /**
     * Sets the detail of the <code>Pet</code>.
     * @param mDetails The new detail
     */
    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }
    /**
     * Sets the Image Uri of the <code>pet</code>.
     * @param mImageURI The new Image Uri
     */
    public void setmImageURI(Uri mImageURI) {
        this.mImageURI = mImageURI;
    }
    /**
     * Sets the name of the <code>Pet</code>.
     * @param mName The new name
     */
    public void setmName(String mName) {
        this.mName = mName;
    }
    /**
     * Sets the phone number of the <code>Pet</code>.
     * @param mPhone The new phone number
     */
    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    /**
     * A method for displaying a <code>Pet</code> as a String in the form:
     *
     * @return The formatted String
     */
    @Override
    public String toString() {
        return "Pet{" +
                "Id=" + mId +
                ", Name='" + mName + '\'' +
                ", Detail='" + mDetails + '\'' +
                ", PhoneNumber=" + mPhone +
                ", ImageUri='" + mImageURI + '\'' +
                '}';
    }
}
