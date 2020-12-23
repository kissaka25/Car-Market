package Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.DialogCheckAdapter;
import Adapters.DialogPostAdapter;
import Adapters.SearchCheckAdapter;

import com.ken.cars.R;

public class AdvancedSearchFragment extends Fragment implements DialogCheckAdapter.OnCheckListener, DialogPostAdapter.OnDialogListener, SearchCheckAdapter.OnCheckListener {

    int width, height;
    List<String> dialogItems;
    boolean[] checked;
    int btnID;

    LinearLayout advancedType, advancedSeatsNr, advancedDoorsNr, advancedTransmission, advancedColor, advancedClimatisation,
            advancedInterior, advancedSecurity, advancedAirbag, advancedParking, advancedSellerType, advancedOwners, advancedDamaged;
    Dialog checkDialog, radioDialog;
    TextView tvVehicleType, tvSeatsNr, tvDoorsNr, tvTransmission, tvColor,tvClimatisation, tvInterior, tvSecurity, tvAirbag, tvParking,
            tvSeller, tvSellerNr, tvDamaged;
    CheckBox checkMetallic, matte;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        advancedType = (LinearLayout)v.findViewById(R.id.advancedVehicleType);
        advancedSeatsNr = (LinearLayout)v.findViewById(R.id.advancedSeatsNr);
        advancedDoorsNr = (LinearLayout)v.findViewById(R.id.advancedDoorsNr);
        advancedTransmission = (LinearLayout)v.findViewById(R.id.advancedTransmission);
        advancedColor = (LinearLayout)v.findViewById(R.id.advancedColor);
        advancedClimatisation = (LinearLayout)v.findViewById(R.id.advancedClimatisation);
        advancedInterior = (LinearLayout)v.findViewById(R.id.advancedInterior);
        advancedSecurity = (LinearLayout)v.findViewById(R.id.advancedSecurity);
        advancedAirbag = (LinearLayout)v.findViewById(R.id.advancedAirbag);
        advancedParking = (LinearLayout)v.findViewById(R.id.advancedParking);
        advancedSellerType = (LinearLayout)v.findViewById(R.id.advancedSellerType);
        advancedOwners = (LinearLayout)v.findViewById(R.id.advancedOwnerNr);
        advancedDamaged = (LinearLayout)v.findViewById(R.id.advancedDamaged);

        tvVehicleType = (TextView)v.findViewById(R.id.tvType);
        tvSeatsNr = (TextView)v.findViewById(R.id.tvSeatsNr);
        tvDoorsNr = (TextView)v.findViewById(R.id.tvDoors);
        tvColor = (TextView)v.findViewById(R.id.tvColor);
        tvClimatisation = (TextView)v.findViewById(R.id.tvClimatisation);
        tvInterior = (TextView)v.findViewById(R.id.tvInterior);
        tvSecurity = (TextView)v.findViewById(R.id.tvSecurity);
        tvAirbag = (TextView)v.findViewById(R.id.tvAdvancedAirbag);
        tvParking = (TextView)v.findViewById(R.id.tvParking);
        tvSeller = (TextView)v.findViewById(R.id.tvSeller);
        tvSellerNr = (TextView)v.findViewById(R.id.tvOwners);
        tvDamaged = (TextView)v.findViewById(R.id.tvDamaged);
        tvTransmission = (TextView)v.findViewById(R.id.tvTransmission);

        checkMetallic = (CheckBox) v.findViewById(R.id.advancedMetallic);
        matte = (CheckBox)v.findViewById(R.id.advancedMatte);

