package com.example.tugasbesar2.View;

import com.example.tugasbesar2.Model.Enemy;
import com.example.tugasbesar2.Model.Plane;
import com.example.tugasbesar2.Model.Shot;

import java.util.List;

public class ThreadShots implements Runnable{
    protected Thread thread;
    protected ThreadShotHandler threadShotHandler;
    protected Shot shot;
    protected Plane plane;
    protected List<Enemy> enemies;


    public ThreadShots(ThreadShotHandler threadShotHandler, Shot shot, Plane plane, List<Enemy> enemies){
        this.threadShotHandler = threadShotHandler;
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
        System.out.println(this.enemies.size());
        while(true){
            if(curY > 0) {
                //kalau peluru belum keluar map
                curY -= 50;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int hitBoxXMax = 0;
                int hitBoxXMin = 0;
                int hitBoxYBawah = 0;
                int hitBoxYAtas = 0;
                //if it hits enemies and penetrates the enemy
                for(int i = 0 ; i < this.enemies.size() ; i++){
                    //create hitbox
                    hitBoxXMax = this.enemies.get(i).getPosX() + 50;
                    hitBoxXMin = this.enemies.get(i).getPosX() - 50;
                    hitBoxYBawah = this.enemies.get(i).getPosY()+50;
                    hitBoxYAtas = this.enemies.get(i).getPosY()-50;
                    if(curX >= hitBoxXMin && curX <=hitBoxXMax && curY<=hitBoxYBawah && curY>=hitBoxYAtas){
                        //tambah API untuk POINT
                        this.enemies.get(i).setDead(true);
                        curY = -100;

                    }
                }
                this.threadShotHandler.x(new Shot(curX, curY));
            }else{
                //kalau peluru di reload animation
                curX = plane.getPosX()+130;
                curY = plane.getPosY();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.threadShotHandler.x(new Shot(curX, curY));
            }
        }
    }

    public void initiate(){
        this.thread.start();
    }
}
