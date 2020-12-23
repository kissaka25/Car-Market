package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ken.cars.PostActivity;
import com.ken.cars.R;

public class PostDescriptionFragment extends Fragment {

    EditText postDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_description, container, false);
        PostActivity.setTitle("Car description");
        ((PostActivity) getActivity()).getSupportActionBar().show();

        postDescription = (EditText)v.findViewById(R.id.editPostDescription);
        postDescription.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                PostActivity.post.setDescription(postDescription.getText().toString());
                return false;
            }
        });

        if(PostActivity.post.getDescription()!=null)
            postDescription.setText(PostActivity.post.getDescription());


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
