package com.melissa.booksdelivered;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;

public class SignupActivity extends Activity {
    private static final String TAG = "SignupActivity";
    private final int version = Build.VERSION.SDK_INT;

    @BindView(R.id.newName_ID) EditText _userNameText;
    @BindView(R.id.newphoneNumber_ID) EditText _phoneNumberText;
    @BindView(R.id.newpassword_ID) EditText _passwordText;
    @BindView(R.id.confirmed_newpassword_ID) EditText _confirmedPasswordText;
    @BindView(R.id.register_button_signupPage) Button _registerButton;
    @BindView (R.id.oldAccountTextView) TextView _oldAccountText;
    @BindView(R.id.signupActivityRelativeLayout) RelativeLayout _relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);


        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _oldAccountText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
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

        _registerButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String phoneNumber = _phoneNumberText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

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
        _registerButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _registerButton.setEnabled(true);
    }

    //Method to check whether user input requirements are met
    public boolean validate() {
        boolean valid = true;

        String userName = _userNameText.getText().toString();
        String phoneNumber = _phoneNumberText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmedPassword = _confirmedPasswordText.getText().toString();

        //Name Requirements
        if (userName.isEmpty()) {
            _userNameText.setError("required field");
            valid = false;
        } else {
            _userNameText.setError(null);
        }

        //Phone Number requirements
        if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            _phoneNumberText.setError("10 digits required");
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

        //Password confirmation
        if (!password.equals(confirmedPassword)) {
            _confirmedPasswordText.setError("password mismatch");
            valid = false;
        } else {
            _confirmedPasswordText.setError(null);
        }

        return valid;
    }
}