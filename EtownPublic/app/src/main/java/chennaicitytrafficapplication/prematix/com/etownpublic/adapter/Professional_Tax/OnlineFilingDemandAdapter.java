package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Professional_Tax;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Profession_Tax.OnlineFilingDemandEntity;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.Profession.OnlineFiling;

public class OnlineFilingDemandAdapter extends RecyclerView.Adapter<OnlineFilingDemandAdapter.ViewHolder> {
    public Context context;
    ArrayList<OnlineFilingDemandEntity> demandlist;
    CheckBox next_checkbox;
    CheckBox allTermCheckBox;
    int all_term_condition = 0;
    View view;
    RecyclerView rv_demand;
    int currentPosition;
    int nextPosition;
    int total_demand_sel;
    int amount = 0;
    CheckBox mallTermCheckBox;

    public OnlineFilingDemandAdapter(Context context, ArrayList<OnlineFilingDemandEntity> demandlist, CheckBox allTermCheckBox) {

        this.demandlist = demandlist;
        this.context = context;
        this.mallTermCheckBox=allTermCheckBox;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demand_detail_list, parent, false);
        rv_demand = (RecyclerView) parent;
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(position % 2 ==1){
            holder.items.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }else{
            holder.items.setBackgroundColor(Color.parseColor("#BDB9CD"));
        }
        holder.tvFinYear.setText(demandlist.get(position).getFinancialYear());
        holder.tvDemand.setText(""+demandlist.get(position).getDemand());
        holder.checkbox.setTag("" + position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_online_filing, null);
        allTermCheckBox = (CheckBox) view.findViewById(R.id.check_all);


        mallTermCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (all_term_condition == 0) {
                    for (int i = 0; i < getItemCount(); i++) {
                        LinearLayout linearLayout = (LinearLayout) rv_demand.getChildAt(i);
                        CheckBox checkBoxInRow = (CheckBox) linearLayout.findViewById(R.id.sel_checkbox);
                        checkBoxInRow.setChecked(true);
                    }
                    all_term_condition = 1;

                } else {
                    for (int i = 0; i < getItemCount(); i++) {
                        LinearLayout linearLayout = (LinearLayout) rv_demand.getChildAt(i);
                        CheckBox checkBoxInRow = (CheckBox) linearLayout.findViewById(R.id.sel_checkbox);
                        checkBoxInRow.setChecked(false);
                    }
                    all_term_condition = 0;

                }

