package com.example.david.dawi.Chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.david.dawi.MainActivity;
import com.example.david.dawi.Matches.MatchesActivity;
import com.example.david.dawi.Matches.MatchesAdapter;
import com.example.david.dawi.Matches.MatchesObject;
import com.example.david.dawi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {
    private RecyclerView myrecyclerview;
    private RecyclerView.Adapter mychattingadapter;
    private RecyclerView.LayoutManager mychattinglayoutmanager;

    private EditText mySendEditText;
    private Button mySendButton;

    private String currentUserID, matchID, chatID;

    DatabaseReference myDatabaseUser, myDatabaseChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        matchID = getIntent().getExtras().getString("matchId");

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("connection").child("match").child(matchID).child("ChatID");
        myDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        getChatID();

        myrecyclerview = (RecyclerView)findViewById(R.id.myrecyclerview);
        myrecyclerview.setNestedScrollingEnabled(false);
        myrecyclerview.setHasFixedSize(false);
        mychattinglayoutmanager = new LinearLayoutManager(ChattingActivity.this );
        myrecyclerview.setLayoutManager(mychattinglayoutmanager);
        mychattingadapter = new ChattingAdapter(getDataSetChat(), ChattingActivity.this);
        myrecyclerview.setAdapter(mychattingadapter);

        mySendEditText =findViewById(R.id.etMessage);
        mySendButton = findViewById(R.id.btnSend);

        mySendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    private void sendMessage() {
        String messageText = mySendEditText.getText().toString();
        if (!messageText.isEmpty()){
            DatabaseReference newMessageDB = myDatabaseChat.push();

            Map newMessage = new HashMap();
            newMessage.put("fromuser", currentUserID);
            newMessage.put("text", messageText);

            newMessageDB.setValue(newMessage);
        }
        mySendEditText.setText(null);
    }

    private void getChatID(){
        myDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    chatID = dataSnapshot.getValue().toString();
                    myDatabaseChat = myDatabaseChat.child(chatID);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getChatMessages() {
        myDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()){
                    String message = null;
                    String createdbyuser = null;

                    if (dataSnapshot.child("text").getValue()!=null){
                        message = dataSnapshot.child("text").getValue().toString();
                    }
                    if (dataSnapshot.child("fromuser").getValue()!=null){
                        createdbyuser = dataSnapshot.child("fromuser").getValue().toString();
                    }

                    if (message!=null && createdbyuser!=null){
                        Boolean currentUserBoolean = false;
                        if (createdbyuser.equals(currentUserID)){
                            currentUserBoolean=true;
                        }
                        ChattingObject newMessage = new ChattingObject(message,currentUserBoolean);
                        resultChats.add(newMessage);
                        mychattingadapter.notifyDataSetChanged();
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

    private ArrayList<ChattingObject> resultChats = new ArrayList<ChattingObject>();
    private List<ChattingObject> getDataSetChat() {
        return resultChats;
    }

    public void click_mainpage(View view) {
        Intent i = new Intent(ChattingActivity.this,MainActivity.class);
        startActivity(i);
        return;
    }
}
