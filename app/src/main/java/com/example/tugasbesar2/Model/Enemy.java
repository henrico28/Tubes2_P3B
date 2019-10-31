package com.example.tugasbesar2.Model;

public class Enemy {
    private float posX;
    private float posY;
    private float ukuranX;
    private float ukuranY;

    public Enemy(float x, float y, float ukuranX, float ukuranY){
        this.posX = x;
        this.posY = y;
        this.ukuranX = ukuranX;
        this.ukuranY = ukuranY;
    }

    void moveHorizontal(float x){
        this.posX = x;
    }

    void moveVertical(float y){
        this.posY = y;
    }
}
