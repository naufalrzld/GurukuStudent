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
import mbd.student.gurukustudent.adapter.HistoryAdapter;
import mbd.student.gurukustudent.model.history.History;
import mbd.student.gurukustudent.model.history.HistoryResponse;
import mbd.student.gurukustudent.model.student.Student;
import mbd.student.gurukustudent.model.teacher.Teacher;
import mbd.student.gurukustudent.services.RetrofitServices;
import mbd.student.gurukustudent.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.tvNoHistory)
    TextView tvNoHistory;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private HistoryAdapter adapter;
    private List<History> historyList = new ArrayList<>();

    private int studentID = 0;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, v);

        swipeRefreshLayout.setOnRefreshListener(this);

        sharedPreferencesUtils = new SharedPreferencesUtils(getContext(), "DataMember");
        if (sharedPreferencesUtils.checkIfDataExists("profile")) {
            Student student = sharedPreferencesUtils.getObjectData("profile", Student.class);
            studentID = student.getStudentID();
        }

        adapter = new HistoryAdapter(getContext(), historyList);
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHistory.setAdapter(adapter);

        getBookingHistory(studentID);

        return v;
    }

    @Override
    public void onRefresh() {
        getBookingHistory(studentID);
    }

    private void getBookingHistory(int studentID) {
        swipeRefreshLayout.setRefreshing(true);
        historyList.clear();
        Call<HistoryResponse> call = RetrofitServices.sendStudentRequest().APIGetBookingHistory(studentID);
        if (call != null) {
            call.enqueue(new Callback<HistoryResponse>() {
                @Override
                public void onResponse(@NonNull Call<HistoryResponse> call, @NonNull Response<HistoryResponse> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        int count = response.body().getHistory().size();

                        for (int i=0; i<count; i++) {
                            History history = response.body().getHistory().get(i);
                            int bookID = history.getBookID();
                            int teacherID = history.getTeacherID();
                            int status = history.getStatus();
                            String fNameGuru = history.getTeacher().getFirstName();
                            String lNameGuru = history.getTeacher().getLastName();

                            historyList.add(new History(bookID, status, new Teacher(teacherID, fNameGuru, lNameGuru)));
                        }

                        if (historyList.isEmpty()) {
                            tvNoHistory.setVisibility(View.VISIBLE);
                        } else {
                            tvNoHistory.setVisibility(View.GONE);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HistoryResponse> call, @NonNull Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e("error", t.getMessage());
                }
            });
        }
    }
}
