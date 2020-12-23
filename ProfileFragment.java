package Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import Adapters.SearchRecyclerAdapter;
import Models.Address;
import Models.Dealer;
import Models.SearchConditions;
import com.ken.cars.ProfileActivity;
import com.ken.cars.R;
import Models.User;
import Other.UniversalImageLoader;

public class ProfileFragment extends Fragment implements SearchRecyclerAdapter.OnPostListener {

    private FirebaseDatabase db;
    private DatabaseReference dRef;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    public static String userID;
    User user = null;
    Dealer dealer = null;
    Address address = null;
    TextView tvAutoservice, tvProfilName, tvProfilDealer, tvAutoTel, tvAutoDescription, tvProfilEmail, tvProfilTel,
            tvProfilAddress, tvProfilBirthday, tvProfilRegistered;
    LinearLayout layoutDealer;
    ImageView imgUserProfile;
    RecyclerView recyclerProfile;
    private MapView mapView;
    private GoogleMap googleMap;

    String url;
    ProgressDialog progressDialog;
    public static List<List> finalResult;
    SearchRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        setHasOptionsMenu(true);
        try {
            ProfileActivity.toolbar.setBackground(new ColorDrawable(Color.argb(0, 0, 0, 0)));

        } catch (Exception e) {

        }

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        userID = fUser.getUid();
        db = FirebaseDatabase.getInstance();
        dRef = db.getReference();

        tvProfilName = (TextView) view.findViewById(R.id.tvProfileName);
        tvProfilEmail = (TextView) view.findViewById(R.id.tvProfilEmail);
        tvProfilDealer = (TextView) view.findViewById(R.id.tvProfiliDealer);
        tvAutoTel = (TextView) view.findViewById(R.id.tvProfilDealerPhone);
        tvAutoDescription = (TextView) view.findViewById(R.id.tvProfilDealerDescription);
        tvProfilAddress = (TextView) view.findViewById(R.id.tvProfilAddress);
        tvProfilTel = (TextView) view.findViewById(R.id.tvProfilTel);
        tvProfilBirthday = (TextView) view.findViewById(R.id.tvProfilBirthday);
        tvProfilRegistered = (TextView) view.findViewById(R.id.tvProfilRegistered);
        layoutDealer = (LinearLayout) view.findViewById(R.id.layoutProfilDealer);
        tvAutoservice = (TextView) view.findViewById(R.id.tvAutoservice);
        imgUserProfile = (ImageView) view.findViewById(R.id.imgUserProfile);
        mapView = (MapView) view.findViewById(R.id.profileMapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();


        recyclerProfile = (RecyclerView) view.findViewById(R.id.recyclerProfile);
        recyclerProfile.setHasFixedSize(true);
        recyclerProfile.setLayoutManager(new LinearLayoutManager(getContext()));

        loadImage();

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(fUser!=null && userID!=null && fUser.getUid().equals(userID)) {
            inflater.inflate(R.menu.profile_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(fUser!=null && userID!=null && fUser.getUid().equals(userID)) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            Fragment objF = new UpdateProfileFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.screen_profile, objF);
            ft.commit();
        }}
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void userData(DataSnapshot dataSnapshot) {
        try {
            user = dataSnapshot.child("users").child(userID).getValue(User.class);
            dealer = dataSnapshot.child("carDealers").child(userID).getValue(Dealer.class);
            address = dataSnapshot.child("addresses").child(user.getAddressID()).getValue(Address.class);

            tvProfilName.setText(user.getName() + " " + user.getSurname());
            tvProfilEmail.setText(user.getEmail());
            tvProfilRegistered.setText(user.getDateCreated());
            tvProfilBirthday.setText(user.getBirthday());
            tvProfilTel.setText(user.getTel());

            String[] counties = getResources().getStringArray(R.array.counties);

            tvProfilAddress.setText(address.getStreet() + ", " + address.getBuilding() + ", " + address.getCode() + " "
                    + counties[Integer.valueOf(address.getCounty())]);
            if (Integer.valueOf(user.getAccountType()) == 2) {
                tvProfilDealer.setText(dealer.getName());
                tvAutoTel.setText(dealer.getPhone());
                tvAutoDescription.setText(dealer.getDescription());
            }

            if (!address.getLat().equals("-") && !address.getLng().equals("-")) {
                try {
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap mMap) {
                            googleMap = mMap;
                            LatLng dealerMap = new LatLng(Double.parseDouble(address.getLat()), Double.parseDouble(address.getLng()));
                            if (dealer != null)
                                googleMap.addMarker(new MarkerOptions().position(dealerMap).title(dealer.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));
                            else
                                googleMap.addMarker(new MarkerOptions().position(dealerMap).title(user.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));

                            googleMap.getUiSettings().setAllGesturesEnabled(false);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dealerMap, 17));
                        }
                    });
                }catch (NumberFormatException n){
                    mapView.setVisibility(View.GONE);
                }
            } else
                mapView.setVisibility(View.GONE);


            if (user.getAccountType().equals("1")) {
                layoutDealer.setVisibility(View.GONE);
                tvAutoservice.setVisibility(View.GONE);
            } else {
                layoutDealer.setVisibility(View.VISIBLE);
            }

            finalResult = new ArrayList<>();
            SearchConditions searchConditions = new SearchConditions();
            searchConditions.setOwnerID(userID);
            finalResult = Database.RemoteDatabase.postSearch(dataSnapshot.child("posts"), searchConditions);
            adapter = new SearchRecyclerAdapter(finalResult.get(0), finalResult.get(1), getContext(), this);
            recyclerProfile.setAdapter(adapter);


        } catch (Exception e) {

        }


    }

    private void loadImage() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig(getContext()));

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child("userImages/" + fUser.getUid() + "/profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString();
                UniversalImageLoader.setImage(url, imgUserProfile, null, "");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onPostClick(int position, int id) {

    }
}
