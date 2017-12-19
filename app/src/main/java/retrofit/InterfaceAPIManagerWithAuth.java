package retrofit;

/**
 * Created by macadmin on 10/24/17.
 */
public interface InterfaceAPIManagerWithAuth  extends InterfaceAPIManager{
    void onAPIAuthError(String REQUEST_TYPE, String message);
}