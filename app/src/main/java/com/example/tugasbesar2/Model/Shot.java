package com.example.tugasbesar2.Model;

public class Shot {
    private int posX;
    private int posY;

    public Shot(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }
}
