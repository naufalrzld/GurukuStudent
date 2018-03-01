package mbd.student.gurukustudent.activity.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.model.teacher.Teacher;

public class DetailTeacherActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.tvNama)
    TextView tvNama;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvNoTlp)
    TextView tvNoTlp;
    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @BindView(R.id.tvNoWa)
    TextView tvNoWa;
    @BindView(R.id.tvLineAccount)
    TextView tvLineAccount;
    @BindView(R.id.btnBook)
    Button btnBook;

    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    private String bookData;
    private Intent dataIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_teacher);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_title_detail_teacher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dataIntent = getIntent();
        bookData = dataIntent.getStringExtra("bookData");

        Locale localeID = new Locale("in", "ID");

        NumberFormat numberFormatCurrency = NumberFormat.getCurrencyInstance(localeID);
        numberFormatCurrency.setMaximumFractionDigits(0);

        Teacher teacher = new Gson().fromJson(dataIntent.getStringExtra("dataTeacher"), Teacher.class);

        String nama = teacher.getFirstName() + " " + teacher.getLastName();
        String username = teacher.getUsername();
        String noTlp = teacher.getNoTlp();
        String email = teacher.getEmail();
        String noWA = teacher.getNoWA();
        String lineAccount = teacher.getLineAccount();
        int price = teacher.getPrice();

        setProfileImage(nama);
        tvPrice.setText(numberFormatCurrency.format(price));
        tvNama.setText(nama);
        tvUsername.setText(username);
        tvNoTlp.setText(noTlp);
        tvEmail.setText(email);
        tvNoWa.setText(noWA);
        tvLineAccount.setText(lineAccount);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailTeacherActivity.this, BookingTeacherActivity.class);
                i.putExtra("from", "DetailTeacher");
                i.putExtra("bookData", bookData);
                i.putExtra("dataTeacher", dataIntent.getStringExtra("dataTeacher"));
                startActivity(i);
            }
        });
    }

    private void setProfileImage(String nama) {
        String letter = "A";

        if(nama != null && !nama.isEmpty()) {
            letter = nama.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);
        profileImage.setImageDrawable(mDrawableBuilder);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
