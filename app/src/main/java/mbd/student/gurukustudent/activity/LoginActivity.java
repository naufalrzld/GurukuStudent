package mbd.student.gurukustudent.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.model.APIErrorModel;
import mbd.student.gurukustudent.model.member.LoginResponse;
import mbd.student.gurukustudent.model.member.Member;
import mbd.student.gurukustudent.services.RetrofitServices;
import mbd.student.gurukustudent.utils.APIErrorUtils;
import mbd.student.gurukustudent.utils.SessionManager;
import mbd.student.gurukustudent.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.ilUsername)
    TextInputLayout ilUsername;
    @BindView(R.id.ilPassword)
    TextInputLayout ilPassword;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.btnDaftar)
    Button btnDaftar;
    @BindView(R.id.btnMasuk)
    Button btnMasuk;

    private ProgressDialog loginLoading;
    private SessionManager session;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginLoading = new ProgressDialog(this);
        loginLoading.setMessage("Loading . . .");
        loginLoading.setCancelable(false);

        session = new SessionManager(this);
        sharedPreferencesUtils = new SharedPreferencesUtils(this, "DataMember");

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInputValid()) {
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    try {
                        JSONObject params = new JSONObject();
                        params.put("username", username);
                        params.put("password", password);

                        login(params);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean isInputValid() {
        ilUsername.setErrorEnabled(false);
        ilPassword.setErrorEnabled(false);

        if (TextUtils.isEmpty(etUsername.getText().toString()) ||
                TextUtils.isEmpty(etPassword.getText().toString())) {
            if (TextUtils.isEmpty(etUsername.getText().toString())) {
                ilUsername.setErrorEnabled(true);
                ilUsername.setError("Masukkan username");
            }

            if (TextUtils.isEmpty(etPassword.getText().toString())) {
                ilPassword.setErrorEnabled(true);
                ilPassword.setError("Masukkan password");
            }

            return false;
        } else {
            if (etPassword.getText().toString().length() < 6) {
                ilPassword.setErrorEnabled(true);
                ilPassword.setError("Password minimal 6 karakter");

                return false;
            }
        }

        return true;
    }

    private void login(JSONObject param) {
        loginLoading.show();
        Call<LoginResponse> call = RetrofitServices.sendMemberRequest().APILogin(param);
        if (call != null) {
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    loginLoading.dismiss();
                    if (response.isSuccessful()) {
                        String json = new Gson().toJson(response.body());
                        Log.d("response", json);

                        /*Member member = response.body().getMember();
                        sharedPreferencesUtils.storeData("profile", member);
                        session.setLogin(true);*/
                    } else {
                        APIErrorModel error = APIErrorUtils.parserError(response);
                        String message = error.getMessage();
                        new AlertDialog.Builder(LoginActivity.this)
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
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    loginLoading.dismiss();
                    Log.d("error", t.getMessage());
                }
            });
        }
    }
}
