package com.nawinc27.mac.blooddonorfrontend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nawinc27.mac.blooddonorfrontend.history.History;
import com.nawinc27.mac.blooddonorfrontend.history.HistoryAdapter;
import com.nawinc27.mac.blooddonorfrontend.utility.Extensions;
import com.nawinc27.mac.blooddonorfrontend.utility.SessionManager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class HistoryFragment extends Fragment {

    private ArrayList<History> histories = new ArrayList<History>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private TextView name;
    private TextView times;
    private SessionManager session;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mainpage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        session = new SessionManager(getContext());

        if (!session.checkLogin()) {
            Extensions.goTo(getActivity(), new LoginFragment());
        } else {
            showHistory();
            showUserProfile();
            initLogoutBtn();
        }

        TextView goto_from_btn = getActivity().findViewById(R.id.goto_from_btn);
        goto_from_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Extensions.goTo(getActivity(), new FormFragment());
            }
        });
    }

    public void showUserProfile(){
        //This function use to show User profile by setText in fragment_history
        name = getView().findViewById(R.id.main_name);
        myRef = database.getReference("/donor/profile/" + session.getUsername());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nameStr = dataSnapshot.child("firstname").child("0").getValue(String.class)
                        + " " + dataSnapshot.child("lastname").child("0").getValue(String.class);
                name.setText(nameStr);
                Log.i("HISTORY", "RETREIVE USER PROFILE SUCCESS");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("HISTORY", "ERROR CAN'T RETREIVE USER PROFILE FROM FIREBASE");
            }
        });
    }

    public void showHistory(){
        //This function use to show History that get from firebase and show on LiseView
        myRef = database.getReference("/donor/blood_donation/" + session.getUsername());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListView historyList = getView().findViewById(R.id.main_history_donor);
                final HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(),
                        R.layout.list_item_history, histories);
                historyList.setAdapter(historyAdapter);
                histories.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    histories.add(child.getValue(History.class));
                }
                Log.i("TESTTTT " , histories.size()+"");
                Collections.reverse(histories);
                times = getView().findViewById(R.id.main_number_donate);
                times.setText(""+histories.size());

                Log.i("HISTORY", "RETREIVE HISTORY SUCCESS");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("HISTORY", "ERROR CANNOT RETREIVE HISTORY FROM FIREBASE");
            }
        });
    }

    public int getAge(String dateOfBirth){
        int age;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String[] year = dateOfBirth.split("-");
        age = Integer.parseInt(sdf.format(timestamp)) - Integer.parseInt(year[0]);
        return age;
    }

    public void initLogoutBtn() {
        ImageView logoutBtn = getView().findViewById(R.id.main_btn_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                Log.d("HISTORY", "Goto LoginFragment");
                Extensions.goTo(getActivity(), new LoginFragment());
            }
        });
    }
}