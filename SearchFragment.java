package Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Database.LocalDatabase;
import Models.SearchConditions;
import Models.RangeSearch;
import Models.Post;
import Adapters.DialogPostAdapter;
import com.ken.cars.NonUserActivity;
import com.ken.cars.R;
import Adapters.SearchCheckAdapter;
import com.ken.cars.SearchResultActivity;
import com.ken.cars.UserActivity;

public class SearchFragment extends Fragment implements DialogPostAdapter.OnDialogListener, SearchCheckAdapter.OnCheckListener {

    Spinner searchCounty;
    LinearLayout linearCondition, linearMakeModel, linearPrice, linearPlate, linearFirstRegistration, linearKm,
            linearPower, linearFuel, linearDetailed;
    TextView tvCondition, tvMakeModel, tvPrice, tvPlate, tvFirstRegistration, tvKm, tvPower, tvFuel, tvDetailed;
    Button btnSearch;
    Dialog searchDialog, modelDialog, makeDialog, radioDialog;

    int width, height;
    String[] pickerData;
    int btnID;
    List<String> dialogItems, items;
    public static SearchConditions searchConditions = new SearchConditions();
    //    public static VehicleType vehicle = new VehicleType();
//    public static TransmissionSearch transmission = new TransmissionSearch();
//    public static ColorSearch colorSearch = new ColorSearch();
//    public static InteriorSearch interiorSearch = new InteriorSearch();
//    public static SecurityAndEnvironmentSearch security = new SecurityAndEnvironmentSearch();
//    public static Airbag airbag = new Airbag();
//    public static ParkingSearch parking = new ParkingSearch();
    public static List<Post> result;
    boolean[] checked;
    public static DataSnapshot mDataSnapshot;
    public static List<List> finalResult;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        searchCounty = (Spinner) view.findViewById(R.id.spinnerSearchCounty);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.counties,
                        R.layout.spinner_search);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCounty.setAdapter(staticAdapter);

        searchCounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchConditions.setCounty(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        linearCondition = (LinearLayout) view.findViewById(R.id.searchCondition);
        linearMakeModel = (LinearLayout) view.findViewById(R.id.searchMakeModel);
        linearPrice = (LinearLayout) view.findViewById(R.id.searchPrice);
        linearPlate = (LinearLayout) view.findViewById(R.id.searchPlate);
        linearFirstRegistration = (LinearLayout) view.findViewById(R.id.searchFirstRegistration);
        linearKm = (LinearLayout) view.findViewById(R.id.searchKm);
        linearPower = (LinearLayout) view.findViewById(R.id.searchPower);
        linearFuel = (LinearLayout) view.findViewById(R.id.searchFuel);
        linearDetailed = (LinearLayout) view.findViewById(R.id.searchDetailed);

        tvCondition = (TextView) view.findViewById(R.id.tvSearchCondition);
        tvMakeModel = (TextView) view.findViewById(R.id.tvMakeModel);
        tvPrice = (TextView) view.findViewById(R.id.tvSearchPrice);
        tvPlate = (TextView) view.findViewById(R.id.tvSearchPlate);
        tvFirstRegistration = (TextView) view.findViewById(R.id.tvSearchFirstRegistration);
        tvKm = (TextView) view.findViewById(R.id.tvSearchKm);
        tvPower = (TextView) view.findViewById(R.id.tvSearchPower);
        tvFuel = (TextView) view.findViewById(R.id.tvSearchFuel);
        tvDetailed = (TextView) view.findViewById(R.id.tvSearchDetailed);


        btnSearch = (Button) view.findViewById(R.id.btnShfaqSearch);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        finalResult = new ArrayList<>();
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDataSnapshot = dataSnapshot.child("posts");
                finalResult = Database.RemoteDatabase.postSearch(dataSnapshot.child("posts"), searchConditions);
                btnSearch.setText(finalResult.get(0).size() + " Results");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dRef = db.getReference();
                dRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (finalResult.get(0).size() != 0) {
                            //finalResult = Database.RemoteDatabase.postSearch(dataSnapshot, searchConditions);
                            Intent intent = new Intent(getContext(), SearchResultActivity.class);
                            SearchResultActivity.searchConditions = searchConditions;
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext().getApplicationContext(), "No results", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        initValues();
        linearCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 1;
                dialogCondition();
            }
        });

        linearMakeModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 2;
                makeModel();
            }
        });

        linearPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 3;
                checkDialog(btnID);

            }
        });

        linearPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 4;
                searchDialog(btnID);
            }
        });

        linearFirstRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 5;
                searchDialog(btnID);
            }
        });

        linearKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 6;
                searchDialog(btnID);
            }
        });

        linearPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 7;
                searchDialog(btnID);
            }
        });
        linearFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnID = 8;
                checkDialog(8);
            }
        });

        linearDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AdvancedSearchFragment();
                FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    ft.replace(R.id.screen_home, fragment);
                else
                    ft.replace(R.id.screen_user, fragment);

                ft.commit();

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            ((NonUserActivity) getActivity()).getSupportActionBar().setTitle(R.string.search_str);
        } else {
            ((UserActivity) getActivity()).getSupportActionBar().setTitle(R.string.search_str);
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_clear, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_clear){
            searchConditions = new SearchConditions();
            initValues();
        }


        return super.onOptionsItemSelected(item);
    }

    private void searchDialog(int i) {
        final int btn = i;
        searchDialog = new Dialog(getContext());
        searchDialog.setContentView(R.layout.range_dialog);
        searchDialog.setTitle(" ");
        searchDialog.getWindow().setLayout(width - 100, width + 100);
        searchDialog.show();
        final EditText etFrom = (EditText) searchDialog.findViewById(R.id.etDialogFrom);
        final EditText etTo = (EditText) searchDialog.findViewById(R.id.etDialogTo);
        TextView tvTitle = (TextView) searchDialog.findViewById(R.id.tvSearchDialog);
        final NumberPicker pickerFrom = (NumberPicker) searchDialog.findViewById(R.id.pickerDialogFrom);
        final NumberPicker pickerTo = (NumberPicker) searchDialog.findViewById(R.id.pickerDialogTo);
        Button btnOk = (Button) searchDialog.findViewById(R.id.btnDialogOk);
        Button btnCancel = (Button) searchDialog.findViewById(R.id.btnDialogCancel);
        ImageView btnClear = (ImageView) searchDialog.findViewById(R.id.dialogClear);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDialog.dismiss();
            }
        });

        if (i == 4 || i == 6 || i == 7) {

            if (i == 4) {
                pickerData = getResources().getStringArray(R.array.searchPrice);
                tvTitle.setText("Price");
            } else if (i == 6) {
                pickerData = getResources().getStringArray(R.array.searchKm);
                tvTitle.setText("Mileage (km)");
            } else if (i == 7) {
                pickerData = getResources().getStringArray(R.array.searchPower);
                tvTitle.setText("Power (KW)");

            }
            pickerFrom.setMinValue(0);
            pickerFrom.setMaxValue(pickerData.length - 1);
            pickerFrom.setDisplayedValues(pickerData);

            pickerTo.setMinValue(0);
            pickerTo.setMaxValue(pickerData.length - 1);
            pickerTo.setDisplayedValues(pickerData);

            pickerFrom.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    etFrom.setText(pickerData[pickerFrom.getValue()]);
                }
            });

            pickerTo.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    etTo.setText(pickerData[pickerTo.getValue()]);
                }
            });

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn == 4) {
                        if (pickerFrom.getValue() < pickerTo.getValue()) {
                            searchConditions.setPrice(new RangeSearch(Long.parseLong(pickerData[pickerFrom.getValue()]), Long.parseLong(pickerData[pickerTo.getValue()])));
                            tvPrice.setText(pickerData[pickerFrom.getValue()] + " - " + pickerData[pickerTo.getValue()] + " KES");
                            searchDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Invalid data", Toast.LENGTH_LONG).show();
                        }
                    } else if (btn == 6) {
                        if (pickerFrom.getValue() < pickerTo.getValue()) {
                            searchConditions.setKm(new RangeSearch(Long.parseLong(pickerData[pickerFrom.getValue()]), Long.parseLong(pickerData[pickerTo.getValue()])));
                            tvKm.setText(pickerData[pickerFrom.getValue()] + " - " + pickerData[pickerTo.getValue()] + " KM");
                            searchDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Invalid data", Toast.LENGTH_LONG).show();
                        }

                    } else if (btn == 7) {
                        if (pickerFrom.getValue() < pickerTo.getValue()) {
                            String[] array1 = pickerData[pickerFrom.getValue()].split(" ");
                            String[] array2 = pickerData[pickerTo.getValue()].split(" ");

                            searchConditions.setPower(new RangeSearch(Long.parseLong(array1[0]), Long.parseLong(array2[0])));
                            tvPower.setText(pickerData[pickerFrom.getValue()] + " - " + pickerData[pickerTo.getValue()]);
                            searchDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Invalid data", Toast.LENGTH_LONG).show();
                        }
                    }
                    searchResultNr();

                }
            });

            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn == 1) {
                        searchConditions.setPrice(new RangeSearch());
                        tvPrice.setText("");
                    } else if (btn == 6) {
                        searchConditions.setKm(new RangeSearch());
                        tvKm.setText("");
                    } else if (btn == 4) {
                        searchConditions.setPower(new RangeSearch());
                        tvPower.setText("");
                    }
                }
            });

        } else if (i == 5) {
            pickerFrom.setMinValue(1950);
            pickerFrom.setMaxValue(2019);

            pickerTo.setMinValue(1950);
            pickerTo.setMaxValue(2019);

            tvTitle.setText("First registration");
            pickerFrom.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    etFrom.setText(String.valueOf(pickerFrom.getValue()));
                }
            });

            pickerTo.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    etTo.setText(String.valueOf(pickerTo.getValue()));
                }
            });

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pickerFrom.getValue() < pickerTo.getValue()) {
                        searchConditions.setFirstRegistration(new RangeSearch(pickerFrom.getValue(), pickerTo.getValue()));
                        tvFirstRegistration.setText(String.valueOf(pickerFrom.getValue()) + " - " + String.valueOf(pickerTo.getValue()));
                        searchDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Invalid data", Toast.LENGTH_LONG).show();

                    }
                }
            });

            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchConditions.setFirstRegistration(new RangeSearch());
                    tvFirstRegistration.setText("");
                }
            });
        }


    }

    private void dialogCondition() {
        radioDialog = new Dialog(getContext());
        radioDialog.setContentView(R.layout.dialog_post);
        radioDialog.getWindow().setLayout(width - 100, width - 100);
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
        dialogItems = new ArrayList<>();
        dialogItems = Arrays.asList(getResources().getStringArray(R.array.state));
        tvTitle.setText("Car condition");
        DialogPostAdapter dialogAdapter = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapter);
        radioDialog.show();
    }

    private void checkDialog(int id) {
        radioDialog = new Dialog(getContext());
        radioDialog.setContentView(R.layout.dialog_post);
        radioDialog.getWindow().setLayout(width - 100, width - 100);
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
        dialogItems = new ArrayList<>();

        if (id == 8) {
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.fuel_str));
            checked = new boolean[dialogItems.size()];
            checked[0] = searchConditions.getFuel().isDiesel();
            checked[1] = searchConditions.getFuel().isPetrol();
            checked[2] = searchConditions.getFuel().isGas();
            checked[3] = searchConditions.getFuel().isElectric();
            checked[4] = searchConditions.getFuel().isHybrid();
            tvTitle.setText("Fuel");
        } else if (id == 3) {
            dialogItems = Arrays.asList(getResources().getStringArray(R.array.plates));
            checked = new boolean[dialogItems.size()];
            checked[0] = searchConditions.getLicencePlates().isUnspecified();
            checked[1] = searchConditions.getLicencePlates().isLocal();
            checked[2] = searchConditions.getLicencePlates().isKenya();
            checked[3] = searchConditions.getLicencePlates().isForeign();
            tvTitle.setText("Licence Plate");
        }

        SearchCheckAdapter searchCheckAdapter = new SearchCheckAdapter(dialogItems, getContext(), this, checked);
        recyclerDialog.setAdapter(searchCheckAdapter);
        radioDialog.show();

    }

    private void makeModel() {
        makeDialog = new Dialog(getContext());
        makeDialog.setContentView(R.layout.dialog_post);
        makeDialog.getWindow().setLayout(width - 100, width - 100);

        TextView tvTitle = (TextView) makeDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) makeDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) makeDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
        Cursor c = null;
        dialogItems = new ArrayList<>();
        c = objDb.rawQuery("Select * from tblMake", null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                for (int j = 0; j < c.getCount(); j++) {
                    dialogItems.add(c.getString(1));
                    c.moveToNext();
                }
                c.close();
            }
        }
        tvTitle.setText("Make");
        DialogPostAdapter dialogAdapter = new DialogPostAdapter(dialogItems, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapter);
        makeDialog.show();

    }

    private void modelDialog(int id) {
        modelDialog = new Dialog(getContext());
        btnID = 20;
        modelDialog.setContentView(R.layout.dialog_post);
        modelDialog.getWindow().setLayout(width - 100, width - 100);
        TextView tvTitle = (TextView) modelDialog.findViewById(R.id.tvPostDialog);
        Button btnCancel = (Button) modelDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelDialog.dismiss();
            }
        });

        RecyclerView recyclerDialog = (RecyclerView) modelDialog.findViewById(R.id.recyclerPostDialog);
        recyclerDialog.setHasFixedSize(true);
        recyclerDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
        Cursor c = null;
        items = new ArrayList<>();
        c = objDb.rawQuery("Select * from tblModel where mID=" + (id + 1), null);
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                for (int j = 0; j < c.getCount(); j++) {
                    items.add(c.getString(2));
                    c.moveToNext();
                }
                c.close();
            }
        }
        tvTitle.setText("Model");
        DialogPostAdapter dialogAdapter = new DialogPostAdapter(items, getContext(), this);
        recyclerDialog.setAdapter(dialogAdapter);
        modelDialog.show();
    }

    @Override
    public void onDialogItemClick(int position) {
        if (btnID == 1) {
            tvCondition.setText(dialogItems.get(position));
            searchConditions.setCondition(position);
            radioDialog.dismiss();
        } else if (btnID == 2) {
            searchConditions.setMakeID(position + 1);
            searchConditions.setModelID(0);
            tvMakeModel.setText(dialogItems.get(position));
            makeDialog.dismiss();
            modelDialog(position);

        } else if (btnID == 20) {
            searchConditions.setModelID(position + 1);
            tvMakeModel.setText(tvMakeModel.getText() + " " + items.get(position));
            modelDialog.dismiss();
        }
        searchResultNr();
    }

    private void initValues() {

        List<String> values;
        values = Arrays.asList(getResources().getStringArray(R.array.fuel_str));
        String text = "";
        if (SearchFragment.searchConditions.getFuel().isDiesel())
            text += values.get(0);
        if (searchConditions.getFuel().isPetrol())
            text += " - " + values.get(1);
        if (searchConditions.getFuel().isGas())
            text += " - " + values.get(2);
        if (searchConditions.getFuel().isElectric())
            text += " - " + values.get(3);
        if (searchConditions.getFuel().isHybrid())
            text += " - " + values.get(4);
        tvFuel.setText(text);

        values = Arrays.asList(getResources().getStringArray(R.array.plates));
        text = "";
        if (searchConditions.getLicencePlates().isUnspecified())
            text += values.get(0);
        if (searchConditions.getLicencePlates().isLocal())
            text += " - " + values.get(1);
        if (searchConditions.getLicencePlates().isKenya())
            text += " - " + values.get(2);
        if (searchConditions.getLicencePlates().isForeign())
            text += " - " + values.get(3);
        tvPlate.setText(text);

        values = Arrays.asList(getResources().getStringArray(R.array.state));
        tvCondition.setText(values.get(searchConditions.getCondition()));

        text = "";
        values = new ArrayList<>();
        if (searchConditions.getMakeID() != 0) {
            SQLiteDatabase objDb = (new LocalDatabase(getContext())).getReadableDatabase();
            Cursor c = null;
            values = new ArrayList<>();
            c = objDb.rawQuery("Select * from tblMake", null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int j = 0; j < c.getCount(); j++) {
                        values.add(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
                    text += values.get(searchConditions.getMakeID()-1);
                }
            }
            c = null;
            values = new ArrayList<>();
            c = objDb.rawQuery("Select * from tblModel", null);
            if (c != null) {
                c.moveToFirst();
                if (c.getCount() > 0) {
                    for (int j = 0; j < c.getCount(); j++) {
                        values.add(c.getString(2));
                        c.moveToNext();
                    }
                    c.close();
                    text += values.get(searchConditions.getMakeID()-1);
                }
            }


        }
        tvMakeModel.setText(text);


        try {
            if (searchConditions.getPrice().getFrom() != 0 || searchConditions.getPrice().getTo() != 0) {
                values = new ArrayList<>();
                text = "";
                text = String.valueOf(searchConditions.getPrice().getFrom()) + " - " + String.valueOf(searchConditions.getPrice().getTo());
                tvPrice.setText(text);
            }
        } catch (Exception e) {

        }


        try {
            if (searchConditions.getFirstRegistration().getFrom() != 0 || searchConditions.getFirstRegistration().getTo() != 0) {

                values = new ArrayList<>();
                text = "";
                text = String.valueOf(searchConditions.getFirstRegistration().getFrom()) + " - "
                        + String.valueOf(searchConditions.getFirstRegistration().getTo());
                tvFirstRegistration.setText(text);
            }
        } catch (Exception e) {

        }


        try {
            if (searchConditions.getKm().getFrom() != 0 || searchConditions.getKm().getTo() != 0) {

                values = new ArrayList<>();
                text = "";
                text = String.valueOf(searchConditions.getKm().getFrom()) + " - "
                        + String.valueOf(searchConditions.getKm().getTo());
                tvKm.setText(text);
            }
        } catch (Exception e) {

        }


        try {
            if (searchConditions.getPower().getFrom() != 0 || searchConditions.getPower().getTo() != 0) {

                values = new ArrayList<>();
                text = "";
                text = String.valueOf(searchConditions.getPower().getFrom()) + " KW - "
                        + String.valueOf(searchConditions.getPower().getTo()) + " KW";
                tvPower.setText(text);
            }
        } catch (Exception e) {

        }


    }


    @Override
    public void onSearchCheck(int position) {

        if (btnID == 8) {
            if (position == 0)
                SearchFragment.searchConditions.getFuel().setDiesel(!SearchFragment.searchConditions.getFuel().isDiesel());
            else if (position == 1)
                SearchFragment.searchConditions.getFuel().setPetrol(!SearchFragment.searchConditions.getFuel().isPetrol());
            else if (position == 2)
                SearchFragment.searchConditions.getFuel().setGas(!SearchFragment.searchConditions.getFuel().isGas());
            else if (position == 3)
                SearchFragment.searchConditions.getFuel().setElectric(!SearchFragment.searchConditions.getFuel().isElectric());
            else
                SearchFragment.searchConditions.getFuel().setHybrid(!SearchFragment.searchConditions.getFuel().isHybrid());

            String text = "";
            if (SearchFragment.searchConditions.getFuel().isDiesel())
                text += dialogItems.get(0);
            if (searchConditions.getFuel().isPetrol())
                text += " - " + dialogItems.get(1);
            if (searchConditions.getFuel().isGas())
                text += " - " + dialogItems.get(2);
            if (searchConditions.getFuel().isElectric())
                text += " - " + dialogItems.get(3);
            if (searchConditions.getFuel().isHybrid())
                text += " - " + dialogItems.get(4);
            tvFuel.setText(text);
        } else if (btnID == 3) {
            if (position == 0)
                SearchFragment.searchConditions.getLicencePlates().setUnspecified(!SearchFragment.searchConditions.getLicencePlates().isUnspecified());
            else if (position == 1)
                SearchFragment.searchConditions.getLicencePlates().setLocal(!SearchFragment.searchConditions.getLicencePlates().isLocal());
            else if (position == 2)
                SearchFragment.searchConditions.getLicencePlates().setKenya(!SearchFragment.searchConditions.getLicencePlates().isKenya());
            else if (position == 3)
                SearchFragment.searchConditions.getLicencePlates().setForeign(!SearchFragment.searchConditions.getLicencePlates().isForeign());

            String text = "";
            if (searchConditions.getLicencePlates().isUnspecified())
                text += dialogItems.get(0);
            if (searchConditions.getLicencePlates().isLocal())
                text += " - " + dialogItems.get(1);
            if (searchConditions.getLicencePlates().isKenya())
                text += " - " + dialogItems.get(2);
            if (searchConditions.getLicencePlates().isForeign())
                text += " - " + dialogItems.get(3);
            tvPlate.setText(text);
        }
        searchResultNr();
    }

    public void searchResultNr() {
        if(mDataSnapshot!=null) {
            finalResult = Database.RemoteDatabase.postSearch(mDataSnapshot, searchConditions);
            btnSearch.setText(finalResult.get(0).size() + " Results");
        }
    }

}
