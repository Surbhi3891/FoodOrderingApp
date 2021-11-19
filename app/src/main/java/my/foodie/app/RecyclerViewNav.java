package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;



public class RecyclerViewNav extends AppCompatActivity {
    public static Activity chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_nav);
        BottomNavigationView bar = findViewById(R.id.bottom_nav);
        chef=this;
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()){

                    case R.id.chefProfile:
                        temp = new ChefProfile();
                        break;
                    case R.id.chef_Home:
                        temp = new ChefHomeFragment();
                        break;
                    case R.id.chef_Orders:
                        temp = new Chef_Orders();
                        break;
                    case R.id.chef_PostDish:
                        temp = new Chef_PostDish();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,temp).commit();
                return true;
            }
        });



    }


}