package mbd.student.gurukustudent.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.model.guru.Guru;

/**
 * Created by Naufal on 13/02/2018.
 */

public class GuruAdapter extends RecyclerView.Adapter<GuruAdapter.ViewHolder> {
    private Context context;
    private List<Guru> listGutu;

    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public GuruAdapter(Context context, List<Guru> listGutu) {
        this.context = context;
        this.listGutu = listGutu;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.guru_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Guru guru = listGutu.get(position);

        Locale localeID = new Locale("in", "ID");

        NumberFormat numberFormatCurrency = NumberFormat.getCurrencyInstance(localeID);
        numberFormatCurrency.setMaximumFractionDigits(0);

        String nama = guru.getFirstName() + " " + guru.getLastName();
        String kemampuan = guru.getKemampuan();
        int harga = guru.getHarga();

        setProfileImage(holder.profileImage, nama);

        holder.tvNamaGuru.setText(nama);
        holder.tvKemampuan.setText(kemampuan);
        holder.tvHarga.setText(numberFormatCurrency.format(harga));
    }

    @Override
    public int getItemCount() {
        return listGutu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cvItemGuru)
        CardView cvItemGuru;
        @BindView(R.id.profile_image)
        ImageView profileImage;
        @BindView(R.id.tvNamaGuru)
        TextView tvNamaGuru;
        @BindView(R.id.tvKemampuan)
        TextView tvKemampuan;
        @BindView(R.id.tvHarga)
        TextView tvHarga;

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
