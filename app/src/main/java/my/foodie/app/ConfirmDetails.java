package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    EditText name,phNumber,address,city,zip;
    Button checkout;
    public static String cnfName,cnfPhone, cnfAddress,cnfCity,cnfZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);

        checkout = findViewById(R.id.checkoutBtn);
        name = findViewById(R.id.cnf_name);
        phNumber = findViewById(R.id.cnf_phone);
        address = findViewById(R.id.cnf_address);
        city = findViewById(R.id.cnf_city);
        zip = findViewById(R.id.cnf_zip);
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){

                    String Name = userProfile.fname + " "+userProfile.lname;
                    String phoneNumber = userProfile.phone;
                    //String Email_profile = userProfile.email;
                    String Address = userProfile.address;
                    String City = userProfile.city +", "+userProfile.state;
                    //String State = userProfile.state;
                    String Zip = userProfile.zip;
                    name.setText(Name);
                    phNumber.setText(phoneNumber);
                    address.setText(Address);
                    city.setText(City);
                    zip.setText(Zip);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnfName = name.getText().toString().trim();
                cnfPhone = phNumber.getText().toString().trim();
                cnfAddress = address.getText().toString().trim();
                cnfCity = city.getText().toString().trim();
                cnfZip = zip.getText().toString().trim();
                startActivity(new Intent( ConfirmDetails.this,Payment.class));
                //finish();
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