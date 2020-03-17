package co.kyozen.stokbarang;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter(Context context, List<Result> results){
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewID.setText(result.getId());
        holder.textViewNama.setText(result.getNama());
        holder.textViewJumlah.setText(result.getJml());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textID) TextView textViewID;
        @BindView(R.id.textNama) TextView textViewNama;
        @BindView(R.id.textJumlah) TextView textViewJumlah;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String id = textViewID.getText().toString();
            String nama = textViewNama.getText().toString();
            String jml = textViewJumlah.getText().toString();

            Intent i = new Intent(context, UpdateActivity.class);
            i.putExtra("id", id);
            i.putExtra("nama", nama);
            i.putExtra("jml", jml);
            context.startActivity(i);
        }
    }
}
