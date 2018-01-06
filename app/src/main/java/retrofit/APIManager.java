package retrofit;

import android.content.Context;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
/**
 * Created by ebinjoy999 on 10/05/17.
 */

public class APIManager<TClassResponse,TClass>  {

    public static final String KEY_REQUEST_QUERY_PARAMS = "keyQuery_Params";
    InterfaceAPIManager interfaceAPIManager;
Context ct;
ApiInterface apiService;

public APIManager(TClass classCallBack, Context ct){
    if(classCallBack!=null) this.interfaceAPIManager = (InterfaceAPIManager) classCallBack;
    this.ct =  ct;
    apiService = ApiClient.getClient(ct).create(ApiInterface.class);
}

    public static final String REQUEST_GET_SYMBOLS = "REQUEST_GET_SYMBOLS";
    public static final String REQUEST_GET_TICKERS = "REQUEST_GET_TICKERS";
    public static final String REQUEST_GET_PLATFORM_STATUS = "REQUEST_GET_PLATFORM_STATUS";

    public  void getResponseAsJavaModel(final String REQUEST_TYPE, HashMap<String, Object> params){
        Call<TClassResponse> call = getDesiredAPIMethod(REQUEST_TYPE,params) ;
        if(call!=null)
            call.enqueue(new Callback<TClassResponse>() {
            @Override
            public void onResponse(Call<TClassResponse> call, retrofit2.Response<TClassResponse> response) {
                if ((interfaceAPIManager instanceof InterfaceAPIManagerWithAuth) && response.code()==401 )
                   ((InterfaceAPIManagerWithAuth) interfaceAPIManager).onAPIAuthError(REQUEST_TYPE,"");
                else
                    interfaceAPIManager.onAPILoadSuccess(REQUEST_TYPE,"",response.body());
            }
            @Override
            public void onFailure(Call<TClassResponse> call, Throwable t) {
                interfaceAPIManager.onAPILoadFailed(REQUEST_TYPE,"",null);
            }
        });
    }

    private Call<TClassResponse> getDesiredAPIMethod(String REQUEST_TYPE, HashMap<String, Object> params) {
        switch (REQUEST_TYPE){
//            case REQUEST_TYPE_CREATE_CHECKIN:
//                RequestBody bodyCheckin = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
//                        (params.get(KEY_REQUEST_BODY).toString()).toString());
//                return (Call<TClassResponse>) apiService.createCheckin(bodyCheckin);
          case REQUEST_GET_SYMBOLS:
                return (Call<TClassResponse>) apiService.getSymbols();
            case REQUEST_GET_TICKERS:
                return  (Call<TClassResponse>) apiService.getTickers(params.get(KEY_REQUEST_QUERY_PARAMS).toString());

        }
        return null;
    }

}



