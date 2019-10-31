package com.example.tugasbesar2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.tugasbesar2.R;

public class MainActivity extends AppCompatActivity implements FragmentListener{
    protected FrontPage fp;
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fp = new FrontPage();

        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, this.fp).addToBackStack(null).commit();
    }

    @Override
    public void closePage() {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.hide(this.fp);
        ft.commit();
    }
}
