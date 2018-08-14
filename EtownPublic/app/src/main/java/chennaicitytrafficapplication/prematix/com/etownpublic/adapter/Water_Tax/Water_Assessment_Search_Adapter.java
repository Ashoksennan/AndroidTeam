package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Water_Tax;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.Water_Tax.Water_Assessment_Search_Pojo;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.property.PaidHistory.AssessmentSearch.AssessmentSearch_Pojo;

public class Water_Assessment_Search_Adapter extends RecyclerView.Adapter<Water_Assessment_Search_Adapter.MyViewHolder> {

        private List<Water_Assessment_Search_Pojo> balanceList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView finYear, halfYear, balance;

            public MyViewHolder(View view) {
                super(view);
                finYear = view.findViewById(R.id.tv_finyear);
                halfYear = view.findViewById(R.id.tv_halfyear);
                halfYear.setText("QuarterYear");
                balance = view.findViewById(R.id.tv_balance);
            }
        }


        public Water_Assessment_Search_Adapter(List<Water_Assessment_Search_Pojo> balanceList) {
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
            Water_Assessment_Search_Pojo balance = balanceList.get(position);
            holder.finYear.setText(balance.getFinancialYear());
            holder.halfYear.setText(""+balance.getQuarterYear());
            holder.balance.setText(""+balance.getBalance());
        }

        @Override
        public int getItemCount() {
            return balanceList.size();
        }
    }
