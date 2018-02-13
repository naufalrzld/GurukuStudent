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
import mbd.student.gurukustudent.adapter.GuruAdapter;
import mbd.student.gurukustudent.model.guru.Guru;
import mbd.student.gurukustudent.model.guru.GuruResponse;
import mbd.student.gurukustudent.services.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuruFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvGuru)
    RecyclerView rvGuru;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private List<Guru> listGuru = new ArrayList<>();
    private GuruAdapter adapter;

    public GuruFragment() {
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
        adapter = new GuruAdapter(getContext(), listGuru);
        rvGuru.setAdapter(adapter);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getAllDataGuru();
    }

    private void getAllDataGuru() {
        listGuru.clear();

        swipeRefreshLayout.setRefreshing(true);

        adapter.notifyDataSetChanged();

        Call<GuruResponse> call = RetrofitServices.sendGuruRequest().APIGetAllGuru();
        if (call != null) {
            call.enqueue(new Callback<GuruResponse>() {
                @Override
                public void onResponse(@NonNull Call<GuruResponse> call, @NonNull Response<GuruResponse> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.isSuccessful()) {
                        int count = response.body().getGuru().size();

                        for (int i=0; i<count; i++) {
                            String username = response.body().getGuru().get(i).getUsername();
                            String firstName = response.body().getGuru().get(i).getFirstName();
                            String lastName = response.body().getGuru().get(i).getLastName();
                            String email = response.body().getGuru().get(i).getEmail();
                            String noTlp = response.body().getGuru().get(i).getNoTlp();
                            Object sosmed = response.body().getGuru().get(i).getSosmed();
                            String kemampuan = response.body().getGuru().get(i).getKemampuan();
                            String deskripsi = response.body().getGuru().get(i).getDeskripsi();
                            Integer harga = response.body().getGuru().get(i).getHarga();
                            String createdAt = response.body().getGuru().get(i).getCreatedAt();
                            String updatedAt = response.body().getGuru().get(i).getUpdatedAt();

                            listGuru.add(new Guru(username, firstName, lastName, email, noTlp,
                                    sosmed, kemampuan, deskripsi, harga, createdAt, updatedAt));
                        }
                        //listGuru = response.body().getGuru();

                        if (listGuru.isEmpty()) {
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvNoData.setVisibility(View.GONE);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GuruResponse> call, @NonNull Throwable t) {
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
