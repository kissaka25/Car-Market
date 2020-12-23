package Database;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Models.Address;
import Models.Dealer;
import Models.ErrorReport;
import Models.SearchConditions;
import Models.Post;
import Models.User;
import Other.ImageConverter;

public class RemoteDatabase {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dRef = db.getReference();
    FirebaseUser user = firebaseAuth.getCurrentUser();


    public static Boolean ret = false;


    public static Task<AuthResult> userCredentials(String email, String pass) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> register = firebaseAuth.createUserWithEmailAndPassword(email, pass);

        return register;
    }

    public static boolean userDbRegistration(String email, String pass, String name, String surname, String birthday, String tel, String type, String addressID) {
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dRef = db.getReference();
            FirebaseUser useri = firebaseAuth.getCurrentUser();

            String data = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            User user = new User(name, surname, email, birthday, tel, type, data, data, addressID);
            dRef.child("users").child(useri.getUid()).setValue(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean carDealerRegistration(String name, String phone, String description) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Dealer dealer = new Dealer(name, description, phone);
        if (dRef.child("carDealers").child(user.getUid()).setValue(dealer).isSuccessful()) {
            return true;
        } else
            return false;
    }

    public static String addressRegistration(String street, String building, String code, String county, String lat, String lng) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();
        FirebaseUser useri = firebaseAuth.getCurrentUser();

        Address addressRe = new Address(street, building, code, county, lat, lng);

        String aKey = dRef.child("addresses").push().getKey();

        dRef.child("addresses").child(aKey).setValue(addressRe);
        return aKey;

    }


    public static User getUser(DataSnapshot dataSnapshot, String userID) {
        User user = dataSnapshot.child("users").child(userID).getValue(User.class);
        return user;
    }

    public static Dealer getDealer(DataSnapshot dataSnapshot, String autosallonID) {
        Dealer dealer = dataSnapshot.child("carDealers").child(autosallonID).getValue(Dealer.class);
        return dealer;
    }

    public static Address getAddress(DataSnapshot dataSnapshot, String addressID) {
        Address address = dataSnapshot.child("addresses").child(addressID).getValue(Address.class);
        return address;
    }

    public static Task<Void> profileUpdate(FirebaseUser user, String fullName, Uri fotoPath) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .setPhotoUri(fotoPath)
                .build();

        return user.updateProfile(profileUpdates);
    }

    public static Task<Void> userDbUpdate(FirebaseUser firebaseUser, User user) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("users").child(firebaseUser.getUid()).setValue(user);
    }

    public static Task<Void> updateAddress(String addressID, Address address) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("addresses").child(addressID).setValue(address);
    }

    public static Task<Void> updateCarDealer(FirebaseUser firebaseUser, Dealer dealer) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("carDealers").child(firebaseUser.getUid()).setValue(dealer);
    }

    public static Task<Void> emailUpdate(FirebaseUser fUser, String email) {
        return fUser.updateEmail(email);
    }

    public static Task<Void> updatePass(FirebaseUser fUser, String pass) {
        return fUser.updatePassword(pass);
    }

    public static Task<AuthResult> loginUser(String email, String pass) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Task<AuthResult> login = firebaseAuth.signInWithEmailAndPassword(email, pass);
        return login;
    }

    public static Task<Void> deleteCarDealer(FirebaseUser firebaseUser) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dRef = db.getReference();

        return dRef.child("carDealers").child(firebaseUser.getUid()).removeValue();
    }

    public static String addPost(Post post) {
        try {
            //db
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dRef = db.getReference();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            //id of post
            String id = dRef.child("posts").push().getKey();

            dRef.child("posts").child(id).setValue(post);
            return id;
        } catch (Exception e) {
            return null;
        }
    }

    public static UploadTask postImages(String id, String imgUrl, int i) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("postImages/" + id + "/");
        Bitmap bm = ImageConverter.imgToBitmap(imgUrl);
        byte[] bt = ImageConverter.bitmapToBytes(bm, 40);
        UploadTask uploadTask = storageReference.child("Foto" + (i + 1)).putBytes(bt);
        return uploadTask;
    }

    public static List<List> postSearch(@NotNull DataSnapshot dataSnapshot, SearchConditions search) {

        List<Post> result = new ArrayList<>();
        List<String> resultID = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Post post = ds.getValue(Post.class);
            String id = ds.getKey();
            int searchNr = 0;
            int matchedNr = 0;

            if(search.getOwnerID()!=null){
                searchNr++;
                if(search.getOwnerID().equals(post.getOwnerID())){
                    matchedNr++;
                }
            }

            if(search.getPostIdList()!=null){
                searchNr++;
                for (int i=0;i<search.getPostIdList().size();i++){
                    if(id.equals(search.getPostIdList().get(i))){
                        matchedNr++;
                        i=search.getPostIdList().size();
                    }
                }
            }

            if (search.getCondition() == 1) {
                searchNr++;
                if (Long.parseLong(post.getKm()) < 10000) {
                    matchedNr++;
                }
            }

            if (search.getCondition() == 2) {
                searchNr++;
                if (Long.parseLong(post.getKm()) > 10000) {
                    matchedNr++;
                }
            }

            if (search.getMakeID() != 0) {
                searchNr++;
                if (post.getMakeID() == search.getMakeID())
                    matchedNr++;

            }

            if (search.getModelID() != 0) {
                searchNr++;
                if (search.getModelID() == post.getModelID())
                    matchedNr++;
            }

            if (search.getPrice() != null) {
                searchNr++;
                if (post.getPrice() >= search.getPrice().getFrom() && post.getPrice() <= search.getPrice().getTo())
                    matchedNr++;
            }

            if (search.getLicencePlates().isUnspecified() || search.getLicencePlates().isLocal()
                    || search.getLicencePlates().isKenya() || search.getLicencePlates().isForeign()) {
                searchNr++;
                if ((search.getLicencePlates().isUnspecified() && post.getPlate() != null && post.getPlate().equals("Unspecified"))
                        || (search.getLicencePlates().isLocal() && post.getPlate() != null && post.getPlate().equals("Local"))
                        || (search.getLicencePlates().isKenya() && post.getPlate() != null && post.getPlate().equals("Kenyan"))
                        || (search.getLicencePlates().isForeign() && post.getPlate() != null && post.getPlate().equals("Foreign"))) {
                    matchedNr++;
                }
            }


            if (search.getFirstRegistration() != null) {
                searchNr++;
                if (Long.parseLong(post.getFirstRegistration().split("/")[1]) >= search.getFirstRegistration().getFrom() && Long.parseLong(post.getFirstRegistration().split("/")[1]) <= search.getFirstRegistration().getTo())
                    matchedNr++;
            }

            if (search.getKm() != null) {
                searchNr++;
                if (search.getKm().getFrom() <= Long.parseLong(post.getKm()) && search.getKm().getTo() >= Long.parseLong(post.getKm())) {
                    matchedNr++;
                }
            }

            if(search.getPower() !=null){
                searchNr++;
                if (search.getPower().getFrom() <= Long.parseLong(post.getPower()) && search.getPower().getTo() >= Long.parseLong(post.getPower())) {
                    matchedNr++;
                }
            }

            if (search.getFuel().isDiesel() || search.getFuel().isPetrol() || search.getFuel().isElectric()
                    || search.getFuel().isGas() || search.getFuel().isHybrid()) {
                searchNr++;
                if ((search.getFuel().isDiesel() && post.getFuelID() == 1)
                        || (search.getFuel().isPetrol() && post.getFuelID() == 2)
                        || (search.getFuel().isGas() && post.getFuelID() == 3)
                        || (search.getFuel().isElectric() && post.getFuelID() == 4)
                        || (search.getFuel().isHybrid()) && post.getFuelID() == 5) {
                    matchedNr++;
                }
            }

            /*if (search.getCounty() != 0) {
                searchNr++;
                User user = dataSnapshot.child("users").child(post.getOwnerID()).getValue(User.class);
                Address address = dataSnapshot.child("addresses").child(user.getAddressID()).getValue(Address.class);
                if (String.valueOf(search.getCounty()).equals(address.getCounty()))
                    matchedNr++;
            }*/

            if(search.getVehicle().isSedan() || search.getVehicle().isCaravan() || search.getVehicle().isConvertible()
                    || search.getVehicle().isOffroad() || search.getVehicle().isSmall()){
                searchNr++;
                if((search.getVehicle().isSedan() && post.getCarTypeID()==1)
                        || (search.getVehicle().isCaravan() && post.getCarTypeID()==2)
                        || (search.getVehicle().isConvertible() && post.getCarTypeID()==3)
                        || (search.getVehicle().isOffroad() && post.getCarTypeID()==4)
                        || (search.getVehicle().isSmall() && post.getCarTypeID()==5)){
                    matchedNr++;
                }
            }

            if(search.getSeatsNr()!=0 && post.getNrSeats()!=null && !post.getNrSeats().equals("0")){
                searchNr++;
                int seatsNr=Integer.parseInt(post.getNrSeats().substring(0,1))+1;
                if(search.getSeatsNr()<7 && search.getSeatsNr()>seatsNr){
                    matchedNr++;
                } else if(search.getSeatsNr()==7 && seatsNr>6){
                    matchedNr++;
                }
            }

            if(search.getDoorsNr()!=0){
                searchNr++;
                if(post.getNrDoors()==search.getDoorsNr()){
                    matchedNr++;
                }
            }

            if(search.getTransmission().isUnspecified() || search.getTransmission().isManual() ||search.getTransmission().isHalfAutomatic() || search.getTransmission().isAutomatic()){
                searchNr++;
                if(search.getTransmission().isUnspecified()
                        || (search.getTransmission().isManual() && post.getTransmission()==1)
                        || (search.getTransmission().isHalfAutomatic() && post.getTransmission()==2)
                        || (search.getTransmission().isAutomatic() && post.getTransmission()==3)){
                    matchedNr++;
                }
            }

            if(search.getClimatissation()!=0){
                searchNr++;
                if(search.getClimatissation()==post.getFeatures().getAirConditioner()){
                    matchedNr++;
                }
            }

            if(search.getInteriorSearch().isBluetooth() || search.getInteriorSearch().isOnBoardComputer()
                    || search.getInteriorSearch().isCdPlayer() || search.getInteriorSearch().isElectricWindow()
                    || search.getInteriorSearch().isHeatedSeats() || search.getInteriorSearch().isSportSeats()
                    || search.getInteriorSearch().isMp3() || search.getInteriorSearch().isAux()
                    || search.getInteriorSearch().isSteeringWheelButtons() || search.getInteriorSearch().isNav()
                    || search.getInteriorSearch().isPanoramic()
                    || search.getInteriorSearch().isRoofRack() || search.getInteriorSearch().isCentralCloser()){
                searchNr++;
                if((search.getInteriorSearch().isBluetooth() == post.getFeatures().isBluetooth())
                        && (search.getInteriorSearch().isOnBoardComputer() == post.getFeatures().isOnBoardComputer())
                        && (search.getInteriorSearch().isCdPlayer() == post.getFeatures().isCDPlayer())
                        && (search.getInteriorSearch().isElectricWindow() == post.getFeatures().isElectricWindows())
                        && (search.getInteriorSearch().isHeatedSeats() == post.getFeatures().isHeatedSeats())
                        && (search.getInteriorSearch().isSportSeats() == post.getFeatures().isSportSeats())
                        && (search.getInteriorSearch().isMp3() == post.getFeatures().isMp3())
                        && (search.getInteriorSearch().isAux() == post.getFeatures().isAux())
                        && (search.getInteriorSearch().isSteeringWheelButtons() == post.getFeatures().isSteeringWheel())
                        && (search.getInteriorSearch().isNav() == post.getFeatures().isNavigation())
                        && (search.getInteriorSearch().isPanoramic() == post.getFeatures().isPanorama())
                        && (search.getInteriorSearch().isRoofRack() == post.getFeatures().isRoofRack())
                        && (search.getInteriorSearch().isCentralCloser() == post.getFeatures().isCentralLocking())){
                    matchedNr++;
                }
            }

            if(search.isSportPacket()){
                searchNr++;
                if(post.getFeatures().isSportPacket()){
                    matchedNr++;
                }
            }

            if(search.isSportAmortization()){
                searchNr++;
                if(post.getFeatures().isSportAmortisation()){
                    matchedNr++;
                }
            }

            if(search.getSecurity().isAbs() || search.getSecurity().isFourX4() || search.getSecurity().isEsp()
                    || search.getSecurity().isAdaptingLights() || search.getSecurity().isLightsSensor()
                    || search.getSecurity().isXenonHeadlights() || search.getSecurity().isBiXenonHeadlights()
                    || search.getSecurity().isRainSensor() || search.getSecurity().isStartStopSensor()){
                searchNr++;
                if((search.getSecurity().isAbs()==post.getFeatures().isAbs())
                        && (search.getSecurity().isFourX4()==post.getFeatures().isFourX4())
                        && (search.getSecurity().isEsp()==post.getFeatures().isEsp())
                        && (search.getSecurity().isAdaptingLights() == post.getFeatures().isAdaptiveLight())
                        && (search.getSecurity().isLightsSensor() ==  post.getFeatures().isLightSensor())
                        && (search.getSecurity().isXenonHeadlights() == post.getFeatures().isLightXenon())
                        && (search.getSecurity().isBiXenonHeadlights() == post.getFeatures().isLightBiXenon())
                        && (search.getSecurity().isRainSensor() == post.getFeatures().isRainSensor())
                        && (search.getSecurity().isStartStopSensor() == post.getFeatures().isStartStop())){
                    matchedNr++;
                }
            }

            if(search.getCarTypes()!=null && search.getCarTypes().size()!=0){
                searchNr++;
                for(int i=0;i<search.getCarTypes().size();i++){
                    if(search.getCarTypes().get(i).getMake() == post.getMakeID() && search.getCarTypes().get(i).getModel()==post.getModelID()){
                        matchedNr++;
                    }
                }

            }

            if(search.getAirbag().isAirbagDriver() || search.getAirbag().isAirbagSide()
                    || search.getAirbag().isAirbagBack() ||search.getAirbag().isAirbagOther()){
                searchNr++;
                if((search.getAirbag().isAirbagDriver() && post.getFeatures().getAirbag().isAirbagDriver())
                        && (search.getAirbag().isAirbagSide() && post.getFeatures().getAirbag().isAirbagSide())
                        && (search.getAirbag().isAirbagBack() && post.getFeatures().getAirbag().isAirbagBack())
                        && (search.getAirbag().isAirbagOther() && post.getFeatures().getAirbag().isAirbagOther())){
                    matchedNr++;
                }
            }

            /*if(search.getSellerType()!=0){
                searchNr++;
                User user = dataSnapshot.child("users").child(post.getOwnerID()).getValue(User.class);
                if(String.valueOf(search.getSellerType()).equals(user.getAccountType())){
                    matchedNr++;
                }
            }*/

            if(search.getOwners()!=0 && post.getNrOwners()!=null){
                searchNr++;
                if(Integer.valueOf(post.getNrOwners())<=search.getOwners()){
                    matchedNr++;
                }
            }

            if(search.getDamaged()==1){
                searchNr++;
                if(post.getDefect()!=1){
                    matchedNr++;
                }
            }


            if(post.getTtl()>System.currentTimeMillis()){
                if (searchNr == matchedNr) {
                    result.add(post);
                    resultID.add(id);
                }
            }


        }

        List<List> finalResult = new ArrayList<>();
        finalResult.add(result);
        finalResult.add(resultID);


        return finalResult;
    }

    public static UploadTask profileImageUpdate(String userID, String type, String imgUrl){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("userImages/" +userID+ "/");
        Bitmap bm = ImageConverter.imgToBitmap(imgUrl);
        byte[] bt = ImageConverter.bitmapToBytes(bm, 100);
        UploadTask uploadTask = storageReference.child(type).putBytes(bt);
        return uploadTask;
    }

    public static Task<Void> errorReport(ErrorReport error){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        return databaseReference.child("reports").push().setValue(error);
    }
}
