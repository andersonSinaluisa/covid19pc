package com.today.covid_19puntoscriticos.Main.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.today.covid_19puntoscriticos.Adapters.CardAdapter;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.Model.Pages;
import com.today.covid_19puntoscriticos.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CardAdapter adapter;
    private List<Pages> list = new ArrayList<Pages>();
    private GridView gridView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = (GridView) root.findViewById(R.id.gridView);



        cardList(root);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pages p = (Pages) adapter.getItem(position);
                String url = p.getUrl();
                Uri u = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, u);
                startActivity(intent);

            }
        });

        return root;
    }



    private void cardList(final View root) {
        final Firebase db = new Firebase();

        final DatabaseReference reference = db.getmDatabase("Paginas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Pages p = obj.getValue(Pages.class);

                        list.add(p);


                    }

                    adapter = new CardAdapter(list,root.getContext());
                    gridView.setAdapter(adapter);
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
