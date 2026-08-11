package com.ascended.app;
import android.app.Activity;
import android.os.Bundle;
import android.os.Build;
import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity implements SensorEventListener {
 WebView web; SensorManager sm; Sensor step; float first=-1;
 public void onCreate(Bundle b){super.onCreate(b);web=new WebView(this);WebSettings s=web.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setAllowFileAccess(true);web.setWebViewClient(new WebViewClient());setContentView(web);web.loadUrl("file:///android_asset/index.html");if(Build.VERSION.SDK_INT>=29&&checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION)!=PackageManager.PERMISSION_GRANTED)requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION},7);sm=(SensorManager)getSystemService(SENSOR_SERVICE);step=sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);}
 protected void onResume(){super.onResume();if(step!=null)sm.registerListener(this,step,SensorManager.SENSOR_DELAY_NORMAL);}
 protected void onPause(){super.onPause();if(sm!=null)sm.unregisterListener(this);}
 public void onSensorChanged(SensorEvent e){if(e.sensor.getType()==Sensor.TYPE_STEP_COUNTER){if(first<0)first=e.values[0];final int n=Math.max(0,Math.round(e.values[0]-first));runOnUiThread(()->web.evaluateJavascript("window.nativeStepUpdate("+n+")",null));}}
 public void onAccuracyChanged(Sensor s,int a){}
}