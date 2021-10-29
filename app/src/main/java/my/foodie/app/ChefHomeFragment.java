package my.foodie.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChefHomeFragment extends Fragment {

    RecyclerView recview;
    DatabaseReference data;
    Adap myadapter;
    ArrayList<model> food;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChefHomeFragment() {

    }


    public static ChefHomeFragment newInstance(String param1, String param2) {
        ChefHomeFragment fragment = new ChefHomeFragment();
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
       View root= inflater.inflate(R.layout.fragment_chef_home, container, false);

       recview = root.findViewById(R.id.menulist);
       data = FirebaseDatabase.getInstance().getReference("FoodMenu");
       recview.setHasFixedSize(true);
       recview.setLayoutManager(new LinearLayoutManager(getContext()));
       food = new ArrayList<>();
       myadapter = new Adap(getContext(),food);
       recview.setAdapter(myadapter);

       data.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for(DataSnapshot dataSnapshot : snapshot.getChildren()){



                   model md = dataSnapshot.getValue(model.class);
                   food.add(md);
               }
               myadapter.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       return root;
    }
}