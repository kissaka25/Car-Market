package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import Other.ImageFoursquare;
import com.ken.cars.PostActivity;
import com.ken.cars.R;

public class GridImageAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgUrls;

    public GridImageAdapter(Context context, int layoutResource, String mAppend, ArrayList<String> imgUrls) {
        super(context, layoutResource, imgUrls);
        this.mContext = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutResource = layoutResource;
        this.mAppend = mAppend;
        this.imgUrls = imgUrls;
    }

    private static class ViewHolder {
        ImageFoursquare profileImage;
        ProgressBar mProgressBar;
        LinearLayout layNr;
        TextView tvNr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.gridViewProgressbar);
            viewHolder.profileImage = (ImageFoursquare) convertView.findViewById(R.id.gridViewImage);
            viewHolder.layNr = (LinearLayout) convertView.findViewById(R.id.layNr);
            viewHolder.tvNr = (TextView) convertView.findViewById(R.id.tvNr);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String imgUrl = getItem(position);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mAppend + imgUrl, viewHolder.profileImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (viewHolder.mProgressBar != null)
                    viewHolder.mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (viewHolder.mProgressBar != null)
                    viewHolder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (viewHolder.mProgressBar != null)
                    viewHolder.mProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        int klikuar = 0;
        for (int i = 0; i < PostActivity.postFotos.size(); i++) {
            if (imgUrl.equals(PostActivity.postFotos.get(i).toString())) {
                viewHolder.layNr.setVisibility(View.VISIBLE);
                viewHolder.tvNr.setText(String.valueOf(i + 1));
                viewHolder.layNr.bringToFront();
                klikuar++;
            }
        }
        if (klikuar == 0)
            viewHolder.layNr.setVisibility(View.GONE);

            return convertView;
    }

}
