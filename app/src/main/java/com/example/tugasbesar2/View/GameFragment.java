package com.example.tugasbesar2.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tugasbesar2.R;

public class GameFragment extends Fragment {

    static GameFragment instance;
    private ImageView imageView;
    private Paint paint;
    private Canvas mCanvas;

    public static GameFragment newInstance(){
        GameFragment fragment = new GameFragment();
        GameFragment.instance = fragment;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.game_screen,container,false);
        this.imageView = view.findViewById(R.id.imageView);

        this.paint = new Paint();
        this.initiateCanvas();
        return view;
    }

    void initiateCanvas(){
        Bitmap bitmap = Bitmap.createBitmap(this.imageView.getWidth(),this.imageView.getHeight(), Bitmap.Config.ARGB_8888);
        this.imageView.setImageBitmap(bitmap);
        this.mCanvas = new Canvas(bitmap);
        this.paint.setColor(getResources().getColor(R.color.black));
        this.paint.setStyle(Paint.Style.STROKE);
        this.resetCanvas();
        this.createPlane();
    }

    void resetCanvas(){
        this.mCanvas.drawColor(getResources().getColor(R.color.white));
        this.imageView.invalidate();
    }

    void createPlane(){
        mCanvas.drawRect(10,10,10,10,paint);
        imageView.invalidate();
    }
}
