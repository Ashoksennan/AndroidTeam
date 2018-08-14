package chennaicitytrafficapplication.prematix.com.etownpublic.adapter.Property_Tax;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chennaicitytrafficapplication.prematix.com.etownpublic.R;
import chennaicitytrafficapplication.prematix.com.etownpublic.model.property.PaidHistory.TaxBalancePayment;

public class PaidHostoryAdapter extends RecyclerView.Adapter<PaidHostoryAdapter.MyViewHolder> {

    ArrayList<TaxBalancePayment> paidList;
    Context mContext;

    public PaidHostoryAdapter(Context context, ArrayList<TaxBalancePayment> paidList) {
        this.mContext = context;
        this.paidList = paidList;
    }


    @NonNull
    @Override
    public PaidHostoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.paid_history_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaidHostoryAdapter.MyViewHolder holder, int position) {
        TaxBalancePayment paidHistory = paidList.get(position);

        holder.tvFinYear.setText(Html.fromHtml("<b>Fin Year: </b> " + paidHistory.getFinancialYear()));
        holder.tvTaxAmt.setText(Html.fromHtml("<b>Tax Amount :</b>" + paidHistory.getTaxPaid()));
        holder.tvReceiptDate.setText(Html.fromHtml("<b>Receipt Date : </b>" + paidHistory.getReceiptDate()));
        holder.tvReceiptNo.setText(Html.fromHtml("<b>Receipt No :</b>" + paidHistory.getReceiptNo()));
        holder.tvPaid.setText(Html.fromHtml("<b>Payment Mode :</b>" + paidHistory.getPaymentMode()));

    }

    @Override
    public int getItemCount() {
        return paidList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvFinYear, tvPaid, tvReceiptNo, tvReceiptDate, tvTaxAmt, tvDowload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFinYear = itemView.findViewById(R.id.fin_year);
            tvPaid = itemView.findViewById(R.id.payment);
            tvReceiptNo = itemView.findViewById(R.id.receipt_no);
            tvReceiptDate = itemView.findViewById(R.id.receipt_date);
            tvTaxAmt = itemView.findViewById(R.id.tax_paid);
            tvDowload = itemView.findViewById(R.id.download);


        }
    }
}
