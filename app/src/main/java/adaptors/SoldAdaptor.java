package adaptors;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezra.motortradeinventory.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import models.Motorcycle;
import models.OrderDetails;
import models.Remaining;
import services.APIclient;
import services.APIservice;
import utils.utils;

public class SoldAdaptor extends BaseAdapter {
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    Context ctx;
    List<Remaining> remainingList;
    public SoldAdaptor(Context ctx, List<Remaining> remainingList)
    {
        this.ctx = ctx;
        this.remainingList = remainingList;

    }

    @Override
    public int getCount() {
        return remainingList.size();
    }

    @Override
    public Object getItem(int i) {
        return remainingList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return remainingList.get(i).getOrderMasterId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            row = inflater.inflate(R.layout.item_sold_out, viewGroup, false);
            holder = new ViewHolder();
            holder.lblBrandName =  row.findViewById(R.id.lblBrandName);
            holder.lblModelName = row.findViewById(R.id.lblModel);
            holder.lblImg = row.findViewById(R.id.img);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final Remaining rem = remainingList.get(i);

        if (rem != null)
        {
            holder.lblBrandName.setText(""+rem.getMtrBrand());
            holder.lblModelName.setText(""+rem.getMtrModel());
            Picasso.get().load(utils.IMG+""+rem.getMtrImg()).into(holder.lblImg);
        }


        return row;
    }

    static class ViewHolder {
        TextView lblBrandName;
        TextView lblModelName;
        ImageView lblImg;
    }




}
