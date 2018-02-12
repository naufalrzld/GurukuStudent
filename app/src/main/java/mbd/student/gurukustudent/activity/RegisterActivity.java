package mbd.student.gurukustudent.activity;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.ilFirstName)
    TextInputLayout ilFirstName;
    @BindView(R.id.ilLastName)
    TextInputLayout ilLastName;
    @BindView(R.id.ilUsername)
    TextInputLayout ilUsername;
    @BindView(R.id.ilPassword)
    TextInputLayout ilPassword;
    @BindView(R.id.ilCPassword)
    TextInputLayout ilCPassword;

    @BindView(R.id.etFirstName)
    TextInputEditText etFirstName;
    @BindView(R.id.etLastName)
    TextInputEditText etLastName;
    @BindView(R.id.etUsername)
    TextInputEditText etUsername;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.etCPassword)
    TextInputEditText etCPassword;

    @BindView(R.id.btnDaftar)
    Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInputValid()) {
                    String fName = etFirstName.getText().toString();
                    String lName = etLastName.getText().toString();
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    try {
                        JSONObject params = new JSONObject();
                        params.put("akunType", "Member");
                        params.put("firstName", fName);
                        params.put("lastName", lName);
                        params.put("username", username);
                        params.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean isInputValid() {
        ilFirstName.setErrorEnabled(false);
        ilLastName.setErrorEnabled(false);
        ilUsername.setErrorEnabled(false);
        ilPassword.setErrorEnabled(false);
        ilCPassword.setErrorEnabled(false);

        if (TextUtils.isEmpty(etFirstName.getText().toString()) ||
                TextUtils.isEmpty(etLastName.getText().toString()) ||
                TextUtils.isEmpty(etUsername.getText().toString()) ||
                TextUtils.isEmpty(etPassword.getText().toString()) ||
                TextUtils.isEmpty(etCPassword.getText().toString())) {
            if (TextUtils.isEmpty(etFirstName.getText().toString())) {
                ilFirstName.setErrorEnabled(true);
                ilFirstName.setError("Silahkan masukkan nama depan anda");
            }

            if (TextUtils.isEmpty(etLastName.getText().toString())) {
                ilLastName.setErrorEnabled(true);
                ilLastName.setError("Silahkan masukkan nama bekalang anda");
            }

            if (TextUtils.isEmpty(etUsername.getText().toString())) {
                ilUsername.setErrorEnabled(true);
                ilUsername.setError("Silahkan masukkan username anda");
            }

            if (TextUtils.isEmpty(etPassword.getText().toString())) {
                ilPassword.setErrorEnabled(true);
                ilPassword.setError("Silahkan masukkan password anda");
            }

            if (TextUtils.isEmpty(etCPassword.getText().toString())) {
                ilCPassword.setErrorEnabled(true);
                ilCPassword.setError("Silahkan ulangi password anda");
            }

            return false;
        } else {
            if (etPassword.getText().toString().length() < 6) {
                ilPassword.setErrorEnabled(true);
                ilPassword.setError("Minimal password 6 karakter");

                return false;
            }

            if (etCPassword.getText().toString().length() < 6) {
                ilCPassword.setErrorEnabled(true);
                ilCPassword.setError("Minimal password 6 karakter");

                return false;
            } else {
                if (etCPassword.getText().toString().equals(etPassword.getText().toString())) {
                    ilCPassword.setErrorEnabled(true);
                    ilCPassword.setError("Password tidak cocok");

                    return false;
                }
            }
        }

        return true;
    }
}
