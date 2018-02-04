/*
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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class EditSubscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        Intent intent = getIntent();
        Subscription editSub = new Gson().fromJson(intent.getStringExtra("editSub"), Subscription.class);

        final Button subButton = (Button) findViewById(R.id.editSubButton);

        View.OnClickListener listener = new View.OnClickListener(){
            public void onClick(View view) {

                if (canSubmit()) {

                } else {
                    Toast.makeText(EditSubscription.this, "Incorrect input format" ,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        subButton.setOnClickListener(listener);

    }

    public String getName(){
        EditText editText = findViewById(R.id.editName);
        return editText.getText().toString();
    }

    public String getDate(){
        EditText editText = findViewById(R.id.editDate);
        return editText.getText().toString();
    }

    public double getCharge(){
        EditText editText = findViewById(R.id.editCharge);
        String text = editText.getText().toString();
        if(text == null || text.isEmpty()){
            return -250;
        }
        double chargeInDouble = Double.parseDouble(text);
        return chargeInDouble;
    }

    public String getComments(){
        EditText editText = findViewById(R.id.editComments);
        return editText.getText().toString();
    }

    public boolean canSubmit(){
        String name = this.getName();
        String comments = this.getComments();
        double charge = this.getCharge();
        String date = this.getDate();
        if(name == null || name.isEmpty()){
            Log.d("AddActivity", "invalid name");
            return false;
        } else if (comments == null || comments.isEmpty()){
            Log.d("AddActivity", "null or empty comments");
            return false;
        } else if(charge == -250 || charge < 0){
            Log.d("AddActivity", "empty charge");
            return false;
        } else if(!isDateValid()){
            Log.d("AddActivity", "date not valid");
            return false;
        } else{
            Log.d("AddActivity", "can submit");
            return true;
        }
    }

    public boolean isDateValid(){
        String date = this.getDate();
        char[] dateArray = date.toCharArray();
        for(int i = 0; i < 10; i++){

            int charToInt = dateArray[i] - '0';

            if(i == 4 || i == 7){
                if (dateArray[i] != '-'){
                    Log.d("AddActivity", "problem of -");
                    return false;
                } else{
                    continue;
                }
            }

            if(i == 5){
                int charToIntPlus = dateArray[i+1] - '0';

                if (charToInt > 1){
                    return false;
                } else if(charToInt == 1 && charToIntPlus > 2){
                    Log.d("AddActivity", "0000-0x-00");
                    return false;
                }
            }

            if(i == 8){
                int charToIntPlus = dateArray[i+1] - '0';

                if(charToInt > 3){
                    return false;
                } else if(charToInt == 3 && charToIntPlus > 1){
                    Log.d("AddActivity", "0000-00-0x");
                    return false;
                }
            }

            if(!Character.isDigit(dateArray[i])){
                Log.d("AddActivity", "notDigit");
                return false;
            }
        }
        Log.d("AddActivity", "date valid");
        return true;
    }

}
