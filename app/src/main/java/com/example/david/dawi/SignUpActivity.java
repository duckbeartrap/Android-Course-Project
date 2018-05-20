package com.example.david.dawi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etAge, etEmail, etPassword;
    private RadioGroup rgSex;
    private Button btnSignUp, btnGoBack;
    ProgressDialog dialog;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
            }
        };

        etUsername = (EditText)findViewById(R.id.etSignUpUsername);
        rgSex = (RadioGroup)findViewById(R.id.rgSex);
        etAge = (EditText)findViewById(R.id.etSignUpAge);
        etEmail = (EditText)findViewById(R.id.etSignUpEmail);
        etPassword = (EditText)findViewById(R.id.etSignUpPassword);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

    }

    public void click_register(View v){

        final String name = etUsername.getText().toString();
        final int age = Integer.parseInt(etAge.getText().toString());
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        int selectedId = rgSex.getCheckedRadioButtonId();
        final RadioButton radioButton = (RadioButton)findViewById(selectedId);

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter Username...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Integer.toString(age))) {
            Toast.makeText(getApplicationContext(), "Enter age...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters...", Toast.LENGTH_SHORT).show();
            return;
        }

        if(radioButton.getText() == null){
            return;
        }

        dialog.setMessage("Registering. Please Wait...");
        dialog.show();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,"Sign Up Couldn`t accomplished!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                else {
                    String userId = auth.getCurrentUser().getUid();
                    DatabaseReference currentUserName = FirebaseDatabase.getInstance().getReference().child("Users").child(radioButton.getText().toString()).child(userId).child("name");
                    DatabaseReference currentUserAge = FirebaseDatabase.getInstance().getReference().child("Users").child(radioButton.getText().toString()).child(userId).child("age");
                    currentUserName.setValue(name);
                    currentUserAge.setValue(age);
                }
            }
        });

    }

    public void click_mainpage(View v){
        Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
