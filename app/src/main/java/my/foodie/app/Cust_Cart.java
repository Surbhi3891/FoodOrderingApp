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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.pyplcheckout.pojo.To;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Cust_Cart extends Fragment {


    RecyclerView recview_cart;
    DatabaseReference data;
    cartAdap cartadapter;
    ArrayList<CartModel> cart;
    int TotalAmount;
    TextView total,header;
    RadioGroup deliverymode;
    RadioButton radioButton;
    TextView tax;
    Button continueBtn;
    LinearLayout radio,totalLL,taxLL;
    public static float TotalBill = 0;
    public static String deliveryMode;
    public static float SubTotalAmount = 0;

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
        radio = V.findViewById(R.id.modeofdelivery);
        totalLL=V.findViewById(R.id.subtotalLL);
        taxLL=V.findViewById(R.id.taxLL);
        recview_cart.setHasFixedSize(true);
        recview_cart.setLayoutManager(new LinearLayoutManager(getContext()));
        cart = new ArrayList<>();
        cartadapter = new cartAdap(getContext(),cart);
        recview_cart.setAdapter(cartadapter);
        total = V.findViewById(R.id.TotalAmount);
        header=V.findViewById(R.id.itemList);
        tax = V.findViewById(R.id.Tax);
        continueBtn = V.findViewById(R.id.ContinueBtn);
        deliverymode=V.findViewById(R.id.deliverymode);


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedID = deliverymode.getCheckedRadioButtonId();
                radioButton = V.findViewById(selectedID);
                deliveryMode = radioButton.getText().toString().trim();
                startActivity(new Intent(getActivity(),ConfirmDetails.class));
            }
        });


        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TotalBill = 0;

                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    CartModel cmd = datasnapshot.getValue(CartModel.class);
                    cart.add(cmd);
                    final DecimalFormat df = new DecimalFormat("0.00");

                        TotalBill = Float.parseFloat(df.format(TotalBill+ (Float.valueOf(cmd.getPrice()) * Float.valueOf(cmd.getQuantity()))));


                        float taxest = Float.parseFloat(df.format((float) (0.0625 * TotalBill)));
                        total.setText(String.valueOf(TotalBill) + "$");
                        tax.setText(String.valueOf(taxest) + "$");

                        SubTotalAmount = Float.parseFloat(df.format(TotalBill + taxest ));
                }

                cartadapter.notifyDataSetChanged();
                if(cart.size()==0){
                    header.setText("There are no items in your cart.");
                    continueBtn.setVisibility(View.INVISIBLE);
                    totalLL.setVisibility(View.INVISIBLE);
                    taxLL.setVisibility(View.INVISIBLE);
                    deliverymode.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return V;
    }


}