package com.robotrader.ebinjoy999.robotrader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TraderMainService extends Service {
    final String TAG = "TraderMainService";

  /**  If a component starts the service by calling startService() (which results in a call to onStartCommand()),
    the service continues to run until it stops itself with stopSelf() or another component stops
    it by calling stopService().

   your service is started, you must design it to gracefully handle restarts by the system.
   If the system kills your service, it restarts it as soon as resources become available,
   but this also depends on the value that you return from onStartCommand().
   **/

    public TraderMainService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onStart called");
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand called");



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy called");
    }
}
