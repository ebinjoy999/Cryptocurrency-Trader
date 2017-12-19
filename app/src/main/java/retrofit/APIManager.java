package retrofit;

import android.content.Context;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import tools.Configration;
import tools.STATIC_KEYS;

/**
 * Created by ebinjoy999 on 10/05/17.
 */

public class APIManager<TClassResponse,TClass> implements STATIC_KEYS, Configration {

InterfaceAPIManager interfaceAPIManager;
Context ct;
ApiInterface apiService;

public APIManager(TClass classCallBack, Context ct){
    this.interfaceAPIManager = (InterfaceAPIManager) classCallBack;
    this.ct =  ct;
    apiService = ApiClient.getClient(ct).create(ApiInterface.class);
}

    public static final String REQUEST_TYPE_ASSIGNED_EMPLOYEES = "REQUEST_TYPE_ASSIGNED_EMPLOYEES";
    public static final String REQUEST_TYPE_DEDIT_CHECKIN = "REQUEST_TYPE_DEDIT_CHECKIN";
    public static final String REQUEST_TYPE_CREATE_CHECKIN = "REQUEST_TYPE_CREATE_CHECKIN";
    public static final String REQUEST_TYPE_GET_CHECKIN_TEMPLATE = "REQUEST_TYPE_GET_CHECKIN_TEMPLATE";
    public static final String REQUEST_TYPE_MYCHECKIN = "REQUEST_TYPE_MYCHECKIN";
    public static final String REQUEST_TYPE_TEAMCHECKIN = "REQUEST_TYPE_TEAMCHECKIN";
    public static final String REQUEST_DRILL_DOWN = "REQUEST_DRILL_DOWN";

    public static final String REQUEST_MISSED_CHEKIN_SELF = "REQUEST_MISSED_CHEKIN_SELF";
    public static final String REQUEST_MISSED_CHEKIN_MANAGER = "REQUEST_MISSED_CHEKIN_MANAGER";
    public static final String REQUEST_MISSED_MILESTONE_SELF = "REQUEST_MISSED_MILESTONE_SELF";
    public static final String REQUEST_MISSED_MILESTONE_MANAGER = "REQUEST_MISSED_MILESTONE_MANAGER";

    public static final String REQUEST_TYPE_GET_CHECKIN_SUMMARY_COUNT = "REQUEST_TYPE_GET_CHECKIN_SUMMARY_COUNT";
    public static final String REQUEST_TYPE_GET_CHECKIN_SUMMARY_DETAIL = "REQUEST_TYPE_GET_CHECKIN_SUMMARY_DETAIL";

    public static final String KEY_PAGE_NUMBER = "KEY_PAGE_NUMBER";
    public static final String KEY_CHECKIN_LIST_FORMAT = "KEY_CHECKIN_LIST_FORMAT";
    public static final String KEY_SAERCH_KEY = "KEY_SAERCH_KEY";
    public static final String KEY_EMPLOYEE_ID = "KEY_EMPLOYEE_ID";
    public static final String KEY_REQUEST_BODY = "KEY_REQUEST_BODY";
    public static final String KEY_CHECKIN_TEMPLATE_ID = "KEY_CHECKIN_TEMPLATE_ID";
    public static final String KEY_DRILL_DOWN_CODE_NAME = "KEY_DRILL_DOWN_CODE_NAME";
    public static final String KEY_DRILL_DOWN_TYPE = "KEY_DRILL_DOWN_TYPE";

    public  void getResponseAsJavaModel(String REQUEST_TYPE,HashMap<String, Object> params){
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
            case REQUEST_TYPE_ASSIGNED_EMPLOYEES:
                return (Call<TClassResponse>) apiService.getAssignedEmployeesToCreateCheckin();
            case REQUEST_TYPE_DEDIT_CHECKIN:
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                        (params.get(KEY_REQUEST_BODY).toString()).toString());
                return (Call<TClassResponse>) apiService.editCheckin(body, params.get(KEY_CHECKIN_ID).toString());
            case REQUEST_TYPE_CREATE_CHECKIN:
                RequestBody bodyCheckin = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                        (params.get(KEY_REQUEST_BODY).toString()).toString());
                return (Call<TClassResponse>) apiService.createCheckin(bodyCheckin);
            case REQUEST_TYPE_GET_CHECKIN_TEMPLATE:
                return (Call<TClassResponse>) apiService.getCheckinTemplates(params.get(KEY_CHECKIN_TEMPLATE_ID).toString(), params.get(KEY_CHECKIN_ID).toString());
            case REQUEST_TYPE_GET_CHECKIN_SUMMARY_COUNT:
                return (Call<TClassResponse>) apiService.getCheckinReportCounts();
            case REQUEST_TYPE_GET_CHECKIN_SUMMARY_DETAIL:
                return (Call<TClassResponse>) apiService.getCheckinTemplates(params.get(KEY_CHECKIN_TEMPLATE_ID).toString(), params.get(KEY_CHECKIN_ID).toString());
            case REQUEST_TYPE_MYCHECKIN:
                return (Call <TClassResponse>) apiService.getEmployeeOrManagerCheckin((int) params.get(KEY_EMPLOYEE_ID),
                        PAGELIMIT, (int) params.get(KEY_PAGE_NUMBER),  params.get(KEY_CHECKIN_LIST_FORMAT).toString(),  params.get(KEY_SAERCH_KEY).toString());
            case REQUEST_TYPE_TEAMCHECKIN:
                // here KEY_EMPLOYEE_ID = manager_id
                return (Call <TClassResponse>) apiService.getManagerAssignedEmployeeCheckin((int) params.get(KEY_EMPLOYEE_ID),
                        PAGELIMIT, (int) params.get(KEY_PAGE_NUMBER),  params.get(KEY_CHECKIN_LIST_FORMAT).toString(),  params.get(KEY_SAERCH_KEY).toString());
            case REQUEST_DRILL_DOWN:
                return  (Call <TClassResponse>) apiService.getDrillDownResult(params.get(KEY_DRILL_DOWN_CODE_NAME).toString(),
                        params.get(KEY_DRILL_DOWN_TYPE).toString(), PAGELIMIT, (int) params.get(KEY_PAGE_NUMBER));
            case REQUEST_MISSED_CHEKIN_SELF:
                return  (Call <TClassResponse>) apiService.getMissedCheckin((int) params.get(KEY_EMPLOYEE_ID), PAGELIMIT, (int) params.get(KEY_PAGE_NUMBER));
            case REQUEST_MISSED_CHEKIN_MANAGER:
                return  (Call <TClassResponse>) apiService.getMissedCheckinManger((int) params.get(KEY_EMPLOYEE_ID), PAGELIMIT, (int) params.get(KEY_PAGE_NUMBER));
            case REQUEST_MISSED_MILESTONE_SELF:
                return  (Call <TClassResponse>) apiService.getMissedMilestone((int) params.get(KEY_EMPLOYEE_ID), PAGELIMIT, (int) params.get(KEY_PAGE_NUMBER));
            case REQUEST_MISSED_MILESTONE_MANAGER:
                return  (Call <TClassResponse>) apiService.getMissedMilestoneManager((int) params.get(KEY_EMPLOYEE_ID),
                        PAGELIMIT, (int) params.get(KEY_PAGE_NUMBER));
        }
        return null;
    }

}



