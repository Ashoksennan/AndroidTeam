package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Birth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.TrackBirthRegistration_Pojo;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class TrackBirthRegistration_Adapter extends RecyclerView.Adapter<TrackBirthRegistration_Adapter.ViewHolder> {

    ArrayList<TrackBirthRegistration_Pojo> beanlist = new ArrayList<>();
    Context context;
    SpecificDetails specificDetails;

    public TrackBirthRegistration_Adapter(ArrayList<TrackBirthRegistration_Pojo> beanlist, Context context, SpecificDetails specificDetails) {
        this.beanlist = beanlist;
        this.context = context;
        this.specificDetails = specificDetails;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_track_birth_registration, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv_requestNo.setText(beanlist.get(i).getmRequestNo());
        viewHolder.tv_requestDate.setText(beanlist.get(i).getmRequestDate());
        viewHolder.tv_districtname.setText(beanlist.get(i).getmDistrict());
        viewHolder.tv_panchayat.setText(beanlist.get(i).getmPanchayat());
        viewHolder.tv_childname.setText(beanlist.get(i).getmChildName());
        viewHolder.tv_fathername.setText(beanlist.get(i).getmFatherName());
        viewHolder.tv_dob.setText(beanlist.get(i).getmDOB());
        viewHolder.tv_mobileno.setText(beanlist.get(i).getmMobileNo());
        viewHolder.tv_gender.setText(beanlist.get(i).getmGender());
        viewHolder.tv_birthplace.setText(beanlist.get(i).getmBirthPlace());
        viewHolder.tv_status.setText(beanlist.get(i).getmStatus());
        viewHolder.tv_requestNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specificDetails.getpos(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.tv_requestNo)
        TextView tv_requestNo;
        @Nullable
        @BindView(R.id.tv_requestDate)
        TextView tv_requestDate;
        @Nullable
        @BindView(R.id.tv_districtname)
        TextView tv_districtname;
        @Nullable
        @BindView(R.id.tv_panchayat)
        TextView tv_panchayat;
        @Nullable
        @BindView(R.id.tv_childname)
        TextView tv_childname;
        @Nullable
        @BindView(R.id.tv_fathername)
        TextView tv_fathername;
        @Nullable
        @BindView(R.id.tv_dob)
        TextView tv_dob;
        @Nullable
        @BindView(R.id.tv_mobileno)
        TextView tv_mobileno;
        @Nullable
        @BindView(R.id.tv_gender)
        TextView tv_gender;
        @Nullable
        @BindView(R.id.tv_status)
        TextView tv_status;
        @Nullable
        @BindView(R.id.tv_birthplace)
        TextView tv_birthplace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface SpecificDetails {
        void getpos(int pos);
    }
}
