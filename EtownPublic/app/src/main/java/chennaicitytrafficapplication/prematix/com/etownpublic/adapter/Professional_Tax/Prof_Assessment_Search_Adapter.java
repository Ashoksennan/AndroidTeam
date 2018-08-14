package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Professional_Tax;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Profession_Tax.BalanceHistory;

public class Prof_Assessment_Search_Adapter extends RecyclerView.Adapter<Prof_Assessment_Search_Adapter.MyViewHolder> {

    ArrayList<BalanceHistory> paidList;
    Context mContext;

    public Prof_Assessment_Search_Adapter(Context context, ArrayList<BalanceHistory> paidList) {
        this.mContext = context;
        this.paidList = paidList;
    }


    @NonNull
    @Override
    public Prof_Assessment_Search_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_professionaltax_assessmentsearch, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Prof_Assessment_Search_Adapter.MyViewHolder holder, int position) {
        BalanceHistory paidHistory = paidList.get(position);


        if (paidHistory.getTax_Type().equalsIgnoreCase("NonTax"))
            holder.mtxtHalfYear.setText(Html.fromHtml("<b> Month :</b>" + paidHistory.getHalfYear()));
        else
            holder.mtxtHalfYear.setText(Html.fromHtml("<b> Half Year :</b>" + paidHistory.getHalfYear()));

        holder.mtxtBalance.setText(Html.fromHtml("<b>Balance : </b> " + paidHistory.getBalance()));
        holder.mtxtDistrict.setText(Html.fromHtml("<b>District :</b>" + paidHistory.getDistrict()));
        holder.mtxtPanchayat.setText(Html.fromHtml("<b>Panchayat : </b>" + paidHistory.getPanchayat()));
        holder.mtxtFinancial.setText(Html.fromHtml("<b>Financial Yr :</b>" + paidHistory.getFinanicalYear()));
        holder.mtxtTaxNo.setText(Html.fromHtml("<b>Tax No :</b>" + paidHistory.getTaxNo()));

    }

    @Override
    public int getItemCount() {
        return paidList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mtxtBalance, mtxtDistrict, mtxtPanchayat, mtxtFinancial, mtxtHalfYear, mtxtTaxNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtxtBalance = itemView.findViewById(R.id.txt_balance);
            mtxtDistrict = itemView.findViewById(R.id.txt_district);
            mtxtPanchayat = itemView.findViewById(R.id.txt_panchayat);
            mtxtFinancial = itemView.findViewById(R.id.txt_financial);
            mtxtHalfYear = itemView.findViewById(R.id.txt_halfyear);
            mtxtTaxNo = itemView.findViewById(R.id.txt_taxno);


        }
    }
}

