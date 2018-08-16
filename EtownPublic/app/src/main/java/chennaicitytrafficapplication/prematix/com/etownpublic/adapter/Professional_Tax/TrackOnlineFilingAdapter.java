package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Professional_Tax;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import chennaicitytrafficapplication.prematix.com.etownpublic.Model.Profession_Tax.TrackOnlineFilingEntity;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.Profession.ViewOnlineFilingTracking;

public class TrackOnlineFilingAdapter extends RecyclerView.Adapter<TrackOnlineFilingAdapter.MyViewHolder>{

    List<TrackOnlineFilingEntity> trackList;
    Context context;

    public TrackOnlineFilingAdapter(Context context, List<TrackOnlineFilingEntity> entityList){
        this.context = context;
        this.trackList=entityList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_onlinefiling_item_view,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TrackOnlineFilingEntity onlineFilingEntity = trackList.get(position);

        holder.tvRequestNo.setText(Html.fromHtml("<b>Request No : </b> " +onlineFilingEntity.getReqNo()));
        holder.tvDistrict.setText(Html.fromHtml("<b>District : </b> " +onlineFilingEntity.getDistrict()));
        holder.tvPanchayat.setText(Html.fromHtml("<b>Panchayat : </b> " +onlineFilingEntity.getPanchayat()));
        holder.tvName.setText(Html.fromHtml("<b>Name : </b> " +onlineFilingEntity.getName()));
        holder.tvTaxSNo.setText(Html.fromHtml("<b>Tax S No : </b> " +onlineFilingEntity.getTaxSNo()));
        holder.tvFinYear.setText(Html.fromHtml("<b>Fin Year : </b> " +onlineFilingEntity.getFinYear()));
        holder.tvTaxNo.setText(Html.fromHtml("<b>Tax No : </b> " +onlineFilingEntity.getTaxNo()));
        holder.tvOrgName.setText(Html.fromHtml(onlineFilingEntity.getOrgName()));
        holder.tvStatus.setText(Html.fromHtml("<b>Status : </b> " +onlineFilingEntity.getStatus()));

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewOnlineFilingTracking.class);
                i.putExtra("RequestNo",onlineFilingEntity.getReqNo());
                i.putExtra("RequestDate",onlineFilingEntity.getReqDate());
                i.putExtra("District",onlineFilingEntity.getDistrict());
                i.putExtra("Panchayat",onlineFilingEntity.getPanchayat());
                i.putExtra("FinYear",onlineFilingEntity.getFinYear());
                i.putExtra("Name",onlineFilingEntity.getName());
                i.putExtra("AssessmentType",onlineFilingEntity.getAssessmentType());
                i.putExtra("TradeName",onlineFilingEntity.getTradeName());
                i.putExtra("OrganizationCode",onlineFilingEntity.getOrgCode());
                i.putExtra("OrganizationName",onlineFilingEntity.getOrgName());
                i.putExtra("TaxSno",onlineFilingEntity.getTaxSNo());
                i.putExtra("TradeOrgName",onlineFilingEntity.getTradeOrgName());
                i.putExtra("TaxNo",onlineFilingEntity.getTaxNo());
                i.putExtra("Status",onlineFilingEntity.getStatus());
                i.putExtra("MobileNo",onlineFilingEntity.getMobileNo());
                i.putExtra("EmailId",onlineFilingEntity.getEmailId());
                i.putExtra("Date",onlineFilingEntity.getDate());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvRequestNo,tvDistrict,tvName,tvTaxSNo,tvOrgName,tvPanchayat,tvFinYear,tvTaxNo,tvStatus;
        Button btnView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRequestNo = (TextView)itemView.findViewById(R.id.tvRequestNo);
            tvDistrict = (TextView)itemView.findViewById(R.id.tvDistrict);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvTaxSNo=(TextView)itemView.findViewById(R.id.tvTaxSNo);
            tvOrgName=(TextView)itemView.findViewById(R.id.tvOrgName);
            tvPanchayat=(TextView)itemView.findViewById(R.id.tvPanchayat);
            tvFinYear=(TextView)itemView.findViewById(R.id.tvFinYear);
            tvTaxNo = (TextView)itemView.findViewById(R.id.tvTaxNo);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);

            btnView = (Button)itemView.findViewById(R.id.btnSubmit);

        }
    }
}
