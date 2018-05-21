package com.example.david.dawi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.david.dawi.Matches.MatchesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private cards cards_data[];

    private arrayAdapter arrayAdapter;
    private int i;
    private FirebaseAuth auth;
    private String currentuserID;
    private String currentUserSex, oppositeSex;
    private DatabaseReference usersDd;

    ListView listView;
    List<cards> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDd = FirebaseDatabase.getInstance().getReference().child("Users");

        auth = FirebaseAuth.getInstance();
        currentuserID = auth.getCurrentUser().getUid();

        checkUserSex();

        rowItems = new ArrayList<cards>();

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems );
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                cards object = (cards) dataObject;
                String userID = object.getUserId();
                usersDd.child(userID).child("connection").child("deny").child(currentuserID).setValue(true);
                Toast.makeText(MainActivity.this, "Deny!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards object = (cards) dataObject;
                String userID = object.getUserId();
                usersDd.child(userID).child("connection").child("match").child(currentuserID).setValue(true);
                isConnectionMatch(userID);
                Toast.makeText(MainActivity.this, "Like!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Click!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void isConnectionMatch(String userID) {
        DatabaseReference currentUserconnectionDb = usersDd.child(currentuserID).child("connection").child("match").child(userID);
        currentUserconnectionDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Toast.makeText(MainActivity.this,"New Connection", Toast.LENGTH_LONG).show();

                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    usersDd.child(dataSnapshot.getKey()).child("connection").child("match").child(currentuserID).child("ChatID").setValue(key);
                    usersDd.child(currentuserID).child("connection").child("match").child(dataSnapshot.getKey()).child("ChatID").setValue(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void checkUserSex(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb= usersDd.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("gender").getValue() != null){
                        currentUserSex = dataSnapshot.child("gender").getValue().toString();
                        switch (currentUserSex){
                            case "Male":
                                oppositeSex = "Female";
                                break;
                            case "Female":
                                oppositeSex="Male";
                                break;
                        }
                        getOppositeSexUser();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getOppositeSexUser(){
        usersDd.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("gender").getValue()!=null) {
                    if (dataSnapshot.exists() && !dataSnapshot.child("connection").child("match").hasChild(currentuserID) && dataSnapshot.child("gender").getValue().toString().equals(oppositeSex)) {
                        String profileImageUrl = "default";
                        if (!dataSnapshot.child("profileImageUrl").getValue().toString().equals("default")) {
                            profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        }
                        cards Item = new cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("age").getValue().toString(), profileImageUrl);
                        rowItems.add(Item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void onclick_signout(View v){
        auth.signOut();
        Intent i = new Intent(MainActivity.this,SignInActivity.class);
        startActivity(i);
        finish();
        return;
    }

    public void onclick_settings(View v){
        Intent i = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(i);
        return;
    }

    public void onclick_matches(View view) {
        Intent i = new Intent(MainActivity.this,MatchesActivity.class);
        startActivity(i);
        return;
    }
}