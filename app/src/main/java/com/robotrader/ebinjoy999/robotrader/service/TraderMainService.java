package com.robotrader.ebinjoy999.robotrader.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.robotrader.ebinjoy999.robotrader.R;

public class TraderMainService extends Service {
    final String TAG = "TraderMainService";

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    Message msg;

    CustomNotificationManager customNotificationManager;
    MarketTickerWatcher marketTickerWatcher;

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
        Log.e(TAG,"onCreate called");
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND );
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        customNotificationManager = new CustomNotificationManager(this);
        marketTickerWatcher = new MarketTickerWatcher(this);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
    return null; //Don't provide binding.
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand called");
        Toast.makeText(this, msg==null? "Initializing Robo..." : "Connecting to Robo...", Toast.LENGTH_SHORT).show();
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
         msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        customNotificationManager.showNotification(CustomNotificationManager.NOTIFICATION_ID_SERVICE_RUNNER);

//        return super.onStartCommand(intent, flags, startId);
    return START_STICKY;
      /**  If the system kills the service after onStartCommand() returns, recreate the service and call onStartCommand(),
            but do not redeliver the last intent. Instead, the system calls onStartCommand() with a null intent
        unless there are pending intents to start the service. In that case, those intents are delivered.
        This is suitable for media players (or similar services)
        that are not executing commands but are running indefinitely and waiting for a job.**/
    }




    @Override
    public void onDestroy() {
        super.onDestroy();




        Toast.makeText(this, "Disconnecting Robo...", Toast.LENGTH_SHORT).show();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(CustomNotificationManager.NOTIFICATION_ID_SERVICE_RUNNER);
        stopSelf();
        mServiceHandler = null;
        Log.e(TAG,"onDestroy called");
    }




    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.

            while (TraderMainService.this.mServiceHandler != null) {

                try {

                    if(marketTickerWatcher!=null  && !marketTickerWatcher.isRunningForLivePrice())
                        marketTickerWatcher.intializeSymbolDetailsAndGetLivePrice();

                    Thread.sleep(7000);
                    Log.e(TAG,"Running...");
                } catch (InterruptedException e) {
                    // Restore interrupt status.
                    Thread.currentThread().interrupt();
                }
            }



            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

}
