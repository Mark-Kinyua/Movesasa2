package com.muriithi.movesasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoard extends AppCompatActivity {ViewPager viewPager;
    LinearLayout linearLayout;

    OnBoardAdapter onBoardAdapter;
    TextView[] dots;

    Button getStarted;
    Animation buttonAnim;

    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_board);

        viewPager = findViewById(R.id.view_pager);
        linearLayout = findViewById(R.id.dots_layout);
        getStarted = findViewById(R.id.get_started);

        onBoardAdapter = new OnBoardAdapter(this);
        viewPager.setAdapter(onBoardAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    private void addDots(int position) {
        dots = new TextView[3];
        linearLayout.removeAllViews();

        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            linearLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.colorMaroon));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPosition = position;

            if (position == 0){
                getStarted.setVisibility(View.INVISIBLE);
            }
            else if(position == 1){
                getStarted.setVisibility(View.INVISIBLE);
            }
            else {
                buttonAnim = AnimationUtils.loadAnimation(OnBoard.this, R.anim.button_anim);
                getStarted.setAnimation(buttonAnim);
                getStarted.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void skipBoard(View view) {
        Intent intent = new Intent(OnBoard.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void getStarted(View view) {
        Intent intent = new Intent(OnBoard.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void nextPage(View view) {
        viewPager.setCurrentItem(currentPosition + 1);
    }
}