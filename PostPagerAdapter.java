package Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import Other.ImageFoursquare;
import com.ken.cars.R;
import Other.UniversalImageLoader;

public class PostPagerAdapter extends PagerAdapter {
    Activity activity;
    List<String> images;
    LayoutInflater inflater;

    public PostPagerAdapter(Activity activity, List<String> images) {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.post_viewpager_item, container, false);

        ImageFoursquare image = (ImageFoursquare)itemView.findViewById(R.id.imageView);
        ProgressBar progressBar = (ProgressBar)itemView.findViewById(R.id.viewPagerProgressBar);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int width = dis.widthPixels;
        int height = dis.heightPixels;
        image.setMaxWidth(width);
        image.setMaxHeight(width);

        UniversalImageLoader universalImageLoader = new UniversalImageLoader(activity);
        ImageLoader.getInstance().init(universalImageLoader.getConfig(activity));
        UniversalImageLoader.setImage(images.get(position ), image, progressBar, "");

        container.addView(itemView);
        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
    }
}
