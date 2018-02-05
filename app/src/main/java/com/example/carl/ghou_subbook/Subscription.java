/**
 *
 * Subscription
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

import java.util.Calendar;

/**
 * Subscription is the object which will provide information for listView to show
 * It takes name, date, charge and comments as args and other class can access these
 * variables by get and set method.
 */

public class Subscription {
    private String name;
    private String date;
    private double charge;
    private String comments;

    public Subscription(){
        this.name = "default subscription";
        this.date = "2018/10/10";
        this.charge = 100.00;
        this.comments = "default comments";
    }

    public Subscription(String name, String date, double charge, String comments){
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comments = comments;
    }

    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }

    public double getCharge(){
        return charge;
    }

    public String getComments(){
        return comments;
    }

    public void setDate(String newDate){
        if (newDate.length() != 10){
            System.out.println("Date must be the format of YYYY-MM-DD");
        }
        this.date = newDate;
    }

    public void setCharge(double newCharge){
        if (newCharge < 0){
            System.out.println("Can't have negative input value for charge");
        }
        else{
            this.charge = newCharge;
        }
    }

    public void setComments(String newComments){
        this.comments = newComments;
    }

    public void setName(String newName){
        this.name = newName;
    }
}


