package mbd.student.gurukustudent.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import mbd.student.gurukustudent.adapter.TeacherAdapter;
import mbd.student.gurukustudent.model.teacher.Teacher;
import mbd.student.gurukustudent.model.teacher.TeacherResponse;
import mbd.student.gurukustudent.services.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvGuru)
    RecyclerView rvGuru;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

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

        swipeRefreshLayout.setOnRefreshListener(this);

        rvGuru.setHasFixedSize(true);
        rvGuru.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TeacherAdapter(getContext(), listTeacher);
        rvGuru.setAdapter(adapter);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getAllDataGuru();
    }

    private void getAllDataGuru() {
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

                            listTeacher.add(new Teacher(teacherID, username, firstName, lastName, email, noTlp,
                                    lineAccount, noWA, igAccount, otherAccount, deskripsi, harga, createdAt, updatedAt));
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
    }

    @Override
    public void onRefresh() {
        getAllDataGuru();
    }
}
