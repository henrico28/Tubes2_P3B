package com.example.tugasbesar2.View;

import com.example.tugasbesar2.Model.Plane;
import com.example.tugasbesar2.Model.Shot;

public class ThreadShots implements Runnable{
    protected Thread thread;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected Plane plane;


    public ThreadShots(UIThreadedWrapper uiThreadedWrapper, Plane plane){
        this.uiThreadedWrapper = uiThreadedWrapper;
        this.plane = plane;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println("hai");
        int curX = plane.getPosX();
        int curY = plane.getPosY();
        while(true){
            curY-=10;
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            this.uiThreadedWrapper.x(new Shot(curX, curY));
        }
    }

    public void initiate(){
        this.thread.start();
    }
}
