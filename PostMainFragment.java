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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import Database.LocalDatabase;
import Adapters.DialogPostAdapter;
import com.ken.cars.PostActivity;
import com.ken.cars.R;

public class PostMainFragment extends Fragment implements DialogPostAdapter.OnDialogListener {

    private RecyclerView.Adapter dialogAdapter;
    Dialog dialogPicker;

    private static int element;

    private DatePickerDialog.OnDateSetListener mRegistrationDate;

    int width, height;

    List<String> dialogItems;


    LinearLayout postMake, postModel, postFuel, postVariant, postBodyType, postDoors,
            postYear, postDefect, postAccident, postTransmission;

    TextView tvMake, tvModel, tvFuel, tvVariant, tvBodyType, tvDoors, tvYear, tvDefect, tvAccident, tvTransmission;
    TextView tvMakeID, tvModelID, tvFuelID, tvVariantID, tvBodyTypeID, tvDoorsID,
            tvYearID, tvDefectID, tvAccidentID, tvTransmissionID, tvKmID, tvPowerID, tvPriceID;

    EditText etKm, etPower, etPrice;

    CheckBox checkNegotiable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_main, container, false);
        PostActivity.setTitle("The main data");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;


        postMake = (LinearLayout)view.findViewById(R.id.postMake);
        postModel = (LinearLayout)view.findViewById(R.id.postModel);
        postFuel = (LinearLayout)view.findViewById(R.id.postFuel);
        postVariant = (LinearLayout)view.findViewById(R.id.postVariant);
        postBodyType = (LinearLayout)view.findViewById(R.id.postBodyType);
        postDoors = (LinearLayout)view.findViewById(R.id.postDoors);
        postYear = (LinearLayout)view.findViewById(R.id.postYear);
        postDefect = (LinearLayout)view.findViewById(R.id.postDefekt);
        postAccident = (LinearLayout)view.findViewById(R.id.postAccident);
        postTransmission = (LinearLayout)view.findViewById(R.id.postTransmission);

        tvMake = (TextView) view.findViewById(R.id.tvPostMake);
        tvModel = (TextView) view.findViewById(R.id.tvPostModel);
        tvFuel = (TextView) view.findViewById(R.id.tvPostFuel);
        tvBodyType = (TextView) view.findViewById(R.id.tvPostKarroceria);
        tvVariant = (TextView) view.findViewById(R.id.tvPostVariant);
        tvDoors = (TextView) view.findViewById(R.id.tvPostDoors);
        tvYear = (TextView) view.findViewById(R.id.tvPostYear);
        tvDefect = (TextView) view.findViewById(R.id.tvPostDefekt);
        tvAccident = (TextView) view.findViewById(R.id.tvPostAccident);
        tvTransmission = (TextView) view.findViewById(R.id.tvPostTransmission);

        tvMakeID = (TextView) view.findViewById(R.id.tvMakeID);
        tvModelID = (TextView) view.findViewById(R.id.tvModelID);
        tvFuelID = (TextView) view.findViewById(R.id.tvFuelID);
        tvBodyTypeID = (TextView) view.findViewById(R.id.tvBodyTypeID);
        tvVariantID = (TextView) view.findViewById(R.id.tvVariantID);
        tvDoorsID = (TextView) view.findViewById(R.id.tvDoorsID);
        tvYearID = (TextView) view.findViewById(R.id.tvYearID);
        tvDefectID = (TextView) view.findViewById(R.id.tvDefektID);
        tvAccidentID = (TextView) view.findViewById(R.id.tvAccidentID);
        tvTransmissionID = (TextView) view.findViewById(R.id.tvTransmissionID);
        tvKmID = (TextView) view.findViewById(R.id.tvKmID);
        tvPowerID = (TextView) view.findViewById(R.id.tvPowerID);
        tvPriceID = (TextView) view.findViewById(R.id.tvPriceID);

        etKm = (EditText)view.findViewById(R.id.etKm);
        etPower = (EditText)view.findViewById(R.id.etPower);
        etPrice = (EditText) view.findViewById(R.id.etPrice);

        checkNegotiable = (CheckBox)view.findViewById(R.id.checkNegotiable);

        SQLiteDatabase objDbStart = (new LocalDatabase(getContext())).getReadableDatabase();

        if(PostActivity.post.getMakeID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblMake where mID=="+ PostActivity.post.getMakeID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvMake.setText(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        } else
        {
            if(PostHomeFragment.clicked) {
                tvMake.setTextColor(getResources().getColor(R.color.colorAccent));
                tvMakeID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }
        if(PostActivity.post.getModelID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblModel where modID=="+ PostActivity.post.getModelID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvModel.setText(c.getString(2));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        }else
        {
            if(PostHomeFragment.clicked) {
                tvModel.setTextColor(getResources().getColor(R.color.colorAccent));
                tvModelID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getFuelID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblFuel where fID=="+ PostActivity.post.getFuelID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvFuel.setText(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        }else
        {
            if(PostHomeFragment.clicked) {
                tvFuel.setTextColor(getResources().getColor(R.color.colorAccent));
                tvFuelID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getCarTypeID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblBody where bodyID=="+ PostActivity.post.getCarTypeID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvBodyType.setText(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        }else
        {
            if(PostHomeFragment.clicked) {
                tvBodyType.setTextColor(getResources().getColor(R.color.colorAccent));
                tvBodyTypeID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getVariantID()!=0){
            Cursor c = objDbStart.rawQuery("Select * from tblVariant where vID=="+ PostActivity.post.getVariantID(), null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int i = 0; i < c.getCount(); i++) {
                        tvVariant.setText(c.getString(3));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
        }

        if(PostActivity.post.getNrDoors()!=0) {
            List<String> doors = Arrays.asList(getResources().getStringArray(R.array.door));
            tvDoors.setText(doors.get(PostActivity.post.getNrDoors()));
        } else {
            if(PostHomeFragment.clicked) {
                tvDoors.setTextColor(getResources().getColor(R.color.colorAccent));
                tvDoorsID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getPrice()!=0) {
            etPrice.setText(String.valueOf(PostActivity.post.getPrice()));
        } else {
            if(PostHomeFragment.clicked) {
                etPrice.setTextColor(getResources().getColor(R.color.colorAccent));
                tvPriceID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getFirstRegistration()!=null) {
            tvYear.setText(PostActivity.post.getFirstRegistration());
        } else{
                if(PostHomeFragment.clicked) {
                    tvYear.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvYearID.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }

        if(PostActivity.post.getKm()!=null) {
            etKm.setText(PostActivity.post.getKm());
        }else
        {
            if(PostHomeFragment.clicked) {
                etKm.setTextColor(getResources().getColor(R.color.colorAccent));
                tvKmID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getDefect()!=0) {
            List<String> defect = Arrays.asList(getResources().getStringArray(R.array.options));
            tvDefect.setText(defect.get(PostActivity.post.getDefect()));
        }

        if(PostActivity.post.getAccident()!=0){
            List<String> accident = Arrays.asList(getResources().getStringArray(R.array.options));
            tvAccident.setText(accident.get(PostActivity.post.getAccident()));
        }

        if(PostActivity.post.getTransmission()!=0) {
            List<String> transmission = Arrays.asList(getResources().getStringArray(R.array.transmission));
            tvTransmission.setText(transmission.get(PostActivity.post.getTransmission()));
        }
        else
        {
            if(PostHomeFragment.clicked) {
                tvTransmission.setTextColor(getResources().getColor(R.color.colorAccent));
                tvTransmissionID.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        if(PostActivity.post.getPower()!=null)
            etPower.setText(PostActivity.post.getPower());

        if(PostActivity.post.isNegotiable())
            checkNegotiable.setChecked(true);
        else checkNegotiable.setChecked(false);

        etKm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!etKm.getText().toString().trim().isEmpty())
                    if(!hasFocus)
                        PostActivity.post.setKm(etKm.getText().toString().trim());
            }
        });

        etPower.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!etPower.getText().toString().trim().isEmpty())
                    if(!hasFocus)
                        PostActivity.post.setPower(etPower.getText().toString().trim());
            }
        });

        etPower.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                PostActivity.post.setPower(etPower.getText().toString().trim());
                return false;
            }
        });

       etPrice.setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (!etPrice.getText().toString().trim().isEmpty())
                   PostActivity.post.setPrice(Long.parseLong(etPrice.getText().toString()));
               else
                   PostActivity.post.setPrice(0);
               return false;
           }
       });

        checkNegotiable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNegotiable.isChecked())
                    PostActivity.post.setNegotiable(true);
                else if(!checkNegotiable.isChecked())
                    PostActivity.post.setNegotiable(false);
            }
        });

        postMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(0);
            }
        });

        postModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PostActivity.post.getMakeID()==0)
                    Toast.makeText(getContext(), "You must first choose the make!",Toast.LENGTH_LONG).show();
                else
                    postDialog(1);
            }
        });

        postFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(2);
            }
        });

        postVariant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PostActivity.post.getFuelID()==0 || PostActivity.post.getModelID()==0)
                    Toast.makeText(getContext(), "You must first choose the model!",Toast.LENGTH_LONG).show();
                else
                    postDialog(3);
            }
        });

        postBodyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(4);
            }
        });

        postDoors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(5);
            }
        });

        postDefect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(8);
            }
        });

        postAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(9);
            }
        });

        postTransmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDialog(10);
            }
        });

        postYear.setOnClickListener(new View.OnClickListener() {
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
                try {
                    ((ViewGroup) dialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                }catch (NullPointerException n){}
                dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mRegistrationDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String yr = String.valueOf(month+1)+"/"+String.valueOf(year);
                tvYear.setText(yr);
                PostActivity.post.setFirstRegistration(yr);
            }
        };

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void postDialog(int i) {
        element = i;
        dialogPicker = new Dialog(getContext());
        dialogPicker.setContentView(R.layout.dialog_post);
        dialogPicker.setTitle(" ");
        dialogPicker.getWindow().setLayout((width - 100), (width - 100));

        TextView tvTitle = (TextView) dialogPicker.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) dialogPicker.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPicker.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) dialogPicker.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));


            SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
            Cursor c = null;

            dialogItems = new ArrayList<>();
            int[] dbStringIndex = {1, 2, 1, 3, 1};

            if (i == 0) {
                tvTitle.setText("Make");
                c = objDb.rawQuery("Select * from tblMake", null);
            } else if (i == 1) {
                tvTitle.setText("Model");
                c = objDb.rawQuery("Select * from tblModel where mID=="+ PostActivity.post.getMakeID(), null);
            } else if (i == 2) {
                tvTitle.setText("Fuel");
                c = objDb.rawQuery("Select * from tblFuel", null);
            } else if (i == 3) {
                tvTitle.setText("Variant of model");
                c = objDb.rawQuery("Select * from tblVariant where modID=="+ PostActivity.post.getModelID()
                        +" and fID=="+ PostActivity.post.getFuelID(), null);
            } else if (i == 4) {
                tvTitle.setText("Body Type");
                c = objDb.rawQuery("Select * from tblBody", null);
            } else if (i == 5) {
                tvTitle.setText("Number of doors");
                dialogItems = Arrays.asList(getResources().getStringArray(R.array.door));
            } else if (i == 8) {
                tvTitle.setText("Defective cars?");
                dialogItems = Arrays.asList(getResources().getStringArray(R.array.options));
            } else if (i == 9) {
                tvTitle.setText("Accident car");
                dialogItems = Arrays.asList(getResources().getStringArray(R.array.options));
            } else if (i == 10) {
                tvTitle.setText("Transmission");
                dialogItems = Arrays.asList(getResources().getStringArray(R.array.transmission));
            }
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int j = 0; j < c.getCount(); j++) {
                        dialogItems.add(c.getString(dbStringIndex[i]));
                        c.moveToNext();
                    }
                    c.close();
                }
            }
            dialogAdapter = new DialogPostAdapter(dialogItems, getContext(), this);
            recyclerDialog.setAdapter(dialogAdapter);
            dialogPicker.show();
    }

    @Override
    public void onDialogItemClick(int position) {

        if(element ==0)
        {
            PostActivity.post.setMakeID(position + 1);
            PostActivity.post.setModelID(0);
            PostActivity.post.setVariantID(0);
            tvMake.setText(dialogItems.get(position));
            tvModel.setText("Select model");
            tvVariant.setText("Select variant");
        }
        else if(element ==1) {
            PostActivity.post.setModelID(position + 1);
            PostActivity.post.setTitle(dialogItems.get(position));
            tvModel.setText(dialogItems.get(position));
            tvVariant.setText("Select variant");
        }
        else if(element ==2) {
            PostActivity.post.setFuelID(position + 1);
            PostActivity.post.setTitle(PostActivity.post.getTitle()+" "+dialogItems.get(position));
            tvFuel.setText(dialogItems.get(position));
            tvVariant.setText("Select variant");
        }
        else if(element ==3) {
            PostActivity.post.setVariantID(position + 1);
            PostActivity.post.setTitle(PostActivity.post.getTitle()+", "+dialogItems.get(position));
            tvVariant.setText(dialogItems.get(position));
        }
        else if(element ==4) {
            PostActivity.post.setCarTypeID(position + 1);
            tvBodyType.setText(dialogItems.get(position));
        }
        else if(element ==5) {
            PostActivity.post.setNrDoors(position);
            tvDoors.setText(dialogItems.get(position));
        }
        else if(element ==8) {
            PostActivity.post.setDefect(position);
            tvDefect.setText(dialogItems.get(position));
        }
        else if(element ==9) {
            PostActivity.post.setAccident(position);
            tvAccident.setText(dialogItems.get(position));
        }
        else if(element ==10) {
            PostActivity.post.setTransmission(position);
            tvTransmission.setText(dialogItems.get(position));
        }

        dialogPicker.dismiss();
    }

}
