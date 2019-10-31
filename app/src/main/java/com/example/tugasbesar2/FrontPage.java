package com.example.tugasbesar2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FrontPage extends Fragment implements View.OnClickListener {
    protected Button btnStart;
    protected FragmentListener listener;

    public FrontPage(){

    }

    public static FrontPage newInstance(){
        FrontPage fragment = new FrontPage();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.front_page, container, false);

        this.btnStart = view.findViewById(R.id.btn_start);
        this.btnStart.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.btnStart.getId()){
            this.listener.closePage();
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.listener = (FragmentListener) context;
        }
        else{
            throw new ClassCastException(context.toString() + "must implement FragmentListener");
        }
    }
}
