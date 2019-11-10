package com.smartstudio.sajmovi.eu.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.holders.DrawerItemsCalendar;

import java.util.List;

public class DrawerAdapterCalendar extends ArrayAdapter {

    Context context;
    List drawerItemList;
    int layoutResID;

    public DrawerAdapterCalendar(Context context, int layoutResourceID, List listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.draweritems2);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.draweritems_icon2);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }
        final DrawerItemsCalendar dItem = (DrawerItemsCalendar) this.drawerItemList.get(position);
        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                dItem.getImgResID()));
        drawerHolder.ItemName.setText(dItem.getItemName());
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        SharedPreferences settings = context.getSharedPreferences("datacountry", Context.MODE_PRIVATE);
        boolean Checked = settings.getBoolean(dItem.getKeyName(), false);
        checkBox.setChecked(Checked);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle your conditions here
                if (checkBox.isChecked()) {

                    SharedPreferences settings = context.getSharedPreferences("datacountry", Context.MODE_PRIVATE);
                    settings.edit().putBoolean(dItem.getKeyName(), true).apply();
                    //Toast.makeText(context, "You selected " + dItem.getItemName(), Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences settings = context.getSharedPreferences("datacountry", Context.MODE_PRIVATE);
                    settings.edit().putBoolean(dItem.getKeyName(), false).apply();
                    //Toast.makeText(context, "You deselected " + dItem.getItemName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
    }

}