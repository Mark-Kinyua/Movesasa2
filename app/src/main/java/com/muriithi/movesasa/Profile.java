package com.muriithi.movesasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    TextInputLayout fullName, email,phone_number, pass_word,birth_date;
    TextView fullNameLabel, usernameLabel;
    DatabaseReference reference;

    String full_name,email_add,birth,phone, user_name,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        fullName = findViewById(R.id.profile_full_name);
        email = findViewById(R.id.profile_email);
        phone_number = findViewById(R.id.profile_phone);
        pass_word = findViewById(R.id.profile_password);
        birth_date = findViewById(R.id.profile_date);
        fullNameLabel = findViewById(R.id.profile_full);
        usernameLabel = findViewById(R.id.profile_user_name);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        //shows all the users data
        showAllData();
    }

    private void showAllData() {
        Intent intent = getIntent();

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails = sessionManager.getUserDetails();

        full_name = userDetails.get(SessionManager.KEY_NAME);
        email_add = userDetails.get(SessionManager.KEY_EMAIL);
        birth = userDetails.get(SessionManager.KEY_DATE);
        phone = userDetails.get(SessionManager.KEY_PHONE);
        user_name = userDetails.get(SessionManager.KEY_USER);
        pass = userDetails.get(SessionManager.KEY_PASSWORD);

//        _USERNAME = intent.getStringExtra("username");
//        _NAME = intent.getStringExtra("name");
//        _DATE = intent.getStringExtra("date");
//        _EMAIL = intent.getStringExtra("email");
//        _PHONE = intent.getStringExtra("phone");
//        _PASSWORD = intent.getStringExtra("password");

        fullNameLabel.setText(full_name);
        usernameLabel.setText(user_name);
        fullName.getEditText().setText(full_name);
        email.getEditText().setText(email_add);
        birth_date.getEditText().setText(birth);
        phone_number.getEditText().setText(phone);
        pass_word.getEditText().setText(pass);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Profile.this, Dashboard.class);
        startActivity(intent);
        finish();

    }

    public void updateUser(View view) {
        if(isNameChanged() | isPasswordChanged() | isDateChanged() | isEmailChanged() | isPhoneNumberChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Data is similar and cannot be updated", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isPhoneNumberChanged() {
        if(!phone_number.equals(phone_number.getEditText().getText().toString())){
            reference.child(user_name).child("phone").setValue(phone_number.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        if(!email.equals(email.getEditText().getText().toString())){
            reference.child(user_name).child("email").setValue(email.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isDateChanged() {
        if(!birth_date.equals(birth_date.getEditText().getText().toString())){
            reference.child(user_name).child("date").setValue(birth_date.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if(!pass_word.equals(pass_word.getEditText().getText().toString())){
            reference.child(user_name).child("password").setValue(pass_word.getEditText().getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!fullName.equals(fullName.getEditText().getText().toString())){
            reference.child(user_name).child("name").setValue(fullName.getEditText().getText().toString());
            reference.child(user_name).child("name").setValue(fullNameLabel.getText().toString());
            return true;
        }else{
            return false;
        }
    }

}
