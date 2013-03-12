package com.example.android.wvclock;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Util {

  protected static boolean _active = true;
  public static void loop_test( final int mSleepTime, final Handler mHandler) {

    Thread head_Thread = new Thread() {

      @Override
      public void run() {
        try {

          while (_active) {

            int waited = 0;
            while (waited < mSleepTime) {
              sleep(100);

              if (_active) {
                waited += 100;
              } else {
                waited = mSleepTime;
              }

              Bundle bundle = new Bundle();
              bundle.putLong("waited", waited);
              bundle.putString("Message", "in run");

              Log.i("wvclock", "bundle msg=1");

              Message msg = Message.obtain();
              msg.what = 1;
              msg.setData(bundle);

              mHandler.sendMessage(msg);
            }

          }
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          // e.printStackTrace();
        }

      } // run
    };  // Thread
    head_Thread.start();
  }

  public static void stop_thread() {
    _active = false;
  }
    
}
