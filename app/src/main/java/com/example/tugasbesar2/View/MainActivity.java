package com.example.tugasbesar2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.tugasbesar2.R;

public class MainActivity extends AppCompatActivity implements FragmentListener, SensorEventListener {
    //fragment : FrontPage
    protected FrontPage fp;
    protected FragmentManager fragmentManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        int friendly_color = ResourcesCompat.getColor(getResources(),R.color.blue,null);
        this.friendly_paint.setColor(friendly_color);
        this.enemy_paint = new Paint();
        int enemy_color = ResourcesCompat.getColor(getResources(),R.color.red,null);
        this.enemy_paint.setColor(enemy_color);
    }
    //game screen (prototype)
    public void initiateCanvas(){
        System.out.println(this.imageView.getHeight() + " " + this.imageView.getWidth());
        this.mBitmap = Bitmap.createBitmap(this.imageView.getWidth(),this.imageView.getHeight(), Bitmap.Config.ARGB_8888);
        this.imageView.setImageBitmap(this.mBitmap);
        this.mCanvas = new Canvas(this.mBitmap);
        int mColorBackground = ResourcesCompat.getColor(getResources(),R.color.white,null);
        this.mCanvas.drawColor(mColorBackground);
        this.mCanvas.drawRect(1000,1000,1000,1000,this.friendly_paint);
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
            System.out.println("right");
        }else if(roll < -0.35){
            System.out.println("left");
        }else if(roll < 0.35 && roll > 0.35){
            System.out.println("steady");
        }
    }


    protected void engageSensors(){
        //registering sensor on start fragment "FrontPage" + engage canvas (prototype)
        if(this.accelerometer != null){
            this.mSensorManager.registerListener(this,this.accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("accel","registered!");
        }
        if(this.magnetometer != null){
            this.mSensorManager.registerListener(this,this.magnetometer,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("magnet","registered!");
        }

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
}
