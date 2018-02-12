package mbd.student.gurukustudent.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.utils.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        session = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                Intent i;
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

                if (session.isLoggedIn()) {
                    i = new Intent(SplashScreenActivity.this, MainActivity.class);
                } else {
                    i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                }

                startActivity(i, options.toBundle());
                finish();
            }
        }, 3000);
    }
}
