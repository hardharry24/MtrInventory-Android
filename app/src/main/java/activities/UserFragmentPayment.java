package activities;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.ezra.motortradeinventory.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adaptors.MyOrderAdaptor;
import adaptors.MyPaymentAdaptor;
import models.MyOrder;
import models.Payment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.APIclient;
import services.APIservice;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragmentPayment extends Fragment {
    View view;
    Context ctx;
    String username = "";
    Bundle bundle;
    Date dateToday;
    MyPaymentAdaptor adaptor;
    ListView listView;
    List<Payment> paymentList = new ArrayList<>();
    APIservice apIservice = APIclient.getClient().create(APIservice.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_fragment_payment, container, false);
        initialize();
        return view;
    }

    private void initialize() {
        ctx = getContext();
        dateToday = new Date();
        bundle = getActivity().getIntent().getExtras();
        username = bundle.getString("username");
        listView = view.findViewById(R.id.listViewMyPayment);
        getActivity().setTitle("My Payment");

        onloadPayment();
    }

    private void onloadPayment() {
        Call<Payment> getPayment = apIservice.getPayment("MY_PAYMENT",username);
        getPayment.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful())
                {
                    paymentList = response.body().getPaymentList();
                    adaptor = new MyPaymentAdaptor(ctx,paymentList);
                    listView.setAdapter(adaptor);
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

            }
        });

    }

}
