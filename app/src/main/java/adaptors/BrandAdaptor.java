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

import java.util.List;

import models.Brand;
import models.MyOrder;
import services.APIclient;
import services.APIservice;
import utils.utils;

public class BrandAdaptor extends BaseAdapter {
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    Context ctx;
    List<Brand> brandList;
    public BrandAdaptor(Context ctx, List<Brand> brandList)
    {
        this.ctx = ctx;
        this.brandList = brandList;
    }
    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Object getItem(int i) {
        return brandList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return brandList.get(i).getBrandId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            row = inflater.inflate(R.layout.item_brand_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.lblBrandName =  row.findViewById(R.id.lblBrandName);
            holder.lblImg = row.findViewById(R.id.lblImg);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final Brand br = brandList.get(i);

        if (br != null)
        {
            holder.lblBrandName.setText(""+br.getBrandName());
            Picasso.get().load(utils.IMG+""+br.getBrandImg()).into(holder.lblImg);

        }


        return row;
    }

    static class ViewHolder {
        TextView lblBrandName;
        ImageView lblImg;
    }


}
