package ba.etf.musicportal.retrofit;

import ba.etf.musicportal.SessionManager;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface AuthService {

    @FormUrlEncoded
    @POST("token")
    retrofit.Call<SessionManager.TokenModel> getToken(@Field("grant_type") String grantType,
                                                      @Field("username") String username,
                                                      @Field("password") String password);
}
