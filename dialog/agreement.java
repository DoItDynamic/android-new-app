package com.smartstudio.sajmovi.eu.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.widget.Toast;

import com.smartstudio.sajmovi.eu.R;

/**
 * Created by Boris on 11.6.2015..
 */
public class agreement {

    public agreement(final Activity activity) {
        //UVJETI KORIŠTENJA
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(activity);
        //alertDialog2.setCanceledOnTouchOutside(false);
        alertDialog2.setCancelable(false);

        // Setting Dialog Title
        alertDialog2.setTitle("Uvjeti korištenja");

        // Setting Dialog Message
        alertDialog2.setMessage(Html.fromHtml(activity.getResources().getString(R.string.agreement)));

        // Setting Icon to Dialog
        alertDialog2.setIcon(R.mipmap.ic_launcher);

        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("Prihvaćam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences settings_agreement = activity.getSharedPreferences("data_agreement", Context.MODE_PRIVATE);
                        settings_agreement.edit().putBoolean("agreement", true).apply();
                        Toast.makeText(activity.getApplicationContext(),
                                "Dobrodošli u Sajmovi.eu", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("Ne prihvaćam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
        alertDialog2.show();
    }
}
