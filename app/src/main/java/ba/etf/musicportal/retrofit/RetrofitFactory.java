package ba.etf.musicportal.retrofit;

import android.preference.PreferenceActivity;
import android.util.Log;

import ba.etf.musicportal.SessionManager;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;


import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by luka454 on 17/11/2015.
 */
public class RetrofitFactory {

    private static String BASE_URL = "http://192.168.0.101:9000/";
    private static String BASE_URL_SECURE = "https://192.168.0.101:9000/";

    public static String getBaseUrl(){
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl){
        BASE_URL = baseUrl;
    }

    private RetrofitFactory() { }

    public static Retrofit create(){
        return create(false);
    }

    /**
     *
     * @param secure is connection secure or not
     * @description Use example
     * AuthService authService = RetrofitFactory.create(true).create(AuthService.class)
     * @return
     *
     */
    /*static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();

            Buffer buf = new Buffer();
            request.body().writeTo(buf);
            Log.e("O.o", String.format("Sending request %s on %s%n%s",
                    request.url(), request.headers(), buf.readString(Charset.defaultCharset())));

            Response response = chain.proceed(request);

            Response.Builder build = response.newBuilder();
            Response dResp = build.build();


            //Response dRes = new Response.Builder().body(response.body()).build();
            long t2 = System.nanoTime();
            //Log.e("O.o", String.format("Received response for %s in %.1fms%n%s, body: %s",
            //        response.request().url(), (t2 - t1) / 1e6d, response.headers(),
            //        dResp.body().string()));

            return response;
        }
    }*/

    public static Retrofit create(boolean secure){
        OkHttpClient client = new OkHttpClient();
        //Auth interceptor
        AuthInterceptor auth = new AuthInterceptor(new SessionManager());
        client.interceptors().add(auth);

        //Logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("OkHttp Log", message);
            }
        });

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.interceptors().add(logging);


        return new Retrofit.Builder().baseUrl(secure ? BASE_URL_SECURE : BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }



}