        checkMetallic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SearchFragment.searchConditions.setMetallic(isChecked);
            }
        });

        matte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SearchFragment.searchConditions.setMat(isChecked);
            }
        });

        initValues();

        advancedType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog(1);
            }
        });

        advancedSeatsNr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog(2);
            }
        });

        advancedDoorsNr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog(3);
            }
        });

        advancedTransmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog(4);
            }
        });

        advancedColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog(5);
            }
        });

        advancedClimatisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog(6);
            }
        });

        advancedInterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog(7);
            }
        });

        advancedSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog(8);
            }
        });

        advancedAirbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog(9);
            }
        });

        advancedParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog(10);
            }
        });

        advancedSellerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog(11);
            }
        });

        advancedOwners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog(12);
            }
        });

        advancedDamaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog(13);
            }
        });



        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void checkDialog(int i){
        btnID = i;
        checkDialog = new Dialog(getContext());
        checkDialog.setContentView(R.layout.dialog_post);
        checkDialog.setTitle(" ");
        checkDialog.getWindow().setLayout((width - 100), (height - 300));

        TextView tvTitle = (TextView) checkDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) checkDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) checkDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogItems = new ArrayList<>();
        if(btnID==1){
            tvTitle.setText("Car Type");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.body_type));
            checked = new boolean[dialogItems.size()];
            checked[0] = SearchFragment.searchConditions.getVehicle().isSedan();
            checked[1] = SearchFragment.searchConditions.getVehicle().isCaravan();
            checked[2] = SearchFragment.searchConditions.getVehicle().isConvertible();
            checked[3] = SearchFragment.searchConditions.getVehicle().isOffroad();
            checked[4] = SearchFragment.searchConditions.getVehicle().isSmall();
            checked[5] = SearchFragment.searchConditions.getVehicle().isSportive();
        } else if(btnID==4){
            tvTitle.setText("Transmission");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.transmission));
            checked = new boolean[dialogItems.size()];
            checked[0] = SearchFragment.searchConditions.getTransmission().isUnspecified();
            checked[1] = SearchFragment.searchConditions.getTransmission().isManual();
            checked[2] = SearchFragment.searchConditions.getTransmission().isHalfAutomatic();
            checked[3] = SearchFragment.searchConditions.getTransmission().isAutomatic();
            checkDialog.getWindow().setLayout((width - 100), (width - 100));
        } else  if(btnID==5){
            tvTitle.setText("Color");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.colors));
            checked = new boolean[dialogItems.size()];
            checked[0] = SearchFragment.searchConditions.getColorSearch().isUnspecified();
            checked[1] = SearchFragment.searchConditions.getColorSearch().isWhite();
            checked[2] = SearchFragment.searchConditions.getColorSearch().isGreen();
            checked[3] = SearchFragment.searchConditions.getColorSearch().isGrey();
            checked[4] = SearchFragment.searchConditions.getColorSearch().isBlue();
            checked[5] = SearchFragment.searchConditions.getColorSearch().isRed();
            checked[6] = SearchFragment.searchConditions.getColorSearch().isOrange();
            checked[7] = SearchFragment.searchConditions.getColorSearch().isYellow();
            checked[8] = SearchFragment.searchConditions.getColorSearch().isBlack();
            checked[9] = SearchFragment.searchConditions.getColorSearch().isPurple();
            checked[10] = SearchFragment.searchConditions.getColorSearch().isOther();
        } else if(btnID==7){
            tvTitle.setText("Interior");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.interior_features));
            checked = new boolean[dialogItems.size()];
            checked[0] = SearchFragment.searchConditions.getInteriorSearch().isBluetooth();
            checked[1] = SearchFragment.searchConditions.getInteriorSearch().isOnBoardComputer();
            checked[2] = SearchFragment.searchConditions.getInteriorSearch().isCdPlayer();
            checked[3] = SearchFragment.searchConditions.getInteriorSearch().isElectricWindow();
            checked[4] = SearchFragment.searchConditions.getInteriorSearch().isElectricSeats();
            checked[5] = SearchFragment.searchConditions.getInteriorSearch().isHeatedSeats();
            checked[6] = SearchFragment.searchConditions.getInteriorSearch().isSportSeats();
            checked[7] = SearchFragment.searchConditions.getInteriorSearch().isMp3();
            checked[8] = SearchFragment.searchConditions.getInteriorSearch().isAux();
            checked[9] = SearchFragment.searchConditions.getInteriorSearch().isSteeringWheelButtons();
            checked[10] = SearchFragment.searchConditions.getInteriorSearch().isNav();
            //checked[11] = SearchFragment.searchConditions.getInteriorSearch().isShiber();
            checked[11] = SearchFragment.searchConditions.getInteriorSearch().isPanoramic();
            checked[12] = SearchFragment.searchConditions.getInteriorSearch().isRoofRack();
            checked[13] = SearchFragment.searchConditions.getInteriorSearch().isCentralCloser();
        } else if(btnID==8){
            tvTitle.setText("Security and environment");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.security));
            checked = new boolean[dialogItems.size()];
            checked[0] = SearchFragment.searchConditions.getSecurity().isAbs();
            checked[1] = SearchFragment.searchConditions.getSecurity().isFourX4();
            checked[2] = SearchFragment.searchConditions.getSecurity().isEsp();
            checked[3] = SearchFragment.searchConditions.getSecurity().isAdaptingLights();
            checked[4] = SearchFragment.searchConditions.getSecurity().isLightsSensor();
            checked[5] = SearchFragment.searchConditions.getSecurity().isXenonHeadlights();
            checked[6] = SearchFragment.searchConditions.getSecurity().isBiXenonHeadlights();
            checked[7] = SearchFragment.searchConditions.getSecurity().isRainSensor();
            checked[8] = SearchFragment.searchConditions.getSecurity().isStartStopSensor();
        } else if(btnID==9){
            tvTitle.setText("Airbag");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.airbag));
            checked = new boolean[dialogItems.size()];
            checked[0] = SearchFragment.searchConditions.getAirbag().isAirbagDriver();
            checked[1] = SearchFragment.searchConditions.getAirbag().isAirbagSide();
            checked[2] = SearchFragment.searchConditions.getAirbag().isAirbagBack();
            checked[3] = SearchFragment.searchConditions.getAirbag().isAirbagOther();
            checkDialog.getWindow().setLayout(width-100, width-100);
        } else if(btnID==10){
            tvTitle.setText("Parking");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.parking));
            checked = new boolean[dialogItems.size()];
            checked[0] = SearchFragment.searchConditions.getParking().isParkingSensors();
            checked[1] = SearchFragment.searchConditions.getParking().isCamera();
            checked[2] = SearchFragment.searchConditions.getParking().isAutonomeSystem();
            checkDialog.getWindow().setLayout(width-100, width-100);
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.parking));
        }

        SearchCheckAdapter dialogCheckAdapter = new SearchCheckAdapter(dialogItems, getContext(), this, checked);
        recyclerDialog.setAdapter(dialogCheckAdapter);
        checkDialog.show();

    }

    private void radioDialog(int i){

        btnID=i;
        radioDialog = new Dialog(getContext());
        radioDialog.setContentView(R.layout.dialog_post);
        radioDialog.setTitle(" ");
        radioDialog.getWindow().setLayout((width - 100), (width - 100));

        TextView tvTitle = (TextView) radioDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) radioDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) radioDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogItems=new ArrayList<>();

        if(i==2){
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.seats));
            tvTitle.setText("Number of seats");
        } else if(i==3){
            tvTitle.setText("Number of doors");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.door_search));
        } else if(i==6){
            tvTitle.setText("Air conditioner");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.air_conditioner));
        } else if(i==11){
            tvTitle.setText("Type of seller");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.seller));
        } else if(i==12){
            tvTitle.setText("Number of owners");
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.owners));
        } else if(i==13){
            tvTitle.setText("Defective cars");
            dialogItems = new ArrayList<String>();
            dialogItems.add("Show");
            dialogItems.add("Do not show");
        }

        DialogPostAdapter adapter = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(adapter);
        radioDialog.show();



    }


    private void initValues(){
        if(SearchFragment.searchConditions.getSeatsNr()!=0){
            List<String> items = Arrays.asList(getResources().getStringArray(R.array.seats));
            tvSeatsNr.setText(items.get(SearchFragment.searchConditions.getSeatsNr()));
        }

        if(SearchFragment.searchConditions.getDoorsNr()!=0){
            List<String> items = Arrays.asList(getResources().getStringArray(R.array.door_search));
            tvDoorsNr.setText(items.get(SearchFragment.searchConditions.getDoorsNr()));
        }

        if(SearchFragment.searchConditions.getClimatissation()!=0){
            List<String> items = Arrays.asList(getResources().getStringArray(R.array.air_conditioner));
            tvClimatisation.setText(items.get(SearchFragment.searchConditions.getClimatissation()));
        }

        if(SearchFragment.searchConditions.getSellerType()!=0){
            List<String> items = Arrays.asList(getResources().getStringArray(R.array.seller));
            tvSeller.setText(items.get(SearchFragment.searchConditions.getSellerType()));
        }

        if(SearchFragment.searchConditions.getOwners()!=0){
            List<String> items = Arrays.asList(getResources().getStringArray(R.array.owners));
            tvSellerNr.setText(items.get(SearchFragment.searchConditions.getOwners()));
        }

        if(SearchFragment.searchConditions.getDamaged()==0){
            tvDamaged.setText("Show");
        }

        if(SearchFragment.searchConditions.getDamaged()==1){
            tvDamaged.setText("Do not show");
        }


        String text = "";
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.body_type));
        if(SearchFragment.searchConditions.getVehicle().isSedan())
            text+=items.get(0);
        if(SearchFragment.searchConditions.getVehicle().isCaravan())
            text+=" - "+items.get(1);
        if(SearchFragment.searchConditions.getVehicle().isConvertible())
            text+=" - "+items.get(2);
        if(SearchFragment.searchConditions.getVehicle().isOffroad())
            text+=" - "+items.get(3);
        if(SearchFragment.searchConditions.getVehicle().isSmall())
            text+=" - "+items.get(4);
        tvVehicleType.setText(text);

        items = Arrays.asList(getResources().getStringArray(R.array.transmission));
        text = "";
        if(SearchFragment.searchConditions.getTransmission().isUnspecified())
            text+=items.get(0);
        if(SearchFragment.searchConditions.getTransmission().isManual())
            text+=" - "+items.get(1);
        if(SearchFragment.searchConditions.getTransmission().isHalfAutomatic())
            text+=" - "+items.get(2);
        if(SearchFragment.searchConditions.getTransmission().isAutomatic())
            text+=" - "+items.get(3);
        tvTransmission.setText(text);

        items = Arrays.asList(getResources().getStringArray(R.array.colors));
        text = "";
        if(SearchFragment.searchConditions.getColorSearch().isUnspecified())
            text+=items.get(0);
        if(SearchFragment.searchConditions.getColorSearch().isWhite())
            text+=" - "+items.get(1);
        if(SearchFragment.searchConditions.getColorSearch().isGreen())
            text+=" - "+items.get(2);
        if(SearchFragment.searchConditions.getColorSearch().isGrey())
            text+=" - "+items.get(3);
        if(SearchFragment.searchConditions.getColorSearch().isBlue())
            text+=" - "+items.get(4);
        if(SearchFragment.searchConditions.getColorSearch().isRed())
            text+=" - "+items.get(5);
        if(SearchFragment.searchConditions.getColorSearch().isOrange())
            text+=" - "+items.get(6);
        if(SearchFragment.searchConditions.getColorSearch().isYellow())
            text+=" - "+items.get(7);
        if(SearchFragment.searchConditions.getColorSearch().isBlack())
            text+=" - "+items.get(8);
        if(SearchFragment.searchConditions.getColorSearch().isPurple())
            text+=" - "+items.get(9);
        if(SearchFragment.searchConditions.getColorSearch().isOther())
            text+=" - "+items.get(10);
        tvColor.setText(text);


        items = Arrays.asList(getResources().getStringArray(R.array.interior_features));
        text = "";
        if(SearchFragment.searchConditions.getInteriorSearch().isBluetooth())
            text+=items.get(0);
        if(SearchFragment.searchConditions.getInteriorSearch().isOnBoardComputer())
            text+=" - "+items.get(1);
        if(SearchFragment.searchConditions.getInteriorSearch().isCdPlayer())
            text+=" - "+items.get(2);
        if(SearchFragment.searchConditions.getInteriorSearch().isElectricWindow())
            text+=" - "+items.get(3);
        if(SearchFragment.searchConditions.getInteriorSearch().isElectricSeats())
            text+=" - "+items.get(4);
        if(SearchFragment.searchConditions.getInteriorSearch().isHeatedSeats())
            text+=" - "+items.get(5);
        if(SearchFragment.searchConditions.getInteriorSearch().isSportSeats())
            text+=" - "+items.get(6);
        if(SearchFragment.searchConditions.getInteriorSearch().isMp3())
            text+=" - "+items.get(7);
        if(SearchFragment.searchConditions.getInteriorSearch().isAux())
            text+=" - "+items.get(8);
        if(SearchFragment.searchConditions.getInteriorSearch().isSteeringWheelButtons())
            text+=" - "+items.get(9);
        if(SearchFragment.searchConditions.getInteriorSearch().isNav())
            text+=" - "+items.get(10);
        if(SearchFragment.searchConditions.getInteriorSearch().isPanoramic())
            text+=" - "+items.get(11);
        if(SearchFragment.searchConditions.getInteriorSearch().isRoofRack())
            text+=" - "+items.get(12);
        if(SearchFragment.searchConditions.getInteriorSearch().isCentralCloser())
            text+=" - "+items.get(13);
        tvInterior.setText(text);

        items = Arrays.asList(getResources().getStringArray(R.array.security));
        text = "";
        if(SearchFragment.searchConditions.getSecurity().isAbs())
            text+=items.get(0);
        if(SearchFragment.searchConditions.getSecurity().isFourX4())
            text+=" - "+items.get(1);
        if(SearchFragment.searchConditions.getSecurity().isEsp())
            text+=" - "+items.get(2);
        if(SearchFragment.searchConditions.getSecurity().isAdaptingLights())
            text+=" - "+items.get(3);
        if(SearchFragment.searchConditions.getSecurity().isLightsSensor())
            text+=" - "+items.get(4);
        if(SearchFragment.searchConditions.getSecurity().isXenonHeadlights())
            text+=" - "+items.get(5);
        if(SearchFragment.searchConditions.getSecurity().isBiXenonHeadlights())
            text+=" - "+items.get(6);
        if(SearchFragment.searchConditions.getSecurity().isRainSensor())
            text+=" - "+items.get(7);
        if(SearchFragment.searchConditions.getSecurity().isStartStopSensor())
            text+=" - "+items.get(8);
        tvSecurity.setText(text);



        items = Arrays.asList(getResources().getStringArray(R.array.airbag));
        text = " ";
        if(SearchFragment.searchConditions.getAirbag().isAirbagDriver())
            text+=items.get(0);
        if(SearchFragment.searchConditions.getAirbag().isAirbagSide())
            text+=" - "+items.get(1);
        if(SearchFragment.searchConditions.getAirbag().isAirbagBack())
            text+=" - "+items.get(2);
        if(SearchFragment.searchConditions.getAirbag().isAirbagOther())
            text+=" - "+items.get(3);
        tvAirbag.setText(text);



        items = Arrays.asList(getResources().getStringArray(R.array.parking));
        text = "";
        if(SearchFragment.searchConditions.getParking().isParkingSensors())
            text+=items.get(0);
        if(SearchFragment.searchConditions.getParking().isCamera())
            text+=" - "+items.get(1);
        if(SearchFragment.searchConditions.getParking().isAutonomeSystem())
            text+=" - "+items.get(2);
        tvParking.setText(text);

    }



    @Override
    public void onDialogCheckClick(int position) {

    }

    @Override
    public void onDialogItemClick(int position) {
        if(btnID==2){
            SearchFragment.searchConditions.setSeatsNr(position);
            tvSeatsNr.setText(dialogItems.get(position));
        } else if(btnID==3){
            SearchFragment.searchConditions.setDoorsNr(position);
            tvDoorsNr.setText(dialogItems.get(position));
        } else if(btnID==6){
            SearchFragment.searchConditions.setClimatissation(position);
            tvClimatisation.setText(dialogItems.get(position));
        } else if(btnID==11){
            SearchFragment.searchConditions.setSellerType(position);
            tvSeller.setText(dialogItems.get(position));
        } else if(btnID==12){
            SearchFragment.searchConditions.setOwners(position);
            tvSellerNr.setText(dialogItems.get(position));
        } else if(btnID==13){
            SearchFragment.searchConditions.setDamaged(position);
            tvDamaged.setText(dialogItems.get(position));
        }
        radioDialog.dismiss();
    }

    @Override
    public void onSearchCheck(int position) {
        if(btnID==1){
            if(position==0){
                SearchFragment.searchConditions.getVehicle().setSedan(!SearchFragment.searchConditions.getVehicle().isSedan());
            }else if(position==1){
                SearchFragment.searchConditions.getVehicle().setCaravan(!SearchFragment.searchConditions.getVehicle().isCaravan());
            }else if(position==2){
                SearchFragment.searchConditions.getVehicle().setConvertible(!SearchFragment.searchConditions.getVehicle().isConvertible());
            }else if(position==3){
                SearchFragment.searchConditions.getVehicle().setOffroad(!SearchFragment.searchConditions.getVehicle().isOffroad());
            }else if(position==4){
                SearchFragment.searchConditions.getVehicle().setSmall(!SearchFragment.searchConditions.getVehicle().isSmall());
            }

            String text = "";
            if(SearchFragment.searchConditions.getVehicle().isSedan())
                text+=dialogItems.get(0);
            if(SearchFragment.searchConditions.getVehicle().isCaravan())
                text+=" - "+dialogItems.get(1);
            if(SearchFragment.searchConditions.getVehicle().isConvertible())
                text+=" - "+dialogItems.get(2);
            if(SearchFragment.searchConditions.getVehicle().isOffroad())
                text+=" - "+dialogItems.get(3);
            if(SearchFragment.searchConditions.getVehicle().isSmall())
                text+=" - "+dialogItems.get(4);
            tvVehicleType.setText(text);

        } else if(btnID==4){
            if(position==0){
                SearchFragment.searchConditions.getTransmission().setUnspecified(!SearchFragment.searchConditions.getTransmission().isUnspecified());
            }else if(position==1){
                SearchFragment.searchConditions.getTransmission().setManual(!SearchFragment.searchConditions.getTransmission().isManual());
            }else if(position==2){
                SearchFragment.searchConditions.getTransmission().setHalfAutomatic(!SearchFragment.searchConditions.getTransmission().isHalfAutomatic());
            }else if(position==3){
                SearchFragment.searchConditions.getTransmission().setAutomatic(!SearchFragment.searchConditions.getTransmission().isAutomatic());
            }

            String text = "";
            if(SearchFragment.searchConditions.getTransmission().isUnspecified())
                text+=dialogItems.get(0);
            if(SearchFragment.searchConditions.getTransmission().isManual())
                text+=" - "+dialogItems.get(1);
            if(SearchFragment.searchConditions.getTransmission().isHalfAutomatic())
                text+=" - "+dialogItems.get(2);
            if(SearchFragment.searchConditions.getTransmission().isAutomatic())
                text+=" - "+dialogItems.get(3);
            tvTransmission.setText(text);


        } else  if(btnID==5){
            if(position==0){
                SearchFragment.searchConditions.getColorSearch().setUnspecified(!SearchFragment.searchConditions.getColorSearch().isUnspecified());
            }else if(position==1){
                SearchFragment.searchConditions.getColorSearch().setWhite(!SearchFragment.searchConditions.getColorSearch().isWhite());
            }else if(position==2){
                SearchFragment.searchConditions.getColorSearch().setGreen(!SearchFragment.searchConditions.getColorSearch().isGreen());
            }else if(position==3){
                SearchFragment.searchConditions.getColorSearch().setGrey(!SearchFragment.searchConditions.getColorSearch().isGrey());
            }else if(position==4){
                SearchFragment.searchConditions.getColorSearch().setBlue(!SearchFragment.searchConditions.getColorSearch().isBlue());
            }else if(position==5){
                SearchFragment.searchConditions.getColorSearch().setRed(!SearchFragment.searchConditions.getColorSearch().isRed());
            }else if(position==6){
                SearchFragment.searchConditions.getColorSearch().setOrange(!SearchFragment.searchConditions.getColorSearch().isOrange());
            }else if(position==7){
                SearchFragment.searchConditions.getColorSearch().setYellow(!SearchFragment.searchConditions.getColorSearch().isYellow());
            }else if(position==8){
                SearchFragment.searchConditions.getColorSearch().setBlack(!SearchFragment.searchConditions.getColorSearch().isBlack());
            }else if(position==9){
                SearchFragment.searchConditions.getColorSearch().setPurple(!SearchFragment.searchConditions.getColorSearch().isPurple());
            }else if(position==10){
                SearchFragment.searchConditions.getColorSearch().setOther(!SearchFragment.searchConditions.getColorSearch().isOther());
            }

            String text = "";
            if(SearchFragment.searchConditions.getColorSearch().isUnspecified())
                text+=dialogItems.get(0);
            if(SearchFragment.searchConditions.getColorSearch().isWhite())
                text+=" - "+dialogItems.get(1);
            if(SearchFragment.searchConditions.getColorSearch().isGreen())
                text+=" - "+dialogItems.get(2);
            if(SearchFragment.searchConditions.getColorSearch().isGrey())
                text+=" - "+dialogItems.get(3);
            if(SearchFragment.searchConditions.getColorSearch().isBlue())
                text+=" - "+dialogItems.get(4);
            if(SearchFragment.searchConditions.getColorSearch().isRed())
                text+=" - "+dialogItems.get(5);
            if(SearchFragment.searchConditions.getColorSearch().isOrange())
                text+=" - "+dialogItems.get(6);
            if(SearchFragment.searchConditions.getColorSearch().isYellow())
                text+=" - "+dialogItems.get(7);
            if(SearchFragment.searchConditions.getColorSearch().isBlack())
                text+=" - "+dialogItems.get(8);
            if(SearchFragment.searchConditions.getColorSearch().isPurple())
                text+=" - "+dialogItems.get(9);
            if(SearchFragment.searchConditions.getColorSearch().isOther())
                text+=" - "+dialogItems.get(10);

            tvColor.setText(text);

        } else if(btnID==7){

            if(position==0){
                SearchFragment.searchConditions.getInteriorSearch().setBluetooth(!SearchFragment.searchConditions.getInteriorSearch().isBluetooth());
            }else if(position==1){
                SearchFragment.searchConditions.getInteriorSearch().setOnBoardComputer(!SearchFragment.searchConditions.getInteriorSearch().isOnBoardComputer());
            }else if(position==2){
                SearchFragment.searchConditions.getInteriorSearch().setCdPlayer(!SearchFragment.searchConditions.getInteriorSearch().isCdPlayer());
            }else if(position==3){
                SearchFragment.searchConditions.getInteriorSearch().setElectricWindow(!SearchFragment.searchConditions.getInteriorSearch().isElectricWindow());
            }else if(position==4){
                SearchFragment.searchConditions.getInteriorSearch().setElectricSeats(!SearchFragment.searchConditions.getInteriorSearch().isElectricSeats());
            }else if(position==5){
                SearchFragment.searchConditions.getInteriorSearch().setHeatedSeats(!SearchFragment.searchConditions.getInteriorSearch().isHeatedSeats());
            }else if(position==6){
                SearchFragment.searchConditions.getInteriorSearch().setSportSeats(!SearchFragment.searchConditions.getInteriorSearch().isSportSeats());
            }else if(position==7){
                SearchFragment.searchConditions.getInteriorSearch().setMp3(!SearchFragment.searchConditions.getInteriorSearch().isMp3());
            }else if(position==8){
                SearchFragment.searchConditions.getInteriorSearch().setAux(!SearchFragment.searchConditions.getInteriorSearch().isAux());
            }else if(position==9){
                SearchFragment.searchConditions.getInteriorSearch().setSteeringWheelButtons(!SearchFragment.searchConditions.getInteriorSearch().isSteeringWheelButtons());
            }else if(position==10){
                SearchFragment.searchConditions.getInteriorSearch().setNav(!SearchFragment.searchConditions.getInteriorSearch().isNav());
            }else if(position==11){
                SearchFragment.searchConditions.getInteriorSearch().setPanoramic(!SearchFragment.searchConditions.getInteriorSearch().isPanoramic());
            }else if(position==12){
                SearchFragment.searchConditions.getInteriorSearch().setRoofRack(!SearchFragment.searchConditions.getInteriorSearch().isRoofRack());
            }else if(position==13){
                SearchFragment.searchConditions.getInteriorSearch().setCentralCloser(!SearchFragment.searchConditions.getInteriorSearch().isCentralCloser());
            }

            String text = "";
            if(SearchFragment.searchConditions.getInteriorSearch().isBluetooth())
                text+=dialogItems.get(0);
            if(SearchFragment.searchConditions.getInteriorSearch().isOnBoardComputer())
                text+=" - "+dialogItems.get(1);
            if(SearchFragment.searchConditions.getInteriorSearch().isCdPlayer())
                text+=" - "+dialogItems.get(2);
            if(SearchFragment.searchConditions.getInteriorSearch().isElectricWindow())
                text+=" - "+dialogItems.get(3);
            if(SearchFragment.searchConditions.getInteriorSearch().isElectricSeats())
                text+=" - "+dialogItems.get(4);
            if(SearchFragment.searchConditions.getInteriorSearch().isHeatedSeats())
                text+=" - "+dialogItems.get(5);
            if(SearchFragment.searchConditions.getInteriorSearch().isSportSeats())
                text+=" - "+dialogItems.get(6);
            if(SearchFragment.searchConditions.getInteriorSearch().isMp3())
                text+=" - "+dialogItems.get(7);
            if(SearchFragment.searchConditions.getInteriorSearch().isAux())
                text+=" - "+dialogItems.get(8);
            if(SearchFragment.searchConditions.getInteriorSearch().isSteeringWheelButtons())
                text+=" - "+dialogItems.get(9);
            if(SearchFragment.searchConditions.getInteriorSearch().isNav())
                text+=" - "+dialogItems.get(10);
            if(SearchFragment.searchConditions.getInteriorSearch().isPanoramic())
                text+=" - "+dialogItems.get(11);
            if(SearchFragment.searchConditions.getInteriorSearch().isRoofRack())
                text+=" - "+dialogItems.get(12);
            if(SearchFragment.searchConditions.getInteriorSearch().isCentralCloser())
                text+=" - "+dialogItems.get(13);
            tvInterior.setText(text);

        } else if(btnID==8){
            if(position==0){
                SearchFragment.searchConditions.getSecurity().setAbs(!SearchFragment.searchConditions.getSecurity().isAbs());
            }else if(position==1){
                SearchFragment.searchConditions.getSecurity().setFourX4(!SearchFragment.searchConditions.getSecurity().isFourX4());
            }else if(position==2){
                SearchFragment.searchConditions.getSecurity().setEsp(!SearchFragment.searchConditions.getSecurity().isEsp());
            }else if(position==3){
                SearchFragment.searchConditions.getSecurity().setAdaptingLights(!SearchFragment.searchConditions.getSecurity().isAdaptingLights());
            }else if(position==4){
                SearchFragment.searchConditions.getSecurity().setLightsSensor(!SearchFragment.searchConditions.getSecurity().isLightsSensor());
            }else if(position==5){
                SearchFragment.searchConditions.getSecurity().setXenonHeadlights(!SearchFragment.searchConditions.getSecurity().isXenonHeadlights());
            }else if(position==6){
                SearchFragment.searchConditions.getSecurity().setBiXenonHeadlights(!SearchFragment.searchConditions.getSecurity().isBiXenonHeadlights());
            }else if(position==7){
                SearchFragment.searchConditions.getSecurity().setRainSensor(!SearchFragment.searchConditions.getSecurity().isRainSensor());
            }else if(position==8){
                SearchFragment.searchConditions.getSecurity().setStartStopSensor(!SearchFragment.searchConditions.getSecurity().isStartStopSensor());
            }

            String text = "";
            if(SearchFragment.searchConditions.getSecurity().isAbs())
                text+=dialogItems.get(0);
            if(SearchFragment.searchConditions.getSecurity().isFourX4())
                text+=" - "+dialogItems.get(1);
            if(SearchFragment.searchConditions.getSecurity().isEsp())
                text+=" - "+dialogItems.get(2);
            if(SearchFragment.searchConditions.getSecurity().isAdaptingLights())
                text+=" - "+dialogItems.get(3);
            if(SearchFragment.searchConditions.getSecurity().isLightsSensor())
                text+=" - "+dialogItems.get(4);
            if(SearchFragment.searchConditions.getSecurity().isXenonHeadlights())
                text+=" - "+dialogItems.get(5);
            if(SearchFragment.searchConditions.getSecurity().isBiXenonHeadlights())
                text+=" - "+dialogItems.get(6);
            if(SearchFragment.searchConditions.getSecurity().isRainSensor())
                text+=" - "+dialogItems.get(7);
            if(SearchFragment.searchConditions.getSecurity().isStartStopSensor())
                text+=" - "+dialogItems.get(8);
            tvSecurity.setText(text);

        } else if(btnID==9){
            if(position==0){
                SearchFragment.searchConditions.getAirbag().setAirbagDriver(!SearchFragment.searchConditions.getAirbag().isAirbagDriver());
            }else if(position==1){
                SearchFragment.searchConditions.getAirbag().setAirbagSide(!SearchFragment.searchConditions.getAirbag().isAirbagSide());
            }else if(position==2){
                SearchFragment.searchConditions.getAirbag().setAirbagBack(!SearchFragment.searchConditions.getAirbag().isAirbagBack());
            }else if(position==3){
                SearchFragment.searchConditions.getAirbag().setAirbagOther(!SearchFragment.searchConditions.getAirbag().isAirbagOther());
            }

            String text = " ";
            if(SearchFragment.searchConditions.getAirbag().isAirbagDriver())
                text+=dialogItems.get(0);
            if(SearchFragment.searchConditions.getAirbag().isAirbagSide())
                text+=" - "+dialogItems.get(1);
            if(SearchFragment.searchConditions.getAirbag().isAirbagBack())
                text+=" - "+dialogItems.get(2);
            if(SearchFragment.searchConditions.getAirbag().isAirbagOther())
                text+=" - "+dialogItems.get(3);
            tvAirbag.setText(text);

        } else if(btnID==10){
            if(position==0){
                SearchFragment.searchConditions.getParking().setParkingSensors(!SearchFragment.searchConditions.getParking().isParkingSensors());
            }else if(position==1){
                SearchFragment.searchConditions.getParking().setCamera(!SearchFragment.searchConditions.getParking().isCamera());
            }else if(position==2){
                SearchFragment.searchConditions.getParking().setAutonomeSystem(!SearchFragment.searchConditions.getParking().isAutonomeSystem());
            }

            String text = "";
            if(SearchFragment.searchConditions.getParking().isParkingSensors())
                text+=dialogItems.get(0);
            if(SearchFragment.searchConditions.getParking().isCamera())
                text+=" - "+dialogItems.get(1);
            if(SearchFragment.searchConditions.getParking().isAutonomeSystem())
                text+=" - "+dialogItems.get(2);
            tvParking.setText(text);
        }
    }
}
