package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ViewReport extends AppCompatActivity {
    float expense = 0;
    int calorieintake=0;
    TextView totalExpense, totalCalories,goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        totalExpense = findViewById(R.id.totalexpense);
        totalCalories = findViewById(R.id.totalcalories);
        goback = findViewById(R.id.goBack);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseDatabase.getInstance().getReference("Orders").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    OrderModel od = ds.getValue(OrderModel.class);
                    String date = od.getDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate orderDate = LocalDate.parse(date, formatter);
                    LocalDate currentDate= LocalDate.now();
                    Period p = Period.between(orderDate, currentDate);
                     int days = p.getDays();
                     if(days<15){
                          expense = expense + Float.parseFloat(od.getPrice());
                          calorieintake = calorieintake + Integer.valueOf(od.getFoodCal());

                     }
                     System.out.println(expense);



                }
                totalExpense.setText("$" +String.valueOf(expense));
                totalCalories.setText(String.valueOf(calorieintake) + " Calories");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}