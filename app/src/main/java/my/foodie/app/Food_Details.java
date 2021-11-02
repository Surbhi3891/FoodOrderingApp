package my.foodie.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class Food_Details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String fooditem,Desc,ingd,img,price,cal,chefname;
    ImageView Fd_Img;
    TextView Fd_name , Fd_desc, Fd_ing, Fd_price, Fd_cal, Fd_chef ;
    Button Fd_Delete;
    Button Fd_AddtoCart ;
    ElegantNumberButton itemCount ;
    Button AddtoCart ;

    public Food_Details() {

    }
    public Food_Details(String fooditem,String Desc, String ingd,String img,String price,String cal,String chefname) {

        this.fooditem = fooditem;
        this.Desc = Desc;
        this.ingd = ingd;
        this.img = img;
        this.price=price;
        this.cal = cal;
        this.chefname=chefname;


    }


    public static Food_Details newInstance(String param1, String param2) {
        Food_Details fragment = new Food_Details();
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
      View v= inflater.inflate(R.layout.fragment_food__details, container, false);

         Fd_Img = v.findViewById(R.id.FD_img);
         Fd_name = v.findViewById(R.id.FD_name);
         Fd_desc = v.findViewById(R.id.FD_desc);
         Fd_ing = v.findViewById(R.id.FD_ing);
         Fd_price = v.findViewById(R.id.FD_price);
         Fd_cal = v.findViewById(R.id.FD_cal);
         Fd_chef = v.findViewById(R.id.FD_chef);
         Fd_Delete = v.findViewById(R.id.del);
         Fd_AddtoCart = v.findViewById(R.id.addCart);
         itemCount = v.findViewById(R.id.itemNumber);
         AddtoCart = v.findViewById(R.id.addCart);
        String currentActivity = getActivity().toString();
        //System.out.println(currentActivity+"----------------------------------------------cuttent Activity");

        Fd_name.setText(fooditem);
        Fd_desc.setText(Desc);
        Fd_ing.setText(ingd);
        Fd_price.setText(price);
        Fd_cal.setText(cal);
        Fd_chef.setText(chefname);
        Glide.with(getContext()).load(img).into(Fd_Img);

        if (currentActivity.contains("CustomerView")){
            Fd_Delete.setVisibility(View.INVISIBLE);

        }
        if (currentActivity.contains("RecyclerViewNav")){
            Fd_AddtoCart.setVisibility(View.INVISIBLE);
            itemCount.setVisibility(View.INVISIBLE);

        }

        AddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingTocartList();
            }
        });

        Fd_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Item");
                builder.setMessage("Are you sure to delete? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("FoodMenu").child(fooditem).removeValue();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ChefHomeFragment()).addToBackStack(null).commit();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ChefHomeFragment()).addToBackStack(null).commit();


                    }
                });
                builder.show();

            }
        });






      return  v;
    }

    private void addingTocartList() {
        String saveCurrentDate, saveCurrTime;

        Calendar caldate = Calendar.getInstance();

            SimpleDateFormat currDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currDate.format(caldate.getTime());
            SimpleDateFormat currTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrTime = currTime.format(caldate.getTime());

        //DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("Cart List");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();


        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("FoodItem", Fd_name.getText().toString().trim());
        cartMap.put("Price",Fd_price.getText().toString().trim());
        cartMap.put("ChefName",Fd_chef.getText().toString().trim());
        cartMap.put("Quantity",itemCount.getNumber());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrTime);

        CartItem cartitem = new CartItem(fooditem,price, chefname,itemCount.getNumber(),saveCurrentDate,saveCurrTime);

        //cartListRef.child(userEmail).child("Products").child(fooditem).setValue(cartMap)
        FirebaseDatabase.getInstance().getReference("Cart List").child(userid).child(fooditem).setValue(cartitem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getContext(),"Item added to the cart",Toast.LENGTH_LONG).show();

                    //AppCompatActivity activity = (AppCompatActivity)getContext();
                    //activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Cust_Home()).addToBackStack(null).commit();


                }
            }
        });




    }

    public  void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ChefHomeFragment()).addToBackStack(null).commit();

    }

}