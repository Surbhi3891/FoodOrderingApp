package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    EditText forgot_pwd_email;
    Button resetPwdBtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot_pwd_email = findViewById(R.id.enterEmail);
        resetPwdBtn = findViewById(R.id.resetPwd);
        auth = FirebaseAuth.getInstance();
        resetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
        
    }

    private void resetPassword() {

        String email = forgot_pwd_email.getText().toString().trim();

        if(email.isEmpty()){
            forgot_pwd_email.setError("Email is required.");
            forgot_pwd_email.requestFocus();
            return;

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            forgot_pwd_email.setError("Please provide valid email.");
            forgot_pwd_email.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(forgotPassword.this,"Check your email to reset your password",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(forgotPassword.this,ChefEmailLogin.class));
                }else{

                    Toast.makeText(forgotPassword.this,"Try Again! Something went wrong",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}