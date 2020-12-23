package Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Database.RemoteDatabase;
import de.hdodenhof.circleimageview.CircleImageView;
import Models.Address;
import Models.Dealer;
import Other.ImageConverter;
import com.ken.cars.MapsActivity;
import com.ken.cars.ProfileActivity;
import com.ken.cars.R;
import Models.User;
import Other.UniversalImageLoader;

import static android.app.Activity.RESULT_OK;

public class UpdateProfileFragment extends Fragment {

    Spinner editCounty;
    public static CircleImageView imgProfilEdit, imgDealerEdit;
    ImageView imgProfil, imgDealer;

    int PICK_IMAGE = 71;
    int image_type;
    Uri fotoPath;

    EditText editName, editSurname, editBirthday, editTel, editAutoName, editAutoTel, editAutoDescription,
            editStreet, editBuilding, editCode, editEmail, editPassWord, editPassRi, editPassRi2;
    Button btnEditMap, btnChangePassword, btnCancelEdit;
    RadioButton radioP, radioA;
    ImageView gName, gSurname, gBirthday, gTel, gAutoName, gStreet, gBuilding, gCode, gCounty, gEmail;
    LinearLayout layoutDealer;
    ConstraintLayout editPhotoDealer;
    public static LatLng updateLatLng;
    static MapView mapView;
    private GoogleMap googleMap;
    String name, surname, birthday, phone, autoName, autoTel, autoDescription, addressStreet, addressBuilding, addressCode, addressCounty, email, type;
    String addressID;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase db;
    FirebaseUser user;
    DatabaseReference dRef;

    User usr = new User();
    Dealer dealer = new Dealer();
    Address address = new Address();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        setHasOptionsMenu(true);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        dRef = db.getReference();


        editName = (EditText) v.findViewById(R.id.editname);
        editSurname = (EditText) v.findViewById(R.id.editSurname);
        editBirthday = (EditText) v.findViewById(R.id.editBirthday);
        editTel = (EditText) v.findViewById(R.id.editTel);
        editAutoName = (EditText) v.findViewById(R.id.editNameAutosallon);
        editAutoTel = (EditText) v.findViewById(R.id.editAutoTel);
        editAutoDescription = (EditText) v.findViewById(R.id.editAutoDescription);
        editStreet = (EditText) v.findViewById(R.id.editStreet);
        editBuilding = (EditText) v.findViewById(R.id.editNr);
        editCode = (EditText) v.findViewById(R.id.editKodi);
        editEmail = (EditText) v.findViewById(R.id.editEmail);
        editPassWord = (EditText) v.findViewById(R.id.editPassOld);
        editPassRi = (EditText) v.findViewById(R.id.editPassNew);
        editPassRi2 = (EditText) v.findViewById(R.id.editPassNew2);
        btnEditMap = (Button) v.findViewById(R.id.btnEditMap);
        btnChangePassword = (Button) v.findViewById(R.id.btnChangePassword);
        btnCancelEdit = (Button) v.findViewById(R.id.btnCancelEditing);
        radioP = (RadioButton) v.findViewById(R.id.radioP);
        radioA = (RadioButton) v.findViewById(R.id.radioA);

