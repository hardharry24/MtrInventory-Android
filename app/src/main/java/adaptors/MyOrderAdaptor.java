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

import com.ezra.motortradeinventory.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import models.MyOrder;
import models.Order;
import models.Response;
import retrofit2.Call;
import retrofit2.Callback;
import services.APIclient;
import services.APIservice;
import utils.utils;

public class MyOrderAdaptor extends BaseAdapter {
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    Context ctx;
    List<MyOrder> myOrders;
    public MyOrderAdaptor(Context ctx, List<MyOrder> myOrders)
    {
        this.ctx = ctx;
        this.myOrders = myOrders;
    }
    @Override
    public int getCount() {
        return myOrders.size();
    }

    @Override
    public Object getItem(int i) {
        return myOrders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return myOrders.get(i).getOrderMasterId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            row = inflater.inflate(R.layout.item_my_order_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.lblId =  row.findViewById(R.id.lblOrderId);
            holder.lblDateTime = row.findViewById(R.id.lblOrderDateTime);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final MyOrder ordr = myOrders.get(i);

        if (ordr != null)
        {
            holder.lblId.setText("Order # : "+ordr.getOrderMasterId());
            holder.lblDateTime.setText(""+ordr.getOrderMstrDateTime());

        }


        return row;
    }

    static class ViewHolder {
        TextView lblId;
        TextView lblDateTime;
    }


}
