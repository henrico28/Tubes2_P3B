package com.example.tugasbesar2.Presenter;

import com.example.tugasbesar2.Model.Plane;
import com.example.tugasbesar2.View.MainActivity;

public class Presenter {
    private MainActivity activity;
    private Plane plane;
    public Presenter(MainActivity activity){
        this.activity = activity;
        this.plane = new Plane();
    }

    public Plane getPlane() {
        return plane;
    }
}
