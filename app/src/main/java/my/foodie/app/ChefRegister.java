package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ChefRegister extends AppCompatActivity {

    TextInputLayout Fname, Lname, Email, Password, Cpassword, Phone,Address, City, Zip;
    Spinner State;
    //RadioGroup Login;
    //RadioButton Logintype;
    CheckBox Chef,Customer,Driver;
    CountryCodePicker code;
    Button Chef_signup;
    FirebaseAuth FbAuth;
    DatabaseReference dbRef;
    FirebaseDatabase firebaseDB;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    String fname,lname,email,pwd, cpwd,phone,address,city,state,zip, login_type;
    //boolean isChef, isCustomer,isDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_register);
        Fname = (TextInputLayout) findViewById(R.id.Firstname);
        Lname = (TextInputLayout) findViewById(R.id.Lastname);
        Email = (TextInputLayout) findViewById(R.id.Email);
        Password = (TextInputLayout) findViewById(R.id.Password);
        Cpassword = (TextInputLayout) findViewById(R.id.ConfirmPassword);
        Phone = (TextInputLayout) findViewById(R.id.Phone);
        Address = (TextInputLayout) findViewById(R.id.Address);
        City = (TextInputLayout) findViewById(R.id.City);
        //State = (TextInputLayout) findViewById(R.id.State);
        State = (Spinner) findViewById(R.id.State);
        Zip = (TextInputLayout) findViewById(R.id.ZipCode);
        Chef= findViewById(R.id.cb_chef);
        Customer=findViewById(R.id.cb_cust);
        Driver=findViewById(R.id.cb_driver);
        //Login = findViewById(R.id.rg_types);
        //Logintype = findViewById(Login.getCheckedRadioButtonId());

        Chef_signup = (Button) findViewById(R.id.Signup);
        code = (CountryCodePicker) findViewById(R.id.ccp);
        Chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Chef.isChecked()){
                   login_type = Chef.getText().toString().trim();
                    //isChef=true;
                }

            }
        });
        Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Customer.isChecked()){
                    login_type= login_type +","+ Customer.getText().toString().trim();
                    //isCustomer= true;
                }
            }
        });
        Driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Driver.isChecked()){
                    login_type = login_type+","+Driver.getText().toString().trim();
                    //isDriver=true;
                }


            }
        });


        dbRef = firebaseDB.getInstance().getReference("States");

        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(ChefRegister.this, android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        State.setAdapter(adapter);
        retrieveStates(dbRef);


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
        Chef_signup.setOnClickListener(new View.OnClickListener() {
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
                //login_type = Logintype.getText().toString();


                if (isValid()) {
                    final ProgressDialog message = new ProgressDialog(ChefRegister.this);
                   message.setCancelable(false);
                   message.setCanceledOnTouchOutside(false);
                   message.setMessage("Please wait....");
                   message.show();
                   System.out.println(" 000" + email + pwd);

                   FbAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override

                           public void onComplete(@NonNull Task<AuthResult> task) {

                               if (task.isSuccessful()) {

                                   //User user = new User(fname, lname, email, pwd, cpwd, phone, address, city, state, zip, login_type);
                                   User user = new User(fname, lname, email, phone, address, city, state, zip, login_type);
                                   System.out.println(" 333");
                                   FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           System.out.println(" 555");
                                           if (task.isSuccessful()) {


                                               FbAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       if (task.isSuccessful()) {
                                                           message.dismiss();
                                                           AlertDialog.Builder builder = new AlertDialog.Builder(ChefRegister.this);
                                                           builder.setMessage(" Registration Succesful. Please verify your email id.");
                                                           builder.setCancelable(false);
                                                           builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialogInterface, int i) {
                                                                   dialogInterface.dismiss();
                                                                   Intent chef_Login = new Intent(ChefRegister.this,EmailLogin.class);
                                                                   startActivity(chef_Login);
                                                               }
                                                           });

                                                           AlertDialog Alert = builder.create();
                                                           Alert.show();

                                                       }
                                                   }
                                               });

                                               //Toast.makeText(ChefRegister.this, "User registered successfully.Please verify your email.", Toast.LENGTH_LONG).show();

                                               //System.out.println(" User registered successfully..");
                                           }
                                       }


                                   });
                               }


                               else{
                                   message.dismiss();

                                   Toast.makeText(ChefRegister.this, "User already exists..", Toast.LENGTH_LONG).show();
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
            //Logintype.setError("");
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

        public void retrieveStates( DatabaseReference dbref){

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
