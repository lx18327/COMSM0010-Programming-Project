package com.uob.durgaraj.quickcook;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class RecipesListActivity extends AppCompatActivity {
    GridView gridView;
    String[] openView = {"Web page", "You Tube"};
    int[] imageId = {
            R.drawable.chickenrice,
            R.drawable.pizza,
            R.drawable.pizza,
            R.drawable.pasta,
            R.drawable.ricebowl,
            R.drawable.nachos,
            R.drawable.sandwich,
            R.drawable.sandwich,
            R.drawable.fingersandwich,
            R.drawable.pasta

    };

    List<QuickCookListDO> quickCookListDO = null;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeslist);

        quickCookListDO = AmazonLexFragment.itemList;

       if(quickCookListDO.size()==0) {
           android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RecipesListActivity.this);
           builder.setTitle("Error").setMessage("Sorry! Could'nt fetch the recipe with the ingredients provided!!").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                 startActivity(new Intent(RecipesListActivity.this,TabbarActivity.class));

               }
           });
           final android.support.v7.app.AlertDialog alert = builder.create();
           alert.show();

        }

        gridView = (GridView) findViewById(R.id.gridView);
        mContext =this;
        ImageAdapter adapter = new ImageAdapter(RecipesListActivity.this, quickCookListDO, imageId);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
            //    Toast.makeText(RecipesListActivity.this, "You Clicked at " +item[+ position], Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle("Please select a view to open");
                    alertDialogBuilder.setItems(openView, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which==0){

                                        Intent i = new Intent(RecipesListActivity.this, RecepiesWebViewActivity.class);
                                        i.putExtra("selected item", position);
                                        startActivity(i);

                                    }
                                    else{
                                        Intent intent = new Intent(
                                              Intent.ACTION_VIEW ,
                                             Uri.parse(quickCookListDO.get(position).getYoutubeUrl()));
                                        intent.setPackage("com.google.android.youtube");
                                          mContext.startActivity(intent);

                                    }
                                }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
        });

    }
    }