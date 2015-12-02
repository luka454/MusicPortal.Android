package ba.etf.musicportal.retrofit;

import android.app.DownloadManager;
import android.content.pm.PackageInstaller;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import ba.etf.musicportal.SessionManager;

/**
 * Created by luka454 on 02/12/2015.
 */
public class AuthInterceptor implements Interceptor {

    private SessionManager sessionManager;

    public AuthInterceptor(SessionManager sessionManager){
        setSessionManager(sessionManager);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if(sessionManager != null && getSessionManager().isLoggedIn()){
            request.newBuilder().header("Authorization",
                    "Bearer " + getSessionManager().getCurrentToken());
        }

        return chain.proceed(request);
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
