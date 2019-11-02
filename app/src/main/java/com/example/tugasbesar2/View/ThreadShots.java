package com.example.tugasbesar2.View;

import com.example.tugasbesar2.Model.Enemy;
import com.example.tugasbesar2.Model.Plane;
import com.example.tugasbesar2.Model.Shot;

public class ThreadShots implements Runnable{
    protected Thread thread;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected Shot shot;
    protected Plane plane;
    protected Enemy[] enemies;


    public ThreadShots(UIThreadedWrapper uiThreadedWrapper, Shot shot, Plane plane, Enemy[] enemies){
        this.uiThreadedWrapper = uiThreadedWrapper;
        this.thread = new Thread(this);
        this.shot = shot;
        this.plane = plane;
        this.enemies = enemies;
    }

    @Override
    public void run() {
        //single shot only
        int curX = shot.getPosX() + 130;
        int curY = shot.getPosY();
        while(true){
            if(curY > 0) {
                //kalau peluru belum keluar map
                curY -= 30;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int hitBoxXMax = 0;
                int hitBoxXMin = 0;
                int hitBoxY = 0;
                //if it hits enemies and penetrates the enemy
                for(int i = 0 ; i < this.enemies.length ; i++){
                    //create hitbox
                    hitBoxXMax = this.enemies[i].getPosX() + 50;
                    hitBoxXMin = this.enemies[i].getPosX() - 50;
                    hitBoxY = this.enemies[i].getPosY()+25;
                    if(curX >= hitBoxXMin && curX <=hitBoxXMax && curY<=hitBoxY){
                        //tambah API untuk POINT
                        this.enemies[i].setDead(true);
                        curY = -100;
                    }
                }
                this.uiThreadedWrapper.x(new Shot(curX, curY));
            }else{
                //kalau peluru di reload animation
                curX = plane.getPosX()+130;
                curY = plane.getPosY();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.uiThreadedWrapper.x(new Shot(curX, curY));
            }
        }
    }

    public void initiate(){
        this.thread.start();
    }
}