package com.android.nmobile.equityassurance.ui;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nmobile.equityassurance.R;
import com.android.nmobile.equityassurance.utils.ConnectionDetector;
import com.android.nmobile.equityassurance.utils.ServiceHandler;
import com.android.nmobile.equityassurance.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Dev Rupesh Saxena
 */

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;
    private String status, description, emailAddress, phoneNumber, firstName, lastName, email, password;

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    TextView _loginButton;
    @Bind(R.id.link_signup)
    TextView _forgetPasswordLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        //ConnectionDetector obj
        connectionDetector = new ConnectionDetector(this);

        //setup login button
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //popup keyboard off
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                if (connectionDetector.isConnectingToInternet()) {
                    login(v);
                } else {
                    Snackbar.make(v, "No Internet Connection", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        //setup forget password link
        _forgetPasswordLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);
            }
        });
    }

    public void login(View v) {
        if (!validate(v)) {
            return;
        }

        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        String authenticatingUrl = "https://apis.nmobile.ng/mobile/index.php?action=login&emailAddress=" + email + "&password=" + password + "";
        //Authenticating
        new AuthenticatingTask().execute(authenticatingUrl);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public boolean validate(View v) {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Snackbar.make(v, "Password between 4 and 10 alphanumeric characters", Snackbar.LENGTH_SHORT).show();
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(v, "Enter a valid email address", Snackbar.LENGTH_SHORT).show();
            valid = false;
        } else {
            _emailText.setError(null);
        }

        return valid;
    }

    //
    class AuthenticatingTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler mHandler = new ServiceHandler();
            return mHandler.GetHTTPData(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //parse response
            if (s != null) {
                try {
                    //first json object
                    JSONObject jsonObject = new JSONObject(s);
                    status = jsonObject.getString("status");
                    if (!status.matches("failure")) {
                        description = jsonObject.getString("description");

                        //second json object
                        JSONObject object = new JSONObject();
                        object = jsonObject.getJSONObject("details");

                        emailAddress = object.getString("emailAddress");
                        phoneNumber = object.getString("phoneNumber");
                        firstName = object.getString("firstName");
                        lastName = object.getString("lastName");

                        //set session
                        SessionManager.intialize(LoginActivity.this);
                        SessionManager.add(firstName, lastName, phoneNumber, emailAddress, password);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect Email Address or Password", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Try after some time.", Toast.LENGTH_SHORT).show();
                _emailText.setText("");
                _passwordText.setText("");
            }

        }
    }
}
