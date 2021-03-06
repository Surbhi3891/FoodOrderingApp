package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailLogin extends AppCompatActivity {

    EditText Email;
    //EditText Password;
    TextInputLayout Password;
    Button Signin;
    String email, pwd;
    TextView forgot_pwd;
    TextView Signup;

    FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        final Animation Zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation Zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);

        ImageView bg = findViewById(R.id.bg);
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


        Email = findViewById(R.id.email_login);
        Password = findViewById(R.id.e_password);
        Signin = findViewById(R.id.Email_Btn);
        forgot_pwd= findViewById(R.id.forgot_pwd);
        Signup = findViewById(R.id.JoinNow);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailLogin.this,UserRegisteration.class));
            }
        });
        fbAuth = FirebaseAuth.getInstance();
        forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailLogin.this,forgotPassword.class));
                
            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = Email.getText().toString().trim();
                //pwd = Password.getText().toString().trim();
                pwd = Password.getEditText().getText().toString().trim();

                if(email.isEmpty()){
                    Email.setError("Email is Required");
                    Email.requestFocus();
                    return;

                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Enter Valid Email");
                    Email.requestFocus();
                    return;

                }
                if (pwd.isEmpty()){
                    Password.setError("Password is required");
                    Password.requestFocus();
                    return;

                }
                if(pwd.length()<8){

                    Password.setError("Min Password length is 8 characters");
                    Password.requestFocus();
                    return;
                }
                final ProgressDialog message = new ProgressDialog(EmailLogin.this);
                message.setCancelable(false);
                message.setCanceledOnTouchOutside(false);
                message.setMessage("Please wait....");
                message.show();
                fbAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            message.dismiss();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()){
                                startActivity(new Intent(EmailLogin.this,SelectLogin.class));
                                //finish();
                            }else {

                                message.dismiss();
                                user.sendEmailVerification();
                                Toast.makeText(EmailLogin.this,"Check your email to verify your account..",Toast.LENGTH_LONG).show();
                            }


                        }else{
                            message.dismiss();
                            Toast.makeText(EmailLogin.this,"Login Failed. Check your credentials and Try again..",Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }


        });

    }
}