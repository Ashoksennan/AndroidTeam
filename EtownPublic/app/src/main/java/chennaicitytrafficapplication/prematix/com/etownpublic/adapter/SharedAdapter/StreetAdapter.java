package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.Street_Pojo;

/**
 * Created by admin on 15-09-2017.
 */

public class StreetAdapter extends RecyclerView.Adapter<StreetAdapter.ViewHolder> {

    private ArrayList<Street_Pojo> beanList;

    public StreetAdapter(ArrayList<Street_Pojo> beanList) {
        this.beanList = beanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPlace.setText(beanList.get(position).getStreetName());
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public void filterList(ArrayList<Street_Pojo> filterdNames) {
        this.beanList = filterdNames;
        notifyDataSetChanged();
    }

    public ArrayList<Street_Pojo> getList() {
        return beanList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlace;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPlace = itemView.findViewById(R.id.text);
        }
    }
}
