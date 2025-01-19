package com.ista.zhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button9);
        int colorStart = Color.parseColor("#056688");
        int colorEnd = Color.parseColor("#87CEEB");
        ObjectAnimator anim = ObjectAnimator.ofInt(button, "backgroundColor", colorStart, colorEnd);
        anim.setDuration(3000);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();

        Button button1 = findViewById(R.id.button11);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button1, "scaleX", 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button1, "scaleY", 1.2f);
        scaleX.setDuration(2000);
        scaleY.setDuration(2000);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);
        scaleX.setInterpolator(new BounceInterpolator());
        scaleY.setInterpolator(new BounceInterpolator());
        scaleX.start();
        scaleY.start();

    }
    public void Registro(View view){
        Intent registro = new Intent(this, Registrar.class);
        startActivity(registro);
    }
    public void Iniciar(View view){
        Intent registro1 = new Intent(this, Login.class);
        startActivity(registro1);
    }
}