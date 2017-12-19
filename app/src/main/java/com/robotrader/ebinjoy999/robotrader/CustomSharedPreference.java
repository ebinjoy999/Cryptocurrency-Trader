package com.robotrader.ebinjoy999.robotrader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mac on 23/03/17.
 */
public class CustomSharedPreference {
  private static CustomSharedPreference ourInstance ;
  private static SharedPreferences.Editor sharedEditor;

  public static CustomSharedPreference getInstance(Context ct) {
    if(ourInstance==null)ourInstance = new CustomSharedPreference(ct);
    return ourInstance;
  }


  public static void destroyInstance(){
    ourInstance = null;
  }

  private static String EMP_NAME = "";
  private  static String EMAIL = "";
  private  static String BASE_URL = "";

  public static void setEmpName(String empName) {
    EMP_NAME = empName;
    sharedEditor.putString(Keys.EMP_NAME,empName).commit();
  }

  public static void setEMAIL(String EMAIL) {
    CustomSharedPreference.EMAIL = EMAIL;
    sharedEditor.putString(Keys.EMAIL,EMAIL).commit();
  }

  public static String getEmpName() {
    return EMP_NAME;
  }

  public static String getEMAIL() {
    return EMAIL;
  }

  public static String getBaseUrl() {
    return BASE_URL;
  }

  private CustomSharedPreference(Context ct) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ct);
    sharedEditor = preferences.edit();
    EMP_NAME = preferences.getString(Keys.EMP_NAME, "");
    EMAIL = preferences.getString(Keys.EMAIL, "");
    BASE_URL = preferences.getString(Keys.BASE_URL, "https://api.bitfinex.com/v1");
  }


  interface Keys{
    String EMP_NAME = "employee_name";
    String EMAIL = "employee_name";
    String BASE_URL = "base";
  }
}
