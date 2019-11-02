package com.example.tugasbesar2.Model;

public class Enemy {
    private int posX;
    private int posY;

    //you ded?
    private boolean dead;

    public Enemy(int x, int y){
        this.posX = x;
        this.posY = y;
        this.dead = false;
    }

    void moveHorizontal(int x){
        this.posX = x;
    }

    void moveVertical(int y){
        this.posY = y;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public boolean isDead(){
        return this.dead;
    }

    public void setDead(boolean dead){
        this.dead = dead;
    }
}
