package com.TrekOnTrek.android.wvclock;

import com.example.android.wvclock.R;

/* Define Android libraries */
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

  SharedPreferences      prefs;
  public static int      secs = 0;

  private static WebView mWebView;  

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);  /* use this layout */

    GetPhoneInfo();                          /* Call this method */

    prefs = getSharedPreferences( Globals.PREFS_FILE, 0 );
    ReadGlobalSettings();

    /*
     * Setup the WebView
     */
    mWebView = (WebView) findViewById(R.id.webView1);          /* Get the webview in the layout  */
    mWebView.getSettings().setJavaScriptEnabled(true);         /* Turn on JavaScript             */
    mWebView.loadUrl("file:///android_asset/www/index.html");  /* Load this page from the device */

    mWebView.addJavascriptInterface(new JavaScriptInterface(this), "wvclock");  /* Defie how to get instructions from JavaScript */
    mWebView.setWebViewClient(new WebViewClient());            /* Set the WebClient */

    mWebView.setWebChromeClient(new WebChromeClient() {        /* Use the Chrome client which is more customizable than the standard browser */

      public boolean onConsoleMessage(ConsoleMessage cm) {     /* Report messages from JavaScript in this format */
        Log.i("wvclock", cm.message() + " -- From line " + cm.lineNumber()
            + " of " + cm.sourceId());
        return true;
      }
    });
  }

  /*
   * Menu in Android.  This is the menu that will appear when you click on the device's menu button
   * 
   * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activity_main, menu);
    return true;
  }

/*
 * Dynamically add menu options.  You can test for some value and make a menu optionvisible
 * 
 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
 */
  
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {

    MenuItem item = menu.findItem(R.id.Debug);

    if (Globals.debug == "Y") {
      item.setVisible(true);      /* turn it on  */
    } else {
      item.setVisible(false);     /* turn it off */
    }

    return true;
  }

  /*
   * What to do when a menu option is clicked.
   *  
   * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.Show:
        Log.i("wvclock", "menu Show");
        Show();
        return true;

      case R.id.Help:
        Log.i("wvclock", "Open a new browser");
        
        /* Open a browser and point to page specified in Globals.URL */
        Intent browserIntentHelp = new Intent("android.intent.action.VIEW",
            Uri.parse(Globals.URL));
        startActivity(browserIntentHelp);
        return true;

      case R.id.Quit:
        Log.i("wvclock", "Quit application");
        SaveGlobalSettings();       /* Save Settings */
        finish();                   /* Quit          */
        return true;
    }
    return false;
  }

  /* Run something in javascript */
  public void Show() {                
    /* run a JavaScript function */
    mWebView.loadUrl("javascript:gotoPage('page_setup');");
  }

  /* Handle JavaScript communications */
  public class JavaScriptInterface {
    Context mContext;

    /** Instantiate the interface and set the context **/
    JavaScriptInterface(Context c) {
      mContext = c;

    }

    public void doInJAVA(String time_length, int check_every) {

      Log.i("wvclock", "doInJava:" + " Time Lengh:  " + time_length
          + " Check Every: " + check_every);

    }
  }

  /* Get information about the device */
  public void GetPhoneInfo() {
    Log.i("wvclock", "In GetPhoneInfo");
    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    Log.i("wvclock", "telephonyManager");
    Globals.mein = telephonyManager.getDeviceId();
    Log.i("wvclock", "mein");
    Globals.phone = telephonyManager.getLine1Number();
    Log.i("wvclock", "phone");
  }

  /* Save values from global variables locally */
  public void SaveGlobalSettings() {
    prefs.edit().putString("DEBUG", Globals.debug).commit();
  }

  /* Read locally saved information to Globals variables */
  public void ReadGlobalSettings() {
    PackageInfo packageInfo = null;

    Globals.debug = prefs.getString("DEBUG", "N"); /* defaults to N */
    if (Globals.debug == "Y") {
      Globals.PageParams = "debug=Y";
    }

    try {
      packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
    } catch (NameNotFoundException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    }

    Globals.AppVersion = packageInfo.versionName + ":"
        + String.valueOf(packageInfo.versionCode);         /* Contcatinate strings */

  }
}
