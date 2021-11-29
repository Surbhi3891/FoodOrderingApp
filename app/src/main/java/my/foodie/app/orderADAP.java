package my.foodie.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class orderADAP extends RecyclerView.Adapter<orderADAP.orderViewholder> {

    Context contextOrder;
    ArrayList<OrderModel> orderList;
    //int Subtotal = 0;

    public orderADAP(Context contextOrder, ArrayList<OrderModel> orderList) {
        this.contextOrder = contextOrder;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public orderADAP.orderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextOrder).inflate(R.layout.orderitem,parent,false);

        return new orderViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull orderADAP.orderViewholder holder, int position) {
           OrderModel c = orderList.get(position);
           holder.orderID.setText(c.getOrderid());
           holder.orderTotal.setText("$"+c.getPrice());
           holder.orderDate.setText(c.getDate());
           holder.orderStatus.setText(c.getOrderStatus());
           holder.orderID.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   AppCompatActivity activity = (AppCompatActivity)v.getContext();
                   activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new OrderDetails(c.getOrderid(),c.getName(),c.getDate(),c.getItems(),c.getPrice(),c.getPaymenttype(),c.getChefAddress(),c.getAddress(),c.getOrderStatus(),c.getDeliverymode(),c.getCustomerid(),c.chefID,c.getCustomerPhoneNumber(),c.getChefname())).addToBackStack(null).commit();

               }
           });





//           holder.deleteitem.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                   FirebaseDatabase.getInstance().getReference("Cart List").child(uid).child(c.getItemID()).removeValue();
//                   AppCompatActivity activity = (AppCompatActivity)v.getContext();
//                   activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Cust_Cart()).commit();
//               }
//           });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class orderViewholder extends RecyclerView.ViewHolder{

        TextView orderID, orderTotal,orderDate,orderStatus;


        public orderViewholder(@NonNull View itemView) {
            super(itemView);

            orderID = itemView.findViewById(R.id.orderId);
            orderTotal = itemView.findViewById(R.id.orderTotal);
            orderDate=itemView.findViewById(R.id.orderdate);
            orderStatus=itemView.findViewById(R.id.orderstatus);
        }
    }
}
