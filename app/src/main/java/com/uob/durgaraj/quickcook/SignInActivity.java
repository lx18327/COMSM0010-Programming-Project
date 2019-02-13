package com.uob.durgaraj.quickcook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.uob.durgaraj.quickcook.aws.AWSLoginHandler;
import com.uob.durgaraj.quickcook.aws.AWSLoginModel;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, AWSLoginHandler {

    private Button signIn_Btn;
    AWSLoginModel awsLoginModel;
    CallbackManager callbackManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        awsLoginModel = new AWSLoginModel(this, this);

        signIn_Btn = (Button) findViewById(R.id.signIn);
        signIn_Btn.setOnClickListener(this);
    }



    @Override
    public void onFailure(int process, Exception exception) {
        exception.printStackTrace();
        String whatProcess = "";
        switch (process) {
            case AWSLoginModel.PROCESS_SIGN_IN:
                whatProcess = "Sign In:";
                if(exception.getMessage().contains("not confirmed")){
                    Intent i = new Intent(SignInActivity.this, TabbarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else {
                    showErrorAlert(whatProcess + exception.getMessage());
                }
                break;
        }
         //Toast.makeText(SignInActivity.this, whatProcess + exception.getMessage(), Toast.LENGTH_LONG).show();
       // SignInActivity.this.startActivity(new Intent(SignInActivity.this, TabbarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    private void showErrorAlert(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        builder.setTitle("Error").setMessage(msg).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSignInSuccess() {
        //Toast.makeText(SignInActivity.this, "Sign in Success", Toast.LENGTH_LONG).show();
        Intent i = new Intent(SignInActivity.this, TabbarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    @Override
    public void onRegisterConfirmed() {

    }

    @Override
    public void onRegisterSuccess(boolean mustConfirmToComplete) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                signInAction();
            break;
        }
}

    private void signInAction() {
        EditText userOrEmail = findViewById(R.id.emailorusername);
        EditText password = findViewById(R.id.password);

        // do sign in and handles on interface
        awsLoginModel.signInUser(userOrEmail.getText().toString(), password.getText().toString());
    }
    }
