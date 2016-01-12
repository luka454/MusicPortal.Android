package ba.etf.musicportal;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ba.etf.musicportal.R;
import ba.etf.musicportal.SongPlay.SearchResultPlay;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button btn1 = (Button)findViewById(R.id.search_button);
        final EditText search = (EditText)findViewById(R.id.editText);
        final String token_auth;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if(extras == null)
            {
                token_auth = null;
            } else
            {
                token_auth = extras.getString("token_auth");
            }
        }
        else
        {
            token_auth = (String)savedInstanceState.getSerializable("token_auth");
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent res = new Intent(getApplicationContext(), SearchResultPlay.class);
                Bundle extras = new Bundle();
                extras.putString("search_word", search.getText().toString());
                extras.putString("token_auth", token_auth);
                res.putExtras(extras);
                startActivity(res);
            }
        });

    }
}
