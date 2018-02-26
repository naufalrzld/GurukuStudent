package mbd.student.gurukustudent.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.adapter.TeacherAdapter;
import mbd.student.gurukustudent.model.teacher.Category;
import mbd.student.gurukustudent.model.teacher.CategoryResponse;
import mbd.student.gurukustudent.model.teacher.Teacher;
import mbd.student.gurukustudent.services.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherFragment extends Fragment implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    @BindView(R.id.cvDate)
    CardView cvDate;
    @BindView(R.id.cvTime)
    CardView cvTime;
    @BindView(R.id.tvBookDate)
    TextView tvBookDate;
    @BindView(R.id.tvTimePrivate)
    TextView tvTimePrivate;
    @BindView(R.id.etLocation)
    EditText etLocation;
    @BindView(R.id.spnCategory)
    MaterialSpinner spnCategory;
    @BindView(R.id.rgDuration)
    RadioGroup rgDuration;
    @BindView(R.id.rbOneHour)
    RadioButton rbOneHour;
    @BindView(R.id.rbTwoHour)
    RadioButton rbTwoHour;
    @BindView(R.id.rbThreeHour)
    RadioButton rbThreeHour;
    @BindView(R.id.btnFind)
    Button btnFind;

    /*@BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvGuru)
    RecyclerView rvGuru;
    @BindView(R.id.tvNoData)
    TextView tvNoData;*/

    private List<Category> categories = new ArrayList<>();
    private String mDate, mTime, categoryName;
    private Calendar mCalendar;
    private int mHour, mMinute, mDay, mMonth, mYear;

    private List<Teacher> listTeacher = new ArrayList<>();
    private TeacherAdapter adapter;

    public TeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_guru, container, false);
        ButterKnife.bind(this, v);

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        tvBookDate.setText(mDate);
        tvTimePrivate.setText(mTime);

        spnCategory.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String name) {
                categoryName = name;
            }
        });

        cvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TeacherFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        cvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        TeacherFragment.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setThemeDark(false);
                tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etLocation.getText().toString())) {
                    etLocation.setError("Masukkan lokasi dimana anda ingin belajar");
                    return;
                } else {
                    String location = etLocation.getText().toString();
                    int indeks = spnCategory.getSelectedIndex();
                    String categoryName = categories.get(indeks).getCategoryName();
                    Log.d("categoryName", categoryName);
                    int duration = 0;

                    if (rbOneHour.isChecked()) {
                        duration = 1;
                    } else if (rbTwoHour.isChecked()) {
                        duration = 2;
                    } else if (rbThreeHour.isChecked()) {
                        duration = 3;
                    }

                    try {
                        JSONObject data = new JSONObject();

                        data.put("date", mDate);
                        data.put("time", mTime);
                        data.put("location", location);
                        data.put("category", categoryName);
                        data.put("duration", duration);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        getCategory();

        /*swipeRefreshLayout.setOnRefreshListener(this);

        rvGuru.setHasFixedSize(true);
        rvGuru.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TeacherAdapter(getContext(), listTeacher);
        rvGuru.setAdapter(adapter);*/

        return v;
    }

    private void setToSpinner(List<Category> categories) {
        List<String> categoryName = new ArrayList<>();

        if (!categories.isEmpty()) {
            Collections.sort(categories, new Comparator<Category>() {
                @Override
                public int compare(Category s1, Category s2) {
                    return s1.getCategoryName().compareTo(s2.getCategoryName());
                }
            });

            for (Category category : categories) {
                categoryName.add(category.getCategoryName());
            }
        }

        spnCategory.setItems(categoryName);
    }

    private void getCategory() {
        Call<CategoryResponse> call = RetrofitServices.sendTeacherRequest().APIGetAllCategory();
        if (call != null) {
            call.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                    if (response.isSuccessful()) {
                        int size = response.body().getData().size();
                        for (int i=0; i<size; i++) {
                            categories.add(response.body().getData().get(i));
                        }

                        setToSpinner(categories);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                    Log.e("error", t.getMessage());
                }
            });
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;

        tvBookDate.setText(mDate);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }

        tvTimePrivate.setText(mTime);
    }

    /*private void getAllDataGuru() {
        listTeacher.clear();

        swipeRefreshLayout.setRefreshing(true);

        adapter.notifyDataSetChanged();

        Call<TeacherResponse> call = RetrofitServices.sendTeacherRequest().APIGetAllGuru();
        if (call != null) {
            call.enqueue(new Callback<TeacherResponse>() {
                @Override
                public void onResponse(@NonNull Call<TeacherResponse> call, @NonNull Response<TeacherResponse> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        int count = response.body().getTeacher().size();

                        for (int i=0; i<count; i++) {
                            Integer teacherID = response.body().getTeacher().get(i).getTeacherID();
                            String username = response.body().getTeacher().get(i).getUsername();
                            String firstName = response.body().getTeacher().get(i).getFirstName();
                            String lastName = response.body().getTeacher().get(i).getLastName();
                            String email = response.body().getTeacher().get(i).getEmail();
                            String noTlp = response.body().getTeacher().get(i).getNoTlp();
                            String lineAccount = response.body().getTeacher().get(i).getLineAccount();
                            String noWA = response.body().getTeacher().get(i).getNoWA();
                            String igAccount = response.body().getTeacher().get(i).getIgAccount();
                            String otherAccount = response.body().getTeacher().get(i).getOtherAccount();
                            String deskripsi = response.body().getTeacher().get(i).getDescription();
                            Integer harga = response.body().getTeacher().get(i).getPrice();
                            String createdAt = response.body().getTeacher().get(i).getCreatedAt();
                            String updatedAt = response.body().getTeacher().get(i).getUpdatedAt();
                            List<Category> categories = response.body().getTeacher().get(i).getCategories();

                            listTeacher.add(new Teacher(teacherID, username, firstName, lastName, email, noTlp,
                                    lineAccount, noWA, igAccount, otherAccount, deskripsi, harga, categories, createdAt, updatedAt));
                        }
                        //listTeacher = response.body().getTeacher();

                        if (listTeacher.isEmpty()) {
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvNoData.setVisibility(View.GONE);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TeacherResponse> call, @NonNull Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.d("error", t.getMessage());
                }
            });
        }
    }*/

    /*@Override
    public void onRefresh() {
        //getAllDataGuru();
    }*/
}
