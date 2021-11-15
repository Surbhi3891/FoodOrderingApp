package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmDetails extends AppCompatActivity {
    Spinner State;
    DatabaseReference dbRef;
    FirebaseDatabase firebaseDB;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);

        checkout = findViewById(R.id.checkoutBtn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ConfirmDetails.this,Payment.class));
            }
        });

//        State = (Spinner) findViewById(R.id.cnf_state);
//        spinnerDataList = new ArrayList<>();
//        adapter = new ArrayAdapter<String>(ConfirmDetails.this, android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
//        State.setAdapter(adapter);
//        retrieveStates(dbRef);
//
//
//        //FbAuth = FirebaseAuth.getInstance();
//
//        State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Object value = adapterView.getItemAtPosition(i);
//                //state = value.toString().trim();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


    }

//    public void retrieveStates( DatabaseReference dbref){
//
//        listener = dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot item: snapshot.getChildren()){
//
//                    spinnerDataList.add(item.getValue().toString());
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}