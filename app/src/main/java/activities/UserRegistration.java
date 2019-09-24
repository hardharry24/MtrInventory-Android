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

import org.w3c.dom.Text;

import java.util.Random;

import models.Response;
import retrofit2.Call;
import retrofit2.Callback;
import services.APIclient;
import services.APIservice;
import utils.*;

public class UserRegistration extends AppCompatActivity {
    Context ctx;
    EditText txtLname,txtFname,txtMI,txtUsername,txtPassword,txtRePassword;
    Button btnSignUp;

    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        setTitle("Registration");
        initialization();
        onclicks();
    }

    private void onclicks() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                {
                    final String code = getCode();
                    Call<Response> userRegistration = apIservice.UserRegistration(utils.getText(txtLname),utils.getText(txtFname),utils.getText(txtMI),utils.getText(txtUsername),utils.getText(txtPassword),"1",code);
                    userRegistration.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful())
                            {
                                int success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                Toast.makeText(ctx, "Success : "+success, Toast.LENGTH_SHORT).show();
                                if (success == 1)
                                {
                                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                                    utils.notification("Motor Trade Verification","Your code verification is "+code,ctx);
                                    Intent intent = new Intent(ctx,UserVerification.class);
                                    intent.putExtra("username",utils.getText(txtUsername));
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(ctx,message, Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(ctx, call.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(utils.getText(txtLname)))
        {
            Toast.makeText(ctx, "Lastname is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(utils.getText(txtFname)))
        {
            Toast.makeText(ctx, "Firstname is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(utils.getText(txtMI)))
        {
            Toast.makeText(ctx, "Middle Initial is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(utils.getText(txtUsername)))
        {
            Toast.makeText(ctx, "Username is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(utils.getText(txtPassword)))
        {
            Toast.makeText(ctx, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(utils.getText(txtRePassword)))
        {
            Toast.makeText(ctx, "Confirm Password is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(TextUtils.equals(utils.getText(txtRePassword),utils.getText(txtPassword))))
        {
            Toast.makeText(ctx, "Password dont match!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initialization() {
        ctx = this;
        txtLname = findViewById(R.id.txtLname);
        txtFname = findViewById(R.id.txtFname);
        txtMI = findViewById(R.id.txtMI);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtRePassword = findViewById(R.id.txtRePassword);
        btnSignUp = findViewById(R.id.BtnSignUp);
    }

    public String getCode()
    {
        Random random = new Random();
        String generatedPassword = String.format("%04d", random.nextInt(10000));
        return  generatedPassword;
    }
}
