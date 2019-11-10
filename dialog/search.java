package com.smartstudio.sajmovi.eu.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.SearchResultsActivity;

/**
 * Created by Boris on 11.6.2015..
 */
public class search {
    private Button button;
    private TextView resultText;

    public search(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        //LayoutInflater layoutInflater = LayoutInflater.from(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View promptView = layoutInflater.inflate(R.layout.search_default, null);
        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Traži", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String searchedvalue = editText.getText().toString();
                        Bundle extras = new Bundle();
                        Intent SearchIntent = new Intent(activity, SearchResultsActivity.class);
                        extras.putString("searchvalue", searchedvalue);
                        SearchIntent.putExtras(extras);
                        activity.startActivityForResult(SearchIntent, 0);
                    }
                })
                .setNegativeButton("Odustani",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        // create an alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
    }
}
