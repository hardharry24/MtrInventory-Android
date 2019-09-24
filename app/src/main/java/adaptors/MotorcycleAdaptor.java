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

import models.Motorcycle;
import utils.utils;

public class MotorcycleAdaptor extends BaseAdapter {
    public String URL = "http://"+ utils.IP+"/MotorTradeInventory/Images/";
    Context ctx;
    List<Motorcycle> listMotorcycles;
    public MotorcycleAdaptor(Context ctx, List<Motorcycle> listMotorcycles)
    {
        this.ctx = ctx;
        this.listMotorcycles = listMotorcycles;
    }
    @Override
    public int getCount() {
        return listMotorcycles.size();
    }

    @Override
    public Object getItem(int i) {
        return listMotorcycles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listMotorcycles.get(i).getMtrId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_motor_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.lblBrand =  row.findViewById(R.id.txtLblBrand);
            holder.lblModel =  row.findViewById(R.id.txtLblModel);
            holder.lblPrice =  row.findViewById(R.id.txtLblPrice);
            holder.imageViewProduct = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Motorcycle mtr = listMotorcycles.get(i);
        Picasso.get().load(URL+mtr.getMtrImg()).into(holder.imageViewProduct);
        holder.lblModel.setText("Model : "+mtr.getMtrModel());
        holder.lblBrand.setText("Brand : "+mtr.getMtrBrand());//
        holder.lblPrice.setText("Php "+utils.moneyFormat.format(Double.parseDouble(mtr.getMtrPrice())));

        return row;
    }

    static class ViewHolder {
        TextView lblModel;
        TextView lblBrand;
        TextView lblPrice;
        ImageView imageViewProduct;
    }
}
