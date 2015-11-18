package ba.etf.musicportal;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by luka454 on 18/11/2015.
 */
public abstract class PipedCallback<T> implements Callback<T> {

    private Callback<T> pipedCallback;

    public PipedCallback(){
        setPipedCallback(null);
    }

    public void pipe(Response<T> response, Retrofit retrofit){
        if(getPipedCallback() != null)
            getPipedCallback().onResponse(response, retrofit);
    }

    public void pipe(Throwable t){
        if(getPipedCallback() != null)
            getPipedCallback().onFailure(t);
    }


    public Callback<T> getPipedCallback() {
        return pipedCallback;
    }

    public void setPipedCallback(Callback<T> pipedCallback) {
        this.pipedCallback = pipedCallback;
    }
}
