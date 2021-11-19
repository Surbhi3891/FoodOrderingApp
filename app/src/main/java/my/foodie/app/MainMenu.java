package my.foodie.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        final Animation Zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation Zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);

        ImageView bg = findViewById(R.id.bg2);
        bg.setAnimation(Zoomin);
        bg.setAnimation(Zoomout);

        Zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bg.startAnimation(Zoomin);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bg.startAnimation(Zoomout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Button email_signin = findViewById(R.id.signin_email);
        Button phone_signin = findViewById(R.id.signin_phone);
        Button sign_up = findViewById(R.id.signup);

        email_signin.setOnClickListener(this);
        phone_signin.setOnClickListener(this);
        sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signin_email:
                Intent signinemail = new Intent(MainMenu.this,EmailLogin.class);
                signinemail.putExtra("Home","Email");
                startActivity(signinemail);
                //finish();
                break;
            case R.id.signin_phone:
                Intent signphone = new Intent(MainMenu.this,ChefPhoneLogin.class);
                signphone.putExtra("Home","Phone");
                startActivity(signphone);
                //finish();
                break;
            case R.id.signup:
                Intent sign=new Intent(MainMenu.this,ChefRegister.class);
                sign.putExtra("Home","Signup");
                startActivity(sign);
               //finish();
                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}