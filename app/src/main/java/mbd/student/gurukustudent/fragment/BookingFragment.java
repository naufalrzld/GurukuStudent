package mbd.student.gurukustudent.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.adapter.BookingAdapter;
import mbd.student.gurukustudent.model.student.Student;
import mbd.student.gurukustudent.model.teacher.Category;
import mbd.student.gurukustudent.model.teacher.Teacher;
import mbd.student.gurukustudent.model.transaction.Data;
import mbd.student.gurukustudent.model.transaction.Transaction;
import mbd.student.gurukustudent.model.transaction.TransactionResponse;
import mbd.student.gurukustudent.services.RetrofitServices;
import mbd.student.gurukustudent.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvTeacher)
    RecyclerView rvTeacher;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private BookingAdapter adapter;
    private List<Data> listData = new ArrayList<>();

    private int studentID = 0;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_booking, container, false);
        ButterKnife.bind(this, v);

        swipeRefreshLayout.setOnRefreshListener(this);

        sharedPreferencesUtils = new SharedPreferencesUtils(getContext(), "DataMember");
        if (sharedPreferencesUtils.checkIfDataExists("profile")) {
            Student student = sharedPreferencesUtils.getObjectData("profile", Student.class);
            studentID = student.getStudentID();
        }

        adapter = new BookingAdapter(getContext(), listData);
        rvTeacher.setHasFixedSize(true);
        rvTeacher.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTeacher.setAdapter(adapter);

        return v;
    }

    @Override
    public void onRefresh() {
        getTransaction(studentID);
    }

    @Override
    public void onResume() {
        super.onResume();
        getTransaction(studentID);
    }

    private void getTransaction(int studentID) {
        swipeRefreshLayout.setRefreshing(true);
        listData.clear();
        Call<TransactionResponse> call = RetrofitServices.sendStudentRequest().APIGetTransaction(studentID);
        if (call != null) {
            call.enqueue(new Callback<TransactionResponse>() {
                @Override
                public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        int count = response.body().getData().size();

                        for (int i=0; i<count; i++) {
                            Data data = response.body().getData().get(i);
                            Teacher teacher = data.getTeacher();
                            List<Category> categories = teacher.getCategories();
                            Transaction transaction = data.getTransaction();

                            int bookID = data.getBookID();
                            int status = data.getStatus();
                            int duration = data.getDuration();
                            String note = data.getNote();
                            String location = data.getLocation();
                            String date = data.getDate();
                            String time = data.getTime();

                            int teacherID = teacher.getTeacherID();
                            String username = teacher.getUsername();
                            String fName = teacher.getFirstName();
                            String lName = teacher.getLastName();
                            String email = teacher.getEmail();
                            String noTlp = teacher.getNoTlp();
                            String lineAccount = teacher.getLineAccount();
                            String noWA = teacher.getNoWA();
                            String igAccount = teacher.getIgAccount();
                            String otherAccount = teacher.getOtherAccount();
                            String desc = teacher.getDescription();
                            int price = teacher.getPrice();

                            if (transaction != null) {
                                int trasactionID = transaction.getTransactionID();
                                int statusTrx = transaction.getStatus();
                                String paymentMethod = transaction.getPaymentMethod();
                                int totalPrice = transaction.getTotalPrice();

                                listData.add(new Data(bookID, status, duration, location, date, time, note,
                                        new Teacher(teacherID, username, fName, lName, email, noTlp,
                                                lineAccount, noWA, igAccount, otherAccount, categories, desc, price),
                                        new Transaction(trasactionID, bookID, statusTrx, paymentMethod, totalPrice)));
                            } else {
                                listData.add(new Data(bookID, status, duration, location, date, time, note,
                                        new Teacher(teacherID, username, fName, lName, email, noTlp,
                                                lineAccount, noWA, igAccount, otherAccount, categories, desc, price)));
                            }
                        }

                        if (listData.isEmpty()) {
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvNoData.setVisibility(View.GONE);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.d("error", t.getMessage());
                }
            });
        }
    }
}
