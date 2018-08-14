package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Death;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Birth_Death.TrackDeathRegistration_Pojo;


/**
 * Created by priyadharshini on 31/07/2018.
 */

public class TrackDeathRegistrationAdapter extends RecyclerView.Adapter<TrackDeathRegistrationAdapter.ViewHolder> {

    ArrayList<TrackDeathRegistration_Pojo> mbeanlist = new ArrayList<>();
    Context mContext;
    DeathSpecificDetails mSpecificDetails;

    public TrackDeathRegistrationAdapter(ArrayList<TrackDeathRegistration_Pojo> beanlist, Context context, DeathSpecificDetails specificDetails) {
        this.mbeanlist = beanlist;
        this.mContext = context;
        this.mSpecificDetails = specificDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_track_birth_registration, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv_requestNo.setText(mbeanlist.get(i).getmRequestNo());
        viewHolder.tv_requestDate.setText(mbeanlist.get(i).getmRequestDate());
        viewHolder.tv_districtname.setText(mbeanlist.get(i).getmDistrict());
        Log.e("dfjgb", "FGhf");
        viewHolder.tv_panchayat.setText(mbeanlist.get(i).getmPanchayat());
        viewHolder.tv_childname.setText(mbeanlist.get(i).getmDeceasedName());
        viewHolder.tv_childname_.setText("Name");
        viewHolder.tv_fathername.setText(mbeanlist.get(i).getmFatherName());
        viewHolder.tv_dob.setText(mbeanlist.get(i).getmDOB());
        viewHolder.tv_mobileno.setText(mbeanlist.get(i).getmMobileNo());
        viewHolder.tv_gender.setText(mbeanlist.get(i).getmGender());
        viewHolder.tv_birthplace.setText(mbeanlist.get(i).getmBirthPlace());
        viewHolder.tv_status.setText(mbeanlist.get(i).getmStatus());
        viewHolder.tv_requestNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpecificDetails.getposition(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mbeanlist.size();
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
        @BindView(R.id.tv_childname_)
        TextView tv_childname_;
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

    public interface DeathSpecificDetails {
        void getposition(int pos);
    }
}
