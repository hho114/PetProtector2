package edu.orangecoastcollege.cs273.hho65.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static edu.orangecoastcollege.cs273.hho65.petprotector.R.id.imageView;

public class PetListActivity extends AppCompatActivity {
    private ImageView petImageView;

    private PhoneNumberUtils PhoneNumber = new PhoneNumberUtils() ;
    private static final int REQUEST_PHOTO = 100;
    private static final int REQUEST_CAMERA = 200;
    //This member variable stores the URI to whatever image has been selected
    //Default name.png (R.drawable.none)
    private Uri imageURI;

    //Database and adapter objects

    private DBHelper db;
    private List<Pet> petList;
    private PetListAdapter petListAdapter;
    private ListView petListView;
    private EditText nameEditText;
    private EditText detailEditText;
    private EditText phoneEditText;

    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        //Hook up the petImageView to Layout
        petImageView = (ImageView) findViewById(imageView);

//        //Constructs a full URI to any Android resource (id, drawable, color, layout, etc)
        imageURI = getUriToResource(this,R.drawable.none);
//
//        //Set the imageURI of the ImageView in code
        petImageView.setImageURI(imageURI);

        db = new DBHelper(this);

        petList = db.getAllPets();

        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item,petList);

        petListView = (ListView) findViewById(R.id.petListView);
        petListView.setAdapter(petListAdapter);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        detailEditText = (EditText) findViewById(R.id.detailEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);


        mDatabase = FirebaseDatabase.getInstance().getReference();


    }


    public void selectPetImage(View view)
    {
        //List of all the permission we need to request from user
        ArrayList<String> permList= new ArrayList<>();

        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(cameraPermission != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.CAMERA);
        }

        int readExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(readExternalStorage != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        //Next check to see if we have to write external storage permission
        int writeExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(writeExternalStorage != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        //Request permission from the user

        //if the lis has items (size >0), wwe need to request permissions from the user
        if(permList.size() >0)
        {
            //Correct the ArrayList into an Array of Strings
            String[] perms = new String[permList.size()];

            ActivityCompat.requestPermissions(this,permList.toArray(perms),REQUEST_PHOTO);
        }
        //if we have all 3 permission, open ImageGallery
        if(cameraPermission == PackageManager.PERMISSION_GRANTED
                && readExternalStorage == PackageManager.PERMISSION_GRANTED
                && writeExternalStorage == PackageManager.PERMISSION_GRANTED)
        {
            //Use intent launch gallery and take pictures
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent,REQUEST_PHOTO);
        }
        else
            Toast.makeText(this,"Pet Protector requires external storage permission",Toast.LENGTH_LONG).show();
    }

    public void captureImage(View view)
    {
        //List of all the permission we need to request from user
        ArrayList<String> permList= new ArrayList<>();

        //Start by seeing if we have permission to camera
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(cameraPermission != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.CAMERA);
        }

        int readExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(readExternalStorage != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        //Next check to see if we have to write external storage permission
        int writeExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(writeExternalStorage != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        //Request permission from the user

        //if the lis has items (size >0), wwe need to request permissions from the user
        if(permList.size() >0)
        {
            //Correct the ArrayList into an Array of Strings
            String[] perms = new String[permList.size()];

            ActivityCompat.requestPermissions(this,permList.toArray(perms),REQUEST_CAMERA);
        }
        //if we have all 3 permission, open ImageGallery
        if(cameraPermission == PackageManager.PERMISSION_GRANTED
                && readExternalStorage == PackageManager.PERMISSION_GRANTED
                && writeExternalStorage == PackageManager.PERMISSION_GRANTED)
        {
            //Use intent launch gallery and take pictures
            Intent captureImage = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
            if (captureImage.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(captureImage, REQUEST_CAMERA);
            }
        }
        else
            Toast.makeText(this,"Pet Protector requires camera and external storage permission",Toast.LENGTH_LONG).show();
    }


    //Ctrl + O to open list Override functions
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Code to handle when the user close gallery by selecting the image
        //or pressing the back button
        //the Intent data is the URI selected from image gallery
        //Decide if the user selected imaged an image:
        if(data != null && requestCode == REQUEST_PHOTO && resultCode == RESULT_OK)
        {
            //Set ImageUri to the data
            imageURI = data.getData();
            petImageView.setImageURI(imageURI);
        }
        else if (data != null && requestCode == REQUEST_CAMERA && resultCode == RESULT_OK)
        {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            petImageView.setImageBitmap(imageBitmap);
            imageURI = getImageUri(this,imageBitmap);

        }

    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void viewPetDetails(View view)
    {
        if(view instanceof LinearLayout)
        {
            LinearLayout selectedPetLayout = (LinearLayout) view;
            Pet selectedPet = (Pet) selectedPetLayout.getTag();
            Intent intent = new Intent(this,PetDetailsActivity.class);

            intent.putExtra("Name", selectedPet.getmName());
            intent.putExtra("Detail", selectedPet.getmDetails());
            intent.putExtra("Phone", selectedPet.getmPhone());
            intent.putExtra("ImageUri", selectedPet.getmImageURI().toString());
            startActivity(intent);


        }

    }

    public void addPet(View view)
    {
        String name = nameEditText.getText().toString();
        String detail = detailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();


        if(name.isEmpty() || detail.isEmpty() || phone.isEmpty())
        {
            Toast.makeText(this,"All pet's information cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else
        {

            Pet pet = new Pet(name, detail, phone, imageURI);
            petListAdapter.add(pet);
            db.addPet(pet);

            Test t = new Test(name, detail, phone);

            mDatabase.child("Pets").child(String.valueOf(t.getmName())).setValue(t);

            nameEditText.setText("");
            detailEditText.setText("");
            phoneEditText.setText("");

            imageURI = getUriToResource(this,R.drawable.none);
            petImageView.setImageURI(imageURI);




        }
    }

    public void clearAllPets(View view)
    {
        // TODO:  Delete all games from the database and lists
        db.deleteAllPets();
        petListAdapter.notifyDataSetChanged();
        petList.clear();
    }



    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException{
        Resources res = context.getResources();

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" +res.getResourcePackageName(resId)
                +'/'+ res.getResourceTypeName(resId)
                + '/' +res.getResourceEntryName(resId)
        );
    }


}

