package ba.etf.musicportal.songs;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Masina on 1/1/2016.
 */
public class SoundCloud
{
    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private static final SCService scService = retrofit.create(SCService.class);

    public static SCService getService() {
        return scService;
    }
}
