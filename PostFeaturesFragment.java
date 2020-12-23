package Fragments;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.DialogCheckAdapter;
import Adapters.DialogPostAdapter;
import com.ken.cars.PostActivity;
import com.ken.cars.R;

public class PostFeaturesFragment extends Fragment implements DialogPostAdapter.OnDialogListener, DialogCheckAdapter.OnCheckListener {

    CheckBox checkBluetooth, checkComputer, checkCD, checkWindows, checkElectricSeats, checkHeatedSeats, checkSportSeats,
            checkMP3, checkAux, checkWheel, checkNav, checkPanorama, checkTrunk, checkCentralClosing,
            checkMirrors, checkAmortisation, checkSportPacket, checkAbs, check4x4,  checkEsp, checkAdaptingLight,
            checkLightSensor, checkFog, checkXenon, checkBiXenon, checkRainSensor, checkStartStop;

    TextView tvAirConditioner, tvAirbag;

    LinearLayout airConditioner, airbag;
    int width, height;
    int btnID;
    Dialog pickerDialog;

    List<String> dialogItems;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_features, container, false);
        ((PostActivity) getActivity()).getSupportActionBar().show();
        PostActivity.setTitle("Car features");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        airConditioner = (LinearLayout) view.findViewById(R.id.postClimatisation);
        airbag = (LinearLayout) view.findViewById(R.id.postAirbag);

        airConditioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 1;
                klima();
            }
        });

        airbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                airbag();
            }
        });

        tvAirConditioner = (TextView)view.findViewById(R.id.tvPostAirConditioner);
        tvAirbag = (TextView)view.findViewById(R.id.tvPostAirbag);

        checkBluetooth = (CheckBox)view.findViewById(R.id.checkBluetooth);
        checkComputer = (CheckBox)view.findViewById(R.id.checkComputer);
        checkCD = (CheckBox)view.findViewById(R.id.checkCD);
        checkWindows = (CheckBox)view.findViewById(R.id.checkWindows);
        checkElectricSeats = (CheckBox)view.findViewById(R.id.checkSeats);
        checkHeatedSeats = (CheckBox)view.findViewById(R.id.checkHeatedSeats);
        checkSportSeats = (CheckBox)view.findViewById(R.id.checkSportSeats);
        checkMP3 = (CheckBox)view.findViewById(R.id.checkMP3);
        checkAux = (CheckBox)view.findViewById(R.id.checkAux);
        checkWheel = (CheckBox)view.findViewById(R.id.checkWheel);
        checkNav = (CheckBox)view.findViewById(R.id.checkNav);
        checkPanorama = (CheckBox)view.findViewById(R.id.checkPanorama);
        checkTrunk = (CheckBox)view.findViewById(R.id.checkRack);
        checkCentralClosing = (CheckBox)view.findViewById(R.id.checkCentral);
        checkMirrors = (CheckBox)view.findViewById(R.id.checkMirrors);
        checkAmortisation = (CheckBox)view.findViewById(R.id.checkAmortisation);
        checkSportPacket = (CheckBox)view.findViewById(R.id.checkSportPacket);
        checkAbs = (CheckBox)view.findViewById(R.id.checkABS);
        check4x4 = (CheckBox)view.findViewById(R.id.check4x4);
        checkEsp = (CheckBox)view.findViewById(R.id.checkESP);
        checkAdaptingLight = (CheckBox)view.findViewById(R.id.checkLights);
        checkLightSensor = (CheckBox)view.findViewById(R.id.checkLightSensor);
        checkFog = (CheckBox)view.findViewById(R.id.checkFog);
        checkXenon = (CheckBox)view.findViewById(R.id.checkXenon);
        checkBiXenon = (CheckBox)view.findViewById(R.id.checkBiXenon);
        checkRainSensor = (CheckBox)view.findViewById(R.id.checkRain);
        checkStartStop = (CheckBox)view.findViewById(R.id.checkStartStop);

        checkBluetooth.setChecked(PostActivity.features.isBluetooth());
        checkComputer.setChecked(PostActivity.features.isOnBoardComputer());
        checkCD.setChecked(PostActivity.features.isCDPlayer());
        checkWindows.setChecked(PostActivity.features.isElectricWindows());
        checkElectricSeats.setChecked(PostActivity.features.isElectricSeats());
        checkHeatedSeats.setChecked(PostActivity.features.isHeatedSeats());
        checkSportSeats.setChecked(PostActivity.features.isSportSeats());
        checkMP3.setChecked(PostActivity.features.isMp3());
        checkAux.setChecked(PostActivity.features.isAux());
        checkWheel.setChecked(PostActivity.features.isSteeringWheel());
        checkNav.setChecked(PostActivity.features.isNavigation());
        checkPanorama.setChecked(PostActivity.features.isPanorama());
        checkTrunk.setChecked(PostActivity.features.isRoofRack());
        checkCentralClosing.setChecked(PostActivity.features.isCentralLocking());
        checkMirrors.setChecked(PostActivity.features.isElectricMirrors());
        checkAmortisation.setChecked(PostActivity.features.isSportAmortisation());
        checkSportPacket.setChecked(PostActivity.features.isSportPacket());
        checkAbs.setChecked(PostActivity.features.isAbs());
        check4x4.setChecked(PostActivity.features.isFourX4());
        checkEsp.setChecked(PostActivity.features.isEsp());
        checkAdaptingLight.setChecked(PostActivity.features.isAdaptiveLight());
        checkLightSensor.setChecked(PostActivity.features.isLightSensor());
        checkFog.setChecked(PostActivity.features.isFogLight());
        checkXenon.setChecked(PostActivity.features.isLightXenon());
        checkBiXenon.setChecked(PostActivity.features.isLightBiXenon());
        checkRainSensor.setChecked(PostActivity.features.isRainSensor());
        checkStartStop.setChecked(PostActivity.features.isStartStop());

        if(PostActivity.features.getAirConditioner()!=0) {
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.air_conditioner));
            tvAirConditioner.setText(dialogItems.get(PostActivity.features.getAirConditioner()));
        }
        String air = "";
        if(PostActivity.airbag.isAirbagDriver())
            air +="Driver Airbag";
        if(PostActivity.airbag.isAirbagSide())
            air +=", side airbag";
        if(PostActivity.airbag.isAirbagBack())
            air +=", back airbag";
        if(PostActivity.airbag.isAirbagOther())
            air +=" and other airbags";

        if(!air.isEmpty())
            tvAirbag.setText(air);

        checkBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setBluetooth(isChecked);
            }
        });

        checkComputer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setOnBoardComputer(isChecked);
            }
        });

        checkCD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setCDPlayer(isChecked);
            }
        });

        checkWindows.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setElectricWindows(isChecked);
            }
        });

        checkElectricSeats.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setElectricSeats(isChecked);
            }
        });

        checkHeatedSeats.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setHeatedSeats(isChecked);
            }
        });

        checkSportSeats.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setSportSeats(isChecked);
            }
        });

        checkMP3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setMp3(isChecked);
            }
        });

        checkAux.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setAux(isChecked);
            }
        });

        checkWheel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setSteeringWheel(isChecked);
            }
        });

        checkNav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setNavigation(isChecked);
            }
        });

        checkPanorama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setPanorama(isChecked);
            }
        });

        checkTrunk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setRoofRack(isChecked);
            }
        });

        checkCentralClosing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setCentralLocking(isChecked);
            }
        });

        checkMirrors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setElectricMirrors(isChecked);
            }
        });

        checkAmortisation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setSportAmortisation(isChecked);
            }
        });

        checkSportPacket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setSportPacket(isChecked);
            }
        });

        checkAbs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setAbs(isChecked);
            }
        });

        check4x4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setFourX4(isChecked);
            }
        });

        checkEsp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setEsp(isChecked);
            }
        });

        checkAdaptingLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setAdaptiveLight(isChecked);
            }
        });

        checkLightSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setLightSensor(isChecked);
            }
        });

        checkFog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setFogLight(isChecked);
            }
        });

        checkXenon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setLightXenon(isChecked);
            }
        });

        checkBiXenon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setLightBiXenon(isChecked);
            }
        });

        checkRainSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setRainSensor(isChecked);
            }
        });

        checkStartStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PostActivity.features.setStartStop(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void klima() {
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
        dialogItems = new ArrayList<>();
        tvTitle.setText("Air conditioner");
        dialogItems = Arrays.asList(getResources().getStringArray(R.array.air_conditioner));
        DialogPostAdapter dialogAdapter = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapter);
        pickerDialog.show();
    }

    private void airbag() {
        pickerDialog = new Dialog(getContext());
        pickerDialog.setContentView(R.layout.dialog_post);
        pickerDialog.setTitle(" ");
        pickerDialog.getWindow().setLayout((width - 100), (width - 100));

        TextView tvTitulli = (TextView) pickerDialog.findViewById(R.id.tvPostDialog);
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
        dialogItems = new ArrayList<>();
        tvTitulli.setText("Airbag");
        dialogItems = Arrays.asList(getResources().getStringArray(R.array.airbag));
        DialogCheckAdapter dialogCheckAdapter = new DialogCheckAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogCheckAdapter);

        String air = "";
        if(PostActivity.airbag.isAirbagDriver())
            air +="Driver Airbag";
        if(PostActivity.airbag.isAirbagSide())
            air +=", side airbag";
        if(PostActivity.airbag.isAirbagBack())
            air +=", back airbag";
        if(PostActivity.airbag.isAirbagOther())
            air +=" and other airbags";

        if(!air.isEmpty())
            tvAirbag.setText(air);

        pickerDialog.show();


    }

    @Override
    public void onDialogItemClick(int position) {
            PostActivity.features.setAirConditioner(position);
            tvAirConditioner.setText(dialogItems.get(position));
            pickerDialog.dismiss();

    }

    @Override
    public void onDialogCheckClick(int position) {


    }
}
