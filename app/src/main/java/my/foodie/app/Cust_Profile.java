package my.foodie.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cust_Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cust_Profile extends Fragment {

    DatabaseReference dbRef ;
    TextView name, email, address, phone, usertype;
    Button UpdateProfile,viewReport;
    TextView logOutprofile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cust_Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cust_Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Cust_Profile newInstance(String param1, String param2) {
        Cust_Profile fragment = new Cust_Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chef_profile, container, false);

        View V = inflater.inflate(R.layout.fragment_cust__profile, container,false);
        name = V.findViewById(R.id.fullname_profile);
        email=V.findViewById(R.id.email_profile);
        address=V.findViewById(R.id.location_profile);
        phone=V.findViewById(R.id.mobile_profile);
        viewReport = V.findViewById(R.id.view_report);
        //usertype=V.findViewById(R.id.logintype_profile);
        UpdateProfile=V.findViewById(R.id.update_btn);
        logOutprofile=V.findViewById(R.id.signOut_Btn);
        viewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ViewReport.class));

            }
        });
        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Update_Profile.class);
                intent.putExtra("LoginType","Customer");
                startActivity(intent);

            }
        });
        logOutprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),EmailLogin.class));
                getActivity().finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        dbRef= FirebaseDatabase.getInstance().getReference("Users");
        dbRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {

                    String fullname = userProfile.fname+ " " + userProfile.lname;
                    String phone_number = userProfile.phone;
                    String loc = userProfile.address +", " +userProfile.city+", " +userProfile.state+", " +userProfile.zip;
                    String emailID = userProfile.email;
                    //String userType = userProfile.type;
                    name.setText(fullname);
                    email.setText(emailID);
                    phone.setText(phone_number);
                    address.setText(loc);
                    //usertype.setText(userType);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return V;
    }
}