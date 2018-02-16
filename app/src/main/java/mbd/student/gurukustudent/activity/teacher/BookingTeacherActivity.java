package mbd.student.gurukustudent.activity.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.model.teacher.Teacher;

public class BookingTeacherActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvNama)
    TextView tvNama;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.rgDuration)
    RadioGroup rgDuration;
    @BindView(R.id.rbOneHour)
    RadioButton rbOneHour;
    @BindView(R.id.rbTwoHour)
    RadioButton rbTwoHour;
    @BindView(R.id.rbThreeHour)
    RadioButton rbThreeHour;

    @BindView(R.id.rgPaymentMethod)
    RadioGroup rgPaymentMethod;
    @BindView(R.id.rbWallet)
    RadioButton rbWallet;
    @BindView(R.id.rbCash)
    RadioButton rbCash;

    @BindView(R.id.lytWallet)
    LinearLayout lytWallet;
    @BindView(R.id.lytCash)
    LinearLayout lytCash;

    @BindView(R.id.tvSaldo)
    TextView tvSaldo;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private Intent dataIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_teacher);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_title_booking_teacher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dataIntent = getIntent();

        Locale localeID = new Locale("in", "ID");

        final NumberFormat numberFormatCurrency = NumberFormat.getCurrencyInstance(localeID);
        numberFormatCurrency.setMaximumFractionDigits(0);

        Teacher teacher = new Gson().fromJson(dataIntent.getStringExtra("dataTeacher"), Teacher.class);
        String nama = teacher.getFirstName() + " " + teacher.getLastName();
        final int price = teacher.getPrice();

        tvNama.setText(nama);
        tvPrice.setText(numberFormatCurrency.format(price));

        rgDuration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rbOneHour) {
                    tvPrice.setText(numberFormatCurrency.format(price));
                } else if (id == R.id.rbTwoHour) {
                    tvPrice.setText(numberFormatCurrency.format(price*2));
                } else if (id == R.id.rbThreeHour) {
                    tvPrice.setText(numberFormatCurrency.format(price*3));
                }
            }
        });

        rgPaymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rbWallet) {
                    lytWallet.setVisibility(View.VISIBLE);
                    lytCash.setVisibility(View.GONE);
                } else if (id == R.id.rbCash) {
                    lytWallet.setVisibility(View.GONE);
                    lytCash.setVisibility(View.VISIBLE);
                }
            }
        });

        tvSaldo.setText(numberFormatCurrency.format(50000));
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
