package com.example.harjoitustyfinnkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class FavouritesListAdapter extends ArrayAdapter<FavouritesInformation> {
    private final Context mContext;
    private final int mResource;
    private int lastPosition = -1;


    static class ViewHolder {
        TextView name;
    }

    public FavouritesListAdapter(Context context, int resource, ArrayList<FavouritesInformation> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();

        FavouritesInformation movie = new FavouritesInformation(name);



        final View result;
        FavouritesListAdapter.ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new FavouritesListAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView2);
            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (FavouritesListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        //adding animation to listview
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position>lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
        lastPosition = position;



        holder.name.setText(movie.name);


        return convertView;
    }
}
