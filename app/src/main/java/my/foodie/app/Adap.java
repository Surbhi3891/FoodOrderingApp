package my.foodie.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class Adap extends RecyclerView.Adapter<Adap.myViewholder> implements Filterable {

    Context context;
    ArrayList<model> list;
    ArrayList<model> listfull;
    ArrayList<model> noserachlist;
    //ArrayList<model>templist = new ArrayList<>();


    public Adap(Context context, ArrayList<model> list) {
        this.context = context;
        this.list = list;
        //this.listfull= new ArrayList<>(list);

    }
    public void setFullList(ArrayList<model> list){
        this.listfull = list;

    }
    public void setnosearchlist(ArrayList<model> l){

        this.noserachlist=l;
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
        holder.price.setText("$"+m.getFoodPrice());
        holder.chefName.setText(m.getChefName());
        holder.cheflocation.setText(m.getChefAddress());


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
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Food_Details(m.getFoodItem(),m.getFoodDesc(),m.getFoodIngredients(),m.getImage(),m.getFoodPrice(),m.getFoodCal(),m.getChefName(),m.getItemID(),m.getUserid(),m.getChefPhoneNumber(),m.getAcceptingOrders())).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filterdata;
    }

    private Filter filterdata =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<model>templist = new ArrayList<>();
            if(searchText.length()==0 || searchText.isEmpty()){
                //templist.addAll(listfull);
                templist = new ArrayList<>(listfull);
                //templist = (ArrayList<model>)listfull.clone();
            }
            else {
                for ( model item:listfull){
                    if((item.getFoodItem()+item.getChefAddress()).toLowerCase().contains(searchText)){

                        templist.add(item);
                    }

                }
            }
            FilterResults filterresults = new FilterResults();
            filterresults.values = templist;

            return filterresults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterresults) {
            list.clear();
            list.addAll((Collection<? extends model>) filterresults.values);
            notifyDataSetChanged();

        }
    };




    public static class myViewholder extends RecyclerView.ViewHolder{

        TextView fooditem, price,chefName,cheflocation;
        ImageView img;
        //ImageButton delete;


        public myViewholder(@NonNull View itemView) {
            super(itemView);


            fooditem = itemView.findViewById(R.id.foodname);
            price = itemView.findViewById(R.id.foodprice);
            chefName = itemView.findViewById(R.id.foodchef);
            //desc = itemView.findViewById(R.id.foodesc);
            img = itemView.findViewById(R.id.foodimageurl);
            cheflocation = itemView.findViewById(R.id.foodlocation);

            //delete = itemView.findViewById(R.id.foodDelete);


        }
    }

}
