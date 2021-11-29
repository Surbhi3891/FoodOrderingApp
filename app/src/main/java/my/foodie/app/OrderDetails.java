package my.foodie.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetails extends Fragment {
    String orderid,orderby,order_date,items,totalprice,paymenttype,pickup,deliveryaddress,status,deliveryMode,delivery_fee,customerID,chefID,chefname;
    LinearLayout pickUp,Delivery,changeStatusLL,delFee;
    String customerNumber;
    Spinner selectStatus;
    TextView OrderNumber,OrderBy,Date,Items,Totalprice,Paymentype,Pickup,Deliveryaddress,OrderStatus,DeliveryFee,Chefname;
    Button changeStatus;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderDetails() {
        // Required empty public constructor
    }
    public OrderDetails(String orderid, String Name,String Date,String Items,String price,String paymenttype,String chefaddress,String deliveryaddress,String Orderstatus,String deliveryMode,String customerID,String chefID,String customerNumber,String chefname){

        this.orderid = orderid;
        this.orderby= Name;
        this.order_date=Date;
        this.items = Items;
        this.totalprice=price;
        this.paymenttype=paymenttype;
        this.pickup= chefaddress;
        this.deliveryaddress=deliveryaddress;
        this.status=Orderstatus;
        this.deliveryMode=deliveryMode;
        this.customerID=customerID;
        this.chefID= chefID;
        this.customerNumber=customerNumber;
        this.chefname=chefname;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetails newInstance(String param1, String param2) {
        OrderDetails fragment = new OrderDetails();
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
        String CurrentActivity = getActivity().toString().trim();
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_order_details, container, false);
        pickUp=v.findViewById(R.id.linearlayout_pickup);
        Delivery=v.findViewById(R.id.linearlayout_delivery);
        OrderNumber = v.findViewById(R.id.Od_ordernumber);
        OrderBy=v.findViewById(R.id.Od_CustomerName);
        Date=v.findViewById(R.id.Od_date);
        Items=v.findViewById(R.id.Od_items);
        Totalprice=v.findViewById(R.id.Od_price);
        Paymentype=v.findViewById(R.id.Od_paymentType);
        Pickup=v.findViewById(R.id.Od_pickupAddress);
        Deliveryaddress = v.findViewById(R.id.Od_deliveryaddress);
        OrderStatus=v.findViewById(R.id.Od_Status);
        DeliveryFee=v.findViewById(R.id.Od_delivery_fee);
        selectStatus=v.findViewById(R.id.selectstatus);
        changeStatus=v.findViewById(R.id.changeStatusBtn);
        changeStatusLL=v.findViewById(R.id.changeStatusLL);
        Chefname=v.findViewById(R.id.Od_ChefName);
        delFee=v.findViewById(R.id.Od_delfee);
        if(deliveryMode.equals("Delivery")){

            delivery_fee = "$10";
            Deliveryaddress.setText(deliveryaddress);
            Pickup.setText(" ");
            pickUp.setVisibility(View.INVISIBLE);
        }
        if(deliveryMode.equals("Pick Up")){

            Delivery.setVisibility(View.INVISIBLE);
            delFee.setVisibility(View.INVISIBLE);
            Deliveryaddress.setText(" ");
            Pickup.setText(pickup);
        }
        if(CurrentActivity.contains("CustomerView")){

            changeStatusLL.setVisibility(View.INVISIBLE);

        }

        String itemlist = items.replace(";",", ");
        OrderNumber.setText(orderid);
        OrderBy.setText(orderby);
        Date.setText(order_date);
        Items.setText(itemlist);
        Totalprice.setText("$"+totalprice);
        Paymentype.setText(paymenttype);
        Chefname.setText(chefname);
        OrderStatus.setText(status);
        DeliveryFee.setText("$10");

        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.orderStatus));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectStatus.setAdapter(myadapter);
        int position = myadapter.getPosition(status);
        selectStatus.setSelection(position);
        selectStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                status = value.toString().trim();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Orders").child(customerID).child(orderid).child("orderStatus").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseDatabase.getInstance().getReference("Chef_Orders").child(chefID).child(orderid).child("orderStatus").setValue(status);
                        Toast.makeText(getContext(), "Status has been updated", Toast.LENGTH_LONG).show();
                        //getActivity().onBackPressed();
                        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new OrderDetails()).addToBackStack(null).commit();
                        AppCompatActivity activity = (AppCompatActivity) getContext();
                        if (CurrentActivity.contains("RecyclerViewNav")) {
                            activity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainer, new Chef_Orders()).addToBackStack(null).commit();
                        }
                        if (CurrentActivity.contains("CustomerView")) {

                            activity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainer, new Cust_orders()).addToBackStack(null).commit();
                        }
                        String message="Order Status";
                        if(status.equals("Preparing")){

                            message = "Order Status changed! Chef is preparing your order";

                        }
                        if(status.equals("Ready for Pickup")){

                            message = "Order Status changed! Your order is now Ready for pickup.";

                        }
                        if(status.equals("Out for Delivery")){

                            message = "Order Status changed! Your order is now out for delivery.";

                        }
                        if(status.equals("Completed")){

                            message = "Order is Complete! Enjoy your food.";

                        }
                        sendsms(customerNumber,message);


                    }


                });

            }
        });


        return v;

    }
    private void sendsms(String number,String message){

//        String phone = phoneNumber.getText().toString().trim();
        //String message = "Congratulations, You have a new order.";
        try{
            SmsManager smsmanager = SmsManager.getDefault();
            smsmanager.sendTextMessage(number,null,message,null,null);
            //Toast.makeText(this,"Message is sent",Toast.LENGTH_LONG).show();
        }
        catch(Exception e ){

            Toast.makeText(getContext(),"Please change the permission settings of the app to send SMS notification",Toast.LENGTH_LONG).show();
        }



    }

}