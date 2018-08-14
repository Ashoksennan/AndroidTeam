package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Grievances;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Grievance.TrackGrievanceBean;


public class TrackGrievanceAdapter extends RecyclerView.Adapter<TrackGrievanceAdapter.ViewHolder> {
    ArrayList<TrackGrievanceBean> grievanceList;
    Context context;

    public TrackGrievanceAdapter(ArrayList<TrackGrievanceBean> grievanceList, Context context) {
        this.grievanceList = grievanceList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track_griveance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_grievancenumber.setText(grievanceList.get(position).getmGrievanceNo() + "");
        holder.tv_complaindate.setText(grievanceList.get(position).getmComplaintDate());
        holder.tv_complaintype.setText(grievanceList.get(position).getmComplaintType());
        holder.tv_status.setText(grievanceList.get(position).getmStatus());
    }

    @Override
    public int getItemCount() {
        return grievanceList.size();
    }

    public void filterList(ArrayList<TrackGrievanceBean> filterdNames) {
        this.grievanceList = filterdNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_grievancenumber)
        TextView tv_grievancenumber;
        @BindView(R.id.tv_complaindate)
        TextView tv_complaindate;
        @BindView(R.id.tv_complaintype)
        TextView tv_complaintype;
        @BindView(R.id.tv_status)
        TextView tv_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
