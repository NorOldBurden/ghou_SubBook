/**
 *
 * MainActivity
 *
 * Created by Gefei on 2/1/2018.
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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CODE = 100;
    public static final int EDIT_CODE = 110;
    private ArrayList<Subscription> subList = new ArrayList<>();
    private ListView newListView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String[] stringList = {"first item", "second", "third", "forth"};
        //ListAdapter newAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        ListAdapter myAdapter = new AddSubAdapter(MainActivity.this ,R.layout.layout_subscription,subList);
        newListView = (ListView) findViewById(R.id.newListView);
        newListView.setAdapter(myAdapter);

        newListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditSubscription.class);
                intent.putExtra("editSub", new Gson().toJson(subList.get(position)));
                MainActivity.this.startActivityForResult(intent, MainActivity.EDIT_CODE);
            }
        });

        newListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        final Button addButton = (Button) findViewById(R.id.addButton);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                MainActivity.this.startActivityForResult(intent, MainActivity.ADD_CODE);
            }
        };
        addButton.setOnClickListener(listener);
        updateCharge();

    }

        protected void onActivityResult(int requestCode, int resultCode, Intent intentResult){
            if(resultCode == AddActivity.GOOD_RESULT){
                if(requestCode == MainActivity.ADD_CODE){
                    Subscription newSub = new Gson().fromJson(intentResult.getStringExtra("newSub"), Subscription.class);
                    subList.add(newSub);
                    updateCharge();

                    Log.d("MainActivity", "onclick add sublist");
                    ((AddSubAdapter)newListView.getAdapter()).notifyDataSetChanged();
                }
            }

            //if(resultCode == )

        }

        private void updateCharge(){
            double totalCharge = 0;
            TextView chargeNumber = (TextView) findViewById(R.id.totalMonthlyCharge);
            for(int i = 0; i < subList.size(); i++){
                totalCharge = totalCharge + subList.get(i).getCharge();
            }
            chargeNumber.setText("Total Charge:" + totalCharge + " CAD");
        }



}
