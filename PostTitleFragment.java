package Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import com.ken.cars.PostActivity;
import com.ken.cars.R;

import static android.support.constraint.Constraints.TAG;

public class PostTitleFragment extends Fragment {

    EditText postTitle;
    Button btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_title, container, false);
        PostActivity.setTitle("Post title");
        ((PostActivity) getActivity()).getSupportActionBar().show();

        postTitle = (EditText)v.findViewById(R.id.editPostTitle);
        postTitle.setText(PostActivity.post.getTitle());

        btnNext = (Button)v.findViewById(R.id.btnTitleNext);

        postTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                PostActivity.post.setTitle(postTitle.getText().toString());
                return false;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.post.setTitle(postTitle.getText().toString());
                String id = Database.RemoteDatabase.addPost(PostActivity.post);
                if(id!=null)
                {
                    if(PostActivity.postFotos.size()>0) {
                        for(int i=0;i<PostActivity.postFotos.size();i++){
                            final ProgressDialog progressDialog = new ProgressDialog(getContext());
                            final int nr = i;
                            progressDialog.setTitle("Uploading picture "+String.valueOf(i+1));
                            progressDialog.show();
                            try {
                                UploadTask uploadTask = Database.RemoteDatabase.postImages(id, PostActivity.postFotos.get(i), i);
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Picture " + String.valueOf((nr + 1)) + " uploaded!", Toast.LENGTH_SHORT).show();
                                        if (nr == (PostActivity.postFotos.size() - 1))
                                            ((PostActivity) getActivity()).finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Uploading picture " + String.valueOf((1 + nr)) + " failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        progressDialog.setMessage("Please wait...");
                                    }
                                });
                            }catch (NullPointerException n){
                                progressDialog.dismiss();
                            }
                        }
                    }

                } else {
                    Log.e(TAG, "addNewPost: failed");
                    Toast.makeText(getContext(), "The post could not be saved to database!", Toast.LENGTH_LONG).show();
                }



            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
