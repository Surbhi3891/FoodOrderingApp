package my.foodie.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adap extends RecyclerView.Adapter<Adap.myViewholder> {

    Context context;
    ArrayList<model> list;

    public Adap(Context context, ArrayList<model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.singleitem,parent,false);
        return new myViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, int position) {

        model m = list.get(position);
        holder.fooditem.setText(m.getFoodItem());
        holder.price.setText(m.getFoodPrice());
        //holder.ing.setText(m.getFoodIngredients());
        Glide.with(holder.img.getContext()).load(m.getImage()).into(holder.img);
       /* holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("FoodMenu").child(m.foodItem).removeValue();


            }
        });*/

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Food_Details(m.getFoodItem(),m.getFoodDesc(),m.getFoodIngredients(),m.getImage(),m.getFoodPrice(),m.getFoodCal(),m.getChefName(),m.getItemID(),m.getUserid(),m.getChefPhoneNumber())).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewholder extends RecyclerView.ViewHolder{

        TextView fooditem, price;
        ImageView img;
        //ImageButton delete;


        public myViewholder(@NonNull View itemView) {
            super(itemView);


            fooditem = itemView.findViewById(R.id.foodname);
            price = itemView.findViewById(R.id.foodprice);
            //desc = itemView.findViewById(R.id.foodesc);
            img = itemView.findViewById(R.id.foodimageurl);
            //delete = itemView.findViewById(R.id.foodDelete);


        }
    }
}
