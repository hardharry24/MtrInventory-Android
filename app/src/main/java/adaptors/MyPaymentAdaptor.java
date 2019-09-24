package adaptors;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ezra.motortradeinventory.R;

import java.util.List;

import models.MyOrder;
import models.Payment;
import services.APIclient;
import services.APIservice;
import utils.utils;

public class MyPaymentAdaptor extends BaseAdapter {
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    Context ctx;
    List<Payment> paymentList;
    public MyPaymentAdaptor(Context ctx, List<Payment> paymentList)
    {
        this.ctx = ctx;
        this.paymentList = paymentList;
    }
    @Override
    public int getCount() {
        return paymentList.size();
    }

    @Override
    public Object getItem(int i) {
        return paymentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return paymentList.get(i).getpId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            row = inflater.inflate(R.layout.item_payment_list_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.lblAmount = row.findViewById(R.id.lblPayAmount);
            holder.lblId =  row.findViewById(R.id.lblPayId);
            holder.lblDateTime = row.findViewById(R.id.lblPayDateTime);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final Payment p = paymentList.get(i);

        if (p != null)
        {
            holder.lblId.setText(""+p.getpId());
            holder.lblDateTime.setText(""+p.getpDateTime());
            String amt = utils.moneyFormat.format(p.getpAmount());
            holder.lblAmount.setText("Php "+amt);
        }


        return row;
    }

    static class ViewHolder {
        TextView lblId,lblAmount;
        TextView lblDateTime;
    }


}
