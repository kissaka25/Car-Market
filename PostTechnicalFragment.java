package Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import Database.LocalDatabase;
import Adapters.DialogPostAdapter;
import com.ken.cars.PostActivity;
import com.ken.cars.R;

public class PostTechnicalFragment extends Fragment implements DialogPostAdapter.OnDialogListener {
    LinearLayout postPlate, postCustoms, postRegistration, postOwner, postSeat, postColor, postMaterial, postInteriorColor;
    TextView tvPostPlate, tvPostCustoms, tvPostRegistration, tvPosOwner, tvPostSeat, tvPostColor, tvPostMaterial, tvPostInteriorColor;
    RadioButton radioMetallic, radioMat;
    RadioGroup group;
    Dialog pickerDialog;
    int width, height;
    List<String> dialogItems;
    int btnID;
    private DatePickerDialog.OnDateSetListener mRegistrationDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_technical, container, false);
        ((PostActivity) getActivity()).getSupportActionBar().show();
        PostActivity.setTitle("Technical data");


        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        postPlate = (LinearLayout) v.findViewById(R.id.postPlate);
        postCustoms = (LinearLayout) v.findViewById(R.id.postCustoms);
        postRegistration = (LinearLayout) v.findViewById(R.id.postRegistration);
        postOwner = (LinearLayout) v.findViewById(R.id.postOwner);
        postSeat = (LinearLayout) v.findViewById(R.id.postSeats);
        postColor = (LinearLayout) v.findViewById(R.id.postColor);
        postMaterial = (LinearLayout) v.findViewById(R.id.postMaterial);
        postInteriorColor = (LinearLayout) v.findViewById(R.id.postInteriorColor);

        tvPostPlate = (TextView) v.findViewById(R.id.tvPostPlate);
        tvPostCustoms = (TextView) v.findViewById(R.id.tvPostCustoms);
        tvPostRegistration = (TextView) v.findViewById(R.id.tvPostRegistration);
        tvPosOwner = (TextView) v.findViewById(R.id.tvPostOwner);
        tvPostSeat = (TextView) v.findViewById(R.id.tvPostSeats);
        tvPostColor = (TextView) v.findViewById(R.id.tvPostColor);
        tvPostMaterial = (TextView) v.findViewById(R.id.tvPostMaterial);
        tvPostInteriorColor = (TextView) v.findViewById(R.id.tvPostColorMaterial);

        radioMetallic = (RadioButton) v.findViewById(R.id.btnMetallic);
        radioMat = (RadioButton) v.findViewById(R.id.btnMat);

        radioMetallic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvPostColor.getText().toString().trim().equals("Unspecified")) {
                    PostActivity.post.setMetallicColor(true);
                }
            }
        });

        radioMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvPostColor.getText().toString().trim().equals("Unspecified")) {
                    PostActivity.post.setMetallicColor(false);
                }
            }
        });

        group = (RadioGroup) v.findViewById(R.id.group);

        if (PostActivity.post.getPlate() != null) {
            tvPostPlate.setText(PostActivity.post.getPlate());

            if (PostActivity.post.getPlate().equals("Local")) {
                postRegistration.setVisibility(View.VISIBLE);
                postCustoms.setVisibility(View.GONE);
            } else if (PostActivity.post.getPlate().equals("Foreign")) {
                postRegistration.setVisibility(View.GONE);
                postCustoms.setVisibility(View.VISIBLE);
            } else {
                postRegistration.setVisibility(View.GONE);
                postCustoms.setVisibility(View.GONE);
            }
        }

        if (PostActivity.post.getCleared() != null)
            tvPostCustoms.setText(PostActivity.post.getCleared());

        if (PostActivity.post.getRegistration() != null)
            tvPostRegistration.setText(PostActivity.post.getRegistration());

        if (PostActivity.post.getNrOwners() != null)
            tvPosOwner.setText(PostActivity.post.getNrOwners());

        if (PostActivity.post.getNrSeats() != null)
            tvPostSeat.setText(PostActivity.post.getNrSeats());

        if (PostActivity.post.getColor() != 0) {
            List<String> ngjyra = Arrays.asList(getResources().getStringArray(R.array.colors));
            tvPostColor.setText(ngjyra.get(PostActivity.post.getColor()));
            if(PostActivity.post.isMetallicColor())
                radioMetallic.setChecked(true);
            else
                radioMat.setChecked(true);
        }

        if (PostActivity.post.getInteriorMaterial() != 0){
            List<String> enterier = Arrays.asList(getResources().getStringArray(R.array.interior));
            tvPostMaterial.setText(enterier.get(PostActivity.post.getInteriorMaterial()));
        }


        if (PostActivity.post.getInteriorColor() != 0) {
            List<String> interiorColor = Arrays.asList(getResources().getStringArray(R.array.colorInterior));
            tvPostInteriorColor.setText(interiorColor.get(PostActivity.post.getInteriorColor()));
        }

        postPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 1;
                clicker(btnID);
            }
        });

        postCustoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 2;
                clicker(btnID);
            }
        });

        postRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mRegistrationDate,
                        year, month, day);
                String yr = "31556952000";
                ((ViewGroup) dialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis()+Long.parseLong(yr));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mRegistrationDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String y = String.valueOf(month+1)+"/"+String.valueOf(year);
                tvPostRegistration.setText(y);
                PostActivity.post.setRegistration(y);
            }
        };

        postOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 4;
                clicker(btnID);
            }
        });

        postSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 5;
                clicker(btnID);
            }
        });

        postColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 6;
                clicker(btnID);
            }
        });

        postMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 7;
                clicker(btnID);
            }
        });

        postInteriorColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 8;
                clicker(btnID);
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void clicker(int button) {
        pickerDialog = new Dialog(getContext());
        pickerDialog = new Dialog(getContext());
        pickerDialog.setContentView(R.layout.dialog_post);
        pickerDialog.setTitle(" ");
        pickerDialog.getWindow().setLayout((width - 100), (width - 100));

        TextView tvTitle = (TextView) pickerDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) pickerDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) pickerDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));


        SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
        Cursor c = null;

        dialogItems = new ArrayList<>();

        if (button == 1) {
            tvTitle.setText("Car plates:");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.plates));
        } else if (button == 2) {
            tvTitle.setText("Customs:");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.options));
        } else if (button == 4) {
            tvTitle.setText("Number of owners:");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.owner));
        } else if (button == 5) {
            tvTitle.setText("Number of seats:");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.seats));
        } else if (button == 6) {
            tvTitle.setText("Color of car");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.colors));
        } else if (button == 7) {
            tvTitle.setText("Interior");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.interior));
        } else if (button == 8) {
            tvTitle.setText("Interior color");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.colorInterior));
        }
        DialogPostAdapter dialogAdapteri = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapteri);
        pickerDialog.show();
    }

    @Override
    public void onDialogItemClick(int position) {
        if (btnID == 1) {
            PostActivity.post.setPlate(dialogItems.get(position));
            tvPostPlate.setText(dialogItems.get(position));
            if (position == 1) {
                postCustoms.setVisibility(View.GONE);
                postRegistration.setVisibility(View.VISIBLE);
                PostActivity.post.setCleared(null);
            } else if (position == 3) {
                postCustoms.setVisibility(View.VISIBLE);
                postRegistration.setVisibility(View.GONE);
                PostActivity.post.setRegistration(null);
            } else {
                postCustoms.setVisibility(View.GONE);
                postRegistration.setVisibility(View.GONE);
                PostActivity.post.setCleared(null);
                PostActivity.post.setRegistration(null);
            }
        } else if (btnID == 2) {
            PostActivity.post.setCleared(dialogItems.get(position));
            tvPostCustoms.setText(dialogItems.get(position));
        } else if (btnID == 4) {
            PostActivity.post.setNrOwners(dialogItems.get(position));
            tvPosOwner.setText(dialogItems.get(position));
        } else if (btnID == 5) {
            PostActivity.post.setNrSeats(dialogItems.get(position));
            tvPostSeat.setText(dialogItems.get(position));
        } else if (btnID == 6) {
            PostActivity.post.setColor(position);
            PostActivity.post.setMetallicColor(radioMetallic.isChecked());
            tvPostColor.setText(dialogItems.get(position));
        } else if (btnID == 7) {
            PostActivity.post.setInteriorMaterial(position);
            tvPostMaterial.setText(dialogItems.get(position));
        } else if (btnID == 8) {
            PostActivity.post.setInteriorColor(position);
            tvPostInteriorColor.setText(dialogItems.get(position));
        }
        pickerDialog.dismiss();
    }
}
