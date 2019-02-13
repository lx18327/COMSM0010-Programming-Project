package com.uob.durgaraj.quickcook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.List;

public class TabbarActivity extends AppCompatActivity {
public BottomNavigationView bottomNavigationView;
public Context mcontext;
    DynamoDBMapper dynamoDBMapper;
    String userId = "";
    public static List<QuickCookListDO> favList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        mcontext =getApplication().getApplicationContext();
        initializeDynamoDB();
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                //Toast.makeText(TabbarActivity.this, "Item11111!", Toast.LENGTH_LONG).show();
                                selectedFragment = AmazonLexFragment.newInstance(TabbarActivity.this);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment);
                                transaction.commit();

                                break;
                            case R.id.action_item2:
                                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                               // Toast.makeText(TabbarActivity.this, "Item2222!", Toast.LENGTH_LONG).show();
                                //selectedFragment = MyFavoritesFragment.newInstance(TabbarActivity.this);
                                //FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                                //transaction1.replace(R.id.frame_layout, selectedFragment);
                                //transaction1.commit();
                                scanQuickCook("CHEESE","TOMATO","BREAD");

                                break;
                            case R.id.action_item3:
                                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                               // Toast.makeText(TabbarActivity.this, "Item3333!", Toast.LENGTH_LONG).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(TabbarActivity.this);
                                builder.setTitle("Log Out").setMessage("Do you want to logout from the app?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Toast.makeText(TabbarActivity.this, "Logged Out Successfully!!!!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(TabbarActivity.this, UserRegisterOrSignInActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                       // do nothing
                                        }
                                });
                                final AlertDialog alert = builder.create();
                                alert.show();
                             //   selectedFragment = ItemThreeFragment.newInstance()
                                break;
                        }

                        //Used to select an item programmatically
                        bottomNavigationView.getMenu().getItem(0).setChecked(true);
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AmazonLexFragment.newInstance(TabbarActivity.this));
        transaction.commit();

        //Used to select an item programmatically
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void scanQuickCook(final String ing1, final String ing2, final String ing3) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                QuickCookListDO news = new QuickCookListDO();
                QuickCookListDO partitionkey=new QuickCookListDO();
                //   partitionkey.setItemId("3");
                Log.d("Query result: ", ing1+ing2+ing3);

                Condition rangeKeyCondition1 = new Condition()
                        .withComparisonOperator(ComparisonOperator.IN)
                        .withAttributeValueList(new AttributeValue().withS(ing1), new AttributeValue().withS(ing2), new AttributeValue().withS(ing3));
                Condition rangeKeyCondition2 = new Condition()
                        .withComparisonOperator(ComparisonOperator.IN)
                        .withAttributeValueList(new AttributeValue().withS(ing1), new AttributeValue().withS(ing2), new AttributeValue().withS(ing3));
                Condition rangeKeyCondition3 = new Condition()
                        .withComparisonOperator(ComparisonOperator.IN)
                        .withAttributeValueList(new AttributeValue().withS(ing1), new AttributeValue().withS(ing2), new AttributeValue().withS(ing3));;
                DynamoDBScanExpression queryExpression=new DynamoDBScanExpression()

                        .withFilterConditionEntry("Ingredient 1",rangeKeyCondition1)
                        .withFilterConditionEntry("Ingredient 2",rangeKeyCondition2)
                        .withFilterConditionEntry("Ingredient 3",rangeKeyCondition3);

                /** Condition rangeKeyCondition1 = new Condition()
                 .withComparisonOperator(ComparisonOperator.IN)
                 .withAttributeValueList(new AttributeValue().withS("bread"), new AttributeValue().withS("Cheese"));
                 Condition rangeKeyCondition2 = new Condition()
                 .withComparisonOperator(ComparisonOperator.IN)
                 .withAttributeValueList(new AttributeValue().withS("bread"), new AttributeValue().withS("Cheese"));


                 DynamoDBScanExpression queryExpression=new DynamoDBScanExpression()
                 .withFilterConditionEntry("Ingredient 1",rangeKeyCondition1)
                 .withFilterConditionEntry("Ingredient 2",rangeKeyCondition2);**/
                favList=null;
                favList= dynamoDBMapper.scan(QuickCookListDO.class, queryExpression);
                Log.d("Query result: ", ""+favList.size());

                for (int i=0; i<favList.size();i++) {
                    Log.d("Query result: ", favList.get(i).getItemName());
                }

               Fragment selectedFragment = MyFavoritesFragment.newInstance(TabbarActivity.this);
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.frame_layout, selectedFragment);
                transaction1.commit();

            }
        }).start();
    }

    // initialize DynamoDB
    public void initializeDynamoDB() {
        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(mcontext).execute();
        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
        IdentityManager idm = new IdentityManager(mcontext,
                new AWSConfiguration(mcontext));
        IdentityManager.setDefaultIdentityManager(idm);

        IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
        userId = identityManager.getCachedUserID();
        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();


    }

}