        mapView = (MapView) v.findViewById(R.id.editMapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        imgProfilEdit = (CircleImageView) v.findViewById(R.id.editProfilIcon);
        imgDealerEdit = (CircleImageView) v.findViewById(R.id.editAutosallonIcon);

        imgProfil = (ImageView) v.findViewById(R.id.editProfil);
        imgDealer = (ImageView) v.findViewById(R.id.editAutosallon);

        imgProfilEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_type = 1;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        imgDealerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_type = 2;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        gCounty = (ImageView) v.findViewById(R.id.gCounty);

        gCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Please select county", Toast.LENGTH_SHORT).show();
            }
        });


        layoutDealer = (LinearLayout) v.findViewById(R.id.editLayoutAutosallon);
        editPhotoDealer = (ConstraintLayout) v.findViewById(R.id.editPhotoAutosallon);

        editCounty = (Spinner) v.findViewById(R.id.editSpinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.counties,
                        R.layout.spinner_profile);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        editCounty.setAdapter(staticAdapter);

        radioP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDealer.setVisibility(View.GONE);
            }
        });

        radioA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDealer.setVisibility(View.VISIBLE);
            }
        });


        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPassWord.setText("");
                editPassRi.setText("");
                editPassRi2.setText("");
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = editPassWord.getText().toString().trim();
                if (pass != null && !pass.isEmpty()) {
                    RemoteDatabase.loginUser(FirebaseAuth.getInstance().getCurrentUser().getEmail(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String pass1 = editPassRi.getText().toString().trim();
                                String pass2 = editPassRi2.getText().toString().trim();
                                if (pass1 != null && pass1.equals(pass2) && !pass1.isEmpty() && pass1.length() >= 6) {
                                    RemoteDatabase.updatePass(user, editPassRi.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Password successfully changed!", Toast.LENGTH_LONG).show();
                                                btnCancelEdit.callOnClick();
                                            } else {
                                                Toast.makeText(getActivity(), "Password change failed!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Snackbar.make(getView(), "Passwords do not match!", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(getView(), "Old password is wrong!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProfileActivity.toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ok, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_ok) {

            int error = 0;
            name = editName.getText().toString().trim();
            surname = editSurname.getText().toString().trim();
            birthday = editBirthday.getText().toString().trim();
            phone = editTel.getText().toString().trim();
            autoName = editAutoName.getText().toString().trim();
            autoTel = editAutoTel.getText().toString().trim();
            autoDescription = editAutoDescription.getText().toString().trim();
            addressStreet = editStreet.getText().toString().trim();
            addressBuilding = editBuilding.getText().toString().trim();
            addressCode = editCode.getText().toString().trim();
            addressCounty = String.valueOf(editCounty.getSelectedItemPosition()).trim();
            email = editEmail.getText().toString().trim();

            if (name.isEmpty() || name.length() < 3) {
                error++;
                editName.setError("Name should be at least 2 characters");
                editName.requestFocus();
            }

            if (surname.isEmpty() || surname.length() < 3) {
                error++;
                editSurname.setError("Surname should be atleast 2 characters");
                editSurname.requestFocus();
            }

            if (birthday.equals("Birthday")) {
                error++;
                editBirthday.setError("Specify date of birth");
                editBirthday.requestFocus();
            }

            if (radioA.isChecked()) {
                type = "2";
                if (autoName.isEmpty() || autoName.length() < 3) {
                    error++;
                    editAutoName.setError("Enter dealer name");
                    editAutoName.requestFocus();
                }
            } else
                type = "1";

            if (addressStreet.isEmpty() || addressStreet.length() < 3) {
                error++;
                editStreet.setError("Enter name of street");
                editStreet.requestFocus();
            }

            if (addressBuilding.isEmpty()) {
                error++;
                editBuilding.setError("Enter building/area name");
                editBuilding.requestFocus();
            }

            if (addressCode.isEmpty() || addressCode.length() < 4) {
                error++;
                editCode.setError("Enter postal code");
                editCode.requestFocus();
            }

            if (Integer.valueOf(addressCounty) == 0) {
                error++;
                gCounty.setVisibility(View.VISIBLE);
            } else {
                gCounty.setVisibility(View.GONE);
            }

            if (phone.isEmpty()) {
                error++;
                editTel.setError("Enter phone number");
                editTel.requestFocus();
            }

            if (email.isEmpty() || email.length() < 10) {
                error++;
                editEmail.setError("Enter valid email");
                editEmail.requestFocus();
            }

            if (error == 0) {
                if (!email.equals(usr.getEmail())) {
                    RemoteDatabase.emailUpdate(user, email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dbUpdate();
                            } else {
                                Toast.makeText(getContext(), "Email exists in the database!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    dbUpdate();
                }
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void userData(DataSnapshot dataSnapshot) {
        usr = RemoteDatabase.getUser(dataSnapshot, user.getUid());
        addressID = usr.getAddressID();
        editName.setText(usr.getName());
        editSurname.setText(usr.getSurname());
        editTel.setText(usr.getTel());
        editBirthday.setText(usr.getBirthday());
        editEmail.setText(usr.getEmail());

        address = RemoteDatabase.getAddress(dataSnapshot, usr.getAddressID());
        editStreet.setText(address.getStreet());
        editBuilding.setText(address.getBuilding());
        editCode.setText(address.getCode());
        editCounty.setSelection(Integer.valueOf(address.getCounty()));

        if (Integer.valueOf(usr.getAccountType()) == 2) {
            dealer = RemoteDatabase.getDealer(dataSnapshot, user.getUid());
            radioA.callOnClick();
            radioA.setChecked(true);
            editAutoName.setText(dealer.getName());
            editAutoTel.setText(dealer.getPhone());
            editAutoDescription.setText(dealer.getDescription());
//            editPhotoDealer.setVisibility(View.VISIBLE);
        } else {
            radioP.setChecked(true);
            radioP.callOnClick();
            editPhotoDealer.setVisibility(View.GONE);
        }

        if (!address.getLat().equals("-") && !address.getLng().equals("-")) {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;
                    LatLng dealerMap = new LatLng(Double.parseDouble(address.getLat()), Double.parseDouble(address.getLng()));
                    if (dealer != null)
                        googleMap.addMarker(new MarkerOptions().position(dealerMap).title(dealer.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));
                    else
                        googleMap.addMarker(new MarkerOptions().position(dealerMap).title(usr.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));

                    googleMap.getUiSettings().setAllGesturesEnabled(false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dealerMap, 17));
                }
            });
        } else
            mapView.setVisibility(View.GONE);

        btnEditMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objIntent = new Intent(getContext(), MapsActivity.class);
                startActivity(objIntent);
            }
        });

        loadImage();


    }

    String url;
    private void loadImage() {


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference =firebaseStorage.getReference();

        storageReference.child("userImages/"+user.getUid()+"/profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                UniversalImageLoader universalImageLoader = new UniversalImageLoader(getContext());
                try {
                    ImageLoader.getInstance().init(universalImageLoader.getConfig(getContext()));
                    url = uri.toString();
                    UniversalImageLoader.setImage(url, imgProfil, null, "");
                }catch (NullPointerException n){}
            }
        });




    }

    public static void update(final LatLng latLng) {
        updateLatLng = latLng;
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.addMarker(new MarkerOptions().position(latLng).title("The selected location").icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                mapView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void dbUpdate() {
        if (radioA.isChecked()) {
            String dealerTel;
            if (autoTel.isEmpty())
                dealerTel = phone;
            else
                dealerTel = autoTel;
            String description;
            if (autoDescription.isEmpty())
                description = " ";
            else
                description = autoDescription;
            Dealer autoUpdate = new Dealer(autoName, dealerTel, description);
            RemoteDatabase.updateCarDealer(user, autoUpdate);
        }

        String lat, lng;
        if (updateLatLng != null) {
            lat = String.valueOf(updateLatLng.latitude);
            lng = String.valueOf(updateLatLng.longitude);
        } else {
            lat = "-";
            lng = "-";
        }
        Address addressUpdate = new Address(addressStreet, addressBuilding, addressCode, addressCounty, lat, lng);
        RemoteDatabase.updateAddress(addressID, addressUpdate);


        String data = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

        User userUpdate = new User(name, surname, email, birthday, phone, type, usr.getDateCreated(), data, addressID);
        RemoteDatabase.userDbUpdate(user, userUpdate);


        if (radioP.isChecked()) {
            if (dealer != null)
                RemoteDatabase.deleteCarDealer(user);
        }
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            String url = data.getDataString();


            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference("userImages/" + user.getUid() + "/");
            Bitmap bm = null;
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bt = ImageConverter.bitmapToBytes(bm, 50);

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            if (image_type == 1) {
                storageReference.child("profile").putBytes(bt).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Uploading failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploading: " + (int) progress + "%");
                    }
                });

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    imgProfil.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (image_type == 2) {
                storageReference.child("carDealer").putBytes(bt).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Uploading failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploading: " + (int) progress + "%");
                    }
                });

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    imgDealer.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


    }
}



