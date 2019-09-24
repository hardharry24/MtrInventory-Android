package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezra.motortradeinventory.R;

import models.Response;
import retrofit2.Call;
import retrofit2.Callback;
import services.APIclient;
import services.APIservice;
import utils.*;

public class UserVerification extends AppCompatActivity {
    Context ctx;
    EditText txtV1,txtV2,txtV3,txtV4;
    Button btnVerfiy;
    Bundle bundle;
    String username;
    APIservice apIservice = APIclient.getClient().create(APIservice.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);

        setTitle("Verification");
        initialize();
        onlicks();
    }

    private void onlicks() {
        btnVerfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                {
                    String code = utils.getText(txtV1)+utils.getText(txtV2)+utils.getText(txtV3)+utils.getText(txtV4);
                    Call<Response> UserVerify = apIservice.UserVerification(username,code);
                    UserVerify.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            int success = response.body().getSuccess();
                            String message = response.body().getMessage();
                            Toast.makeText(ctx, "Success : "+success, Toast.LENGTH_SHORT).show();
                            if (success == 1)
                            {
                                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ctx,UserLogin.class);
                                intent.putExtra("username",username);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(ctx,message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {

                        }
                    });

                }
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(utils.getText(txtV1)))
        {
            Toast.makeText(ctx, "1st Number is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(utils.getText(txtV2)))
        {
            Toast.makeText(ctx, "2nd Number is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(utils.getText(txtV3)))
        {
            Toast.makeText(ctx, "3rd Number is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(utils.getText(txtV4)))
        {
            Toast.makeText(ctx, "4th Number is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initialize() {
        ctx = this;
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        txtV1 = findViewById(R.id.txtV1);
        txtV2 = findViewById(R.id.txtV2);
        txtV3 = findViewById(R.id.txtV3);
        txtV4 = findViewById(R.id.txtV4);
        btnVerfiy = findViewById(R.id.btnVerify);
    }
}
