package ba.etf.musicportal;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.POST;
import com.squareup.okhttp.Call;

/**
 * Created by luka454 on 17/11/2015.
 */
public class RetrofitFactory {

    private static String BASE_URL = "http://192.168.0.102:9000/";
    private static String BASE_URL_SECURE = "https://192.168.0.102:9000/";

    private RetrofitFactory() { }

    public static Retrofit create(){
        return create(false);
    }

    /**
     *
     * @param secure is connection secure or not
     * @description Use example
     * AuthService authService = RetrofitFactory.create(true).create(AuthService.class)
     * @return
     */
    public static Retrofit create(boolean secure){
        return new Retrofit.Builder().baseUrl(secure ? BASE_URL_SECURE : BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface AuthService {

        @POST("token")
        retrofit.Call<SessionManager.TokenModel> getToken(String username, String password);
    }
}
