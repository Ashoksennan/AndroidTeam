package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.BirthAbstract_BirthDetails_Pojo;

/**
 * Created by Admin on 8/1/2018.
 */

public class BirthAbstract_BirthDetailsAdapter extends RecyclerView.Adapter<BirthAbstract_BirthDetailsAdapter.ViewHolder> {


    private ArrayList<BirthAbstract_BirthDetails_Pojo> beanlist = new ArrayList<>();
    private Context context;
    private callback mcallback;


    public BirthAbstract_BirthDetailsAdapter(ArrayList<BirthAbstract_BirthDetails_Pojo> beanlist, Context context, callback mcallback) {
        this.beanlist = beanlist;
        this.context = context;
        this.mcallback = mcallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_birth_abstract_birth_details, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        viewHolder.ba_adap_tv_RegDate.setText(beanlist.get(i).getRegDate());
        viewHolder.ba_adap_tv_childname.setText(beanlist.get(i).getChildName());
        viewHolder.ba_adap_tv_dob.setText(beanlist.get(i).getDateOfBirth());
        viewHolder.ba_adap_tv_gender.setText(beanlist.get(i).getGender());

        viewHolder.bcs_adap_ll_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallback.birthDetailsAlert_FromAdapter("Open", i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return beanlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ba_adap_tv_RegDate, ba_adap_tv_childname, ba_adap_tv_dob, ba_adap_tv_gender;
        LinearLayout bcs_adap_ll_items;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ba_adap_tv_RegDate = itemView.findViewById(R.id.ba_adap_tv_RegDate);
            ba_adap_tv_childname = itemView.findViewById(R.id.ba_adap_tv_childname);
            ba_adap_tv_dob = itemView.findViewById(R.id.ba_adap_tv_dob);
            ba_adap_tv_gender = itemView.findViewById(R.id.ba_adap_tv_gender);

            bcs_adap_ll_items = itemView.findViewById(R.id.bcs_adap_ll_items);


        }
    }


    public interface callback {
        void birthDetailsAlert_FromAdapter(String open, int position);
    }

}
