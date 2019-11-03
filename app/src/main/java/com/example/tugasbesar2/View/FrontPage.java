package com.example.tugasbesar2.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.example.tugasbesar2.R;

public class FrontPage extends Fragment implements View.OnClickListener {
    protected Button btnStart;
    protected FragmentListener listener;
    protected TextView greeting_text;
    protected boolean blocker;//blocks the start button
    public FrontPage(){
    }

    public static FrontPage newInstance(){
        FrontPage fragment = new FrontPage();
        return fragment;
    }

    public void checkConnection(){
        //internet connection check
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            this.btnStart.setText(R.string.start);
            this.greeting_text.setText(R.string.welcome);
            this.blocker = false;
        }else{
            this.greeting_text.setText(R.string.no_welcome);
            this.btnStart.setText(R.string.refresh_button);
            this.blocker = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.front_page, container, false);

        this.btnStart = view.findViewById(R.id.btn_start);
        this.btnStart.setOnClickListener(this);
        this.greeting_text = view.findViewById(R.id.greeting_text);
        this.blocker = false;
        this.checkConnection();

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.btnStart.getId() && this.blocker == false){
            //starts the game
            this.listener.closePage();
            MainActivity main = (MainActivity)getActivity();
            main.startApp();
        }else if(view.getId() == this.btnStart.getId() && this.blocker == true){
            //if internet is connected, can press the refresh connection button
            this.checkConnection();
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
