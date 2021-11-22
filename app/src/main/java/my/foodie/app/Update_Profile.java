package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Update_Profile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbRef;
    String userID,state ;
    Button Save, Cancel;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    FirebaseDatabase firebaseDB;
    DatabaseReference dbRef_states;
    DatabaseReference userRef;
    String Logintype_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        dbRef_states = firebaseDB.getInstance().getReference("States");
        userRef=firebaseDB.getInstance().getReference("Users");

        String type = getIntent().getStringExtra("LoginType");
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef= FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        Save = (Button)findViewById(R.id.Save_update);
        Cancel= (Button)findViewById(R.id.Cancel_update);
        //RadioGroup Login = findViewById(R.id.rg_types_update);
        //RadioButton Logintype_update = findViewById(Login.getCheckedRadioButtonId());

        //CheckBox chef = (CheckBox) findViewById(R.id.cb_chef_update);
        //CheckBox cust=(CheckBox) findViewById(R.id.cb_cust_update);
        //CheckBox driver= (CheckBox) findViewById(R.id.cb_driver_update);

        /*chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chef.isChecked()){
                    Logintype_update = chef.getText().toString().trim();
                }
            }
        });
        cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cust.isChecked()){
                    Logintype_update = Logintype_update+","+cust.getText().toString().trim();
                }
            }
        });
        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(driver.isChecked()){

                    Logintype_update=Logintype_update+","+driver.getText().toString().trim();
                }
            }
        });*/

        EditText fname_update = findViewById(R.id.fname_update);
        EditText lname_update = findViewById(R.id.lname_update);
        EditText phone_update = findViewById(R.id.phone_update);
        EditText address_update = findViewById(R.id.address_update);
        EditText city_update = findViewById(R.id.city_update);
        Spinner state_update = findViewById(R.id.State_update);
        EditText zip_update = findViewById(R.id.zip_update);

        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(Update_Profile.this, android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        state_update.setAdapter(adapter);
        retrieveStates();

        //int statePosition = adapter.getPosition(State);

        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){

                    String firstName = userProfile.fname;
                    String lastName = userProfile.lname;
                    //String Email_profile = userProfile.email;
                    String Address = userProfile.address;
                    String City = userProfile.city;
                    String State = userProfile.state;
                    String Zip = userProfile.zip;
                    String phoneNumber = userProfile.phone;
                    //String type = userProfile.type;
                    int statePosition = adapter.getPosition(State);

                    state_update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Object value = adapterView.getItemAtPosition(i);
                            state = value.toString().trim();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                    fname_update.setText(firstName);
                    lname_update.setText(lastName);
                    phone_update.setText(phoneNumber);
                    address_update.setText(Address);
                    city_update.setText(City);
                    state_update.setSelection(statePosition);
                    //Logintype_update.setText(type);
                    zip_update.setText(Zip);




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_Profile.this,"Something went wrong",Toast.LENGTH_LONG).show();

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User update_user = new User();

                String firstName = fname_update.getText().toString().trim();
                String lastName = lname_update.getText().toString().trim();
                //String Email_profile = userProfile.email;
                String Address = address_update.getText().toString().trim();
                String City = city_update.getText().toString().trim();
                String Zip = zip_update.getText().toString().trim();
                String phoneNumber = phone_update.getText().toString().trim();
                //String type = Logintype_update.getText().toString().trim();
                userRef.child(userID).child("fname").setValue(firstName);
                userRef.child(userID).child("lname").setValue(lastName);
                userRef.child(userID).child("address").setValue(Address);
                userRef.child(userID).child("state").setValue(state);
                userRef.child(userID).child("city").setValue(City);
                userRef.child(userID).child("zip").setValue(Zip);
                userRef.child(userID).child("phone").setValue(phoneNumber);
                //userRef.child(userID).child("type").setValue(Logintype_update);

                AlertDialog.Builder builder = new AlertDialog.Builder(Update_Profile.this);
                builder.setTitle("Profile Update");
                builder.setMessage("Your profile has been updated. Refresh the page to see updated profile");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
//                        startActivity(new Intent(Update_Profile.this,RecyclerViewNav.class));
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ChefProfile()).commit();
                    }
                });
                builder.show();

                //Toast.makeText(Update_Profile.this,"User Profile has been updated..",Toast.LENGTH_LONG).show();

                //finish();
                //startActivity(new Intent (Update_Profile.this,UserProfile.class));



            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent (Update_Profile.this,UserProfile.class));
            }
        });

    }
    public void retrieveStates( ){

        listener = dbRef_states.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot item: snapshot.getChildren()){

                    spinnerDataList.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}