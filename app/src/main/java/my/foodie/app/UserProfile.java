package my.foodie.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbRef;
    String userID;
    Button Update;
    Button signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef= FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        Update = (Button)findViewById(R.id.updateProfile_btn);
        signOut=(Button) findViewById(R.id.logoutBtn);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserProfile.this,EmailLogin.class));
            }
        });

        final TextView Fname = (TextView) findViewById(R.id.fname_profile);
        final TextView Lname = (TextView) findViewById(R.id.lname_profile);
        final TextView Email = (TextView) findViewById(R.id.email_profile);
        final TextView FullAddress = (TextView) findViewById(R.id.Address_profile);
        final TextView Phone= (TextView) findViewById(R.id.Phone_profile);
        final TextView Type= (TextView) findViewById(R.id.type_profile);

        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){

                    String firstName = userProfile.fname;
                    String lastName = userProfile.lname;
                    String Email_profile = userProfile.email;
                    String Address = userProfile.address;
                    String City = userProfile.city;
                    String State = userProfile.state;
                    String Zip = userProfile.zip;
                    String phoneNumber = userProfile.phone;
                    String type = userProfile.type;


                    Fname.setText(firstName);
                    Lname.setText(lastName);
                    Email.setText(Email_profile);
                    FullAddress.setText(Address + ", " + City + ", " + State + ", " + Zip);
                    Phone.setText(phoneNumber);
                    Type.setText(type);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this,"Something went wrong",Toast.LENGTH_LONG).show();

            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, Update_Profile.class));
            }
        });
    }
}