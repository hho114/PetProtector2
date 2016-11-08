package edu.orangecoastcollege.cs273.hho65.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDetailsActivity extends AppCompatActivity {

    private TextView petDetailNameView;
    private TextView petDetailDetailView;
    private TextView petDetailPhoneNumView;
    private ImageView petDetailsImageView;
    private  String phoneNumber;
    private static final int REQUEST_PHONE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        petDetailNameView = (TextView) findViewById(R.id.petDetailNameView);;
        petDetailDetailView = (TextView) findViewById(R.id.petDetailDetailView);
        petDetailPhoneNumView = (TextView) findViewById(R.id.petDetailPhoneNumView) ;
        petDetailsImageView = (ImageView) findViewById(R.id.petDetailsImageView);


        Intent intent = getIntent();
        phoneNumber =  intent.getStringExtra("Phone");

        petDetailNameView.setText(intent.getStringExtra("Name"));
        petDetailDetailView.setText( intent.getStringExtra("Detail"));
        petDetailPhoneNumView.setText( intent.getStringExtra("Phone"));
        petDetailsImageView.setImageURI(Uri.parse(intent.getStringExtra("ImageUri")));

    }

    public void makePhoneCall(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +  phoneNumber ));
        startActivity(intent);
    }


}
