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
//    ApiInterface apiServiceWithAuth;

public APIManager(TClass classCallBack, Context ct){
    if(classCallBack!=null) this.interfaceAPIManager = (InterfaceAPIManager) classCallBack;
    this.ct =  ct;
//    apiServiceWithAuth = ApiClient.getClient(ct, true).create(ApiInterface.class);
}

    public static final String REQUEST_GET_PLATFORM_STATUS = "REQUEST_GET_PLATFORM_STATUS";
    public static final String REQUEST_GET_SYMBOLS = "REQUEST_GET_SYMBOLS";
    public static final String REQUEST_GET_TICKERS = "REQUEST_GET_TICKERS";
    public static final String REQUEST_POST_WALLET = "REQUEST_POST_WALLET";

    public static final String REQUEST_ACTIVE_ORDERS = "REQUEST_ACTIVE_ORDERS";
    public static final String REQUEST_NEW_ORDER = "REQUEST_NEW_ORDER";
    public static final String REQUEST_CANCEL_ORDER = "REQUEST_CANCEL_ORDER";
    public static final String REQUEST_CANCEL_ALL_ORDERS = "REQUEST_CANCEL_ALL_ORDERS";

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
              apiService = ApiClient.getClient(ct, true).create(ApiInterface.class);
                return (Call<TClassResponse>) apiService.getSymbols();
            case REQUEST_GET_TICKERS:
                apiService = ApiClient.getClient(ct, true).create(ApiInterface.class);
                return  (Call<TClassResponse>) apiService.getTickers(params.get(KEY_REQUEST_QUERY_PARAMS).toString());
            case REQUEST_POST_WALLET:
                apiService = ApiClient.getClient(ct, true).create(ApiInterface.class);
                return  (Call<TClassResponse>) apiService.getWalletBalences();
            case REQUEST_ACTIVE_ORDERS:
                apiService = ApiClient.getClient(ct, true).create(ApiInterface.class);
                return  (Call<TClassResponse>) apiService.getActiveOrders();
            case REQUEST_CANCEL_ORDER:
                apiService = ApiClient.getClient(ct, true).create(ApiInterface.class);
                return  (Call<TClassResponse>) apiService.postCancelOrder();
            case REQUEST_CANCEL_ALL_ORDERS:
                apiService = ApiClient.getClient(ct, true).create(ApiInterface.class);
                return  (Call<TClassResponse>) apiService.postCancelAllOrder();
            case REQUEST_NEW_ORDER:
                apiService = ApiClient.getClient(ct, true).create(ApiInterface.class);
                return  (Call<TClassResponse>) apiService.postNewOrder();
        }
        return null;
    }

}



