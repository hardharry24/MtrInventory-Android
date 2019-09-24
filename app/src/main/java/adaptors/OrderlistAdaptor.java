package adaptors;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ezra.motortradeinventory.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import models.Motorcycle;
import models.Order;
import models.Response;
import retrofit2.Call;
import retrofit2.Callback;
import services.APIclient;
import services.APIservice;
import utils.utils;

public class OrderlistAdaptor extends BaseAdapter {
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    public String URL = "http://"+ utils.IP+"/MotorTradeInventory/Images/";
    Context ctx;
    List<Order> orderList;
    Dialog dialog;
    public OrderlistAdaptor(Context ctx, List<Order> orderList, Dialog dialog)
    {
        this.ctx = ctx;
        this.orderList = orderList;
        this.dialog = dialog;
    }
    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return orderList.get(i).getOrderId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            row = inflater.inflate(R.layout.item_order_list_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.lblModel =  row.findViewById(R.id.lblModel);
            holder.lblQty =  row.findViewById(R.id.lblQty);
            holder.btnDelete = row.findViewById(R.id.btnDelete);
            holder.lblPrice =  row.findViewById(R.id.lblPrice);
            holder.imageViewProduct = (ImageView) row.findViewById(R.id.img);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final Order ordr = orderList.get(i);

        if (ordr != null)
        {
            Picasso.get().load(URL+ordr.getMtrImg()).into(holder.imageViewProduct);
            holder.lblModel.setText("Model : "+ordr.getMtrModel());
            holder.lblQty.setText(ordr.getOrderQty()+"unit/s");
            holder.lblPrice.setText("Php "+utils.moneyFormat.format(Double.parseDouble(ordr.getMtrPrice())));
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ondelete(ordr);
                }
            });
        }


        return row;
    }

    private void ondelete(Order ordr) {
        Call<Response> ordrDelete = apIservice.OrderRemove("DELETE",""+ ordr.getOrderId());
        ordrDelete.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                {
                    int code = response.body().getSuccess();
                    String msg = response.body().getMessage();
                    if (code == 1)
                    {
                        dialog.dismiss();
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    static class ViewHolder {
        TextView lblModel;
        TextView lblQty;
        TextView lblPrice;
        Button btnDelete;
        ImageView imageViewProduct;
    }


}
