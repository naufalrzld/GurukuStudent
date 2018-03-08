package mbd.student.gurukustudent.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.model.student.Student;
import mbd.student.gurukustudent.model.student.UpdateProfileResponse;
import mbd.student.gurukustudent.services.RetrofitServices;
import mbd.student.gurukustudent.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ilFName)
    TextInputLayout ilFName;
    @BindView(R.id.ilLName)
    TextInputLayout ilLName;
    @BindView(R.id.ilNoTlp)
    TextInputLayout ilNoTlp;
    @BindView(R.id.ilNoWA)
    TextInputLayout ilNoWA;
    @BindView(R.id.ilIDLine)
    TextInputLayout ilIDLine;

    @BindView(R.id.etFName)
    TextInputEditText etFName;
    @BindView(R.id.etLName)
    TextInputEditText etLName;
    @BindView(R.id.etNoTlp)
    TextInputEditText etNoTlp;
    @BindView(R.id.etNoWA)
    TextInputEditText etNoWA;
    @BindView(R.id.etIDLine)
    TextInputEditText etIDLine;

    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.btnBatal)
    Button btnBatal;

    private ProgressDialog updateLoading;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        updateLoading = new ProgressDialog(this);
        updateLoading.setMessage("Loading . . .");
        updateLoading.setCancelable(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_title_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sharedPreferencesUtils = new SharedPreferencesUtils(this, "DataMember");
        if (sharedPreferencesUtils.checkIfDataExists("profile")) {
            Student student = sharedPreferencesUtils.getObjectData("profile", Student.class);
            studentID = student.getStudentID();
            String fName = student.getFirstName();
            String lName = student.getLastName();
            String noTlp = student.getNoTlp();
            String noWA = student.getNoWA();
            String IDLine = student.getLineAccount();

            etFName.setText(fName);
            etLName.setText(lName);
            etNoTlp.setText(noTlp);
            etNoWA.setText(noWA);
            etIDLine.setText(IDLine);
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ilNoTlp.setErrorEnabled(false);
                ilFName.setErrorEnabled(false);

                String fName = etFName.getText().toString();
                String lName = etLName.getText().toString();
                String noTlp = etNoTlp.getText().toString();
                String noWA = etNoWA.getText().toString();
                String lineAccount = etIDLine.getText().toString();

                if (TextUtils.isEmpty(fName) || TextUtils.isEmpty(noTlp)) {
                    if (TextUtils.isEmpty(fName)) {
                        ilFName.setErrorEnabled(true);
                        ilFName.setError("Nama depan harus diisi");
                    }

                    if (TextUtils.isEmpty(noTlp)) {
                        ilNoTlp.setErrorEnabled(true);
                        ilNoTlp.setError("Nomor telpon harus diisi");
                    }

                    return;
                } else {
                    if (TextUtils.isEmpty(noWA)) {
                        noWA = "-";
                    }
                    if (TextUtils.isEmpty(lineAccount)) {
                        lineAccount = "-";
                    }
                }

                try {
                    JSONObject param = new JSONObject();
                    param.put("studentID", studentID);
                    param.put("firstName", fName);
                    param.put("lastName", lName);
                    param.put("no_tlp", noTlp);
                    param.put("lineAccount", lineAccount);
                    param.put("noWA", noWA);

                    editProfile(param);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    private void editProfile(JSONObject param) {
        updateLoading.show();
        Call<UpdateProfileResponse> call = RetrofitServices.sendStudentRequest().APIEditProfile(param);
        if (call != null) {
            call.enqueue(new Callback<UpdateProfileResponse>() {
                @Override
                public void onResponse(@NonNull Call<UpdateProfileResponse> call, @NonNull Response<UpdateProfileResponse> response) {
                    updateLoading.dismiss();
                    if (response.isSuccessful()) {
                        UpdateProfileResponse updateProfileResponse = response.body();
                        String message = updateProfileResponse.getMessage();
                        Student student = updateProfileResponse.getStudent();
                        sharedPreferencesUtils.storeData("profile", student);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UpdateProfileResponse> call, @NonNull Throwable t) {
                    updateLoading.dismiss();
                    Log.d("error", t.getMessage());
                }
            });
        }
    }
}
