package com.studios.patota.myexpo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    LinearLayout imagen;
    MediaPlayer mp;
    TextView debug;
    int pausa;
    SensorManager sensorManager;
    public float data = 0;
    private Sensor acelerometro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagen =(LinearLayout)findViewById(R.id.fondoOpc);

        int[] imagenes = new int[]{
                R.drawable.fo,
        };
        setTitle("Music");
        imagen.setBackgroundResource(imagenes[0]);
        debug=(TextView)findViewById(R.id.debuger);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        acelerometro=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public void play() {
        if (mp == null) {
            mp = MediaPlayer.create(this, R.raw.breake);
            mp.start();
            Toast.makeText(getApplicationContext(), "Play", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!mp.isPlaying()){
                mp.seekTo(pausa);
                mp.start();
                Toast.makeText(getApplicationContext(), "Play", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void pause(View view) {
        if(mp !=null){
            pausa = mp.getCurrentPosition();
            mp.pause();
            Toast.makeText(getApplicationContext(),"Pause", Toast.LENGTH_SHORT).show();
        }

    }
    public void stop(View view) {
        mp.stop();
        mp = null;
        Toast.makeText(getApplicationContext(),"Stop", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onSensorChanged(SensorEvent event) {
    float x,y,z;
        y=event.values[0];
        x=event.values[1];
        z=event.values[2];

        debug.setText("");
        debug.append("X:"+x+"Y:"+y+"Z:"+z);
        if(y>=4)
        {
           data = y;
            play();

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
    super.onResume();
        sensorManager.registerListener(this,acelerometro,SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    protected void onPause() {
    super.onPause();
        sensorManager.unregisterListener(this);

    }



}