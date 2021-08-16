package com.muriithi.movesasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    TextInputLayout reg_name, reg_date, reg_email,reg_phone,reg_usertype,reg_pass;
    Button reg_login,reg_sign_up;
    ImageView logo_image;
    TextView logo_text, slogan_text;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Spinner spinner = (Spinner) findViewById(R.id.usertype);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // logo_image = findViewById(R.id.logo_image);
        logo_text = findViewById(R.id.logo_name);
        slogan_text = findViewById(R.id.slogan);

        reg_name = findViewById(R.id.full_name);
        reg_date = findViewById(R.id.birth_date);
        reg_email = findViewById(R.id.email);
        reg_phone = findViewById(R.id.phone_number);
        reg_pass = findViewById(R.id.password);

        reg_sign_up = findViewById(R.id.sign_btn);
        reg_login = findViewById(R.id.login_btn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.usertype_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    public void userLogin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        Pair[] pairs = new Pair[6];
        pairs[0] = new Pair<View, String>(logo_text,"logo_text");
        pairs[1] = new Pair<View, String>(slogan_text,"sub_trans");
        pairs[2] = new Pair<View, String>(reg_usertype,"user_trans");
        pairs[3] = new Pair<View, String>(reg_pass,"pass_trans");
        pairs[4] = new Pair<View, String>(reg_login,"log_trans");
        pairs[5] = new Pair<View, String>(reg_sign_up,"sign_trans");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
        startActivity(intent,options.toBundle());
    }

    private Boolean validateName(){
        String val = reg_name.getEditText().getText().toString();

        if(val.isEmpty()){
            reg_name.setError("Field Cannot be Empty");
            return false;
        }
        else{
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUserType(){


    }

    private Boolean validateDate(){
        String val = reg_date.getEditText().getText().toString();

        if(val.isEmpty()){
            reg_date.setError("Field Cannot be Empty");
            return false;
        }
        else{
            reg_date.setError(null);
            reg_date.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = reg_email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            reg_email.setError("Field Cannot be Empty");
            return false;
        }
        else if(!val.matches(emailPattern)){
            reg_email.setError("Invalid Email Address");
            return false;
        } else{
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone(){
        String val = reg_phone.getEditText().getText().toString();

        if(val.isEmpty()){
            reg_phone.setError("Field Cannot be Empty");
            return false;
        }
        else{
            reg_phone.setError(null);
            reg_phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = reg_pass.getEditText().getText().toString();
        String passwordValidate = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            reg_pass.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordValidate)) {
            reg_pass.setError("Password is too weak");
            return false;
        }else{
            reg_pass.setError(null);
            reg_pass.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {
        if(!validateName() | !validateDate() | !validatePassword() | !validatePhone() | !validateEmail() | !validateUserType()){
            return;
        }

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        String full_name = reg_name.getEditText().getText().toString().trim();
        String birth_date = reg_date.getEditText().getText().toString().trim();
        String email_address = reg_email.getEditText().getText().toString().trim();
        String phone_number = reg_phone.getEditText().getText().toString().trim();
        String user_type = reg_usertype.getEditText().getText().toString().trim();
        String pass_word = reg_pass.getEditText().getText().toString().trim();

        RegistrationHelper registrationHelper = new RegistrationHelper(full_name, birth_date, email_address, phone_number,user_type, pass_word);
        reference.child(user_type).setValue(registrationHelper);

        Toast.makeText(MainActivity.this, "Registration Successful",Toast.LENGTH_SHORT).show();
    }

}