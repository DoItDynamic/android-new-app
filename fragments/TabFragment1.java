package com.smartstudio.sajmovi.eu.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.function.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Boris on 5.6.2015..
 */
public class TabFragment1 extends Fragment {

    public int CalendarId;
    public TextView naziv;
    public TextView event_start_date;
    public TextView event_end_date;
    public TextView opis_sajma;
    public TextView fulltext;
    private static final String TAG = TabFragment1.class.getSimpleName();
    private ProgressDialog pDialog;
    private static int RESULT_LOAD_IMAGE = 1;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay;
    public EditText newDateTextEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.kalendar_tab_fragment_1, container, false);

        Button dateChange = (Button) view.findViewById(R.id.date_change);
        naziv = (TextView) view.findViewById(R.id.naziv_sajma);
        event_start_date = (TextView) view.findViewById(R.id.event_start_date);
        event_end_date = (TextView) view.findViewById(R.id.event_end_date);
        opis_sajma = (TextView) view.findViewById(R.id.opis_sajma);
        fulltext = (TextView) view.findViewById(R.id.fulltext);
        newDateTextEdit=(EditText)view.findViewById(R.id.newDateTextEdit);
        Bundle bundle = getArguments();
        int CalendarId =  bundle.getInt("CalendarId");

        String serverURL = "http://..........php?id="+CalendarId+"&data=info";

        JsonObjectRequest jsonReq = new JsonObjectRequest(serverURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            naziv.setText(response.getString("naziv"));
                            opis_sajma.setText(response.getString("opis"));
                            event_start_date.setText(response.getString("event_start"));
                            event_end_date.setText(response.getString("event_end"));
                            fulltext.setText(Html.fromHtml(response.getString("fulltext")));
                            fulltext.setMovementMethod(LinkMovementMethod.getInstance());
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //hidePDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonReq);


        //onClick for date_change button
        dateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                newDateTextEdit.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
                Toast.makeText(getActivity(), "U clicked on date_change button",
                        Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}