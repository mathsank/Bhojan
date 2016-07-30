package matt.feedly;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final String REGISTER_URL = "http://bhojansvce.16mb.com/insert2.php";

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_cpassword) EditText _cpasswordText;
    @InjectView(R.id.phno) EditText _phoneNumber;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });

    }


    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }
        else {
            onSignupSuccess();
        }


        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String phno = _phoneNumber.getText().toString();

        // TODO: Full SignUp logic checking

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        registerUser();

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign-Up failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String cpassword = _cpasswordText.getText().toString();
        String phno = _phoneNumber.getText().toString();

        if (name.isEmpty() || name.length() < 5) {
            _nameText.setError("At least 5 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if (cpassword.isEmpty() || cpassword.length() < 4 || cpassword.length() > 10) {
            _cpasswordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _cpasswordText.setError(null);
        }

        if (!(cpassword.matches(password))) {
            _cpasswordText.setError("Passwords don't match");
            valid = false;
        } else {
            _cpasswordText.setError(null);
        }
        if (phno.isEmpty() || phno.length() < 10 || phno.length() > 10) {
            _phoneNumber.setError("Phone number should be 10 digits");
            valid = false;
        } else {
            _phoneNumber.setError(null);
        }
        return valid;
    }
    private void register(String name,String email,String password, String phno) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUp.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("email",params[1]);
                data.put("password",params[2]);
                data.put("phno",params[3]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,email,password,phno);
    }
    private void registerUser() {
        String name = _nameText.getText().toString().trim().toLowerCase();
        String password = _passwordText.getText().toString().trim().toLowerCase();
        String email = _emailText.getText().toString().trim().toLowerCase();
        String phno = _phoneNumber.getText().toString().trim().toLowerCase();
        register(name,email,password,phno);
    }
}