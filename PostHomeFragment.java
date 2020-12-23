package Fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.ken.cars.PostActivity;
import com.ken.cars.R;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class PostHomeFragment extends Fragment {
    private LinearLayout postMain, postTechnical, postFeatures, postDescription;
    private RelativeLayout postFoto;
    TextView tvMain, tvMainDesc;
    Button btnPosts;
    Fragment fragment;


    public static boolean clicked = false;

    private int error1, error2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_home, container, false);

        PostActivity.setTitle("Post");
        ((PostActivity) getActivity()).getSupportActionBar().show();
        postMain = (LinearLayout)v.findViewById(R.id.postMain);
        postFoto = (RelativeLayout) v.findViewById(R.id.postFoto);
        postTechnical = (LinearLayout) v.findViewById(R.id.postTechnical);
        postFeatures = (LinearLayout) v.findViewById(R.id.postFeatures);
        postDescription = (LinearLayout) v.findViewById(R.id.postDescription);
        btnPosts = (Button) v.findViewById(R.id.btnPost);
        tvMain = (TextView) v.findViewById(R.id.tvMain);
        tvMainDesc = (TextView) v.findViewById(R.id.tvMainDesc);

        postMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(1);
            }
        });

        postTechnical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(3);
            }
        });

        postFeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(4);
            }
        });

        postDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(5);
            }
        });

        btnPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = true;
                if(PostActivity.post.getMakeID()==0 || PostActivity.post.getModelID()==0 || PostActivity.post.getFuelID()==0 || PostActivity.post.getCarTypeID()==0
                        || PostActivity.post.getNrDoors()==0 || PostActivity.post.getFirstRegistration()==null || PostActivity.post.getKm()==null
                        || PostActivity.post.getTransmission()==0 || PostActivity.post.getPrice()==0) {
                    tvMain.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvMainDesc.setTextColor(getResources().getColor(R.color.colorAccent));
                    Toast.makeText(getContext(), "Please fill the areas in red!", Toast.LENGTH_LONG).show();
                    error1++;
                } else {
                    tvMain.setTextColor(getResources().getColor(R.color.background));
                    tvMainDesc.setTextColor(getResources().getColor(R.color.gray));
                    PostActivity.post.setOwnerID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    PostActivity.features.setAirbag(PostActivity.airbag);
                    PostActivity.post.setFeatures(PostActivity.features);

                    resetFragment(2);
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // function to navigate through activity fragments
    private void resetFragment(int i)
    {
        fragment = null;
        switch (i){
            case 1:
                fragment = new PostMainFragment();
                break;
            case 2:
                if(ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    fragment = new PostImagesFragment();
                    break;
                } else {
                    fragment = null;
                    ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE}, 200);

                    break;
                }



                

            case 3:
                fragment = new PostTechnicalFragment();
                break;
            case 4:
                fragment = new PostFeaturesFragment();
                break;
            case 5:
                fragment = new PostDescriptionFragment();
                break;
        }
        if(fragment!=null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.screen_post, fragment);
            ft.commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            fragment = new PostImagesFragment();
            Toast.makeText(getContext(), "Access to photos granted", Toast.LENGTH_LONG).show();
        }

//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
