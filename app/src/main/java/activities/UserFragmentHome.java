package activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ezra.motortradeinventory.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adaptors.MotorcycleAdaptor;
import adaptors.OrderlistAdaptor;
import models.Motorcycle;
import models.Order;
import models.SingletonUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.APIclient;
import services.APIservice;
import utils.utils;
import models.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragmentHome extends Fragment {
    View view;
    Context ctx;
    GridView gridViewProducts;
    FloatingActionButton btnCart;
    List<Motorcycle> motorcycleList = new ArrayList<>();
    List<Order> listOrder;
    private MotorcycleAdaptor gridAdapter;
    private OrderlistAdaptor orderlistAdaptor;
    String username = "";
    Bundle bundle;
    Date dateToday;
    Double totalAmountOrder = 0.0,change = 0.0;
    APIservice apIservice = APIclient.getClient().create(APIservice.class);

    public UserFragmentHome() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_fragment_home, container, false);

        initialize();
        loadData();
        return view;
    }

    private void loadData() {
        motorcycleList.clear();
        Call<Motorcycle> motorcycleCall = apIservice.getMotorcycles("MOTOR");
        motorcycleCall.enqueue(new Callback<Motorcycle>() {
            @Override
            public void onResponse(Call<Motorcycle> call, Response<Motorcycle> response) {
                if (response.isSuccessful())
                {
                    motorcycleList = response.body().getMotorcycles();
                    gridAdapter = new MotorcycleAdaptor(ctx,motorcycleList);
                    gridViewProducts.setAdapter(gridAdapter);

                    gridViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            try
                            {
                                Motorcycle mtr = (Motorcycle)adapterView.getItemAtPosition(i);
                                showDetails(mtr);
                            }
                            catch (Exception ex)
                            {
                                Toast.makeText(ctx, ex.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Motorcycle> call, Throwable t) {

            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCart();
            }
        });


    }

    private void initialize() {
        ctx = getContext();
        dateToday = new Date();
        bundle = getActivity().getIntent().getExtras();
        username = bundle.getString("username");
        btnCart = view.findViewById(R.id.btnCart);
        getActivity().setTitle("Motor Trade");

        gridViewProducts = view.findViewById(R.id.gridViewProducts);
    }

    private void showCart() {
        final Dialog dialogDetails = new Dialog(ctx);
        dialogDetails.setContentView(R.layout.user_order_details);

        final ListView listView = dialogDetails.findViewById(R.id.listViewOrder);
        final Button btnCheckOut = dialogDetails.findViewById(R.id.btnCheckout);

        listOrder = new ArrayList<>();
        Call<Order> getOrder = apIservice.UserGetOrder("ORDER",username);
        getOrder.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful())
                {
                    listOrder = response.body().getOrderList();

                    orderlistAdaptor = new OrderlistAdaptor(ctx,listOrder,dialogDetails);
                    listView.setAdapter(orderlistAdaptor);

                    if (listOrder.size() == 0){
                        btnCheckOut.setVisibility(View.INVISIBLE);
                    }


                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialogPay = new Dialog(ctx);
                dialogPay.setContentView(R.layout.user_order_pay);

                final TextView lblTotal = dialogPay.findViewById(R.id.lblTotal);
                final EditText txtAmount = dialogPay.findViewById(R.id.txtAmount);
                final TextView lblChange = dialogPay.findViewById(R.id.lblChange);
                final Button btnPay = dialogPay.findViewById(R.id.btnPay);
                lblTotal.setText("Php "+utils.moneyFormat.format(getTotalOrder()));
                txtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (!b)
                        {
                            try
                            {
                                Double total = totalAmountOrder;
                                Double amount = Double.parseDouble(utils.getText(txtAmount));
                                if (amount < total)
                                {
                                    Toast.makeText(ctx, "Amount must be greater than the Total amount!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                change = amount-total;



                                lblChange.setText("Php "+utils.moneyFormat.format(change));
                            }catch (Exception ex)
                            {

                            }
                        }
                    }
                });
                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setTitle("Confirmation");
                        builder.setMessage("You are about to check out this order. Do you really want to proceed ?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                final Order ordr = listOrder.get(0);

                                Call<models.Response> checkOut = apIservice.OrderCheckOut("CHECKOUT",""+ordr.getOrderMasterId(),utils.dateTimeDB.format(dateToday),username);
                                checkOut.enqueue(new Callback<models.Response>() {
                                    @Override
                                    public void onResponse(Call<models.Response> call, Response<models.Response> response) {
                                        if (response.isSuccessful())
                                        {
                                            int code = response.body().getSuccess();
                                            String msg = response.body().getMessage();
                                            if (code == 1)
                                            {
                                                updateMotorList(listOrder);
                                                payments(ordr,Double.parseDouble(utils.getText(txtAmount)));
                                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                                                dialogDetails.dismiss();
                                                dialogPay.dismiss();
                                            }
                                            else
                                            {
                                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                                                dialogDetails.dismiss();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<models.Response> call, Throwable t) {

                                    }
                                });

                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ctx, "Cancel", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.show();

                    }
                });
                dialogPay.show();

            }
        });



        dialogDetails.show();
    }

    private void payments(Order ordr,Double amt) {
        Call<models.Response> payment = apIservice.OrderPayments("PAY",""+ordr.getOrderMasterId(),""+amt,""+totalAmountOrder,""+change,""+username,utils.dateTimeDB.format(dateToday.getTime()));
        payment.enqueue(new Callback<models.Response>() {
            @Override
            public void onResponse(Call<models.Response> call, Response<models.Response> response) {
                if (response.isSuccessful())
                {
                    int code = response.body().getSuccess();
                    String msg = response.body().getMessage();
                    if (code == 1)
                    {
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<models.Response> call, Throwable t) {

            }
        });
    }

    private Double getTotalOrder()
    {
        Double total = 0.0;
        for (Order o:listOrder) {
            total += (o.getOrderQty()*Integer.parseInt(o.getMtrPrice()));
        }
        totalAmountOrder = total;
        return total;
    }
    private void updateMotorList(List<Order> listOrder) {
        for (Order ord: listOrder) {
            Call<models.Response> updateMtrQty = apIservice.UpdateMotorDetails("UPDATE_MOTOR",""+ord.getOrderMotorId(),""+ord.getOrderQty());
            updateMtrQty.enqueue(new Callback<models.Response>() {
                @Override
                public void onResponse(Call<models.Response> call, Response<models.Response> response) {
                    if (response.isSuccessful())
                    {

                    }
                }

                @Override
                public void onFailure(Call<models.Response> call, Throwable t) {

                }
            });
        }
    }

    public void showDetails(final Motorcycle mtr)
    {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.user_motor_details);

        // set the custom dialog components - text, image and button
        TextView lblbrand =  dialog.findViewById(R.id.details_lblbrand_user_motor);
        TextView lblmodel =  dialog.findViewById(R.id.details_lblmodel_user_motor);
        TextView lblstocks =  dialog.findViewById(R.id.details_lblstocks_user_motor);
        TextView lblprice =  dialog.findViewById(R.id.details_lblprice_user_motor);
        Button btnAddCart = dialog.findViewById(R.id.details_btn_add_cart_user_motor);
        ImageView imgMtr = dialog.findViewById(R.id.details_img_user_motor);


        /****Set Value ******/

        lblbrand.setText(""+mtr.getMtrBrand());
        lblmodel.setText(""+mtr.getMtrModel());
        lblprice.setText("Php "+ utils.moneyFormat.format(Double.parseDouble(mtr.getMtrPrice())));
        lblstocks.setText(mtr.getMtrQty()+"");
        Picasso.get().load(utils.IMG+mtr.getMtrImg()).into(imgMtr);
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<models.Response> orderMtr = apIservice.UserOrder("ORDER", ""+mtr.getMtrId(),"1", username);
                orderMtr.enqueue(new Callback<models.Response>() {
                    @Override
                    public void onResponse(Call<models.Response> call, Response<models.Response> response) {
                        int success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if (success == 1) {
                            Toast.makeText(ctx, "Successfully added to cart!", Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        } else {
                            Toast.makeText(ctx, "Added to cart error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<models.Response> call, Throwable t) {

                    }
                });


            }
        });

        dialog.show();
    }

}