                getSelectedItemTotal();
            }
        });


        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox current_box = (CheckBox) v;
                currentPosition = Integer.parseInt(v.getTag().toString());
                Log.e("currentPosition", "--" + currentPosition);

                nextPosition = currentPosition + 1;
                Log.e("currentPosition", "--" + nextPosition);


                if(currentPosition == demandlist.size()-1){

                    if(current_box.isChecked()){
                        mallTermCheckBox.setChecked(true);
                        all_term_condition=1;
                    }else{
                        mallTermCheckBox.setChecked(false);
                        all_term_condition=0;
                    }
                }

                if (currentPosition != 0) {
                    LinearLayout linearLayout = (LinearLayout) rv_demand.getChildAt((currentPosition) - 1);
                    CheckBox precheckBox = (CheckBox) linearLayout.findViewById(R.id.sel_checkbox);

                    if (currentPosition != (getItemCount() - 1)) {
                        LinearLayout next_linearLayout = (LinearLayout) rv_demand.getChildAt((currentPosition) + 1);
                        next_checkbox = (CheckBox) next_linearLayout.findViewById(R.id.sel_checkbox);
                        if (!precheckBox.isChecked()) {
                            current_box.setChecked(false);
                            displayALertDismiss("Sorry !","Please Select the Previous demand!",
                                    "ic_prevcheck","Ok");
                            mallTermCheckBox.setChecked(false);
                            all_term_condition=0;

                        } else if (next_checkbox.isChecked())
                            current_box.setChecked(true);
                    } else {
                        if (!precheckBox.isChecked()) {
                            current_box.setChecked(false);
                            displayALertDismiss("Sorry !","Please Select the Previous demand!",
                                    "ic_prevcheck","Ok");
                            mallTermCheckBox.setChecked(false);
                            all_term_condition=0;

                        } else {
                            if(currentPosition == demandlist.size()-1){
                                if(current_box.isChecked()){
                                    mallTermCheckBox.setChecked(true);
                                    all_term_condition=1;
                                }else{
                                    mallTermCheckBox.setChecked(false);
                                    all_term_condition=0;
                                }
                            }else{
                                allTermCheckBox.setChecked(false);

                                all_term_condition = 0;
                            }
                        }
                    }
                } else if (currentPosition == 0) {
                    if (getItemCount() > 1) {
                        LinearLayout next_linearLayout = (LinearLayout) rv_demand.getChildAt((currentPosition));

                        next_checkbox = (CheckBox) rv_demand.getChildAt((currentPosition) + 1).findViewById(R.id.sel_checkbox);

                        if (next_checkbox.isChecked())
                            current_box.setChecked(true);
                    } else {
                        LinearLayout next_linearLayout = (LinearLayout) rv_demand.getChildAt((currentPosition));

                        next_checkbox = (CheckBox) rv_demand.getChildAt((currentPosition)).findViewById(R.id.sel_checkbox);

                        if (next_checkbox.isChecked())
                            current_box.setChecked(true);
                    }

                } else {
                    LinearLayout next_linearLayout = (LinearLayout) rv_demand.getChildAt((currentPosition) + 1);

                    next_checkbox = (CheckBox) next_linearLayout.findViewById(R.id.sel_checkbox);

                    if (next_checkbox.isChecked())
                        current_box.setChecked(true);
                }
                getSelectedItemTotal();

            }
        });

    }

    @Override
    public int getItemCount() {
        return demandlist.size();
    }

    public void getSelectedItemTotal() {
        amount = 0;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb_sNo = new StringBuilder();
        StringBuilder sb_taxNo = new StringBuilder();
        StringBuilder sb_amt = new StringBuilder();
        StringBuilder sb_amt1 = new StringBuilder();
        total_demand_sel = 0;
        for (int i = 0; i < getItemCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) rv_demand.getChildAt(i);
            CheckBox checkBoxInRow = (CheckBox) linearLayout.findViewById(R.id.sel_checkbox);
            if (checkBoxInRow.isChecked()) {
                total_demand_sel = total_demand_sel + demandlist.get(i).getDemand();
                Log.e("checkedddd", "" + total_demand_sel);
                Integer amountbalance = demandlist.get(i).getDemand();
                Integer sNo = demandlist.get(i).getSno();
                String year = demandlist.get(i).getFinancialYear();
                String taxNo = demandlist.get(i).getAssessmentNo();

                ArrayList<Integer> amtList;
                ArrayList<String> yearList;
                ArrayList<Integer> snoList;
                ArrayList<String> taxNoList;

                amtList = new ArrayList<>();
                yearList = new ArrayList<>();
                snoList = new ArrayList<>();
                taxNoList = new ArrayList<>();

                amtList.add(amountbalance);
                yearList.add(year);
                snoList.add(sNo);
                taxNoList.add(taxNo);

                Log.e("amtList", "" + amtList);
                Log.e("yearList", "" + yearList);

                for (String str : yearList) {
                    sb.append(str).append(",");
                    Log.e("sb", sb.toString());
                }

                for (String strtaxNo : taxNoList) {
                    sb_taxNo.append(strtaxNo).append(",");
                    Log.e("sb", sb_taxNo.toString());
                }

                for (Integer str2 : amtList) {
                    sb_amt1.append(str2);
                    amount += str2;
                }

                for (Integer strsNo : amtList) {
                    sb_sNo.append(strsNo).append(",");
                   Log.e("sno",sb_sNo.toString());
                }

                Log.e("amt in adpater", "" + total_demand_sel);


            }
        }
        sb_amt.append(amount);
        Log.e("sb_amt", sb_amt.toString());
        setTotalForActivity(String.valueOf(total_demand_sel), sb.toString(), sb_amt.toString(),sb_sNo.toString(),
                sb_taxNo.toString());
    }

    public void setTotalForActivity(String total, String year, String balance, String sNo, String taxNo) {
        OnlineFiling onlineFiling = (OnlineFiling) context;
        onlineFiling.setTotalFromAdapter(total, year, balance,sNo,taxNo);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        TextView tvFinYear, termNumber, tvDemand;
        LinearLayout items;
        public ViewHolder(View itemView) {
            super(itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.sel_checkbox);
            tvFinYear = (TextView) itemView.findViewById(R.id.fin_year);
            tvDemand = (TextView) itemView.findViewById(R.id.demand);
            items =(LinearLayout)itemView.findViewById(R.id.rowItem);
        }
    }


    private void displayALertDismiss(String title, String receiptNo, String getimg, String btnName) {

        int  img = context.getResources().getIdentifier(getimg,
                "drawable", context.getPackageName());
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setIcon(img);
        dialog.setTitle(title);
        dialog.setMessage(receiptNo);
        dialog.setPositiveButton(btnName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        AlertDialog ad = dialog.create();
        ad.show();
    }
}


