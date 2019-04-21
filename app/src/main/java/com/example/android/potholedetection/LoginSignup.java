package com.example.android.potholedetection;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class LoginSignup extends AppCompatActivity {
    private FirebaseAuth auth;
    private static final String TAG = "LoginSignup";
    private static final int REQUEST_SIGNUP = 0;
    String email,password;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_username)
    EditText _usernameText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


//        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        _loginButton.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                email = _emailText.getText().toString();
                                                password = _passwordText.getText().toString();

                                                //authenticate user
                                                auth.signInWithEmailAndPassword(email, password)
                                                        .addOnCompleteListener(LoginSignup.this, new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                // If sign in fails, display a message to the user. If sign in succeeds
                                                                // the auth state listener will be notified and logic to handle the
                                                                // signed in user can be handled in the listener.
                                                                //progressBar.setVisibility(View.GONE);
                                                                if (!task.isSuccessful()) {
                                                                    // there was an error
                                                                    if (password.length() < 6) {
                                                                        _passwordText.setError("Incorrect Password!");
                                                                    } else {
                                                                        Toast.makeText(LoginSignup.this, "Login Failed!!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                } else {
                                                                    Intent intent = new Intent(LoginSignup.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }

                                                            }
                                                        });
                                                login();
                                            }
                                        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginSignup.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

//        final String email = _emailText.getText().toString();
//        final String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
//                        if (email == "tanyamohanani@gmail.com" && password == "tanya@123"){
//                            startActivity(new Intent(LoginSignup.this, MainActivity.class));
//                        }
                        //onLoginFailed();
                        //progressDialog.dismiss();
                    }
                }, 5000);
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

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        //startActivity(new Intent(LoginSignup.this, MainActivity.class));
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        String email1 = "user@gmail.com";
        String username1 = "User";
        String password1 = "user";

        String email2 = "project@ves.com";
        String username2 = "Pothole";
        String password2 = "pothole";

        String email3 = "review@gmail.com";
        String username3 = "Review";
        String password3 = "review";

        String email4 = "yogita@gmail.com";
        String username4 = "Yogita";
        String password4 = "yogita";

        String email5 = "shefali@gmail.com";
        String username5 = "Shefali";
        String password5 = "shefali";


        if ( email.equals(email1)  ) {
            _emailText.setError(null);
        } else if( email.equals(email2) ) {
            _emailText.setError(null);
        }
        else if( email.equals(email3) ) {
            _emailText.setError(null);
        }
        else if( email.equals(email4) ) {
            _emailText.setError(null);
        }
        else if( email.equals(email5) ) {
            _emailText.setError(null);
        }
        else{
            valid = false;
        }
        if ( username.equals(username1)  ) {
            _usernameText.setError(null);
        }
        else if(username.equals(username2) ){
            _usernameText.setError(null);
        }
        else if(username.equals(username3) ){
            _usernameText.setError(null);
        }
        else if(username.equals(username4) ){
            _usernameText.setError(null);
        }
        else if(username.equals(username5) ){
            _usernameText.setError(null);
        }
        else {
            valid = false;
        }
        if ( password.equals(password1) )  {
            _passwordText.setError(null);

        }
        else if( password.equals(password2 ))
        {
            _passwordText.setError(null);
        }
        else if( password.equals(password3 ))
        {
            _passwordText.setError(null);
        }
        else if( password.equals(password4 ))
        {
            _passwordText.setError(null);
        }
        else if( password.equals(password5 ))
        {
            _passwordText.setError(null);
        }

        else {
            valid = false;
            _passwordText.setError(null);
        }

        return valid;
    }

}