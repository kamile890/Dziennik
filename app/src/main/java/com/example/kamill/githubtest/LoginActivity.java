package com.example.kamill.githubtest;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText email_field;
    private EditText password_field;
    private ProgressDialog proggres_dialog;
    private FirebaseAuth firebaseauth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.button);
        email_field = findViewById(R.id.email);
        password_field = findViewById(R.id.password);
        firebaseauth = FirebaseAuth.getInstance();
        button.setOnClickListener(this);

        proggres_dialog = new ProgressDialog(this);



    }

    public void go_to_profile(){
        Intent intent = new Intent(getApplicationContext(),Profile.class);
        startActivity(intent);
    }

    private void registerUser(){
        String email = email_field.getText().toString().trim();
        String password = password_field.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }

        proggres_dialog.setMessage("Registering User");
        proggres_dialog.show();

        firebaseauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Udało się zalogować", Toast.LENGTH_SHORT).show();
                            finish();
                            go_to_profile();
                        }else{
                            Toast.makeText(getApplicationContext(), "Nie udało się zalogować", Toast.LENGTH_SHORT).show();
                            finish();
                            go_to_profile();
                        }
                    }
                });

    }




    @Override
    public void onClick(View v) {
        if(v ==button){
            registerUser();
        }


    }
}

