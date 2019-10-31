package com.example.tugasbesar2.Model;

public class Enemy {
    private float posX;
    private float posY;

    public Enemy(float x, float y){
        this.posX = x;
        this.posY = y;
    }

    void moveHorizontal(float x){
        this.posX = x;
    }

    void moveVertical(float y){
        this.posY = y;
    }
}
