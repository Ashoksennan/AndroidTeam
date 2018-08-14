package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.SharedAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.BirthCertificateSearch_Pojo;

/**
 * Created by Admin on 8/1/2018.
 */

public class BirthCertificateSearch_Adapter extends RecyclerView.Adapter<BirthCertificateSearch_Adapter.ViewHolder> {


    private ArrayList<BirthCertificateSearch_Pojo> beanlist = new ArrayList<>();
    private Context context;
    private callback mcallback;


    public BirthCertificateSearch_Adapter(ArrayList<BirthCertificateSearch_Pojo> beanlist, Context context, callback mcallback) {
        this.beanlist = beanlist;
        this.context = context;
        this.mcallback = mcallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_birth_certificate_search, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.bcs_tv_child_name.setText(String.valueOf(beanlist.get(i).getChildName()));
        viewHolder.bcs_tv_father_name.setText(String.valueOf(beanlist.get(i).getFatherName()));
        viewHolder.bcs_tv_mother_name.setText(String.valueOf(beanlist.get(i).getMotherName()));
        viewHolder.bcs_tv_gender.setText(String.valueOf(beanlist.get(i).getSex()));
        viewHolder.bcs_tv_dob.setText(String.valueOf(beanlist.get(i).getDateOfBirth()));
        viewHolder.bcs_tv_reg_date.setText(String.valueOf(beanlist.get(i).getRegDate()));
        viewHolder.bcs_tv_name_reg_date.setText(String.valueOf(beanlist.get(i).getNameRegDate()));


        Log.e("------",""+String.valueOf(beanlist.get(i).getChildName()));
        viewHolder.bcs_adap_iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallback.downloadSelected_FromAdapter(beanlist.get(i).getDivNo(),
                        beanlist.get(i).getRegYear(),beanlist.get(i).getRegNo());
            }
        });

    }

    @Override
    public int getItemCount() {
        return beanlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bcs_tv_child_name,bcs_tv_father_name,bcs_tv_mother_name,
                bcs_tv_gender,bcs_tv_dob,bcs_tv_reg_date,bcs_tv_name_reg_date;

        TextView bcs_adap_tv_title_name,bcs_adap_tv_title_fathername,bcs_adap_tv_title_dob;

        LinearLayout bcs_adap_ll_mother_name,bcs_adap_ll_nameregDate;

        ImageView bcs_adap_iv_download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bcs_tv_child_name = itemView.findViewById(R.id.bcs_tv_child_name);
            bcs_tv_father_name = itemView.findViewById(R.id.bcs_tv_father_name);
            bcs_tv_mother_name = itemView.findViewById(R.id.bcs_tv_mother_name);
            bcs_tv_gender = itemView.findViewById(R.id.bcs_tv_gender);
            bcs_tv_dob = itemView.findViewById(R.id.bcs_tv_dob);
            bcs_tv_reg_date = itemView.findViewById(R.id.bcs_tv_reg_date);
            bcs_tv_name_reg_date = itemView.findViewById(R.id.bcs_tv_name_reg_date);

            bcs_adap_iv_download = itemView.findViewById(R.id.bcs_adap_iv_download);

        }
    }


    public interface callback{
        void downloadSelected_FromAdapter(String divNo, int regYear, int regNo);
    }

}
