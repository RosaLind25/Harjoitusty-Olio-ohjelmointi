package com.example.harjoitustyfinnkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class MovieListAdapter extends ArrayAdapter<MovieInformation> {
    private final Context mContext;
    private final int mResource;
    private int lastPosition = -1;
    private String movie;
    private OnAddListener onAddListener;

    public void setOnAddListener(OnAddListener listener) {
        this.onAddListener = listener;
    }

    static class ViewHolder {
        TextView name;
        TextView SSynopsis;
        ImageView img;
        Button btn;
    }

    public MovieListAdapter(Context context, int resource, ArrayList<MovieInformation>objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setupImageLoader();
        String name = getItem(position).getName();
        String id = getItem(position).getId();
        String SSynopsis = getItem(position).getSSynopsis();
        String jpg = getItem(position).getJpg();

        MovieInformation movie = new MovieInformation(name,id,SSynopsis,jpg);



        final View result;
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView2);
            holder.SSynopsis = (TextView) convertView.findViewById(R.id.textView3);
            holder.img = (ImageView) convertView.findViewById(R.id.imgView);
            holder.btn = (Button) convertView.findViewById(R.id.btn);
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

        holder.name.setText(movie.name);
        holder.SSynopsis.setText(movie.SSynopsis);
        holder.btn.setOnClickListener(view -> {
            String movie1 = holder.name.getText().toString();
            Toast.makeText(getContext(), "Suosikkeihin lisättiin elokuva: "+ movie1, Toast.LENGTH_LONG).show();
            onAddListener.onAdd(position, movie1);
        });
        return convertView;
    }

    private String findMovie() {
        return movie;
    }

    private void setMovie(String movie){
        this.movie =movie;
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
    public interface OnAddListener {
        public void onAdd(int position, String text);
    }

}
