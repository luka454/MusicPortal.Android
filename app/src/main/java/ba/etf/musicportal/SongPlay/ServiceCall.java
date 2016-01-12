package ba.etf.musicportal.SongPlay;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Masina on 1/10/2016.
 */
public interface ServiceCall {
    @GET("/api/Song/search")
    Call<List<Song>> searchSongs(@Header("Authorization") String authorization, @Query("query") String query);

    @POST("/api/Song/{id}/heart")
    Call<String> heartSong(@Header("Authorization") String authorization, @Path("id") int id);

    @POST("/api/Song/{id}/unheart")
    Call<String> unheartSong(@Header("Authorization") String authorization, @Path("id") int id);
}
