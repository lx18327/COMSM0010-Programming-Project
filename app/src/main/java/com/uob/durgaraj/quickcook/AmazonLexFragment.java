package com.uob.durgaraj.quickcook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.lex.interactionkit.Response;
import com.amazonaws.mobileconnectors.lex.interactionkit.config.InteractionConfig;
import com.amazonaws.mobileconnectors.lex.interactionkit.ui.InteractiveVoiceView;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AmazonLexFragment extends Fragment implements View.OnClickListener {

    DynamoDBMapper dynamoDBMapper;
    String userId = "";
    static Context mcontext;
    InteractiveVoiceView voiceView;
    String ingredients;
    Button confirmButton;
    public static List<QuickCookListDO> itemList = null;

    public static AmazonLexFragment newInstance(Context tabBarContext) {
           AmazonLexFragment fragment = new AmazonLexFragment();
           mcontext =tabBarContext;
           return fragment;
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.activity_lex, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        voiceView=
                (InteractiveVoiceView) getView().findViewById(R.id.voiceinterface);
        confirmButton = (Button) getView().findViewById(R.id.confirm);
        confirmButton.setOnClickListener(this);

       initializeDynamoDB();
        initializeLex();


    }
    //initialize Lex

    public void initializeLex() {
        Log.d("TAG","Bot Error: " + "income");
        voiceView.setInteractiveVoiceListener(
                new InteractiveVoiceView.InteractiveVoiceListener() {
                    @Override
                    public void dialogReadyForFulfillment(Map<String, String> slots, String intent) {
                        Log.d("TAG", String.format(
                                Locale.US,
                                "Dialog ready for fulfillment:\n\tIntent: %s\n\tSlots:%s",
                                intent,
                                slots.toString()));
                        Log.d("TAG","Bot Error: " + "dialogReadyForFulfillment");

                    }

                    @Override
                    public void onResponse(Response response) {
                        Log.d("TAG","Bot Response: " + response.getTextResponse());
                        if(response.getTextResponse().contains("Here")) {
                           ingredients="";
                           ingredients=response.getTextResponse();
                        }

                        /**if(response.getTextResponse().contains("fetching")){
                            String[] userInputs = ingredients.split(" ");
                            String[] use;
                            Log.d("TAG","Bot Response: " + "here");
                            use = userInputs[5].split(",");
                            scanQuickCook(use[0], userInputs[6], userInputs[8]);                        }
                    }**/
                    }

                    @Override
                    public void onError(String responseText, Exception e) {
                       Log.d("TAG","Bot Error: " + e.getMessage());
                        if(e.getMessage().contains("ASR")) {
                            showErrorAlert("Please enable the microphone in the app settings");
                        }
                    }
                });

        CognitoCredentialsProvider credentialsProvider =
                IdentityManager.getDefaultIdentityManager().getUnderlyingProvider();
        voiceView.getViewAdapter().setCredentialProvider(credentialsProvider);
        voiceView.getViewAdapter().setInteractionConfig(new InteractionConfig("BookTripMOBILEHUB","$LATEST"));
        voiceView.getViewAdapter().setAwsRegion(getActivity().getApplicationContext().getString(R.string.aws_region));

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
//scan
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
               itemList=null;
                itemList= dynamoDBMapper.scan(QuickCookListDO.class, queryExpression);
                Log.d("Query result: ", ""+itemList.size());

                for (int i=0; i<itemList.size();i++) {
                    Log.d("Query result: ", itemList.get(i).getItemName());
                }
                    Intent i = (new Intent(mcontext, RecipesListActivity.class));
                    startActivity(i);


                }
        }).start();
    }
    private void showErrorAlert(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("Error").setMessage(msg).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if(ingredients!=null ) {
                    String[] userInputs = ingredients.split(" ");
                    String[] use;
                    Log.d("TAG", "Bot Response: " + "here");
                    use = userInputs[5].split(",");
                    scanQuickCook(use[0].toUpperCase(), userInputs[6].toUpperCase(), userInputs[8].toUpperCase());

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                    builder.setTitle("Error").setMessage("Please say the ingredients!!!").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
                    final AlertDialog alert = builder.create();
                    alert.show();

                }
                break;
        }
    }
}

