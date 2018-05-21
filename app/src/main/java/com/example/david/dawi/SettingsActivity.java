package com.example.david.dawi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private EditText etUserName, etPhone, etDescription;

    private ImageView ivUserImage;

    private Button btnSave, btnGoBack;

    private FirebaseAuth auth;
    private DatabaseReference usersDb;
    private String userId, userImage, userName, userPhone, userDescription, currentUserSex;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etUserName = (EditText)findViewById(R.id.etUserName);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etDescription = (EditText)findViewById(R.id.etDescription);
        ivUserImage = (ImageView)findViewById(R.id.ivUserImage);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnGoBack=(Button)findViewById(R.id.btnBack);

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        usersDb  = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        getUserInformation();
        ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

    }

    private void getUserInformation() {
        usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name")!=null){
                        userName = map.get("name").toString();
                        etUserName.setText(userName);
                    }
                    if (map.get("phone")!=null){
                        userPhone = map.get("phone").toString();
                        etPhone.setText(userPhone);
                    }
                    if (map.get("description")!=null){
                        userDescription = map.get("description").toString();
                        etDescription.setText(userDescription);
                    }
                    if (map.get("gender")!=null){
                        currentUserSex = map.get("gender").toString();
                    }
                    Glide.clear(ivUserImage);
                    if (map.get("profileImageUrl")!=null){
                        userImage = map.get("profileImageUrl").toString();
                        switch (userImage){
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.userimage).into(ivUserImage);
                                break;
                            default:
                                Glide.with(getApplication()).load(userImage).into(ivUserImage);
                                break;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void saveUserInformation() {
        userName = etUserName.getText().toString();
        userPhone = etPhone.getText().toString();
        userDescription = etDescription.getText().toString();

        Map userinfo = new HashMap();
        userinfo.put("name",userName);
        userinfo.put("phone",userPhone);
        userinfo.put("description",userDescription);
        usersDb.updateChildren(userinfo);
        if (resultUri!=null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 15,baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Map userinfo = new HashMap();
                    userinfo.put("profileImageUrl",downloadUrl.toString());
                    usersDb.updateChildren(userinfo);

                    finish();
                    return;
                }
            });
        }else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode== Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            ivUserImage.setImageURI(resultUri);
        }
    }
}
