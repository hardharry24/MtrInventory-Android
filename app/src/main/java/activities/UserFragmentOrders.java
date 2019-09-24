package activities;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.ezra.motortradeinventory.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adaptors.MotorcycleAdaptor;
import adaptors.MyOrderAdaptor;
import adaptors.OrderDetailsAdaptor;
import adaptors.OrderlistAdaptor;
import models.Motorcycle;
import models.MyOrder;
import models.Order;
import models.OrderDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.APIclient;
import services.APIservice;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragmentOrders extends Fragment {
    View view;
    Context ctx;
    String username = "";
    Bundle bundle;
    Date dateToday;
    MyOrderAdaptor adaptor;
    ListView listView;
    OrderDetailsAdaptor orderDetailsAdaptor;
    List<Motorcycle> listMotor = new ArrayList<>();
    List<MyOrder> myOrderList = new ArrayList<>();
    List<OrderDetails> orderDetailsList = new ArrayList<>();
    APIservice apIservice = APIclient.getClient().create(APIservice.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_fragment_orders, container, false);
        initialize();
        return view;
    }

    private void initialize() {
        ctx = getContext();
        dateToday = new Date();
        listView = view.findViewById(R.id.listViewMyOrder);
        bundle = getActivity().getIntent().getExtras();
        username = bundle.getString("username");
        getActivity().setTitle("My Order");


        getMotorList();
        onloadOrder();
        onclicks();
    }

    private void onclicks() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyOrder order = (MyOrder)adapterView.getItemAtPosition(i);
                showDetails(order);
            }
        });
    }

    private void showDetails(MyOrder order) {
        Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.user_my_order_details);
        final ListView listView = dialog.findViewById(R.id.listViewOrder);

        Call<OrderDetails> getDetails = apIservice.getOrderDetails("ORDER_DETAILS",""+order.getOrdertbMasterId());
        //final
        getDetails.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                if(response.isSuccessful())
                {
                    orderDetailsList = response.body().getDetailsList();
                }
                Log.e("LOGS","Size : "+orderDetailsList.size());
                orderDetailsAdaptor = new OrderDetailsAdaptor(ctx,orderDetailsList,listMotor);
                listView.setAdapter(orderDetailsAdaptor);
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {

            }
        });
        //

        dialog.show();
    }

    private void getMotorList(){
        Call<Motorcycle> getMtr = apIservice.getMotorcycles("MOTOR");
        getMtr.enqueue(new Callback<Motorcycle>() {
            @Override
            public void onResponse(Call<Motorcycle> call, Response<Motorcycle> response) {
                if (response.isSuccessful())
                    listMotor = response.body().getMotorcycles();
            }

            @Override
            public void onFailure(Call<Motorcycle> call, Throwable t) {

            }
        });
    }

    private void onloadOrder() {
        Call<MyOrder> myOrderCall = apIservice.getMyOrder("MY_ORDER",username);
        myOrderCall.enqueue(new Callback<MyOrder>() {
            @Override
            public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {
                if (response.isSuccessful())
                {
                    myOrderList = response.body().getMyOrders();
                    adaptor = new MyOrderAdaptor(ctx,myOrderList);
                    listView.setAdapter(adaptor);
                }
            }

            @Override
            public void onFailure(Call<MyOrder> call, Throwable t) {

            }
        });
    }

}
