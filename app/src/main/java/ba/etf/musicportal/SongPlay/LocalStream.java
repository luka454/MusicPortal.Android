package ba.etf.musicportal.SongPlay;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Masina on 1/11/2016.
 */
public class LocalStream
{
    private static final Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private static final ServiceCall scService = retrofit.create(ServiceCall.class);

    public static ServiceCall getService() {
        return scService;
    }
}
