package com.example.tugasbesar2.Model;

public class Pesawat {
    private float posX;
    private float posY;

    public Pesawat(float x, float y){
        this.posX = x;
        this.posY = y;
    }

    public void moveHorizontal (float x){
        this.posY = x;
    }

    public void moveVertical (float y){
        this.posY = y;
    }
}
