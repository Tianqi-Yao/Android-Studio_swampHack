package com.example.swamphack;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;


public class IntroActivity extends AppCompatActivity {

    public static final String TAG = "test";

    //5 sec
    private static int SPLASH_SCREEN =5000;


    LottieAnimationView lottieAnimationView;
    Animation anim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        lottieAnimationView = findViewById(R.id.lottie);

        anim = AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        lottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(4000);

        lottieAnimationView.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart: is start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationStart: is end");

                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }


        });






    }
}