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
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This main activity will be activated once the app is opened.
 * It will show the list of subscription and the total monthly charge.
 * There are three click listeners: Add button, click subscription to edit and long click for delete.
 * The last part is the input and output which will save and load the subscription list when the app
 * opens and closes.
 */

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CODE = 100;
    public static final int EDIT_CODE = 110;
    private ArrayList<Subscription> subList = new ArrayList<>();
    private ListView newListView;
    public static final String FILE_NAME = "SubscriptionList";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListAdapter myAdapter = new AddSubAdapter(MainActivity.this ,R.layout.layout_subscription,subList);
        newListView = (ListView) findViewById(R.id.newListView);
        newListView.setAdapter(myAdapter);

        /**
         * Click listener for the short click on subList
         */
        newListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditSubscription.class);
                intent.putExtra("editSub", new Gson().toJson(subList.get(position)));
                intent.putExtra("editSubIndex", position);
                MainActivity.this.startActivityForResult(intent, MainActivity.EDIT_CODE);
            }
        });

        /**
         * Click listener for the long click on subList
         */
        newListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                subList.remove(position);
                ((AddSubAdapter)newListView.getAdapter()).notifyDataSetChanged();
                updateCharge();
                Toast.makeText(MainActivity.this, "Subscription Deleted" ,
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        final Button addButton = (Button) findViewById(R.id.addButton);
        /**
         * Click listener for the ADD button
         */
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                MainActivity.this.startActivityForResult(intent, MainActivity.ADD_CODE);
            }
        };
        addButton.setOnClickListener(listener);
        updateCharge();
        loadSub();

    }

    /**
     * Get result for startActivityForResult in click listener like ADD button.
     * This will get the package from other activities then transform the package back
     * to a subscription instance.
     * @param requestCode
     * @param resultCode
     * @param intentResult
     */

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

            if(resultCode == EditSubscription.GOOD_EDIT_RESULT){
                if(requestCode == MainActivity.EDIT_CODE){
                    int index = intentResult.getIntExtra("indexOfSub", -1);
                    Subscription newSub = new Gson().fromJson(intentResult.getStringExtra("editSub"), Subscription.class);
                    subList.set(index, newSub);
                    updateCharge();

                    ((AddSubAdapter)newListView.getAdapter()).notifyDataSetChanged();
                }
            }

        }

    /**
     * This method will update the total monthly charge when an item changed in subList
     */
        private void updateCharge(){
            double totalCharge = 0;
            TextView chargeNumber = (TextView) findViewById(R.id.totalMonthlyCharge);
            for(int i = 0; i < subList.size(); i++){
                totalCharge = totalCharge + subList.get(i).getCharge();
            }
            chargeNumber.setText("Total Charge:" + totalCharge + " CAD");
        }

    /**
     * saveSub and loadSub are the methods related to IO.
     * They would save the subList in a file when the app stops and load it when app starts.
     */
        public void saveSub(){
            try{
                File file = new File(getFilesDir(), FILE_NAME);
                if(!file.exists()){
                    file.createNewFile();
                }
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
                outputStreamWriter.write(new Gson().toJson(subList));
                outputStreamWriter.close();
            }
            catch(IOException e){
                Log.e("subList", e.getMessage(), e);
            }
        }

        public void loadSub(){
            try{
                File file = new File(getFilesDir(), FILE_NAME);
                if(!file.exists()){
                    return;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while(line != null){
                    builder.append("\n" + line);
                    line =  reader.readLine();
                }
                reader.close();

                ArrayList<Subscription> subList = new Gson().fromJson(builder.toString(), new TypeToken<List<Subscription>>(){}.getType());
                this.subList.clear();
                this.subList.addAll(subList);
                ((AddSubAdapter)newListView.getAdapter()).notifyDataSetChanged();
                updateCharge();
            }
            catch(IOException e){

            }
            catch(Throwable t){

            }
        }

        @Override
        protected void onStart(){
            super.onStart();
        }

        @Override
        protected void onStop(){
            super.onStop();
            saveSub();
        }



}
