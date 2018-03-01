package mbd.student.gurukustudent.activity.teacher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.adapter.TeacherAdapter;
import mbd.student.gurukustudent.model.teacher.Category;
import mbd.student.gurukustudent.model.teacher.Teacher;
import mbd.student.gurukustudent.model.teacher.TeacherResponse;
import mbd.student.gurukustudent.services.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTeacherActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvGuru)
    RecyclerView rvGuru;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private List<Teacher> listTeacher = new ArrayList<>();
    private TeacherAdapter adapter;
    private Intent dataIntent;
    private String category, bookData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_teacher);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_title_list_teacher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        dataIntent = getIntent();
        bookData = dataIntent.getStringExtra("bookData");
        try {
            JSONObject data = new JSONObject(bookData);
            category = data.getString("category");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(this);

        rvGuru.setHasFixedSize(true);
        rvGuru.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new TeacherAdapter(this, listTeacher, bookData);
        rvGuru.setAdapter(adapter);

        getAllDataGuru(category);
    }

    private void getAllDataGuru(String category) {
        listTeacher.clear();

        swipeRefreshLayout.setRefreshing(true);

        adapter.notifyDataSetChanged();

        Call<TeacherResponse> call = RetrofitServices.sendStudentRequest().APIGetAllGuru(category);
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
    }

    @Override
    public void onRefresh() {
        getAllDataGuru(category);
    }
}
