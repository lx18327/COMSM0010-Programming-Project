package com.uob.durgaraj.quickcook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anupamchugh on 24/10/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    //private final String[] web;
    private final int[] Imageid;
    private List<QuickCookListDO> quickCookListDO;

    public ImageAdapter(Context c, List<QuickCookListDO> quickCookListDO, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.quickCookListDO = quickCookListDO;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return quickCookListDO.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.list_recipes, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            textView.setText(quickCookListDO.get(position).getItemName());
            int id = Integer.parseInt( quickCookListDO.get(position).getItemId());
            imageView.setImageResource(Imageid[id]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}