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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.activity.RegisterActivity;
import mbd.student.gurukustudent.model.APIErrorModel;
import mbd.student.gurukustudent.model.student.Booking;
import mbd.student.gurukustudent.model.student.BookingResponse;
import mbd.student.gurukustudent.model.student.Student;
import mbd.student.gurukustudent.model.teacher.Teacher;
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

    private List<Booking> listBooking = new ArrayList<>();
    private int studentID, teacherID;
    private int duration = 0;
    private int bookID = 0;

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
                    tvPrice.setText(numberFormatCurrency.format(price * 2));
                } else if (id == R.id.rbThreeHour) {
                    tvPrice.setText(numberFormatCurrency.format(price * 3));
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
        Call<BookingResponse> call = RetrofitServices.sendStudentRequest().APIBookTeacher(param);
        if (call != null) {
            call.enqueue(new Callback<BookingResponse>() {
                @Override
                public void onResponse(@NonNull Call<BookingResponse> call, @NonNull Response<BookingResponse> response) {
                    bookLoading.dismiss();
                    if (response.isSuccessful()) {
                        try {
                            Booking booking = response.body().getBooking();
                            bookID = booking.getBookID();

                            /*if (sharedPreferencesUtils.checkIfDataExists("bookingList")) {
                                JSONArray arrayToken = new JSONArray()
                            } else {
                                listBooking.add(booking);
                                sharedPreferencesUtils.storeData("bookingList", new Gson().toJsonTree(listBooking, new TypeToken<List<Booking>>() {
                                }.getType()).getAsJsonArray().toString());
                            }*/
                            tvConfirm.setText(response.body().getMessage());

                            rbOneHour.setEnabled(false);
                            rbTwoHour.setEnabled(false);
                            rbThreeHour.setEnabled(false);

                            btnBookNow.setVisibility(View.GONE);
                            btnPay.setVisibility(View.VISIBLE);
                            btnPay.setEnabled(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BookingResponse> call, @NonNull Throwable t) {
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

                            if (status == 1) {
                                lytConfirmed.setVisibility(View.VISIBLE);
                                lytNotConfirm.setVisibility(View.GONE);
                                btnPay.setEnabled(true);
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

    @Override
    public void onRefresh() {
        if (bookID != 0) {
            getBookingStatus(bookID);
        }
    }
}
