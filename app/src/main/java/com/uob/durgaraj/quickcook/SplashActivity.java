package com.uob.durgaraj.quickcook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
;
//import com.amazonaws.mobile.auth.facebook.FacebookButton;


public class SplashActivity extends AppCompatActivity {
private static int TIME_OUT=4000;
private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              AWSMobileClient.getInstance().initialize(SplashActivity.this, new AWSStartupHandler() {
                    @Override
                    public void onComplete(AWSStartupResult awsStartupResult) {

                       // IdentityManager idm = new IdentityManager(mContext,
//                                new AWSConfiguration(mContext));
                        //IdentityManager.setDefaultIdentityManager(idm);

                        IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
                        identityManager.resumeSession(SplashActivity.this, new StartupAuthResultHandler() {
                            @Override
                            public void onComplete(StartupAuthResult authResults) {
                                   startActivity(new Intent(SplashActivity.this, UserRegisterOrSignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                   finish();
                            }
                        }, 3000);
                    }
                }).execute();

            }
        }, TIME_OUT);
    }
    }
