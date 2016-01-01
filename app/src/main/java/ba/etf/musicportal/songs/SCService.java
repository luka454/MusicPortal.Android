package ba.etf.musicportal.songs;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Masina on 1/1/2016.
 */

public interface SCService
{
    @GET("/tracks?client_id=" + Config.CLIENT_ID)
    Call<List<Track>> getRecentTracks(@Query("created_at[from]") String date);
}
