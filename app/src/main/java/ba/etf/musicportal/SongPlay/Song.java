package ba.etf.musicportal.SongPlay;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Masina on 1/10/2016.
 */
public class Song {

    public int getID() {
        return mID;
    }

    public boolean isHearted() {
        return mHearted;
    }

    public void setHearted(boolean hea) {
        mHearted = hea;
    }
    public String getTitle() {
        return mTitle;
    }

    public String getStreamURL() {
        return mStreamURL;
    }

    @SerializedName("id")
    private int mID;

    @SerializedName("hearted")
    private boolean mHearted;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("link")
    private String mStreamURL;


}
