package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.BirthAbstractWardWise_Pojo;

/**
 * Created by Admin on 8/1/2018.
 */

public class BirthAbstract_Wardwise_Adapter extends RecyclerView.Adapter<BirthAbstract_Wardwise_Adapter.ViewHolder> {


    private ArrayList<BirthAbstractWardWise_Pojo> beanlist = new ArrayList<>();
    private Context context;
    private callback mcallback;


    public BirthAbstract_Wardwise_Adapter(ArrayList<BirthAbstractWardWise_Pojo> beanlist, Context context, callback mcallback) {
        this.beanlist = beanlist;
        this.context = context;
        this.mcallback = mcallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_birth_abstract, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.ba_adap_tv_year.setText(String.valueOf(beanlist.get(i).getRegYear()));
        viewHolder.ba_adap_tv_year.setPaintFlags( viewHolder.ba_adap_tv_year.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        viewHolder.ba_adap_tv_livebirth.setText(String.valueOf(beanlist.get(i).getLiveBirth()));
        viewHolder.ba_adap_tv_stillbirth.setText(String.valueOf(beanlist.get(i).getStillBirth()));
        viewHolder.ba_adap_tv_total.setText(String.valueOf(beanlist.get(i).getTotal()));



        viewHolder.ba_adap_tv_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallback.wardSelectedFromAdapter(String.valueOf(beanlist.get(i).getRegYear()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return beanlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ba_adap_tv_year, ba_adap_tv_livebirth, ba_adap_tv_stillbirth, ba_adap_tv_total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ba_adap_tv_year = itemView.findViewById(R.id.ba_adap_tv_year);
            ba_adap_tv_livebirth = itemView.findViewById(R.id.ba_adap_tv_livebirth);
            ba_adap_tv_stillbirth = itemView.findViewById(R.id.ba_adap_tv_stillbirth);
            ba_adap_tv_total = itemView.findViewById(R.id.ba_adap_tv_total);
        }
    }


    public interface callback{
        void wardSelectedFromAdapter(String ward);
    }

}
