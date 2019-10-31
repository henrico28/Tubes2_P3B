package com.example.tugasbesar2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tugasbesar2.Model.Plane;
import com.example.tugasbesar2.Presenter.Presenter;
import com.example.tugasbesar2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements FragmentListener, SensorEventListener, View.OnClickListener {
    //fragment : FrontPage
    protected FrontPage fp;
    protected FragmentManager fragmentManager;
    protected boolean isStarted;

    //Presenter
    protected Presenter presenter;

    //Da Plane
    private Plane plane;

    //Sensors
    protected SensorManager mSensorManager;
    protected Sensor accelerometer;
    protected Sensor magnetometer;
    protected float[] accelReading = new float[3];
    protected float[] magnetReading = new float[3];
    private static final float VALUE_DRIFT = 0.05f;

    //Game Screen (prototype)
    protected ImageView imageView;
    protected Canvas mCanvas;
    protected Bitmap mBitmap;
    protected Paint friendly_paint;
    protected Paint enemy_paint;

    //button
    protected Button btn_mode;
    protected FloatingActionButton fab_left;
    protected FloatingActionButton fab_right;
    private boolean mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.isStarted = false;
        this.mode = false; //mode gyro

        //Presenter
        this.presenter = new Presenter(this);
        this.plane = this.presenter.getPlane();

        //front page
        this.fp = new FrontPage();
        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, this.fp).addToBackStack(null).commit();

        //sensors
        this.mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnetometer = this.mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //game screen
        this.imageView = findViewById(R.id.game_image_container);
        this.friendly_paint = new Paint();
        this.friendly_paint.setColor(getResources().getColor(R.color.white));
        this.enemy_paint = new Paint();
        int enemy_color = ResourcesCompat.getColor(getResources(),R.color.green,null);
        this.enemy_paint.setColor(enemy_color);

        //btn
        this.btn_mode = findViewById(R.id.btn_mode);
        this.fab_left = findViewById(R.id.fab_left);
        this.fab_right = findViewById(R.id.fab_right);

        //menyembunyikan semua button
        this.btn_mode.setVisibility(View.GONE);
        this.fab_left.hide();
        this.fab_right.hide();

        this.btn_mode.setOnClickListener(this);
        this.fab_left.setOnClickListener(this);
        this.fab_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        if(view.getId() == this.btn_mode.getId()){
            if(this.btn_mode.getText().toString().equalsIgnoreCase("buttons")) {
                this.btn_mode.setText(R.string.modeGyro);
                deactivateButtons();
                activateGyro();
                //hide button
                this.fab_left.hide();
                this.fab_right.hide();
                //show toast for enabling gyro
                Toast toast = Toast.makeText(context, "Gyro Mode Activated", duration);
                toast.show();
            }else{
                this.btn_mode.setText(R.string.modeButton);
                deactivateGyro();
                activateButtons();
                //memunculkan button
                this.fab_left.show();
                this.fab_right.show();
                //show toast for enabling button
                Toast toast = Toast.makeText(context, "Button Mode Activated", duration);
                toast.show();
            }
        }else if(view.getId() == this.fab_left.getId() && mode){
                plane.moveLeft(20);
                this.drawSlave();
        }else if(view.getId() == this.fab_right.getId() && mode){
            plane.moveRight(20);
            this.drawSlave();
        }
    }

    //game button or gyro
    private void activateButtons(){
        this.mode = true;
    }

    private void deactivateButtons(){
        this.mode = false;
    }

    private void activateGyro(){
        if(this.accelerometer != null){
            this.mSensorManager.registerListener(this,this.accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("accel","registered!");
        }
        if(this.magnetometer != null){
            this.mSensorManager.registerListener(this,this.magnetometer,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("magnet","registered!");
        }
    }

    private void deactivateGyro(){
        this.mSensorManager.unregisterListener(this);
    }



    //game screen (prototype)
    private Bitmap a10;
    public void initiateCanvas(){
        this.isStarted = true;
        this.mBitmap = Bitmap.createBitmap(this.imageView.getWidth(),this.imageView.getHeight(), Bitmap.Config.ARGB_8888);
        this.imageView.setImageBitmap(this.mBitmap);
        this.mCanvas = new Canvas(this.mBitmap);
        int mColorBackground = ResourcesCompat.getColor(getResources(),R.color.black,null);
        this.mCanvas.drawColor(mColorBackground);
        this.imageView.invalidate();

        //adding a plane model (A10)
        this.a10 = BitmapFactory.decodeResource(getResources(), R.drawable.a10);
        this.mCanvas.drawBitmap(this.a10,plane.getPosX(),plane.getPosY(),new Paint());
    }

    //changes drawing (update draw)
    public void drawSlave(){
        this.initiateCanvas();

        this.mCanvas.drawBitmap(this.a10,plane.getPosX(),plane.getPosY(),new Paint());

        this.imageView.invalidate();
    }

    @Override
    public void closePage() {
        //closing the fragment "FrontPage"
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.hide(this.fp);
        ft.commit();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //reading the sensors values
        int sensorType = event.sensor.getType();
        switch (sensorType){
            case Sensor.TYPE_ACCELEROMETER:
                this.accelReading = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                this.magnetReading = event.values.clone();
                break;
        }

        final float[] rotationMatrix = new float[9];
        this.mSensorManager.getRotationMatrix(rotationMatrix,null,this.accelReading,this.magnetReading);

        final float[] orientationAngles = new float[3];
        this.mSensorManager.getOrientation(rotationMatrix,orientationAngles);

        float roll = orientationAngles[2];

        if((Math.abs(roll)) < VALUE_DRIFT){
            roll = 0;
        }

        //check the phone gyrometer (for movement)
        if(roll > 0.35){
            if(plane.getPosX() > 920){
                System.out.println("max right");
            }else {
                System.out.println("right");
                plane.moveRight(10);
                this.drawSlave();
            }
        }else if(roll < -0.35){
            if(plane.getPosX() < -90){
                System.out.println("max left");
            }else {
                System.out.println("left");
                plane.moveLeft(10);
                this.drawSlave();
            }
        }else if(roll < 0.35 && roll > 0.35){
            System.out.println("steady");
        }
    }


    protected void engageSensors(){
        //registering sensor on start fragment "FrontPage" + engage canvas (prototype)
        this.btn_mode.setVisibility(View.VISIBLE);
        this.activateGyro(); //NANTI AKAN DIGANTI OLEH BUTTON SEMENTARA DL AJA AJG
        this.initiateCanvas();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //unused
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isStarted) {
            if (this.btn_mode.getText().toString().equalsIgnoreCase("button")) {
                this.activateButtons();
            } else {
                this.activateGyro();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.btn_mode.getText().toString().equalsIgnoreCase("gyro")){
            this.deactivateGyro();
        }
    }
}
