package mbd.student.gurukustudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.activity.teacher.BookingTeacherActivity;
import mbd.student.gurukustudent.activity.teacher.DetailTeacherActivity;
import mbd.student.gurukustudent.model.teacher.Teacher;
import mbd.student.gurukustudent.model.transaction.Data;

/**
 * Created by Naufal on 21/02/2018.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private Context context;
    private List<Data> dataList;

    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public TransactionAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Data data = dataList.get(position);
        Teacher teacher = data.getTeacher();

        String namaGuru = teacher.getFirstName() + " " + teacher.getLastName();
        String statusMsg = "";
        int status = data.getStatus();

        setProfileImage(holder.profileImage, namaGuru);

        if (status == 0) {
            statusMsg = "Menunggu Konfirmasi";
            holder.iconStatus.setImageResource(R.drawable.ic_waiting_confirmation);
        } else if(status == 1) {
            statusMsg = "Telah disetujui";
            holder.iconStatus.setImageResource(R.drawable.ic_accepted);
        }

        holder.tvNamaGuru.setText(namaGuru);
        holder.tvSatus.setText(statusMsg);
        holder.cvItemGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookData = new Gson().toJson(data);
                Intent i = new Intent(context, BookingTeacherActivity.class);
                i.putExtra("from", "Trx");
                i.putExtra("bookData", bookData);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cvItemGuru)
        CardView cvItemGuru;
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

    private void setProfileImage(ImageView profileImage, String nama) {
        String letter = "A";

        if(nama != null && !nama.isEmpty()) {
            letter = nama.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);
        profileImage.setImageDrawable(mDrawableBuilder);
    }
}
