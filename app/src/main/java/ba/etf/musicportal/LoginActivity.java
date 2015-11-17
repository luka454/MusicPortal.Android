package ba.etf.musicportal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {


    SessionManager sessionManager;
    EditText mPasswordView;
    EditText mUsernameView;

    Button mSignInBtn;

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

        SessionManager.TokenModel loginModel = sessionManager.login(mUsernameView.getText().toString(),
                mPasswordView.getText().toString());

        if(loginModel != null){
            Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_LONG);
            //finish();
        }
        else {
            Toast.makeText(null, "Login failed", Toast.LENGTH_LONG);
        }
    }

    //Helpers
    private boolean isUsernameValid(String username){
        return username.length() >= 4;
    }

    private boolean isPasswordValid(String password){
        return !password.isEmpty();
    }





}

