package activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ezra.motortradeinventory.R;
import com.google.android.material.navigation.NavigationView;

public class AdminDrawer extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView lblUser;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drawer);

        initialize();
        events();

        onload();
    }

    private void onload() {
        //AdminFragmentHome homeF = new AdminFragmentHome();

        nextFragment(new AdminFragmentHome());
    }

    private void events() {

    }

    private void initialize() {
        toolbar = findViewById(R.id.toolbar_main_admin);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_admin_layout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.closeDrawer(Gravity.LEFT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view_admin);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#87CEEB")));
        View headerView = navigationView.getHeaderView(0);
        lblUser = (TextView) headerView.findViewById(R.id.nav_header_textView_admin);

        lblUser.setText("Administrator");

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id)
        {
            case R.id.nav_menu_home:
                nextFragment(new AdminFragmentHome());
                break;
            case R.id.nav_menu_new:
                nextFragment(new AdminFragmentNewMotor());
                break;
            case R.id.nav_sign_out:
                Toast.makeText(this, "Successfuly Logout!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,UserLogin.class));
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void nextFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.admin_frame_layout,fragment );
        fragmentTransaction.commit();
    }
}
