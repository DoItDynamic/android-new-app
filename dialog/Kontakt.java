package com.smartstudio.sajmovi.eu.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;

import com.smartstudio.sajmovi.eu.R;

/**
 * Created by Sanja on 12.6.2015..
 */
public class Kontakt extends Activity {

    public Kontakt(Activity activity) {
        //About
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        //alertDialog2.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);

        // Setting Dialog Title
        alertDialog.setTitle("Kontakt");

        // Setting Dialog Message
        //alertDialog2.setMessage(Html.fromHtml(getResources().getString(R.string.agreement)));

        alertDialog.setMessage(Html.fromHtml(activity.getResources().getString(R.string.kontakt)));

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialogs = alertDialog.create();
        alertDialogs.getWindow().getAttributes().windowAnimations = R.style.AlertAnimation;
        alertDialogs.show();
    }


}