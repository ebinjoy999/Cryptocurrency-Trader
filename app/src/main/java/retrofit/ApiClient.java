package retrofit;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.robotrader.ebinjoy999.robotrader.CustomSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rmedls177lt on 2/9/16.
 */

public class ApiClient {

    private static final String TAG = "API_AUTH" ;
    //Change login API also volley
    private static Retrofit retrofit = null;

    public static void destroInstance(){ retrofit = null; }

    public static Retrofit getClient(final Context ct, boolean needAuth) {

        // Adding header for all request
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                  // if the request is login then no header needed
                if (original.url().encodedPath().equalsIgnoreCase("sessions"))
                    return chain.proceed(original);

//
//                 final String ALGORITHM_HMACSHA384 = "HmacSHA384";
//                 String apiKey = "";
//                 String apiKeySecret = "";
//                 long nonce = System.currentTimeMillis();
//                  String urlPath = "/v1/balances";
//
//                JSONObject jo = new JSONObject();
//                try {
//                    jo.put("request", urlPath);
//                    jo.put("nonce", Long.toString(nonce));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                // API v1
//                String payload = jo.toString();
//
//                // this is usage for Base64 Implementation in Android. For pure java you can use java.util.Base64.Encoder
//                // Base64.NO_WRAP: Base64-string have to be as one line string
//                String payload_base64 = Base64.encodeToString(payload.getBytes(), Base64.NO_WRAP);
//                String payload_sha384hmac = hmacDigest(payload_base64, apiKeySecret, ALGORITHM_HMACSHA384);



                 String apiKey = "kBZAnQY7cTDc1QuGtx7xmt0AFWoXsKhVQzV7HoZ5l2e";
                 String apiKeySecret = "046uT0oKewxgorJoRXLB3iSxYze1BMsXitdhzl9YS5W";
                 long nonce = System.currentTimeMillis();
                  String urlPath = "/v2/auth/r/wallets";

               String signature = "/api/" + urlPath + nonce + body

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("X-BFX-APIKEY", apiKey)
                        .header("X-BFX-PAYLOAD", payload_base64)
                        .header("X-BFX-SIGNATURE", payload_sha384hmac)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });



        if (retrofit==null) {
            CustomSharedPreference mUserDetails = CustomSharedPreference.getInstance(ct);
             final String BASE_URL = mUserDetails.getBaseUrl();
            Retrofit.Builder build = new Retrofit.Builder();
                    build.baseUrl(BASE_URL);
                    if(needAuth) build.client(httpClient.build());
                    build.addConverterFactory(GsonConverterFactory.create());
                    retrofit = build.build();
        }
        return retrofit;
    }


    public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return digest;
    }
}