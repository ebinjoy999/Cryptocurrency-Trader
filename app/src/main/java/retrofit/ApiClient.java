package retrofit;

import android.content.Context;

import com.robotrader.ebinjoy999.robotrader.CustomSharedPreference;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rmedls177lt on 2/9/16.
 */

public class ApiClient {

    //Change login API also volley
    private static Retrofit retrofit = null;

    public static void destroInstance(){ retrofit = null; }

    public static Retrofit getClient(Context ct) {

        // Adding header for all request
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//
//                  // if the request is login then no header needed
//                if (original.url().encodedPath().equalsIgnoreCase("sessions"))
//                    return chain.proceed(original);
//
//                Request request = original.newBuilder()
//                        .header("Authorization", "Token token="+CustomSharedPreference.getInstance(ct).getAUTH_TOKEN()+", email=\""+CustomSharedPreference.getInstance(ct).getEMAIL()+"\"")
//                        .header("Content-Type", "application/json")
//                        .method(original.method(), original.body())
//                        .build();
//
//                return chain.proceed(request);
//            }
//        });

        if (retrofit==null) {
            CustomSharedPreference mUserDetails = CustomSharedPreference.getInstance(ct);
             final String BASE_URL = mUserDetails.getBaseUrl();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}