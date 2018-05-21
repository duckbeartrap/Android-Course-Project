package com.example.david.dawi.Matches;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.david.dawi.MainActivity;
import com.example.david.dawi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    private RecyclerView myrecyclerview;
    private RecyclerView.Adapter mymatchesadapter;
    private RecyclerView.LayoutManager mymatcheslayoutmanager;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myrecyclerview = (RecyclerView)findViewById(R.id.myrecyclerview);
        myrecyclerview.setNestedScrollingEnabled(false);
        myrecyclerview.setHasFixedSize(true);
        mymatcheslayoutmanager = new LinearLayoutManager(MatchesActivity.this );
        myrecyclerview.setLayoutManager(mymatcheslayoutmanager);
        mymatchesadapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this);
        myrecyclerview.setAdapter(mymatchesadapter);

        getUserMatchId();



    }

    private void getUserMatchId() {

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("connection").child("match");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match :dataSnapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String userid = dataSnapshot.getKey();
                    String name = "";
                    String age = "";
                    String phone = "";
                    String gender = "";
                    String profileImageUrl = "";

                    if (dataSnapshot.child("name").getValue()!=null){
                        name = dataSnapshot.child("name").getValue().toString();
                    }
                    if (dataSnapshot.child("age").getValue()!=null){
                        age = dataSnapshot.child("age").getValue().toString();
                    }
                    if (dataSnapshot.child("phone").getValue()!=null){
                        phone = dataSnapshot.child("phone").getValue().toString();
                    }
                    if (dataSnapshot.child("gender").getValue()!=null){
                        gender = dataSnapshot.child("gender").getValue().toString();
                    }
                    if (dataSnapshot.child("profileImageUrl").getValue()!=null){
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }

                    MatchesObject obj = new MatchesObject(userid,name,age,phone,gender,profileImageUrl);
                    resultMatches.add(obj);
                    mymatchesadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private ArrayList<MatchesObject> resultMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches() {
        return resultMatches;
    }


    public void click_mainpage(View view) {
        Intent i = new Intent(MatchesActivity.this,MainActivity.class);
        startActivity(i);
        return;
    }
}
