package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.datatransport.runtime.dagger.Reusable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerRegister extends AppCompatActivity {

    TextInputLayout Fname, Lname, Email, Password, Cpassword, Phone,Address, City, Zip;
    Spinner State;
    CountryCodePicker code;
    Button Cust_signup;
    FirebaseAuth FbAuth;
    DatabaseReference dbRef;
    FirebaseDatabase firebaseDB;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    String fname,lname,email,pwd, cpwd,phone,address,city,state,zip, login_type= "Customer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        Fname = (TextInputLayout) findViewById(R.id.Firstname_Cust);
        Lname = (TextInputLayout) findViewById(R.id.Lastname_Cust);
        Email = (TextInputLayout) findViewById(R.id.Email_Cust);
        Password = (TextInputLayout) findViewById(R.id.Password_Cust);
        Cpassword = (TextInputLayout) findViewById(R.id.ConfirmPassword_Cust);
        Phone = (TextInputLayout) findViewById(R.id.Phone_Cust);
        Address = (TextInputLayout) findViewById(R.id.Address_Cust);
        City = (TextInputLayout) findViewById(R.id.City_Cust);
        //State = (TextInputLayout) findViewById(R.id.State);
        State = (Spinner) findViewById(R.id.State_Cust);
        Zip = (TextInputLayout) findViewById(R.id.ZipCode_Cust);

        Cust_signup = (Button) findViewById(R.id.Signup_Cust);
        code = (CountryCodePicker) findViewById(R.id.ccp_Cust);

        dbRef = firebaseDB.getInstance().getReference("States");

        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(CustomerRegister.this, android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        State.setAdapter(adapter);
        retrieveStates();


        FbAuth = FirebaseAuth.getInstance();

         State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object value = adapterView.getItemAtPosition(i);
                state = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Cust_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                email = Email.getEditText().getText().toString().trim();
                pwd = Password.getEditText().getText().toString().trim();
                cpwd = Cpassword.getEditText().getText().toString().trim();
                phone = Phone.getEditText().getText().toString().trim();
                address = Address.getEditText().getText().toString().trim();
                city = City.getEditText().getText().toString().trim();
                //state = State.getEditText().getText().toString().trim();
                zip = Zip.getEditText().getText().toString().trim();


                if (isValid()) {
                    final ProgressDialog message = new ProgressDialog(CustomerRegister.this);
                    message.setCancelable(false);
                    message.setCanceledOnTouchOutside(false);
                    message.setMessage("Please wait....");
                    message.show();
                    System.out.println(" 000" + email + pwd);

                    FbAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override

                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                final HashMap<String, String> UserData = new HashMap<>();
                                UserData.put("Type", login_type);
                                System.out.println("task second" + task.isSuccessful());
                                System.out.println(" 222");
                                firebaseDB.getInstance().getReference("Users").setValue(UserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        User user = new User(fname, lname, email, pwd, cpwd, phone, address, city, state, zip, login_type);
                                        System.out.println(" 333");
                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                System.out.println(" 555");
                                                if (task.isSuccessful()) {

                                                    FbAuth.getCurrentUser().sendEmailVerification();
                                                    message.dismiss();
                                                    Toast.makeText(CustomerRegister.this, "User registered successfully.Please verify your email.", Toast.LENGTH_LONG).show();

                                                    System.out.println(" User registered successfully..");
                                                }
                                            }
                                        });
                                    }
                                });
                            }


                            else{
                                message.dismiss();

                                Toast.makeText(CustomerRegister.this, "User already exists..", Toast.LENGTH_LONG).show();
                                System.out.println("task third" + task.isSuccessful());
                                System.out.println(" Unsuccessful");
                            }
                        }
                    });


                }
            }


        });
    }


    String email_Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid() {
        boolean valid_all = false, validFname = false, validLname = false, validemail = false, validpwd = false, validcpwd = false, validphone = false, validaddress = false, validcity = false, validstate = false, validzip = false;
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Email.setErrorEnabled(false);
        Email.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");
        Cpassword.setErrorEnabled(false);
        Cpassword.setError("");
        Phone.setErrorEnabled(false);
        Phone.setError("");
        Cpassword.setErrorEnabled(false);
        Cpassword.setError("");
        Address.setErrorEnabled(false);
        Address.setError("");
        City.setErrorEnabled(false);
        City.setError("");
        //State.setErrorEnabled(false);
        //State.setError("");
        Zip.setErrorEnabled(false);
        Zip.setError("");

        if (TextUtils.isEmpty(fname)) {

            Fname.setErrorEnabled(true);
            Fname.setError("Enter FirstName");
        } else {
            validFname = true;
        }

        if (TextUtils.isEmpty(lname)) {

            Lname.setErrorEnabled(true);
            Lname.setError("Enter LastName");
        } else {
            validLname = true;
        }

        if (TextUtils.isEmpty(email)) {

            Email.setErrorEnabled(true);
            Email.setError("Enter Email");
        } else {
            if (email.matches(email_Regex)) {
                validemail = true;
            } else {

                Email.setErrorEnabled(true);
                Email.setError("Enter Valid Email");
            }
        }

        if (TextUtils.isEmpty(pwd)) {

            Password.setErrorEnabled(true);
            Password.setError("Enter Password");
        } else {
            if (pwd.length() < 8) {
                Password.setErrorEnabled(true);
                Password.setError("Weak Password");
            } else {

                validpwd = true;
            }

        }
        if (TextUtils.isEmpty(cpwd)) {
            Cpassword.setErrorEnabled(true);
            Cpassword.setError("Enter Password Again");
        } else {
            if (!pwd.equals(cpwd)) {
                Cpassword.setErrorEnabled(true);
                Cpassword.setError("Password Dosen't Match");
            } else {
                validcpwd = true;
            }
        }

        if (TextUtils.isEmpty(phone)) {
            Phone.setErrorEnabled(true);
            Phone.setError("Mobile Number Is Required");
        } else {
            if (phone.length() < 10) {
                Phone.setErrorEnabled(true);
                Phone.setError("Invalid Mobile Number");
            } else {
                validphone = true;
            }
        }

        if (TextUtils.isEmpty(address)) {
            Address.setErrorEnabled(true);
            Address.setError("Address Is Required");
        } else {
            validaddress = true;
        }

        if (TextUtils.isEmpty(city)) {
            City.setErrorEnabled(true);
            City.setError("City Is Required");
        } else {
            validcity = true;
        }
        if (TextUtils.isEmpty(state)) {
            //State.setErrorEnabled(true);
            //State.setError("State Is Required");
        } else {
            validstate = true;
        }
        if (TextUtils.isEmpty(zip)) {
            Zip.setErrorEnabled(true);
            Zip.setError("Zip Code Is Required");
        } else {
            validzip = true;
        }

        valid_all = (validFname && validLname && validemail && validpwd && validcpwd && validphone && validaddress && validcity && validstate && validzip) ? true : false;
        return valid_all;


    }

    public void retrieveStates(){

        listener = dbRef.addValueEventListener(new ValueEventListener() {
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
