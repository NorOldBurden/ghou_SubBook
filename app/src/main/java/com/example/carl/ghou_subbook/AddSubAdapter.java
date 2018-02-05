/*
 * addSubAdapter
 *
 * Created by Gefei on 2/3/2018.
 *
 *
 *         DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 *  Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.example.carl.ghou_subbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter is used to connect listView to layout
 */

public class AddSubAdapter extends ArrayAdapter{

    public AddSubAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Subscription> objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Subscription subscription = (Subscription) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_subscription, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.subName);
        TextView tvDate = (TextView) convertView.findViewById(R.id.subDate);
        TextView tvCharge = (TextView) convertView.findViewById(R.id.subCharge);
        TextView tvComments = (TextView) convertView.findViewById(R.id.subComments);

        /**
         * The following part is to set specific view in the view group to
         * different contents like name, date, charge and comments.
         */

        tvName.setText(subscription.getName());
        tvDate.setText("Date: " + subscription.getDate());
        String chargeInText = Double.toString(subscription.getCharge());
        tvCharge.setText("Monthly Charge: " + chargeInText + " CAD");
        tvComments.setText("Comments: " + subscription.getComments());

        return convertView;
    }


}
