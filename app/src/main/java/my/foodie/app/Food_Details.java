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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class Food_Details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String fooditem,Desc,ingd,img,price,cal,chefname,itemID,userid,chefPhoneNumber,acceptingOrders;
    ImageView Fd_Img;
    TextView Fd_name , Fd_desc, Fd_ing, Fd_price, Fd_cal, Fd_chef,Fd_chefContact ;
    //Button Fd_Delete;
    TextView Fd_Delete;
    Button Fd_AddtoCart ;
    ElegantNumberButton itemCount ;
    Button AddtoCart ;
    CheckBox acceptorders;
    int Count =0;
    String itemkey;
    boolean isvalid = false;
    String ChefID;


    public Food_Details() {

    }
    public Food_Details(String fooditem,String Desc, String ingd,String img,String price,String cal,String chefname, String itemID,String userid,String chefPhoneNumber,String acceptingOrders) {

        this.fooditem = fooditem;
        this.Desc = Desc;
        this.ingd = ingd;
        this.img = img;
        this.price=price;
        this.cal = cal;
        this.chefname=chefname;
        this.itemID=itemID;
        this.userid=userid;
        this.chefPhoneNumber=chefPhoneNumber;
        this.acceptingOrders=acceptingOrders;




    }

//    public Food_Details(String foodItem, String foodDesc, String foodIngredients, String image, String foodPrice, String foodCal, String chefName, String itemID, String userid) {
//    }


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
         Fd_chefContact=v.findViewById(R.id.FD_chef_contact);
         Fd_Delete = v.findViewById(R.id.del);
         Fd_AddtoCart = v.findViewById(R.id.addCart);
         itemCount = v.findViewById(R.id.itemNumber);
         AddtoCart = v.findViewById(R.id.addCart);
         acceptorders = v.findViewById(R.id.checkAcceptOrders);
        String currentActivity = getActivity().toString();
        //System.out.println(currentActivity+"----------------------------------------------cuttent Activity");
        if(acceptingOrders.equals("Yes")){
            acceptorders.setChecked(true);
        }else {acceptorders.setChecked(false);

        }

        Fd_name.setText(fooditem);
        Fd_desc.setText(Desc);
        Fd_ing.setText(ingd);
        Fd_price.setText("$"+price);
        Fd_cal.setText(cal);
        Fd_chef.setText(chefname);
        Fd_chefContact.setText(chefPhoneNumber);
        Glide.with(getContext()).load(img).into(Fd_Img);

        if (currentActivity.contains("CustomerView")){
            Fd_Delete.setVisibility(View.INVISIBLE);
            acceptorders.setVisibility(View.INVISIBLE);

        }
        if (currentActivity.contains("RecyclerViewNav")){
            Fd_AddtoCart.setVisibility(View.INVISIBLE);
            itemCount.setVisibility(View.INVISIBLE);

        }
         cartexists();

       acceptorders.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(acceptorders.isChecked()== true){
                   FirebaseDatabase.getInstance().getReference("FoodMenu").child(itemID).child("acceptingOrders").setValue("Yes");

               }
               if(acceptorders.isChecked()==false){
                   FirebaseDatabase.getInstance().getReference("FoodMenu").child(itemID).child("acceptingOrders").setValue("No");


               }

           }
       });

//        if(acceptorders.isChecked()== true){
//            FirebaseDatabase.getInstance().getReference("FoodMenu").child(itemID).child("acceptingOrders").setValue("true");
//
//        }
//        if(acceptorders.isChecked()==false){
//            FirebaseDatabase.getInstance().getReference("FoodMenu").child(itemID).child("acceptingOrders").setValue("false");
//
//
//        }

        AddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(cartexists() == false){
//                addingTocartList();
//                }else{
//                    Toast.makeText(getContext(), "Cart full", Toast.LENGTH_LONG).show();
//                }

                if(Count==0){
                    addingTocartList();
                }else{
                    if(ChefID.equals(chefname)){
                        addingTocartList();
                    }else {
                        Toast.makeText(getContext(), "Items prepared by one chef only can be ordered at a time.", Toast.LENGTH_LONG).show();
                    }
                }
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
                        FirebaseDatabase.getInstance().getReference("FoodMenu").child(itemID).removeValue();
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();


        CartItem cartitem = new CartItem(fooditem, price, chefname, itemCount.getNumber(), saveCurrentDate, saveCurrTime, itemID, userid,chefPhoneNumber);

        FirebaseDatabase.getInstance().getReference("Cart List").child(id).child(itemID).setValue(cartitem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                    Toast.makeText(getContext(), "Item added to the cart", Toast.LENGTH_LONG).show();

                    //AppCompatActivity activity = (AppCompatActivity)getContext();
                    //activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Cust_Home()).addToBackStack(null).commit();


                }
            }
        });




    }

    public boolean cartexists(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        FirebaseDatabase.getInstance().getReference("Cart List").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Count = (int) snapshot.getChildrenCount();
                for(DataSnapshot ds :snapshot.getChildren()) {

                    ChefID = ds.child("chefName").getValue().toString();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




       return isvalid;

    }

    public  void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ChefHomeFragment()).addToBackStack(null).commit();

    }

}