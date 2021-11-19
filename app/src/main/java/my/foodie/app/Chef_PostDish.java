package my.foodie.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Chef_PostDish#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Chef_PostDish extends Fragment {

    ImageView imgBtn;
    Button postItem;
    EditText foodItem, foodIngridients,foodDesc,foodCalories,foodPrice;
    Uri imgUri;
    private Uri imgCrop;
    FirebaseDatabase db;
    DatabaseReference dbref, userref;
    FirebaseAuth fbAuth;
    UploadTask uploadTask;

    FirebaseStorage storage;
    StorageReference storageref,ref;
    String userID,rand;
    String chefName, state,city,fooditem,desc, ingredient,calories, price,itemID,lname,ChefphoneNumber;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Chef_PostDish() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chef_PostDish.
     */
    // TODO: Rename and change types and number of parameters
    public static Chef_PostDish newInstance(String param1, String param2) {
        Chef_PostDish fragment = new Chef_PostDish();
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
        View Vw = inflater.inflate(R.layout.fragment_chef__post_dish, container,false);


        storageref = FirebaseStorage.getInstance().getReference("FoodPictures");
        foodItem = Vw.findViewById(R.id.food_item);
        foodDesc = Vw.findViewById(R.id.description);
        foodIngridients = Vw.findViewById(R.id.ingredients);
        foodCalories = Vw.findViewById(R.id.calories);
        foodPrice= Vw.findViewById(R.id.price);
        imgBtn = Vw.findViewById(R.id.camera_btn);
        postItem=Vw.findViewById(R.id.post_food);
        fbAuth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference("FoodMenu");
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.launch("image/*");
            }
        });



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        userref = FirebaseDatabase.getInstance().getReference("Users");
        userref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User data = snapshot.getValue(User.class);
                state = data.state;
                city = data.city;
                chefName = data.fname + " " +data.lname;
                lname = data.lname;
                ChefphoneNumber = data.phone;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

     postItem.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             fooditem = foodItem.getText().toString().trim();
             desc=foodDesc.getText().toString().trim();
             ingredient = foodIngridients.getText().toString().trim();
             calories=foodCalories.getText().toString().trim();
             price=foodPrice.getText().toString().trim();
             itemID = lname + String.valueOf(1001+ (int)(Math.random()*8999));

             //if(isValid()){

                 uploadData();
                 getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new ChefHomeFragment()).addToBackStack(null).commit();
             //}

         }
     });


     return Vw;
    }

    private void uploadData() {

        ref = storageref.child(fooditem+ " "+chefName);
        uploadTask = ref.putFile(imgUri);
        Task<Uri> urltask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return  ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if(task.isSuccessful()){
                   Uri downloadUri = task.getResult();
                    FoodMenu menuitem = new FoodMenu(chefName,fooditem,ingredient,desc,calories,price,String.valueOf(downloadUri),rand,userID,itemID,ChefphoneNumber);
                    FirebaseDatabase.getInstance().getReference("FoodMenu").child(itemID).setValue(menuitem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //progressDialog.dismiss();
                            Toast.makeText(getActivity(),"Food Item posted.",Toast.LENGTH_LONG).show();

                        }
                   });

                }

            }
        });

    }

    private void uploadPicture(){

        if(imgUri!= null){
            final ProgressDialog progressDialog =new ProgressDialog(getActivity());
            progressDialog.setTitle("Image is uploading...");
            progressDialog.show();
            rand = UUID.randomUUID().toString();
            ref= storageref.child(rand);
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FoodMenu menuitem = new FoodMenu(chefName,fooditem,ingredient,desc,calories,price,String.valueOf(uri),rand,userID,itemID,ChefphoneNumber);
                            FirebaseDatabase.getInstance().getReference("FoodMenu").child(state).child(city).child(userID).setValue(menuitem).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),"Food Item posted.",Toast.LENGTH_LONG).show();

                                }
                            });

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();


                }
            });
        }

    }

    private boolean isValid(){
        boolean isValidfoodItem=false, isValidDesc=false,isValidIng=false,isValidCal=false,isValidPrice=false,isValidimg = false,allValid=false;
        if(foodItem.getText().toString().isEmpty())
        {
            foodItem.setError("Enter food item name");
        }
        else {isValidfoodItem = true ;
        }

        if(foodDesc.getText().toString().isEmpty())
        {
            foodDesc.setError("Enter food description");
        }
        else {isValidDesc = true ;
        }
        if(foodIngridients.getText().toString().isEmpty())
        {
            foodIngridients.setError("Enter food ingredients");
        }
        else {isValidIng = true ;
        }
        if(foodCalories.getText().toString().isEmpty())
        {
            foodCalories.setError("Enter food item calories");
        }
        else {isValidCal = true ;
        }
        if(foodPrice.getText().toString().isEmpty())
        {
            foodPrice.setError("Enter food item price");
        }
        else {isValidPrice = true ;
        }
        if (imgUri != null){
            isValidimg= true;
        }

   allValid =(isValidfoodItem && isValidDesc && isValidIng && isValidCal &&isValidPrice && isValidimg);
        return allValid;
    }

    ActivityResultLauncher<String> act = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            try {if(result != null){

                imgBtn.setImageURI(result);
                imgUri = result;
            } }catch(Exception e ){
                Toast.makeText(getActivity(),"Picture not selected", Toast.LENGTH_LONG).show();
            }

        }
    });
}