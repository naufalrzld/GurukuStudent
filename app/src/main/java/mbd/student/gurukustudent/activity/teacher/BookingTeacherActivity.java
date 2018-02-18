package mbd.student.gurukustudent.activity.teacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.model.student.Student;
import mbd.student.gurukustudent.model.teacher.Teacher;
import mbd.student.gurukustudent.services.RetrofitServices;
import mbd.student.gurukustudent.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingTeacherActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
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

    @BindView(R.id.lytNotConfirm)
    LinearLayout lytNotConfirm;
    @BindView(R.id.lytConfirmed)
    LinearLayout lytConfirmed;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;

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
    @BindView(R.id.btnBookNow)
    Button btnBookNow;
    @BindView(R.id.btnPay)
    Button btnPay;

    private Intent dataIntent;
    private ProgressDialog bookLoading;
    private SharedPreferencesUtils sharedPreferencesUtils;

    private int studentID, teacherID;
    private int duration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_teacher);
        ButterKnife.bind(this);

        bookLoading = new ProgressDialog(this);
        bookLoading.setMessage("Loading . . .");
        bookLoading.setCancelable(false);

        swipeRefreshLayout.setOnRefreshListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_title_booking_teacher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sharedPreferencesUtils = new SharedPreferencesUtils(this, "DataMember");
        if (sharedPreferencesUtils.checkIfDataExists("profile")) {
            Student student = sharedPreferencesUtils.getObjectData("profile", Student.class);
            studentID = student.getStudentID();
        }

        dataIntent = getIntent();

        Locale localeID = new Locale("in", "ID");

        final NumberFormat numberFormatCurrency = NumberFormat.getCurrencyInstance(localeID);
        numberFormatCurrency.setMaximumFractionDigits(0);

        Teacher teacher = new Gson().fromJson(dataIntent.getStringExtra("dataTeacher"), Teacher.class);
        teacherID = teacher.getTeacherID();
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

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbOneHour.isChecked()) {
                    duration = 1;
                } else if (rbTwoHour.isChecked()) {
                    duration = 2;
                } else if (rbThreeHour.isChecked()) {
                    duration = 3;
                }
                try {
                    JSONObject param = new JSONObject();

                    param.put("teacherID", teacherID);
                    param.put("studentID", studentID);
                    param.put("duration", duration);

                    bookTeacher(param);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void bookTeacher(JSONObject param) {
        bookLoading.show();
        Call<String> call = RetrofitServices.sendStudentRequest().APIBookTeacher(param);
        if (call != null) {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    bookLoading.dismiss();
                    if (response.isSuccessful()) {
                        try {
                            JSONObject result = new JSONObject(response.body());

                            tvConfirm.setText(result.getString("message"));

                            rbOneHour.setEnabled(false);
                            rbTwoHour.setEnabled(false);
                            rbThreeHour.setEnabled(false);

                            btnBookNow.setVisibility(View.GONE);
                            btnPay.setVisibility(View.VISIBLE);
                            btnPay.setEnabled(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    bookLoading.dismiss();
                    Log.e("error", t.getMessage());
                }
            });
        }
    }

    private void getStatusConfirm() {

    }

    @Override
    public void onRefresh() {

    }
}
