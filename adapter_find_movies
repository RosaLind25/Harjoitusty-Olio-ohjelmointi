package com.example.harjoitustyfinnkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class adapter_find_movies extends ArrayAdapter<ShowInformation>{

    private final Context mContext;
    private final int mResource;
    private int lastPosition = -1;



    static class ViewHolder {
        TextView title;
        TextView length;
        TextView theatre;
        TextView date_time;
        ImageView img;
    }

    public adapter_find_movies(Context context, int resource, ArrayList<ShowInformation>objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setupImageLoader();
        String start = getItem(position).getStart();
        String title = getItem(position).getTitle();
        String theatre = getItem(position).getTheatre();
        String length = getItem(position).getLength();
        String ID = getItem(position).getID();
        String jpg = getItem(position).getJpg();

        ShowInformation show = new ShowInformation(start,title,length,theatre, ID, jpg);

        final View result;
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textView_title);
            holder.theatre = (TextView) convertView.findViewById(R.id.textView_theatre);
            holder.date_time = (TextView) convertView.findViewById(R.id.textView_date_time);
            holder.length = (TextView) convertView.findViewById(R.id.textView_length);
            holder.img = (ImageView) convertView.findViewById(R.id.imgView);
            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        //adding animation to listview
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position>lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        //adding pictures to listview
        ImageLoader imageLoader = ImageLoader.getInstance();
        int defaultImage = mContext.getResources().getIdentifier("@drawable/notimetodie.jpg",null,mContext.getPackageName());

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        imageLoader.displayImage(jpg, holder.img, options);

        //setting information for listview
        holder.title.setText(show.title);
        holder.theatre.setText(show.theatre);
        holder.date_time.setText(show.start);
        holder.length.setText(show.length);

        return convertView;
    }

    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
