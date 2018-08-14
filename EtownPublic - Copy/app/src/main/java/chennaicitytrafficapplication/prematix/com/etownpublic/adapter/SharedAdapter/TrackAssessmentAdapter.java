package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter;

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

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.activity.Shared_Modules.Shared_Common_All_Tax.ViewTracking;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.SharedBean.TrackAssmentNoEntity;

public class TrackAssessmentAdapter extends RecyclerView.Adapter<TrackAssessmentAdapter.MyViewHolder> {

    List<TrackAssmentNoEntity> trackList;
    Context context;

    public TrackAssessmentAdapter(Context context, List<TrackAssmentNoEntity> entityList) {
        this.context = context;
        this.trackList = entityList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracknewassessment_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TrackAssmentNoEntity assmentNoEntity = trackList.get(position);


        if (assmentNoEntity.getTax_type().equals("Property")) {

            holder.tvBlockNo.setText(Html.fromHtml("<b>Block No : </b> " + assmentNoEntity.getBlockNo()));
            holder.tvBlNo.setText(Html.fromHtml("<b>B.l No : </b> " + assmentNoEntity.getBlNo()));
        } else if (assmentNoEntity.getTax_type().equals("Water")) {

            holder.tvBlockNo.setText(Html.fromHtml("<b>Door no : </b> " + assmentNoEntity.getBlockNo()));
            holder.tvBlNo.setText(Html.fromHtml("<b>Con Type: </b> " + assmentNoEntity.getBlNo()));

        } else if (assmentNoEntity.getTax_type().equals("NonTax")) {

            holder.tvBlockNo.setText(Html.fromHtml("<b> Door no : </b> " + assmentNoEntity.getBlockNo()));
            holder.tvBlNo.setText(Html.fromHtml("<b> LeaseName: </b> " + assmentNoEntity.getBlNo()));

        }
        holder.tvRequestNo.setText(Html.fromHtml("<b>Request No : </b> " + assmentNoEntity.getReqNo()));
        final String date[] = assmentNoEntity.getReqDate().split("T");

        holder.tvRequestDate.setText(Html.fromHtml("<b>Request Date : </b> " + date[0]));
        holder.tvDistrict.setText(Html.fromHtml("<b>District : </b> " + assmentNoEntity.getDistrict()));
        holder.tvPanchayat.setText(Html.fromHtml("<b>Panchayat : </b> " + assmentNoEntity.getPanchayat()));
        holder.tvName.setText(Html.fromHtml("<b>Name : </b> " + assmentNoEntity.getName()));

        holder.tvWardNo.setText(Html.fromHtml("<b>Ward No : </b> " + assmentNoEntity.getWardNo()));
        holder.tvStreetNsme.setText(Html.fromHtml(assmentNoEntity.getStreetName()));
        holder.tvStatus.setText(Html.fromHtml("<b>Status : </b> " + assmentNoEntity.getStatus()));

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewTracking.class);
                i.putExtra("requestNo", assmentNoEntity.getReqNo());
                i.putExtra("mobileNo", assmentNoEntity.getMobileNo());
                i.putExtra("emailId", assmentNoEntity.getEmailId());
                i.putExtra("district", assmentNoEntity.getDistrict());
                i.putExtra("panchayat", assmentNoEntity.getPanchayat());
                i.putExtra("name", assmentNoEntity.getName());
                i.putExtra("blNo", assmentNoEntity.getBlNo());
                i.putExtra("blockNo", assmentNoEntity.getBlockNo());
                i.putExtra("wardNo", assmentNoEntity.getWardNo());
                i.putExtra("streetName", assmentNoEntity.getStreetName());
                i.putExtra("status", assmentNoEntity.getStatus());
                i.putExtra("reqDate", date[0]);
                i.putExtra("Tax_Type", assmentNoEntity.getTax_type());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvRequestNo, tvRequestDate, tvDistrict, tvPanchayat, tvName, tvBlNo, tvBlockNo, tvWardNo, tvStreetNsme, tvStatus;
        Button btnView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRequestNo = itemView.findViewById(R.id.tvRequestNo);
            tvRequestDate = itemView.findViewById(R.id.tvRequestDate);
            tvDistrict = itemView.findViewById(R.id.tvDistrict);
            tvPanchayat = itemView.findViewById(R.id.tvPanchayat);
            tvName = itemView.findViewById(R.id.tvName);
            tvBlNo = itemView.findViewById(R.id.tvBlNo);
            tvBlockNo = itemView.findViewById(R.id.tvBlockNo);
            tvWardNo = itemView.findViewById(R.id.tvWardNo);
            tvStreetNsme = itemView.findViewById(R.id.tvStreetName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnView = itemView.findViewById(R.id.btnSubmit);

        }
    }
}
