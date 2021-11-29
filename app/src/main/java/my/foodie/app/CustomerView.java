package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CustomerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        BottomNavigationView chefNav = findViewById(R.id.bottom_nav_Cust);
        chefNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment t = null ;
                switch (item.getItemId()){

                    case R.id.cust_Home:
                        t = new Cust_Home();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,t).commit();
                        break;

                    case R.id.cust_Cart:
                        t=new Cust_Cart();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,t).commit();
                        break;

                    case R.id.cust_orders:
                        t=new Cust_orders();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,t).commit();
                        break;

                    case R.id.cust_Profile:
                        t=new Cust_Profile();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,t).commit();
                        break;

                    case R.id.switchType:
                        startActivity(new Intent(CustomerView.this,SelectLogin.class));
                        finish();
                        break;
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Cust,t).commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}