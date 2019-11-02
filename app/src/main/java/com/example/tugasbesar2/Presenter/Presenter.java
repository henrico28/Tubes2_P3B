package com.example.tugasbesar2.Presenter;

import com.example.tugasbesar2.Model.Plane;
import com.example.tugasbesar2.Model.Shot;
import com.example.tugasbesar2.View.MainActivity;

public class Presenter {
    private MainActivity activity;
    private Plane plane;
    private Shot shot;
    public Presenter(MainActivity activity){
        this.activity = activity;
        this.plane = new Plane();
        this.shot = new Shot(this.plane.getPosX(),this.plane.getPosY());
    }

    public Plane getPlane() {
        return plane;
    }

    public Shot getShot() {
        return shot;
    }
}
