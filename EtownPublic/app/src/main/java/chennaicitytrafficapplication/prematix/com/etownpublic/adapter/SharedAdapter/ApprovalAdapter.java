package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.SharedBean.ApprovalEntity;

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.MyViewHolder> {

    List<ApprovalEntity> approvalList;
    Context context;

    public ApprovalAdapter(Context context, List<ApprovalEntity> approvalList) {
        this.context = context;
        this.approvalList = approvalList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tracknewassess_approval_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ApprovalEntity approvalEntity = approvalList.get(position);

        int pos = position + 1;

        Log.e("ppp", "----" + pos);

        holder.tvSno.setText(Html.fromHtml("" + pos));
        holder.tvDate.setText(Html.fromHtml(approvalEntity.getReqDate()));
        holder.tvRemarks.setText(Html.fromHtml(approvalEntity.getRemarks()));
        holder.tvStatus.setText(Html.fromHtml(approvalEntity.getStatus()));

    }

    @Override
    public int getItemCount() {
        return approvalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSno, tvDate, tvRemarks, tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSno = itemView.findViewById(R.id.tv_sno);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvRemarks = itemView.findViewById(R.id.tv_remarks);
            tvStatus = itemView.findViewById(R.id.tv_status);


        }
    }
}