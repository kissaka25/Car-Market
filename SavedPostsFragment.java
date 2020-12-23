package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import com.ken.cars.NonUserActivity;
import com.ken.cars.R;
import com.ken.cars.UserActivity;

public class SavedPostsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
            ((NonUserActivity) getActivity()).getSupportActionBar().setTitle(R.string.posts);
        else
            ((UserActivity) getActivity()).getSupportActionBar().setTitle(R.string.posts);



    }
}
