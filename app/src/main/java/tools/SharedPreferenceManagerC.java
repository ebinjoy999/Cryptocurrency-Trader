package tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.robotrader.ebinjoy999.robotrader.model.Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ebinjoy999 on 06/01/18.
 */

public class SharedPreferenceManagerC {

    private SharedPreferenceManagerC ourInstance ;

    public  SharedPreferenceManagerC getInstance(Context ct) {
        ourInstance = new SharedPreferenceManagerC(ct);
        return ourInstance;
    }


     SharedPreferences mPrefs;
     SharedPreferences.Editor prefsEditor;
    static String SHARED_PREF_NAME = "market";
    static String SYMBOLS = "symbol";
    static String TIME_STAMP = "TMS";
    public SharedPreferenceManagerC(Context mContext) {
       mPrefs  = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
       prefsEditor = mPrefs.edit();
    }



    public Boolean checkIsSymbolDetailsInLocalValid(){
        if(System.currentTimeMillis() - mPrefs.getLong(SYMBOLS+TIME_STAMP,0) < 86400000) return true; //1day
        return false;
    }


    public void saveKeyToSharedPreferencString(String key, String value){
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getKeyToSharedPreferencString(String key){
       return mPrefs.getString(key, "");
    }

    public  void saveSymbolDetailsSharedPref(Context mContext, List<Symbol> symbols){
        Gson gson = new Gson();
        String json = gson.toJson(symbols);
        prefsEditor.putString(SYMBOLS, json);
        prefsEditor.putLong(SYMBOLS+TIME_STAMP, System.currentTimeMillis());
        prefsEditor.commit();
    }
    public  List<Symbol> getSymbolsDetailsSharedPref(Context mContext){
        Gson gson = new Gson();
        String json = mPrefs.getString(SYMBOLS, "");
        if(json.equalsIgnoreCase("")){
            return null;
        }
        List<LinkedTreeMap<String, Object>> obj = gson.fromJson(json, List.class);
        return convertKeyValueToJSON(obj);
//        return obj;
    }

    public static List<Symbol> convertKeyValueToJSON(List<LinkedTreeMap<String, Object>>  ltmL) {
        List<Symbol> symbols = new ArrayList<>();
        for(LinkedTreeMap<String, Object> ltm : ltmL){
            Object[] objs = ltm.entrySet().toArray();
            Symbol symbol = new Symbol();
            for (int l=0;l<objs.length;l++)
            {
                Map.Entry o= (Map.Entry) objs[l];
                    if (o !=null) {
                        if(o.getKey().toString().equalsIgnoreCase("pair"))
                            symbol.setPair( o.getValue().toString());
                        else if(o.getKey().toString().equalsIgnoreCase("price_precision"))
                            symbol.setPricePrecision((Double) o.getValue());
                        else if(o.getKey().toString().equalsIgnoreCase("initial_margin"))
                            symbol.setInitialMargin( o.getValue().toString());
                        else if(o.getKey().toString().equalsIgnoreCase("minimum_margin"))
                            symbol.setMinimumMargin( o.getValue().toString());
                        else if(o.getKey().toString().equalsIgnoreCase("maximum_order_size"))
                            symbol.setMaximumOrderSize( o.getValue().toString());
                        else if(o.getKey().toString().equalsIgnoreCase("minimum_order_size"))
                            symbol.setMinimumOrderSize( o.getValue().toString());
                    }
            }
            symbols.add(symbol);
        }
         return symbols;
    }



    //      LinkedTreeMap treeMap = milestone.getImageNames().size()>0? (LinkedTreeMap) milestone.getImageNames().get(0) : null;
//    JSONObject imageJSON =convertKeyValueToJSON(treeMap);
//    if(imageJSON!=null){
//      Iterator<String> iter = imageJSON.keys();
//      while (iter.hasNext()) {
//        String key = iter.next();
//        try {
//      for(ImageName imageName : milestone.getImageNames())
//            milestoneImageDataList.add(new MilestoneImageData(null,imageName.getName(),imageName.getCode(),null,false));
//        } catch (JSONException e) {
//          // Something went wrong!
//        }
//      }
//      }
}
