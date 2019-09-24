package activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
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

import com.ezra.motortradeinventory.R;
import com.google.android.material.navigation.NavigationView;

import models.SingletonUser;
import models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.APIclient;
import services.APIservice;

public class UserDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    TextView lblUser;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    Bundle bundle;
    String username = "";
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_drawer);

        initialize();
        loadUser();
    }

    private void loadUser() {
        Call<User> callUser = apIservice.UserGetCurrent(username);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //
                if(response.isSuccessful())
                {
                    User u = new User();
                    u.setID(response.body().getID());
                    u.setLastname(response.body().getLastname());
                    u.setFirstname(response.body().getFirstname());
                    u.setMI(response.body().getMI());

                    SingletonUser.getInstance().setUser(u);
                    String user = u.getFirstname()+" "+u.getLastname()+" "+u.getMI()+".";
                    lblUser.setText(user.toUpperCase());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void initialize() {
        toolbar = findViewById(R.id.toolbar_main_user);
        //lblUser = findViewByI
        bundle = getIntent().getExtras();
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_user_layout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.closeDrawer(Gravity.LEFT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        lblUser = (TextView) headerView.findViewById(R.id.nav_header_textView_user);
        //navUsername.setText("Your Text Here");
        username = bundle.getString("username");
        navigationView.setNavigationItemSelectedListener(this);



        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4682B4")));
        onload();
    }

    private void onload() {
        //AdminFragmentHome homeF = new AdminFragmentHome();
        UserFragmentHome userFragmentHome = new UserFragmentHome();
        nextFragment(userFragmentHome);
    }
    public void nextFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.user_frame_layout,fragment );
        fragmentTransaction.commit();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id)
        {
            case R.id.nav_menu_add_cart:
                UserFragmentHome userFragmentHome = new UserFragmentHome();
                nextFragment(userFragmentHome);
                break;
            case R.id.nav_menu_my_order:
                UserFragmentOrders userFragmentOrders = new UserFragmentOrders();
                nextFragment(userFragmentOrders);
                break;
            case R.id.nav_menu_my_payment:
                UserFragmentPayment userFragmentPayment = new UserFragmentPayment();
                nextFragment(userFragmentPayment);
                break;
            case R.id.nav_sign_out:
                Toast.makeText(this, "Successfuly Logout!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,UserLogin.class));
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return false;
    }
}
