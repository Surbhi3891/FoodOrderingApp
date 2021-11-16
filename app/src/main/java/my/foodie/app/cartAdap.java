package my.foodie.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class cartAdap extends RecyclerView.Adapter<cartAdap.cartViewholder> {

    Context contextCart;
    ArrayList<CartModel> cartList;
    //int Subtotal = 0;

    public cartAdap(Context contextCart, ArrayList<CartModel> cartList) {
        this.contextCart = contextCart;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public cartAdap.cartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextCart).inflate(R.layout.cartitem,parent,false);

        return new cartViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull cartAdap.cartViewholder holder, int position) {
           CartModel c = cartList.get(position);
           holder.foodName.setText(c.getFoodName());
           holder.price.setText(c.getPrice());
           holder.quantity.setNumber(c.getQuantity());





           holder.deleteitem.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                   FirebaseDatabase.getInstance().getReference("Cart List").child(uid).child(c.getFoodName()).removeValue();
                   AppCompatActivity activity = (AppCompatActivity)v.getContext();
                   activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Cust_Cart()).commit();
               }
           });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class cartViewholder extends RecyclerView.ViewHolder{

        TextView foodName, price;
        ElegantNumberButton quantity;
        ImageButton deleteitem;

        public cartViewholder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            quantity=itemView.findViewById(R.id.itemQuantity);
            deleteitem=itemView.findViewById(R.id.deleteCartitem);
        }
    }
}
