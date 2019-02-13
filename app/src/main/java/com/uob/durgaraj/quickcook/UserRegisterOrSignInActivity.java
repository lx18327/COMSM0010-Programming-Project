package com.uob.durgaraj.quickcook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserRegisterOrSignInActivity extends AppCompatActivity implements View.OnClickListener {
    Button register_btn, signIn_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useractivity);

        initialiseUIViews();
    }

    private void initialiseUIViews() {
        register_btn = (Button) findViewById(R.id.register);
        register_btn.setOnClickListener(this);
        signIn_btn = (Button) findViewById(R.id.signIn);
        signIn_btn.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
               //
                // Toast.makeText(UserRegisterOrSignInActivity.this, "User1", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UserRegisterOrSignInActivity.this, RegisterActivity.class));
                break;

            case R.id.signIn:
               // Toast.makeText(UserRegisterOrSignInActivity.this, "User2", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UserRegisterOrSignInActivity.this, SignInActivity.class));
                break;

        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}