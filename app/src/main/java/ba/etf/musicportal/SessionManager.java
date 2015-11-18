package ba.etf.musicportal;

import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by luka454 on 17/11/2015.
 */
public class SessionManager {


    public PipedCallback<TokenModel> login(String username, String password){
        //TODO: Call login and remember token
        RetrofitFactory.AuthService authService = RetrofitFactory.create(true)
                                                    .create(RetrofitFactory.AuthService.class);

        Call<SessionManager.TokenModel> call = authService.getToken(username, password);

        PipedCallback<TokenModel> pipedCallback = new PipedCallback<TokenModel>(){

            @Override
            public void onResponse(Response<TokenModel> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    TokenModel tModel = response.body();
                    Toast.makeText(null, "Token: " + tModel.token, Toast.LENGTH_LONG);

                    currentUser = tModel;

                }
                else {
                    Toast.makeText(null, "HTTP not success. Code: "
                            + response.code()+ ". Message: " + response.message(), Toast.LENGTH_LONG);
                }

                pipe(response, retrofit);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(null, "Request failure: " + t.getMessage(), Toast.LENGTH_LONG);

                pipe(t);
            }
        };

        call.enqueue(pipedCallback);


        return pipedCallback;
    }

    public boolean isLoggedIn(){
        //TODO: check token expiration;
        return currentUser == null;
    }

    public void logout() {
        //TODO: Call logout on server
        currentUser = null;
    }

    public static String getCurrentUser(){
        if(currentUser == null)
            return null;
        return currentUser == null ? null : currentUser.username;
    }

    private static TokenModel currentUser = null;

    public class TokenModel {
        String token;
        String username;
        String email;
        //TODO: maybe to add token expiration;
        public TokenModel(String token, String username, String email){
            this.token = token;
            this.username = username;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
