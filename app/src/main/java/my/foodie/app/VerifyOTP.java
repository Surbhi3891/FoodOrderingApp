package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {
  EditText otp;
  Button phoneLogin;
  String phonenumber;
  String otpid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        otp = findViewById(R.id.OTP);
        phoneLogin = findViewById(R.id.Verify_phone);
        phonenumber = getIntent().getStringExtra("mobile").toString();
        //initiateotp();
       phoneLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PhoneAuthCredential cred = PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
               //signInWithPhoneAuthCredential(cred);
           }
       });

    }

    private void initiateotp() {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(phonenumber).
                setTimeout(60L, TimeUnit.SECONDS).setActivity(this).
                setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                        super.onCodeSent(s, forceResendingToken);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(VerifyOTP.this,SelectLogin.class));
                            finish();




                        } else {
                            Toast.makeText(VerifyOTP.this,"Check your email to verify your account..",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}