package activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.TotalCaptureResult;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsSpinner;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ezra.motortradeinventory.R;
import com.isapanah.awesomespinner.AwesomeSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Brand;
import models.Model;
import models.Response;
import retrofit2.Call;
import retrofit2.Callback;
import services.APIclient;
import services.APIservice;
import utils.utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragmentNewMotor extends Fragment {
    Button btnSave;
    View view;
    Context ctx;
    Button btnChoose,btnUpload;
    AwesomeSpinner spinnerBrand,spinnerModel;
    ImageView imgTest;
    EditText txtQty,txtPrice;
    String imgPath,fileName;
    Bitmap bitmap;
    String imgFile = "";
    Date dateToday;
    ArrayAdapter<String> adapter;
    List<Model> modelList = new ArrayList<>();
    List<Brand> brandList = new ArrayList<>();
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    public AdminFragmentNewMotor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_fragment_new_motor, container, false);
        initialize();
        onclicks();

        return view;
    }

    private void onclicks() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                {
                    Call<Response> saveMotor = apIservice.adminSaveMtr("INSERT", spinnerBrand.getSelectedItem(),spinnerModel.getSelectedItem(),Integer.parseInt(utils.getText(txtQty)),utils.getText(txtPrice),utils.dateTimeDB.format(dateToday.getTime()) ,imgFile);
                    saveMotor.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful())
                            {
                                int success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if (success == 1)
                                {
                                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                                    clearForm();
                                    //startActivity(new Intent(ctx, UserDrawer.class));
                                }
                                else
                                {
                                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {

                        }
                    });
                }
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }
        });

    }

    private void clearForm() {
        txtQty.setText("");
        imgFile = "";
        imgTest.setImageDrawable(getResources().getDrawable(R.drawable.no_img));
        txtPrice.setText("");
        //loadBrandSpinner();
    }

    private boolean validate() {
        if (TextUtils.isEmpty(utils.getText(txtQty)))
        {
            Toast.makeText(ctx, "Quantity is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(utils.getText(txtQty)) < 0)
        {
            Toast.makeText(ctx, "Quantity must be greater than 0!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(spinnerModel.getSelectedItem().toString()) )
        {
            Toast.makeText(ctx, "Model is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(spinnerBrand.getSelectedItem().toString()) )
        {
            Toast.makeText(ctx, "Brand is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(imgFile) )
        {
            Toast.makeText(ctx, "No image selected!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(utils.getText(txtPrice)) || Double.parseDouble(utils.getText(txtPrice)) < 0 )
        {
            Toast.makeText(ctx, "Price not valid!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initialize() {
        getActivity().setTitle("New Motorcycle");
        ctx = getContext();
        dateToday = new Date();
        btnSave = view.findViewById(R.id.btnSave);
        btnChoose = view.findViewById(R.id.btnChoose);
        imgTest = view.findViewById(R.id.ImgTest);
        btnUpload = view.findViewById(R.id.btnUpload);
        spinnerBrand = view.findViewById(R.id.spinnerBrand);
        spinnerModel = view.findViewById(R.id.spinnerModel);
        txtQty = view.findViewById(R.id.txtQty);
        txtPrice = view.findViewById(R.id.txtPrice);
        loadBrandSpinner();
    }

    private void loadBrandSpinner() {
        spinnerBrand.setHintTextColor(getResources().getColor(R.color.white));
        spinnerBrand.setSelectedItemHintColor(getResources().getColor(R.color.white));
        spinnerModel.setHintTextColor(getResources().getColor(R.color.white));
        spinnerModel.setSelectedItemHintColor(getResources().getColor(R.color.white));

        Call<Brand> getBrands = apIservice.getAllBrand("BRAND");
        getBrands.enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, retrofit2.Response<Brand> response) {
                if (response.isSuccessful())
                {
                    brandList = response.body().getBrands();
                    List<String> brs = new ArrayList<>();
                    for (Brand s : brandList) {
                        brs.add(""+s.getBrandName());
                    }

                    adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, brs);
                    spinnerBrand.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {

            }
        });

        spinnerBrand.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                String slcted = adapter.getItem(position);

                if (slcted != "")
                {
                    Call<Model> callModel = apIservice.getAllModel("MODEL",slcted);
                    callModel.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                            if (response.isSuccessful())
                            {
                                modelList = response.body().getModels();
                                List<String> mdls = new ArrayList<>();
                                if (modelList.size() == 0 || modelList.size() < 0)
                                    mdls.add("No Model Found!");
                                else
                                {
                                    for (Model s : modelList) {
                                        mdls.add(s.getModelName());
                                    }
                                }

                                ArrayAdapter<String> modelAdpter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, mdls);

                                spinnerModel.setAdapter(modelAdpter);
                            }
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {

                        }
                    });
                }
            }
        });

        spinnerModel.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {

            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == -1  && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), selectedImage);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = findViewById(R.id.imageView2);
                imgTest.setImageBitmap(bitmap);
                imgFile = imageToString(bitmap);
            } else {
                Toast.makeText(ctx, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream ouputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,ouputStream);
        byte[]  imageBytes = ouputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return  encodedImage;
    }
}
