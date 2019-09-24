package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezra.motortradeinventory.R;

import models.Response;
import retrofit2.Call;
import retrofit2.Callback;
import services.APIclient;
import services.APIservice;
import utils.*;

public class UserLogin extends AppCompatActivity {
    EditText txtUsername,txtPassword;
    Button btnLogin,btnSignUp;
    Context ctx;
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        initialize();
        onclicks();
        checkUsername();
    }

    private void onclicks() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                {
                    String username = utils.getText(txtUsername).toLowerCase();
                    String password = utils.getText(txtPassword).toLowerCase();
                    if (TextUtils.equals(username,"admin")  && TextUtils.equals(password,"admin"))
                    {
                        Toast.makeText(UserLogin.this, "Successfully Login admin!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ctx, AdminDrawer.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    else {
                        Call<Response> loginUser = apIservice.UserLogin(utils.getText(txtUsername), utils.getText(txtPassword));
                        loginUser.enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()) {
                                    int success = response.body().getSuccess();
                                    String message = response.body().getMessage();
                                    if (success == 1) {
                                        Toast.makeText(UserLogin.this, message, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ctx, UserDrawer.class);
                                        intent.putExtra("username", utils.getText(txtUsername));
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(UserLogin.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {
                                Toast.makeText(UserLogin.this, "" + call.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx,UserRegistration.class));
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(txtUsername.getText()))
        {
            Toast.makeText(this, "Username is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtPassword.getText()))
        {
            Toast.makeText(this, "Password is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initialize() {
        ctx = this;

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

    }

    private void checkUsername() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            txtUsername.setText(""+bundle.getString("username"));
        }
    }


}
