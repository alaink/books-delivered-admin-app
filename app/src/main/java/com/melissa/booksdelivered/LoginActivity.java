package com.melissa.booksdelivered;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.BindView;

import static android.R.attr.id;
import static android.R.attr.start;


public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    final int version = Build.VERSION.SDK_INT;

    @BindView(R.id.phoneNumber_ID) EditText _phoneNumberText;
    @BindView(R.id.password_ID) EditText _passwordText;
    @BindView(R.id.login_button) Button _loginButton;
    @BindView(R.id.forgotPasswordText) TextView _forgotPasswordText;
    @BindView(R.id.loginActivityRelativeLayout) RelativeLayout _relativeLayout;
    @BindView(R.id.newUserTextview) TextView _newUser;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                //TODO: Implement activity for forgotten password
            }
        });

        _newUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

        //If logging requirements are not met
        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        String phoneNumber = _phoneNumberText.getText().toString();
        String password = _passwordText.getText().toString();

        //Authentication Logic: Check on server whether login info is correct
        final ServerCall serverCall = new ServerCall(this, 1);
        serverCall.execute(phoneNumber, password);
        new android.os.Handler().postDelayed (
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (serverCall.isLoginValid()) {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the LibraryPage
        moveTaskToBack(false);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String phoneNumber = _phoneNumberText.getText().toString();
        String password = _passwordText.getText().toString();

        //Phone Number requirements
        if (phoneNumber.isEmpty() ) {
            _phoneNumberText.setError("Empty field");
            valid = false;
        } else {
            _phoneNumberText.setError(null);
        }

        //Password requirements
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}

