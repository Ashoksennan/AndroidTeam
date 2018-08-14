package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Districts;

/**
 * Created by Admin on 8/3/2018.
 */

public class DistrictnamesAdapter extends RecyclerView.Adapter<DistrictnamesAdapter.ViewHolder> {
    List<Districts> districtsList;

    public DistrictnamesAdapter(List<Districts> districtsList) {
        this.districtsList = districtsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_districtname, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_names.setText(districtsList.get(position).getmDistrictName());
    }

    public void filterList(ArrayList<Districts> filterdNames) {
        this.districtsList = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return districtsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.tv_names)
        TextView tv_names;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
