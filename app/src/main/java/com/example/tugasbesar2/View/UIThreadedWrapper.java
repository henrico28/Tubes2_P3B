package com.example.tugasbesar2.View;

import android.os.CountDownTimer;
import android.os.Message;

import android.os.Handler;

import com.example.tugasbesar2.Model.Shot;

public class UIThreadedWrapper extends Handler {
    protected final static int MSG_SET_OUTPUT = 0;
    protected MainActivity mainActivity;

    public UIThreadedWrapper(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void handleMessage(Message msg){
        if(msg.what==UIThreadedWrapper.MSG_SET_OUTPUT){
            Shot shot =  (Shot) msg.obj;
            this.mainActivity.shot = shot;
            new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    mainActivity.setTimer(millisUntilFinished/1000);
//                    mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    mainActivity.setTimerText("TIME'S UP!");
//                    mTextField.setText("done!");
                }
            }.start();
            this.mainActivity.drawSlave();
        }
    }

    public void x(Shot shot){
        Message msg = new Message();
        msg.what = MSG_SET_OUTPUT;
        msg.obj = shot;
        this.sendMessage(msg);
    }
}
