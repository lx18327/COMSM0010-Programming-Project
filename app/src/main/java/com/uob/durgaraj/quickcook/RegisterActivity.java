package com.uob.durgaraj.quickcook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.uob.durgaraj.quickcook.aws.AWSLoginHandler;
import com.uob.durgaraj.quickcook.aws.AWSLoginModel;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/*import com.study.quickrecipies.aws.AWSLoginHandler;
import com.study.quickrecipies.aws.AWSLoginModel;*/


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AWSLoginHandler {

        private static Context mcontext;
        AWSLoginModel awsLoginModel;
        CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private LoginButton loginButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            mcontext=this;
            //instantiating AWSLoginModel(context, callback)
            awsLoginModel = new AWSLoginModel(this, this);

            findViewById(R.id.registerButton).setOnClickListener(this);
            //findViewById(R.id.loginButton).setOnClickListener(this);
            //findViewById(R.id.confirmButton).setOnClickListener(this);

            FacebookSdk.sdkInitialize(mcontext);
            AppEventsLogger.activateApp(this);

            callbackManager = CallbackManager.Factory.create();

            // To generate hash key programmatically
            generateHashKey();


            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList(EMAIL));
            // If you are using in a fragment, call loginButton.setFragment(this);

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    Intent i = new Intent(RegisterActivity.this, TabbarActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }


            });

            }


    public void generateHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.uob.durgaraj.quickcook",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

//        @Override
      public void onRegisterSuccess(boolean mustConfirmToComplete) {
         // Toast.makeText(RegisterActivity.this, "Registered3! Login Now!", Toast.LENGTH_LONG).show();
          //  Intent i = new Intent(RegisterActivity.this, GetinputActivity.class);
          //startActivity(i);
          Intent i = new Intent(RegisterActivity.this, TabbarActivity.class);
          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(i);

        }

        @Override
        public void onRegisterConfirmed() {

        }

        @Override
        public void onSignInSuccess() {
            //Toast.makeText(RegisterActivity.this, "Sign in Success! Login Now!", Toast.LENGTH_LONG).show();
            //RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

        @Override
        public void onFailure(int process, Exception exception) {
            exception.printStackTrace();
            String whatProcess = "";
            switch (process) {
                case AWSLoginModel.PROCESS_REGISTER:
                    whatProcess = "Registration:";
                    showErrorAlert(whatProcess + exception.getMessage());
                    break;
                }

            //Toast.makeText(RegisterActivity.this, whatProcess + exception.getMessage(), Toast.LENGTH_LONG).show();
        }

    private void showErrorAlert(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Error").setMessage(msg).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
       final AlertDialog alert = builder.create();
        alert.show();
        }

    @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.registerButton:
                    registerAction();
                    //Toast.makeText(RegisterActivity.this, "Registered6666666! Login Now!", Toast.LENGTH_LONG).show();

                    break;
                    }
        }

        private void registerAction() {
            EditText userName = findViewById(R.id.registerUsername);
            EditText email = findViewById(R.id.registerEmail);
            EditText password = findViewById(R.id.registerPassword);

            // do register and handles on interface
           awsLoginModel.registerUser(userName.getText().toString(), email.getText().toString(),password.getText().toString());
        }


    }


