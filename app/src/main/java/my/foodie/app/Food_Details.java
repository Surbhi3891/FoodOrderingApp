package my.foodie.app;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class Food_Details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String fooditem,Desc,ingd,img,price,cal,chefname;

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

        ImageView Fd_Img = v.findViewById(R.id.FD_img);
        TextView Fd_name = v.findViewById(R.id.FD_name);
        TextView Fd_desc = v.findViewById(R.id.FD_desc);
        TextView Fd_ing = v.findViewById(R.id.FD_ing);
        TextView Fd_price = v.findViewById(R.id.FD_price);
        TextView Fd_cal = v.findViewById(R.id.FD_cal);
        TextView Fd_chef = v.findViewById(R.id.FD_chef);
        Button Fd_Delete = v.findViewById(R.id.del);

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

        Fd_name.setText(fooditem);
        Fd_desc.setText(Desc);
        Fd_ing.setText(ingd);
        Fd_price.setText(price);
        Fd_cal.setText(cal);
        Fd_chef.setText(chefname);
        Glide.with(getContext()).load(img).into(Fd_Img);




      return  v;
    }

    public  void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ChefHomeFragment()).addToBackStack(null).commit();

    }

}