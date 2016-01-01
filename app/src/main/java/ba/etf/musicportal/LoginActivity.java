package ba.etf.musicportal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ba.etf.musicportal.retrofit.PipedCallback;
import ba.etf.musicportal.retrofit.RetrofitFactory;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    SessionManager sessionManager;
    EditText mPasswordView;
    EditText mUsernameView;

    Button mSignInBtn;

    TextView mTextSuccess;

    EditText mEditBaseUrl;
    Button mBtnBaseUrl;

    EditText mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager();

        mPasswordView = (EditText) findViewById(R.id.password);
        mUsernameView = (EditText) findViewById(R.id.username);
        mSignInBtn = (Button) findViewById(R.id.sign_in_button);

        mSignInBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        mTextSuccess = (TextView) findViewById(R.id.text_success);

        mEditBaseUrl = (EditText) findViewById(R.id.edit_base_url);
        mEditBaseUrl.setText(RetrofitFactory.getBaseUrl());
        mBtnBaseUrl = (Button) findViewById(R.id.btn_base_url);

        mBtnBaseUrl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitFactory.setBaseUrl(mEditBaseUrl.getText().toString());
            }
        });

        mToken = (EditText) findViewById(R.id.textView);

        Button b = (Button) findViewById(R.id.show_auth_token);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAuthToken();
            }
        });
        showAuthToken();
    }

    private void showAuthToken(){
        if(sessionManager.isLoggedIn()){
            mToken.setText(sessionManager.getCurrentToken());
        } else {

            mToken.setText(getString(R.string.no_auth_token));
        }
    }


    public void attemptLogin() {

        boolean valid = true;
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        if(!isUsernameValid(mUsernameView.getText().toString())) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            valid = false;
        }

        if(!isPasswordValid(mPasswordView.getText().toString())){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            valid = false;
        }

        if(!valid)
            return;

        PipedCallback<SessionManager.TokenModel> loginCallback = sessionManager.login(mUsernameView.getText().toString(),
                mPasswordView.getText().toString());


        loginCallback.setPipedCallback(new Callback<SessionManager.TokenModel>() {
            @Override
            public void onResponse(Response<SessionManager.TokenModel> response, Retrofit retrofit) {
                final SessionManager.TokenModel tModel = response.body();

                final Response<SessionManager.TokenModel> cResponse = response;

                if (response.isSuccess()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextSuccess.setText("Token: " + tModel.token + " un: "
                                    + tModel.username);
                            mTextSuccess.setTextColor(Color.rgb(30,190,30));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextSuccess.setText("HTTP not success. Code: "
                                    + cResponse.code() + ". Message: " + cResponse.message());
                            mTextSuccess.setTextColor(Color.rgb(190, 30, 30));
                        }
                    });
                }
            }

            @Override
            public void onFailure(final Throwable t) {
                Handler h = new Handler(getMainLooper());

                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextSuccess.setText("Request failure: " + t.getMessage());
                        mTextSuccess.setTextColor(Color.rgb(255, 30, 30));
                    }
                });
            }
        });


    }

    //Helpers
    private boolean isUsernameValid(String username){
        return username.length() >= 4;
    }

    private boolean isPasswordValid(String password){
        return !password.isEmpty();
    }





}

