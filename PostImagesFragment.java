package Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import Other.FilePaths;
import Other.FileSearch;
import Adapters.GridImageAdapter;
import com.ken.cars.PostActivity;
import com.ken.cars.R;
import Other.UniversalImageLoader;

public class PostImagesFragment extends Fragment {

    private static final int NUM_COLUMNS_GRID = 3;
    private String mAppend = "file:/";

    private ArrayList<String> directories;
    private ArrayList<String> dNames;

    private GridView gridView;
    private Spinner gallerySpinner;
    private ImageView imgGallery, imgGalleryBack;
    Button imgGalleryNext;
    private Button btnGo;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_foto, container, false);

        Toolbar toolbar = v.findViewById(R.id.toolbar_post_gallery);
        ((PostActivity) getActivity()).getSupportActionBar().hide();

        gridView = (GridView) v.findViewById(R.id.gridView);
        gallerySpinner = (Spinner) v.findViewById(R.id.gallerySpinner);
        imgGallery = (ImageView) v.findViewById(R.id.imgGallery);
        imgGalleryBack = (ImageView) v.findViewById(R.id.galleryBack);
        imgGalleryNext = (Button)v.findViewById(R.id.galleryNext);
        btnGo = (Button) v.findViewById(R.id.galleryNext);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Select 1 to 10 photos", Toast.LENGTH_LONG).show();

        imgGalleryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PostActivity)getActivity()).getSupportFragmentManager().popBackStack();
            }
        });

        imgGalleryNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PostTitleFragment();
                FragmentManager fragmentManager = ((PostActivity)getActivity()).getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.screen_post, fragment);
                ft.commit();
            }
        });

        directories = new ArrayList<>();
        dNames = new ArrayList<>();
        init();


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        FilePaths filePaths = new FilePaths();
        if (FileSearch.getDirectoryPaths(filePaths.PICTURES) != null) {
            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
        }
        directories.add(filePaths.CAMERA);

        for (int i = 0; i < directories.size(); i++) {
            String[] directory = directories.get(i).toString().split("/");
            dNames.add(directory[(directory.length - 1)]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gallerySpinner.setAdapter(arrayAdapter);

        gallerySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setGridView(directories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setGridView(String directory) {
        final ArrayList<String> imgUrls = FileSearch.getFilePaths(directory);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / NUM_COLUMNS_GRID;
        gridView.setColumnWidth(imageWidth);

        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig(getContext()));

        final GridImageAdapter gridImageAdapter = new GridImageAdapter(getContext(), R.layout.layout_grid_item, mAppend, imgUrls);
        gridView.setAdapter(gridImageAdapter);
        if(imgUrls.size()>0)
            placePhoto(imgUrls.get(0), imgGallery, mAppend);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                placePhoto(imgUrls.get(position), imgGallery, mAppend);
                LinearLayout lay = view.findViewById(R.id.layNr);
                TextView tv = view.findViewById(R.id.tvNr);
                ImageView img = view.findViewById(R.id.gridViewImage);

                int del = 0;
                for (int i = 0; i < PostActivity.postFotos.size(); i++) {
                    if (imgUrls.get(position).equals(PostActivity.postFotos.get(i))) {
                        PostActivity.postFotos.remove(i);
                        lay.setVisibility(View.GONE);
                        del++;
                    }
                }
                if (del == 0) {
                    if (PostActivity.postFotos.size() >= 10)
                        Toast.makeText(getContext(), "10 photos maximum!", Toast.LENGTH_LONG).show();
                    else {
                        PostActivity.postFotos.add(imgUrls.get(position));
                        tv.setText(String.valueOf(PostActivity.postFotos.size()));
                        lay.setVisibility(View.VISIBLE);
                        lay.bringToFront();
                    }
                }

                gridImageAdapter.notifyDataSetChanged();
            }
        });
    }

    private void placePhoto(String url, ImageView imageView, String append) {
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(append + url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
