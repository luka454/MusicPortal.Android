package ba.etf.musicportal;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import ba.etf.musicportal.retrofit.AuthService;
import ba.etf.musicportal.retrofit.PipedCallback;
import ba.etf.musicportal.retrofit.RetrofitFactory;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by luka454 on 17/11/2015.
 */
public class SessionManager {


    public PipedCallback<TokenModel> login(String username, String password){
        //TODO: Call login and remember token
        AuthService authService = RetrofitFactory.create(false)
                                                    .create(AuthService.class);

        Call<SessionManager.TokenModel> call = authService.getToken("password", username, password);

        PipedCallback<TokenModel> pipedCallback = new PipedCallback<TokenModel>(){

            @Override
            public void onResponse(Response<TokenModel> response, Retrofit retrofit) {
                Log.d("AUTH", "onResponse");

                if(response.isSuccess()){
                    TokenModel tModel = response.body();
                    Log.d("RESP: SUC:", "Token: " + tModel.token);

                    currentUser = tModel;
                }
                else {
                    currentUser = null;
                }

                pipe(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.wtf("lolo", t.getMessage());
                pipe(t);
            }
        };

        call.enqueue(pipedCallback);


        return pipedCallback;
    }

    public boolean isLoggedIn(){
        //TODO: check token expiration;
        return currentUser != null;
    }

    public void logout() {
        //TODO: Call logout on server
        currentUser = null;
    }

    public String getCurrentUser(){
        if(currentUser == null)
            return null;
        return currentUser == null ? null : currentUser.username;
    }

    public String getCurrentToken(){
        if(currentUser == null)
            return null;
        return currentUser == null ? null : currentUser.token;
    }


    private static TokenModel currentUser = null;


    public class TokenModel {
        @SerializedName("access_token")
        String token;
        @SerializedName("userName")
        String username;

        //TODO: maybe to add token expiration;
        public TokenModel(String token, String username){
            this.token = token;
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
