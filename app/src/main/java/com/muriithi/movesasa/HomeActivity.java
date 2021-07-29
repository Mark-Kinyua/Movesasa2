package com.muriithi.movesasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The Hooks
        drawerLayout = findViewById(R.id.drawable_layout);
        navigationView = findViewById(R.id.nav_view);

        //Navigation View Listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.nav_pizzas:
                        Pizzas pizzaFragment = new Pizzas();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_replace, pizzaFragment).commit();
                        break;
                    case R.id.nav_burgers:
                        Burgers burgersFragment = new Burgers();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_replace, burgersFragment).commit();
                        break;
                    case R.id.nav_cocktails:
                        Cocktails cocktailsFragment = new Cocktails();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_replace, cocktailsFragment).commit();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //Inflate the tool bar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Navigation Drawer Menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //Creating an instance for the tab layout and add the tabs
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_3));

        //Set the tab to fill the entire layout
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Use the Page Adapter to connect content
        //Create an instance of the view pager
        final ViewPager viewPager = findViewById(R.id.view_pager);

        //Create an instance of the page adapter
        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Set adapter to view pager
        viewPager.setAdapter(pageAdapter);

        //Handle Clicks and swipes
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}