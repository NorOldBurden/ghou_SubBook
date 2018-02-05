/**
  *
  * AddActivity
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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;


/**
 * This activity is used when the user click on the item in subscription list
 * It is a new activity that let user change the information in existing subscription
 *
 */

public class EditSubscription extends AppCompatActivity {

    /**
     * The following part is the same as AddActivity except for the findViewById
     */

    public final static int GOOD_EDIT_RESULT = 251;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        Intent intent = getIntent();
        Subscription editSub = new Gson().fromJson(intent.getStringExtra("editSub"), Subscription.class);
        final int index = intent.getIntExtra("editSubIndex", -1);

        loadInfo(editSub);


        final Button subButton = (Button) findViewById(R.id.editSubButton);

        View.OnClickListener listener = new View.OnClickListener(){
            public void onClick(View view) {

                if (canSubmit()) {
                    Log.d("EditActivity", "canSubmit");
                    Intent newIntent = new Intent();
                    Subscription newSubscription = new Subscription(getName(), getDate(), getCharge(), getComments());
                    newIntent.putExtra("editSub", new Gson().toJson(newSubscription));
                    newIntent.putExtra("indexOfSub", index);
                    setResult(GOOD_EDIT_RESULT, newIntent);
                    finish();
                } else {
                    Toast.makeText(EditSubscription.this, "Incorrect input format" ,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        subButton.setOnClickListener(listener);

    }

    public String getName(){
        EditText editText = findViewById(R.id.changeName);
        return editText.getText().toString();
    }

    public String getDate(){
        EditText editText = findViewById(R.id.changeDate);
        return editText.getText().toString();
    }

    public double getCharge(){
        EditText editText = findViewById(R.id.changeCharge);
        String text = editText.getText().toString();
        if(text == null || text.isEmpty()){
            return -250;
        }
        double chargeInDouble = Double.parseDouble(text);
        return chargeInDouble;
    }

    public String getComments(){
        EditText editText = findViewById(R.id.changeComments);
        return editText.getText().toString();
    }

    public boolean canSubmit(){
        String name = this.getName();
        String comments = this.getComments();
        double charge = this.getCharge();
        String date = this.getDate();
        if(name == null || name.isEmpty()){
            Log.d("EditActivity", "invalid name");
            return false;
        }  else if(charge == -250 || charge < 0){
            Log.d("EditActivity", "empty charge");
            return false;
        } else if(!isDateValid()){
            Log.d("EditActivity", "date not valid");
            return false;
        } else{
            Log.d("EditActivity", "can submit");
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
                    Log.d("EditActivity", "problem of -");
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
                    Log.d("EditActivity", "0000-0x-00");
                    return false;
                }
            }

            if(i == 8){
                int charToIntPlus = dateArray[i+1] - '0';

                if(charToInt > 3){
                    return false;
                } else if(charToInt == 3 && charToIntPlus > 1){
                    Log.d("EditActivity", "0000-00-0x");
                    return false;
                }
            }

            if(!Character.isDigit(dateArray[i])){
                Log.d("EditActivity", "notDigit");
                return false;
            }
        }
        Log.d("EditActivity", "date valid");
        return true;
    }

    /**
     * The method loadInfo is used to filling the information of subscription in editText
     * @param editSub
     */

    private void loadInfo(Subscription editSub){
        EditText editName = findViewById(R.id.changeName);
        EditText editDate = findViewById(R.id.changeDate);
        EditText editCharge = findViewById(R.id.changeCharge);
        EditText editComments = findViewById(R.id.changeComments);

        editName.setText(editSub.getName());
        editDate.setText(editSub.getDate());
        editCharge.setText(String.valueOf(editSub.getCharge()));
        editComments.setText(editSub.getComments());

    }

}
