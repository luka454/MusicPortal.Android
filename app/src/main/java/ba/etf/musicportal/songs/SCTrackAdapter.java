package ba.etf.musicportal.songs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ba.etf.musicportal.R;

/**
 * Created by Masina on 1/1/2016.
 */
public class SCTrackAdapter extends BaseAdapter {

    private Context mContext;
    private List<Track> mTracks;

    public SCTrackAdapter(Context context, List<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public Track getItem(int position) {
        return mTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Track track = getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.track_list_row, parent, false);
            holder = new ViewHolder();
            holder.trackImageView = (ImageView) convertView.findViewById(R.id.track_image);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.track_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(track.getTitle());

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(mContext).load(track.getArtworkURL()).into(holder.trackImageView);

        final Button heartBtn = (Button)convertView.findViewById(R.id.heart_btn);
        //heartBtn.setBackgroundColor(255);

        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                //or some other task
                //API CALL FOR HEART AND UNHEART
                if (heartBtn.getText().equals("HEART"))
                    heartBtn.setText("UNHEART");
                else if (heartBtn.getText().equals("UNHEART"))
                    heartBtn.setText("HEART");
                else heartBtn.setText("ERROR");
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView trackImageView;
        TextView titleTextView;
    }

}
