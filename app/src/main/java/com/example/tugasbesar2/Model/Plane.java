package com.example.tugasbesar2.Model;

public class Plane {
    private int posX;
    private int posY;

    public Plane(){
        this.posX = 420;
        this.posY = 1600;
    }

    public void moveRight(int x){
        this.posX+=x;
    }

    public void moveLeft(int x){
        this.posX-=x;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }
}
