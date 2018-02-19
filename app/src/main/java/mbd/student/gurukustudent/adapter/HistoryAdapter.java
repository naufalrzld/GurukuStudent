package mbd.student.gurukustudent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.model.history.History;
import mbd.student.gurukustudent.model.teacher.Teacher;

/**
 * Created by Naufal on 19/02/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<History> historyList;

    public HistoryAdapter(Context context, List<History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        History history = historyList.get(position);
        Teacher teacher = history.getTeacher();

        String namaGuru = teacher.getFirstName() + " " + teacher.getLastName();
        String statusMsg = "";
        int status = history.getStatus();

        if (status == 0) {
            statusMsg = "Menunggu Konfirmasi";
        } else if(status == 1) {
            statusMsg = "Telah disetujui";
        }

        holder.tvNamaGuru.setText(namaGuru);
        holder.tvSatus.setText(statusMsg);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_image)
        ImageView profileImage;
        @BindView(R.id.tvNamaGuru)
        TextView tvNamaGuru;
        @BindView(R.id.tvStatus)
        TextView tvSatus;
        @BindView(R.id.icon_status)
        ImageView iconStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
