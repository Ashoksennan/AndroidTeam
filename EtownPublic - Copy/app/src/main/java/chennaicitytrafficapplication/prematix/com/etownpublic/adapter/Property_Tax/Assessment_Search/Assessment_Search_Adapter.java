package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Property_Tax.Assessment_Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.property.PaidHistory.AssessmentSearch.AssessmentSearch_Pojo;

public class Assessment_Search_Adapter extends RecyclerView.Adapter<Assessment_Search_Adapter.MyViewHolder> {

    private List<AssessmentSearch_Pojo> balanceList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView finYear, halfYear, balance;

        public MyViewHolder(View view) {
            super(view);
            finYear = view.findViewById(R.id.tv_finyear);
            halfYear = view.findViewById(R.id.tv_halfyear);
            balance = view.findViewById(R.id.tv_balance);
        }
    }


    public Assessment_Search_Adapter(List<AssessmentSearch_Pojo> balanceList) {
        this.balanceList = balanceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assessmentsearch_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AssessmentSearch_Pojo balance = balanceList.get(position);
        holder.finYear.setText(balance.getFinYear());
        holder.halfYear.setText("" + balance.getHalfYear());
        holder.balance.setText("" + balance.getBalance());
    }

    @Override
    public int getItemCount() {
        return balanceList.size();
    }
}
