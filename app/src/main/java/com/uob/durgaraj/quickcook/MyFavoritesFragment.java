package com.uob.durgaraj.quickcook;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MyFavoritesFragment extends Fragment {
    GridView gridView;
    static Context mcontext;
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
   //Context mContext;

    public static MyFavoritesFragment newInstance(Context tabBarContext) {
        MyFavoritesFragment fragment = new MyFavoritesFragment();
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
        return inflater.inflate(R.layout.activity_recipeslist, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView) getView().findViewById(R.id.gridView);
       // mContext =getActivity().getApplicationContext();
        ImageAdapter adapter = new ImageAdapter(mcontext, TabbarActivity.favList, imageId);

        gridView.setAdapter(adapter);
          gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
               // Toast.makeText(mcontext, "You Clicked at " +item[+ position], Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mcontext);
                alertDialogBuilder.setTitle("Please select a view to open");
                alertDialogBuilder.setItems(openView, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            Intent i = new Intent(mcontext, RecepiesWebViewActivity.class);
                            i.putExtra("index",position );
                            startActivity(i);

                        }
                        else{
                            Intent intent = new Intent(
                                    Intent.ACTION_VIEW ,
                                    Uri.parse(TabbarActivity.favList.get(position).getYoutubeUrl()));
                            intent.setPackage("com.google.android.youtube");
                            mcontext.startActivity(intent);

                        }
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }
}
