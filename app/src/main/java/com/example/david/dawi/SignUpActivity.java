package com.example.david.dawi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername, etAge, etEmail, etPassword;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUsername = findViewById(R.id.etSignUpUsername);
        etAge = findViewById(R.id.etSignUpAge);
        etEmail = findViewById(R.id.etSignUpEmail);
        etPassword = findViewById(R.id.etSignUpPassword);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

    }

    public void click_register(View v){
        dialog.setMessage("Registering. Please Wait...");
        dialog.show();

        auth.createUserWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"User has been successfully Registered", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignUpActivity.this, UserPage.class);
                            startActivity(i);
                        }
                        else{
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"User could not be registered", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void click_mainpage(View v){
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
    }
}
