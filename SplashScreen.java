package com.smartstudio.sajmovi.eu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.smartstudio.sajmovi.eu.function.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Boris on 5.6.2015..
 */
public class SplashScreen extends Activity {

    // Log tag
    private static final String TAG = SplashScreen.class.getSimpleName();

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        if (!isInternetconnected(getApplication())){
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Obavijest!");
                alertDialog.setMessage("Internet veza nije uspostavljena.");
                alertDialog.setButton("Izlaz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                });
                alertDialog.show();
            }
            catch(Exception e) { }
        }else{
            StartAnimations();
            //Preload data for home page
            String serverURL = "http://......./json.php";

            JsonArrayRequest jsonReq = new JsonArrayRequest(serverURL,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            final Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {

                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    if(i == 0) {
                                        intent.putExtra("box1_naslov", obj.getString("naslov"));
                                        intent.putExtra("box1_image", obj.getString("image"));
                                        intent.putExtra("box1_id", obj.getInt("id"));
                                    }else if(i == 1) {
                                        intent.putExtra("box2_naslov", obj.getString("naslov"));
                                        intent.putExtra("box2_image", obj.getString("image"));
                                        intent.putExtra("box2_id", obj.getInt("id"));
                                    }else if(i == 2) {
                                        intent.putExtra("box3_naslov", obj.getString("naslov"));
                                        intent.putExtra("box3_image", obj.getString("image"));
                                        intent.putExtra("box3_id", obj.getInt("id"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Thread timerThread = new Thread(){
                                public void run(){
                                    try{
                                        sleep(1900);
                                    }catch(InterruptedException e){
                                        e.printStackTrace();
                                    }finally{
                                        startActivityForResult(intent, 0);
                                        //finish();
                                    }
                                }
                            };
                            timerThread.start();
                            //
                            //
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);

    }

    private void startApp() {
        /*Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
        startActivity(intent);
        finish();// Finish this Splash Activity so the user can't return to it!*/

    }
    public boolean isInternetconnected(Context ct)
    {
        boolean connected = false;
        //get the connectivity manager object to identify the network state.
        ConnectivityManager connectivityManager = (ConnectivityManager)ct.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Check if the manager object is NULL, this check is required. to prevent crashes in few devices.
        if(connectivityManager != null)
        {
            //Check Mobile data or Wifi net is present
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            {
                //we are connected to a network
                connected = true;
            }
            else
            {
                connected = false;
            }
            return connected;
        }
        else
        {
            return false;
        }
    }

                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/
               /* JSONArray jsonMainNode = jsonResponse.optJSONArray("STORYARRAY");


                int lengthJsonArr = jsonMainNode.length();
                final Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                for (int i = 0; i < lengthJsonArr; i++) {

                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    if(i == 0) {
                        intent.putExtra("box1_naslov", jsonChildNode.getString("naslov"));
                        intent.putExtra("box1_image", jsonChildNode.getString("image"));
                        intent.putExtra("box1_id", jsonChildNode.getInt("id"));
                    }else if(i == 1) {
                        intent.putExtra("box2_naslov", jsonChildNode.getString("naslov"));
                        intent.putExtra("box2_image", jsonChildNode.getString("image"));
                        intent.putExtra("box2_id", jsonChildNode.getInt("id"));
                    }else if(i == 2) {
                        intent.putExtra("box3_naslov", jsonChildNode.getString("naslov"));
                        intent.putExtra("box3_image", jsonChildNode.getString("image"));
                        intent.putExtra("box3_id", jsonChildNode.getInt("id"));
                    }
                }*/

}