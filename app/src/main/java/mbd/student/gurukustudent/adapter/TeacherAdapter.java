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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.activity.teacher.DetailTeacherActivity;
import mbd.student.gurukustudent.model.teacher.Category;
import mbd.student.gurukustudent.model.teacher.Teacher;

/**
 * Created by Naufal on 13/02/2018.
 */

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {
    private static final String SEPARATOR = ", ";
    private Context context;
    private List<Teacher> listGutu;
    private String bookData;

    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public TeacherAdapter(Context context, List<Teacher> listGutu, String bookData) {
        this.context = context;
        this.listGutu = listGutu;
        this.bookData = bookData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Teacher teacher = listGutu.get(position);
        List<Category> categories = teacher.getCategories();

        Locale localeID = new Locale("in", "ID");

        NumberFormat numberFormatCurrency = NumberFormat.getCurrencyInstance(localeID);
        numberFormatCurrency.setMaximumFractionDigits(0);

        String nama = teacher.getFirstName() + " " + teacher.getLastName();
        int harga = teacher.getPrice();

        setProfileImage(holder.profileImage, nama);

        holder.tvNamaGuru.setText(nama);

        StringBuilder stringBuilder = new StringBuilder();

        for (Category c : categories) {
            stringBuilder.append(c.getCategoryName());
            stringBuilder.append(SEPARATOR);
        }

        String category = stringBuilder.toString();
        category = category.substring(0, category.length() - SEPARATOR.length());

        holder.tvCategory.setText(category);
        holder.tvHarga.setText(numberFormatCurrency.format(harga));
        holder.cvItemGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = new Gson().toJson(teacher);

                Intent i = new Intent(context, DetailTeacherActivity.class);
                i.putExtra("dataTeacher", data);
                i.putExtra("bookData", bookData);
                context.startActivity(i);
            }
        });
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
        @BindView(R.id.tvCategory)
        TextView tvCategory;
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
