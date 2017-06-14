package nitimcorp.com.golfstrokesscoreboard.scorecard;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @file        Hole.java
 * @copyright   (C) 2017 NitimCorp, Inc
 * @brief
 */

public class Hole implements Parcelable {

    private String mHoleNumber;
    private int mStrokesTaken;

    public String getHoleNumber() {
        return mHoleNumber;
    }

    public void setHoleNumber(String holeNumber) {
        mHoleNumber = holeNumber;
    }

    public int getStrokesTaken() {
        return mStrokesTaken;
    }

    public void setStrokesTaken(int strokesTaken) {
        mStrokesTaken = strokesTaken;
    }

    public Hole() {

    }

    private Hole(Parcel in) {
        mHoleNumber = in.readString();
        mStrokesTaken = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mHoleNumber);
        dest.writeInt(mStrokesTaken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Classes implementing parcelable must also have a static field Creator.

    public static Creator<Hole> CREATOR = new Creator<Hole>() {
        @Override
        public Hole createFromParcel(Parcel source) {
            return new Hole(source);
        }

        @Override
        public Hole[] newArray(int size) {
            return new Hole[size];
        }
    };
}