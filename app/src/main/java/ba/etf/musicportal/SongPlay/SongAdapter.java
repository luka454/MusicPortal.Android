package ba.etf.musicportal.SongPlay;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;

import java.util.List;

import ba.etf.musicportal.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Masina on 1/11/2016.
 */
public class SongAdapter extends BaseAdapter {

    private Context mContext;
    private List<Song> mTracks;
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public SongAdapter(Context context, List<Song> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    void DrawButton(Song track, Button heartBtn)
    {
        int back = track.isHearted() ? Color.RED : Color.GRAY;
        int txt = track.isHearted() ? Color.WHITE : Color.WHITE;
        String str = track.isHearted() ? "UNHEART" : "HEART";
        heartBtn.setBackgroundColor(back);
        heartBtn.setTextColor(txt);
        heartBtn.setText(str);
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public Song getItem(int position) {
        return mTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Song track = getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.song_list_row, parent, false);
            holder = new ViewHolder();

            holder.titleTextView = (TextView) convertView.findViewById(R.id.track_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(track.getTitle());

        // Trigger the download of the URL asynchronously into the image view.
        //Picasso.with(mContext).load(track.getArtworkURL()).into(holder.trackImageView);
        final Button heartBtn = (Button)convertView.findViewById(R.id.heart_btn);
        DrawButton(track, heartBtn);

        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Call<String> call;

                if(track.isHearted())
                    call = LocalStream.getService().unheartSong("Bearer " + token, track.getID());
                else
                    call = LocalStream.getService().heartSong("Bearer " + token, track.getID());

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {

                        if (response.isSuccess()) {
                            track.setHearted(!track.isHearted());
                            DrawButton(track, heartBtn);
                        } else {
                            int statusCode = response.code();
                            // handle request errors yourself
                            ResponseBody errorBody = response.errorBody();
                            Log.d("HEART", "Error: " + response.toString());
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        // handle execution failures like no internet connectivity
                        Log.d("HEART", "Error failure: " + t.getMessage());
                    }
                });


            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
    }

}

