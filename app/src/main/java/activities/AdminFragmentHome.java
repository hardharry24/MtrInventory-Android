package activities;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ezra.motortradeinventory.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.isapanah.awesomespinner.AwesomeSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adaptors.BrandAdaptor;
import adaptors.SoldAdaptor;
import models.Brand;
import models.Model;
import models.Remaining;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.APIclient;
import services.APIservice;
import utils.utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragmentHome extends Fragment {
    View view;
    Context ctx;
    Date dateToday;
    TextView lblStocks,lblDtSelected,lblTotalSold;
    List<Brand> listBrads = new ArrayList<>();
    ListView listView,listViewSold;
    BrandAdaptor brandAdaptor;
    AwesomeSpinner spinnerBrand,spinnerModel;
    ArrayAdapter<String> adapter;
    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    List<Model> modelList = new ArrayList<>();
    List<Brand> brandList = new ArrayList<>();
    List<Remaining> listRemMtr = new ArrayList<>();
    List<Remaining> listSoldMtrByDt = new ArrayList<>();
    Button mDatePicker;
    Spinner spinnerDtType;
    Calendar calendarPick;
    SoldAdaptor soldAdaptor;
    DatePickerDialog.OnDateSetListener mOnDateSetListener;
    APIservice apIservice = APIclient.getClient().create(APIservice.class);
    public AdminFragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_fragment_home, container, false);
        initialize();
        onload();
        onclicks();


        loadBrandSpinner();
        loadRemainingMtr();
        return view;
    }

    private void onclicks() {
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCalendar =  Calendar.getInstance();
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePickerDialog = new DatePickerDialog(
                        ctx,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mOnDateSetListener,
                        year,month,day);
                mDatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDatePickerDialog.show();


            }
        });



    }

    private void loadRemainingMtr() {
        Call<Remaining> callRem = apIservice.getRemainingMtr("REMAINING");
        callRem.enqueue(new Callback<Remaining>() {
            @Override
            public void onResponse(Call<Remaining> call, Response<Remaining> response) {
                if(response.isSuccessful())
                {
                    listRemMtr = response.body().getRemainingList();
                }
            }

            @Override
            public void onFailure(Call<Remaining> call, Throwable t) {

            }
        });
    }

    private void onload() {
        Call<Brand> getBrand = apIservice.getAllBrand("BRAND");
        getBrand.enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                if(response.isSuccessful())
                {
                    listBrads = response.body().getBrands();
                    brandAdaptor = new BrandAdaptor(ctx,listBrads);
                    listView.setAdapter(brandAdaptor);
                }
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {

            }
        });

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //month = month+1;
                String mDate = day+"/"+month+"/"+year;

                if (spinnerDtType.getSelectedItemPosition() == 0 )
                {
                    Toast.makeText(ctx, "Select Type !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Date dtPick = new Date(mDate);
                //Log.d("TIME",""+dtPick);
                calendarPick.set(Calendar.DATE,day);
                calendarPick.set(Calendar.MONTH,month);
                calendarPick.set(Calendar.YEAR,year);
                if(spinnerDtType.getSelectedItemPosition() == 1) {
                    lblDtSelected.setText("Date Selected : " + utils.daily.format(calendarPick.getTime()));
                    loadGraph("DAY",utils.daily.format(calendarPick.getTime()));
                }
                if(spinnerDtType.getSelectedItemPosition() == 2) {
                    lblDtSelected.setText("Date Selected : " + utils.monthly.format(calendarPick.getTime()));
                    loadGraph("MONTH",utils.monthly.format(calendarPick.getTime()));
                }
                if(spinnerDtType.getSelectedItemPosition() == 3) {
                    lblDtSelected.setText("Date Selected : " + utils.yearly.format(calendarPick.getTime()));
                    loadGraph("YEAR",utils.yearly.format(calendarPick.getTime()));
                }

            }
        };
        lblDtSelected.setText("Date Selected : None");
    }

    private void initialize() {
        getActivity().setTitle("Welcome Admin");
        calendarPick = Calendar.getInstance();
        ctx = getContext();
        listView = view.findViewById(R.id.listViewBrands);
        dateToday = new Date();
        spinnerBrand = view.findViewById(R.id.spinnerBrand);
        spinnerModel = view.findViewById(R.id.spinnerModel);
        chart = view.findViewById(R.id.chartBarStock);
        lblStocks = view.findViewById(R.id.lblStockRem);
        mDatePicker = view.findViewById(R.id.btnChooseDt);
        lblDtSelected = view.findViewById(R.id.lblDateSelected);
        spinnerDtType = view.findViewById(R.id.spinnerDateType);
        listViewSold = view.findViewById(R.id.listViewSold);
        lblTotalSold = view.findViewById(R.id.lblTotalSold);
    }

    private void loadGraph(String type,String dt)
    {
        Call<Remaining> getMtrSoldByDt = apIservice.getSoldMtrBy("TOTAL_BY",type,dt);
        getMtrSoldByDt.enqueue(new Callback<Remaining>() {
            @Override
            public void onResponse(Call<Remaining> call, Response<Remaining> response) {
                if (response.isSuccessful())
                {
                    listSoldMtrByDt = response.body().getRemainingList();
                    soldAdaptor = new SoldAdaptor(ctx,listSoldMtrByDt);
                    listViewSold.setAdapter(soldAdaptor);
                    lblTotalSold.setText("Php "+(utils.moneyFormat.format(getTotalSold(listSoldMtrByDt))));

                }
            }

            @Override
            public void onFailure(Call<Remaining> call, Throwable t) {

            }
        });
    }

    private void loadBrandSpinner() {
        Call<Brand> getBrands = apIservice.getAllBrand("BRAND");
        getBrands.enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, retrofit2.Response<Brand> response) {
                if (response.isSuccessful())
                {
                    brandList = response.body().getBrands();
                    List<String> brs = new ArrayList<>();
                    for (Brand s : brandList) {
                        brs.add(""+s.getBrandName());
                    }

                    adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, brs);
                    spinnerBrand.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {

            }
        });

        spinnerBrand.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                String slcted = adapter.getItem(position);

                if (slcted != "")
                {
                    String brand =  spinnerBrand.getSelectedItem();
                    List<Remaining> list = getMtrBrandRem(brand);
                    setBarChart(list);


                    Call<Model> callModel = apIservice.getAllModel("MODEL",slcted);
                    callModel.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                            if (response.isSuccessful())
                            {
                                modelList = response.body().getModels();
                                List<String> mdls = new ArrayList<>();
                                if (modelList.size() == 0 || modelList.size() < 0)
                                    mdls.add("No Model Found!");
                                else
                                {
                                    for (Model s : modelList) {
                                        mdls.add(s.getModelName());
                                    }
                                }

                                ArrayAdapter<String> modelAdpter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, mdls);

                                spinnerModel.setAdapter(modelAdpter);
                            }
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {

                        }
                    });
                }
            }
        });

        spinnerModel.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                try
                {
                    String brand =  spinnerBrand.getSelectedItem();
                    String model = spinnerModel.getSelectedItem();
                    Remaining rem = getMtrRemaining(brand,model);

                    lblStocks.setText("Stocks : "+rem.getMtrQty() +"       "+rem.getOrderQty()+" sold");
                    //Toast.makeText(ctx, "Qty : "+rem.getOrderQty(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {

                }
            }
        });
    }

    public Remaining getMtrRemaining(String brand,String model)
    {
        Remaining remaining = new Remaining();
        for(int i=0; i<listRemMtr.size();i++)
        {
            Remaining rem = listRemMtr.get(i);
            if(TextUtils.equals(rem.getMtrBrand(),brand) && TextUtils.equals(rem.getMtrModel(),model))
                return rem;
        }
        return null;
    }

    public List<Remaining> getMtrBrandRem(String brand)
    {
        List<Remaining> list = new ArrayList<>();
        for(int i=0; i<listRemMtr.size();i++)
        {
            Remaining rem = listRemMtr.get(i);
            if(TextUtils.equals(rem.getMtrBrand(),brand))
                list.add(rem);
        }
        return list;
    }

    public void setBarChart(List<Remaining> listRemMtr)
    {
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<>();

        for(int i = 0; i<listRemMtr.size();i++)
        {
            Remaining rem = listRemMtr.get(i);
            Float fl = Float.parseFloat(""+rem.getMtrQty());
            BARENTRY.add(new BarEntry(fl,i));
            BarEntryLabels.add(""+rem.getMtrModel());
        }

        Bardataset = new BarDataSet(BARENTRY, "Models");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(3000);

    }

    private Double getTotalSold(List<Remaining> list)
    {
        Double sum = 0.0;
        for(int i=0; i<list.size();i++)
        {
            Remaining rem = list.get(i);
            String price = rem.getMtrPrice();
            sum += Double.parseDouble(price);
        }
        return sum;
    }
}
