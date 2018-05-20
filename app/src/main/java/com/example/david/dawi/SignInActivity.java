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

public class SignInActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail = findViewById(R.id.etSignInEmail);
        etPassword = findViewById(R.id.etSignInPassword);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
    }


    public void click_loginin(View v){
        dialog.setMessage("Logging In. Please Wait...");
        dialog.show();

        auth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"User Logged In Successfully",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignInActivity.this, UserPage.class);
                            startActivity(i);
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"User Could not be found", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void click_mainpage(View v){
        Intent i = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(i);
    }
}
