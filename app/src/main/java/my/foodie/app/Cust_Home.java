package my.foodie.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Cust_Home extends Fragment{

    RecyclerView recview_cust;
    DatabaseReference data;
    Adap cust_adapter;
    ArrayList<model> cust_menu;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Cust_Home() {
        // Required empty public constructor
    }


    public static Cust_Home newInstance(String param1, String param2) {
        Cust_Home fragment = new Cust_Home();
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
        View v= inflater.inflate(R.layout.fragment_cust__home, container, false);
        setHasOptionsMenu(true);
        recview_cust = v.findViewById(R.id.Cust_menu);
        data = FirebaseDatabase.getInstance().getReference("FoodMenu");
        recview_cust.setHasFixedSize(true);
        recview_cust.setLayoutManager(new LinearLayoutManager(getContext()));
        cust_menu = new ArrayList<>();
        cust_adapter = new Adap(getContext(),cust_menu);
        recview_cust.setAdapter(cust_adapter);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    model md = dataSnapshot.getValue(model.class);
                    if(md.getAcceptingOrders().equals("Yes")){
                    cust_menu.add(md);
                    }
                }

                cust_adapter.setFullList(new ArrayList<>(cust_menu));
                cust_adapter.setnosearchlist(new ArrayList<>(cust_menu));
                cust_adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;

    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
           inflater.inflate(R.menu.search_menu,menu);
           MenuItem item = menu.findItem(R.id.action_search);
           SearchView searchView = (SearchView) item.getActionView();
           searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String query) {
                   return false;
               }

               @Override
               public boolean onQueryTextChange(String newText) {

                   cust_adapter.getFilter().filter(newText);
                   return false;
               }
           }) ;


        super.onCreateOptionsMenu(menu, inflater);

    }


}