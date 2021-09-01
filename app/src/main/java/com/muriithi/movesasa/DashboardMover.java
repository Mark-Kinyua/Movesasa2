package com.muriithi.movesasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class DashboardMover extends AppCompatActivity {CardView profile, records, appointment, emergency, find,log;
    RelativeLayout dash;
    TextView dash_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        dash_user = findViewById(R.id.dash_user);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails = sessionManager.getUserDetails();

        String user_name = userDetails.get(SessionManager.KEY_USER);

        dash_user.setText("Welcome " + user_name);

        dash = findViewById(R.id.dash_board);
        dash.setBackgroundResource(R.drawable.layout_border);
        profile = findViewById(R.id.profile_card);
        records = findViewById(R.id.records_card);
        appointment = findViewById(R.id.appointment_card);
        emergency = findViewById(R.id.emergency_card);
        find = findViewById(R.id.find_card);
        log = findViewById(R.id.log_card);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardMover.this, Profile.class);
                startActivity(intent);
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardMover.this, Records.class);
                startActivity(intent);
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardMover.this, ViewJobs.class);
                startActivity(intent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardMover.this, PostActivity.class);
                startActivity(intent);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); //logout
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}
