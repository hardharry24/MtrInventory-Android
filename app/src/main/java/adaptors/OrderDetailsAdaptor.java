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
import models.MyOrder;
import models.OrderDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.APIclient;
import services.APIservice;
import utils.utils;

public class OrderDetailsAdaptor extends BaseAdapter {
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    Context ctx;
    List<OrderDetails> myOrders;
    List<Motorcycle> list = new ArrayList<>();
    public OrderDetailsAdaptor(Context ctx, List<OrderDetails> myOrders,List<Motorcycle> list)
    {
        this.ctx = ctx;
        this.myOrders = myOrders;
        this.list = list;

    }

   /*
    }
*/
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
            row = inflater.inflate(R.layout.item_order_details_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.lblId =  row.findViewById(R.id.lblOrderId);
            holder.lblDateTime = row.findViewById(R.id.lblOrderDateTime);
            holder.lblImg = row.findViewById(R.id.img);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final OrderDetails ordr = myOrders.get(i);
        final Motorcycle mtr = getMotor(ordr.getOrderMotorId());

        if (ordr != null)
        {
            holder.lblId.setText(""+mtr.getMtrBrand());
            holder.lblDateTime.setText(""+mtr.getMtrModel());
            Picasso.get().load(utils.IMG+""+mtr.getMtrImg()).into(holder.lblImg);
        }


        return row;
    }

    static class ViewHolder {
        TextView lblId;
        TextView lblDateTime;
        ImageView lblImg;
    }

    private Motorcycle getMotor(int id)
    {
        for(int i=0; i<list.size(); i++)
        {
            if(list.get(i).getMtrId() == id)
                return list.get(i);
        }
        return null;
    }


}
