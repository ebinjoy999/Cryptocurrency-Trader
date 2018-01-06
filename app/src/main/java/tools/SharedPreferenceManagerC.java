package tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.google.gson.Gson;
import com.robotrader.ebinjoy999.robotrader.model.Symbol;

import java.util.List;

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
        List<Symbol> obj = gson.fromJson(json, List.class);
        return obj;
    }
}
