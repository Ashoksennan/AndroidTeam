package chennaicitytrafficapplication.prematix.com.etownpublic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.BirthAbstractYearWiseBean;


/**
 * Created by Admin on 8/1/2018.
 */

public class BirthAbstractYearWiseAdapter extends RecyclerView.Adapter<BirthAbstractYearWiseAdapter.ViewHolder> {
    List<BirthAbstractYearWiseBean> yearwiselist;
    Context context;

    public BirthAbstractYearWiseAdapter(List<BirthAbstractYearWiseBean> yearwiselist, Context context) {
        this.yearwiselist = yearwiselist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yearwise_row_birth_abstract, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("size", yearwiselist.size() + "");
        Log.e("fvh", yearwiselist.get(position).getLiveBirth() + "");
        holder.year_wise_year.setText(yearwiselist.get(position).getYear());
        Log.e("fbgvd", yearwiselist.get(position).getYear());
        holder.year_wise_live_birth.setText(yearwiselist.get(position).getLiveBirth());
        holder.year_wise_still_birth.setText(yearwiselist.get(position).getStillBirth());
        holder.year_wise_total.setText(yearwiselist.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return yearwiselist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.year_wise_year)
        TextView year_wise_year;
        @BindView(R.id.year_wise_live_birth)
        TextView year_wise_live_birth;
        @BindView(R.id.year_wise_still_birth)
        TextView year_wise_still_birth;
        @BindView(R.id.year_wise_total)
        TextView year_wise_total;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
