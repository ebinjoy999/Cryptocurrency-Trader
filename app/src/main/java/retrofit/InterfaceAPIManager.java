package retrofit;

/**
 * Created by macadmin on 10/24/17.
 */
public interface InterfaceAPIManager<ClassResponse> {
    void onAPILoadSuccess(String REQUEST_TYPE, String message, ClassResponse jsonResult);
    void onAPILoadFailed(String REQUEST_TYPE, String message, ClassResponse jsonResult);
}
