package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ChefPhoneLogin extends AppCompatActivity {

    EditText phonenumber;
    Button verifyOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_phone_login);

        phonenumber = findViewById(R.id.enterPhone);
        verifyOTP = findViewById(R.id.otpVerify);
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!phonenumber.getText().toString().trim().isEmpty()){

                    if(phonenumber.getText().toString().trim().length()==10){
                        Intent intent =new Intent(ChefPhoneLogin.this,VerifyOTP.class);
                        startActivity(intent);
                        intent.putExtra("mobile",phonenumber.getText().toString().trim());


                        startActivity(new Intent(ChefPhoneLogin.this,VerifyOTP.class));


                    }else{
                        Toast.makeText(ChefPhoneLogin.this,"Please enter correct number",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(ChefPhoneLogin.this,"Phone Number is required.",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}