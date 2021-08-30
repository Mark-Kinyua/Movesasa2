package com.muriithi.movesasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp, loginBtn;
    TextView logo_text, slogan_text;
    TextInputLayout user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.no_account);
        logo_text = findViewById(R.id.logo_name);
        slogan_text = findViewById(R.id.slogan);
        user = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);
    }

    public void loginUser(View view) {
        if(!validateUsername() | !validatePassword()){
            return;
        }else{
            isUserExist();
        }
    }

    private void isUserExist() {
        final String userEnteredUser = user.getEditText().getText().toString().trim();
        final String userEnteredPass = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUser);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    user.setError(null);
                    user.setErrorEnabled(false);

                    String passFromDB = dataSnapshot.child(userEnteredUser).child("password").getValue(String.class);

                    if(passFromDB.equals(userEnteredPass)){

                        password.setError(null);
                        password.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredUser).child("name").getValue(String.class);
                        String dateFromDB = dataSnapshot.child(userEnteredUser).child("date").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUser).child("email").getValue(String.class);
                        String phoneFromDB = dataSnapshot.child(userEnteredUser).child("phone").getValue(String.class);
                        String usertypeFromDB = dataSnapshot.child(userEnteredUser).child("usertype").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUser).child("username").getValue(String.class);

                        // Add If statement for mover dashboard or client dashboard

                        if (usertypeFromDB.equals("Client")) {
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class); // Go to client dashboard
                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("date", dateFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("phone", phoneFromDB);
                            intent.putExtra("usertype", usertypeFromDB);
                            intent.putExtra("username", usernameFromDB);
                            intent.putExtra("password", passFromDB);
                            SessionManager sessionManager = new SessionManager(Login.this);
                            sessionManager.createLoginSession(nameFromDB,dateFromDB,emailFromDB,phoneFromDB,usertypeFromDB,usernameFromDB,passFromDB);

                            startActivity(intent);
                        }else if(usertypeFromDB.equals("Mover")){
                            Intent intent = new Intent(getApplicationContext(), DashboardMover.class); //  Go to mover dashboard
                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("date", dateFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("phone", phoneFromDB);
                            intent.putExtra("usertype", usertypeFromDB);
                            intent.putExtra("username", usernameFromDB);
                            intent.putExtra("password", passFromDB);
                            SessionManager sessionManager = new SessionManager(Login.this);
                            sessionManager.createLoginSession(nameFromDB,dateFromDB,emailFromDB,phoneFromDB,usertypeFromDB,usernameFromDB,passFromDB);

                            startActivity(intent);
                        }

                        Toast.makeText(Login.this, "Login Successful",Toast.LENGTH_SHORT).show();
                    }else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }else {
                    user.setError("The user does not exist");
                    user.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Boolean validateUsername(){
        String val = user.getEditText().getText().toString();

        if(val.isEmpty()){
            user.setError("Field Cannot be Empty");
            return false;
        }else{
            user.setError(null);
            user.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void userSignUp(View view) {
        Intent intent = new Intent(Login.this, Register.class);

        Pair[] pairs = new Pair[6];
        pairs[0] = new Pair<View, String>(logo_text,"logo_text");
        pairs[1] = new Pair<View, String>(slogan_text,"sub_trans");
        pairs[2] = new Pair<View, String>(user,"user_trans");
        pairs[3] = new Pair<View, String>(password,"pass_trans");
        pairs[4] = new Pair<View, String>(loginBtn,"log_trans");
        pairs[5] = new Pair<View, String>(callSignUp,"sign_trans");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent,options.toBundle());
    }
}