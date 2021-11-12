package my.foodie.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Cust_Cart extends Fragment {


    RecyclerView recview_cart;
    DatabaseReference data;
    cartAdap cartadapter;
    ArrayList<CartModel> cart;
    int TotalAmount;
    TextView total;
    TextView tax;
    int TotalBill;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Cust_Cart() {

    }


    public static Cust_Cart newInstance(String param1, String param2) {
        Cust_Cart fragment = new Cust_Cart();
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

        View V = inflater.inflate(R.layout.fragment_cust__cart, container, false);
        recview_cart = V.findViewById(R.id.cartlist);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        data = FirebaseDatabase.getInstance().getReference("Cart List").child(userid);

        recview_cart.setHasFixedSize(true);
        recview_cart.setLayoutManager(new LinearLayoutManager(getContext()));
        cart = new ArrayList<>();
        cartadapter = new cartAdap(getContext(),cart);
        recview_cart.setAdapter(cartadapter);
        total = V.findViewById(R.id.TotalAmount);
        tax = V.findViewById(R.id.Tax);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    CartModel cmd = datasnapshot.getValue(CartModel.class);
                    cart.add(cmd);
                    TotalBill = TotalBill + Integer.valueOf(cmd.getPrice())*Integer.valueOf(cmd.getQuantity());
                    total.setText(String.valueOf(TotalBill) + "$");
                    float taxest = (float) (0.0625*TotalBill);
                    tax.setText(String.valueOf(taxest)+ "$");
                }

                cartadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return V;
    }


}