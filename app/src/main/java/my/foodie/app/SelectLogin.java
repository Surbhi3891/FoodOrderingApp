package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import java.util.Locale;

public class SelectLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);
        //getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();
        //String type= intent.getStringExtra("Home").toString().trim();
        Button Chef = findViewById(R.id.chef);
        Button Customer = findViewById(R.id.customer);
        //Button Delivery = findViewById(R.id.driver);

        Chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginemail_Chef = new Intent(SelectLogin.this,RecyclerViewNav.class);
                loginemail_Chef.putExtra("LoginType","Chef");
                startActivity(loginemail_Chef);
                finish();
                /*if (type.equals("Email")){
                    Intent loginemail_Chef = new Intent(SelectLogin.this,RecyclerViewNav.class);
                    startActivity(loginemail_Chef);
                    finish();
            }
                if (type.equals("Phone")){
                    Intent loginphone_Chef = new Intent(SelectLogin.this,ChefPhoneLogin.class);
                    startActivity(loginphone_Chef);
                    finish();
                }
                if (type.equals("Signup")){
                    Intent signup_Chef = new Intent(SelectLogin.this,ChefRegister.class);
                    startActivity(signup_Chef);
                    finish();
                }*/
            }
        });

        Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginemail_Chef = new Intent(SelectLogin.this,CustomerView.class);
                loginemail_Chef.putExtra("LoginType","Customer");
                startActivity(loginemail_Chef);
                finish();
                /*if (type.equals("Email")){
                    Intent loginemail_Cust = new Intent(SelectLogin.this,CustomerEmailLogin.class);
                    startActivity(loginemail_Cust);
                    finish();
                }
               if (type.equals("Phone")){
                    Intent loginphone_Cust = new Intent(SelectLogin.this,CustomerPhoneLogin.class);
                    startActivity(loginphone_Cust);
                    finish();
                }
                if (type.equals("Signup")){
                    Intent signup_Cust = new Intent(SelectLogin.this,CustomerRegister.class);
                    startActivity(signup_Cust);
                    finish();
                }*/
            }

        });

       /* Delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Email")){
                    Intent loginemail_Delivery = new Intent(SelectLogin.this,DriverEmailLogin.class);
                    startActivity(loginemail_Delivery);
                    //finish();
                }
                if (type.equals("Phone")){
                    Intent loginphone_Delivery = new Intent(SelectLogin.this,DriverPhoneLogin.class);
                    startActivity(loginphone_Delivery);
                    //finish();
                }
                if (type.equals("Signup")){
                    Intent signup_Delivery = new Intent(SelectLogin.this,DriverRegister.class);
                    startActivity(signup_Delivery);
                    //finish();
                }
            }
        });*/

    }


}