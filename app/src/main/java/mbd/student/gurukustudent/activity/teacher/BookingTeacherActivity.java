package mbd.student.gurukustudent.activity.teacher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.activity.LoginActivity;
import mbd.student.gurukustudent.model.APIErrorModel;
import mbd.student.gurukustudent.model.student.Student;
import mbd.student.gurukustudent.model.teacher.Teacher;
import mbd.student.gurukustudent.model.transaction.Data;
import mbd.student.gurukustudent.model.transaction.Transaction;
import mbd.student.gurukustudent.services.RetrofitServices;
import mbd.student.gurukustudent.utils.APIErrorUtils;
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
    @BindView(R.id.tvKeahlian)
    TextView tvKeahlian;
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

    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvLocation)
    TextView tvLocation;

    @BindView(R.id.etNote)
    EditText etNote;
    @BindView(R.id.tvNote)
    TextView tvNote;

    @BindView(R.id.lytNotConfirm)
    LinearLayout lytNotConfirm;
    @BindView(R.id.lytConfirmed)
    LinearLayout lytConfirmed;
    @BindView(R.id.lytPaymentSuccess)
    LinearLayout lytPaymentSuccess;
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

    @BindView(R.id.tvPaymentSuccess)
    TextView tvPaymentSuccess;
    @BindView(R.id.tvNoTlp)
    TextView tvNoTlp;
    @BindView(R.id.tvNoWa)
    TextView tvNoWA;
    @BindView(R.id.tvLineAccount)
    TextView tvLineAccount;

    /*@BindView(R.id.tvSaldo)
    TextView tvSaldo;*/
    @BindView(R.id.btnBookNow)
    Button btnBookNow;
    @BindView(R.id.btnPay)
    Button btnPay;
    @BindView(R.id.btnFinish)
    Button btnFinish;

    private Intent dataIntent;
    private ProgressDialog bookLoading;
    private SharedPreferencesUtils sharedPreferencesUtils;

    private int studentID, teacherID;
    private int duration = 0;
    private int bookID = 0;
    private int status, statusTrx;
    private int transactionID;
    private int total_price;
    private String noTlp;
    private String noWA;
    private String lineAccount;
    private String bookData, date, time, location, note;

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

        String from = dataIntent.getStringExtra("from");
        Data data;
        Teacher teacher = null;
        Transaction transaction;

        if (from.equals("DetailTeacher")) {
            bookData = dataIntent.getStringExtra("bookData");
            teacher = new Gson().fromJson(dataIntent.getStringExtra("dataTeacher"), Teacher.class);
            total_price = teacher.getPrice();
            noTlp = teacher.getNoTlp();
            noWA = teacher.getNoWA();
            lineAccount = teacher.getLineAccount();

            try {
                JSONObject jsonBookData = new JSONObject(bookData);
                duration = jsonBookData.getInt("duration");
                date = jsonBookData.getString("date");
                time = jsonBookData.getString("time");
                location = jsonBookData.getString("location");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            swipeRefreshLayout.setEnabled(false);

            switch (duration) {
                case 1 :
                    rbOneHour.setChecked(true);
                    break;
                case 2 :
                    rbTwoHour.setChecked(true);
                    break;
                case 3 :
                    rbThreeHour.setChecked(true);
            }

            total_price = total_price * duration;

            rbOneHour.setEnabled(false);
            rbTwoHour.setEnabled(false);
            rbThreeHour.setEnabled(false);
        } else if (from.equals("Trx")) {
            data = new Gson().fromJson(dataIntent.getStringExtra("bookData"), Data.class);
            teacher = data.getTeacher();
            transaction = data.getTransaction();
            noTlp = teacher.getNoTlp();
            noWA = teacher.getNoWA();
            lineAccount = teacher.getLineAccount();
            bookID = data.getBookID();
            status = data.getStatus();
            duration = data.getDuration();
            location = data.getLocation();
            date = data.getDate();
            time = data.getTime();
            note = data.getNote();
            transactionID = transaction.getTransactionID();
            statusTrx = transaction.getStatus();
            total_price = transaction.getTotalPrice();

            switch (duration) {
                case 1 :
                    rbOneHour.setChecked(true);
                    break;
                case 2 :
                    rbTwoHour.setChecked(true);
                    break;
                case 3 :
                    rbThreeHour.setChecked(true);
            }

            lytNotConfirm.setVisibility(View.GONE);
            btnBookNow.setVisibility(View.GONE);
            etNote.setVisibility(View.GONE);
            tvNote.setVisibility(View.VISIBLE);
            tvNote.setText(note);

            if (statusTrx == 1) {
                btnPay.setVisibility(View.GONE);
                lytConfirmed.setVisibility(View.GONE);
                lytPaymentSuccess.setVisibility(View.VISIBLE);
                btnFinish.setVisibility(View.VISIBLE);
            } else {
                btnPay.setVisibility(View.VISIBLE);
                lytConfirmed.setVisibility(View.VISIBLE);
            }

            rbOneHour.setEnabled(false);
            rbTwoHour.setEnabled(false);
            rbThreeHour.setEnabled(false);
        }

        teacherID = teacher.getTeacherID();
        String nama = teacher.getFirstName() + " " + teacher.getLastName();
        String keahlian = teacher.getCategories().get(0).getCategoryName();

        tvNama.setText(nama);
        tvKeahlian.setText(keahlian);
        tvDate.setText(date);
        tvTime.setText(time);
        tvLocation.setText(location);
        tvPrice.setText(numberFormatCurrency.format(total_price));
        tvNoTlp.setText(noTlp);
        tvNoWA.setText(noWA);
        tvLineAccount.setText(lineAccount);

        rgDuration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rbOneHour) {
                    tvPrice.setText(numberFormatCurrency.format(total_price));
                } else if (id == R.id.rbTwoHour) {
                    tvPrice.setText(numberFormatCurrency.format(total_price * 2));
                } else if (id == R.id.rbThreeHour) {
                    tvPrice.setText(numberFormatCurrency.format(total_price * 3));
                }
            }
        });

        if (rbWallet.isChecked()) {
            btnPay.setEnabled(false);
        } else {
            btnPay.setEnabled(true);
        }

        rgPaymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rbWallet) {
                    lytWallet.setVisibility(View.VISIBLE);
                    lytCash.setVisibility(View.GONE);
                    btnPay.setEnabled(false);
                } else if (id == R.id.rbCash) {
                    lytWallet.setVisibility(View.GONE);
                    lytCash.setVisibility(View.VISIBLE);
                    btnPay.setEnabled(true);
                }
            }
        });

        //tvSaldo.setText(numberFormatCurrency.format(50000));

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

                note = etNote.getText().toString();

                if (TextUtils.isEmpty(note)) {
                    note = "-";
                }

                try {
                    JSONObject param = new JSONObject();

                    param.put("teacherID", teacherID);
                    param.put("studentID", studentID);
                    param.put("duration", duration);
                    param.put("location", location);
                    param.put("date", date);
                    param.put("time", time);
                    param.put("note", note);

                    bookTeacher(param);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JSONObject param = new JSONObject();
                try {
                    param.put("transactionID", transactionID);
                    param.put("status", 1);
                    param.put("paymentMethod", "CASH");

                    paymentCash(param);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JSONObject param = new JSONObject();
                try {
                    param.put("bookID", bookID);
                    param.put("studentID", studentID);
                    param.put("teacherID", teacherID);
                    param.put("finish", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new AlertDialog.Builder(BookingTeacherActivity.this)
                        .setTitle("Pesan")
                        .setMessage("Apakah anda yakin akan menyelesaikan pembelajaran ini? Jika " +
                                "anda sudah melaksanakan kegiatan pembelajaran silahkan klik tombol " +
                                "\"Ya\", jika belum silahkan klik tombol \"Batal\".")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish(param);
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

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

                            bookID = result.getInt("bookID");

                            tvConfirm.setText(result.getString("message"));

                            rbOneHour.setEnabled(false);
                            rbTwoHour.setEnabled(false);
                            rbThreeHour.setEnabled(false);

                            etNote.setVisibility(View.GONE);
                            tvNote.setVisibility(View.VISIBLE);
                            tvNote.setText(note);
                            btnBookNow.setVisibility(View.GONE);
                            btnPay.setVisibility(View.VISIBLE);
                            btnPay.setEnabled(false);
                            swipeRefreshLayout.setEnabled(true);
                        } catch (Exception e) {
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

    private void getBookingStatus(int bookID) {
        swipeRefreshLayout.setRefreshing(true);
        Call<String> call = RetrofitServices.sendStudentRequest().APIGetBookingStatus(bookID);
        if (call != null) {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        try {
                            JSONObject result = new JSONObject(response.body());
                            int status = result.getInt("statusBook");
                            int statusTrx = result.getInt("statusTransaction");
                            transactionID = result.getInt("transactionID");

                            if (status == 1) {
                                lytConfirmed.setVisibility(View.VISIBLE);
                                lytNotConfirm.setVisibility(View.GONE);

                                if (rbWallet.isChecked()) {
                                    btnPay.setEnabled(false);
                                } else {
                                    btnPay.setEnabled(true);
                                }

                                if (statusTrx == 1) {
                                    lytPaymentSuccess.setVisibility(View.VISIBLE);
                                    lytConfirmed.setVisibility(View.GONE);
                                    btnPay.setVisibility(View.GONE);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        APIErrorModel error = APIErrorUtils.parserError(response);
                        String message = error.getMessage();
                        new AlertDialog.Builder(BookingTeacherActivity.this)
                                .setTitle("Pesan")
                                .setMessage(message)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e("error", t.getMessage());
                }
            });
        }
    }

    private void paymentCash(JSONObject param) {
        bookLoading.show();
        Call<String> call = RetrofitServices.sendStudentRequest().APIPaymentCash(param);
        if (call != null) {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    bookLoading.dismiss();
                    if (response.isSuccessful()) {
                        try {
                            JSONObject result = new JSONObject(response.body());
                            String message = result.getString("message");
                            tvPaymentSuccess.setText(message);

                            lytPaymentSuccess.setVisibility(View.VISIBLE);
                            lytConfirmed.setVisibility(View.GONE);
                            btnPay.setVisibility(View.GONE);
                            btnFinish.setVisibility(View.VISIBLE);
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

    private void finish(JSONObject param) {
        bookLoading.show();
        Call<String> call = RetrofitServices.sendStudentRequest().APIFinish(param);
        if (call != null) {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    bookLoading.dismiss();
                    btnFinish.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        try {
                            JSONObject result = new JSONObject(response.body());
                            String message = result.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRefresh() {
        if (bookID != 0) {
            getBookingStatus(bookID);
        }
    }
}
