package com.example.tugasbesar2.Model;

public class Enemy {
    private float posX;
    private float posY;

    //hitboxes
    private float hitBox;

    //is it dead?
    private boolean dead;

    public Enemy(float x, float y){
        this.posX = x;
        this.posY = y;
        this.dead = false;
    }

    void moveHorizontal(float x){
        this.posX = x;
    }

    void moveVertical(float y){
        this.posY = y;
    }

    public float getPosX(){
        return this.posX;
    }

    public float getPosY(){
        return this.posY;
    }

    public boolean isDead(){
        return this.dead;
    }

    public void setDead(boolean dead){
        this.dead = dead;
    }

    public float getHitBox() {
        return hitBox;
    }
}
