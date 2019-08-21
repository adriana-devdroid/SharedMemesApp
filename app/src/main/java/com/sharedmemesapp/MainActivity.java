package com.sharedmemesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sharedmemesapp.entities.Imagen;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.SecureRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;


    @BindView(R.id.imageViewStorage)
    ImageView imageView;

    @BindView(R.id.miTV)
    TextView miTv;

    @BindView(R.id.miProgressBar)
    ProgressBar miProgressBar;

    @BindView(R.id.buttonMostrarImgsFromStorage)
    Button mostrarButton;

    @OnClick(R.id.buttonUpload)
    public void clic() {
        openFileChooser();
    }

    @OnClick(R.id.buttonMostrarImgsFromStorage)
    public void func(){
        mostrarImgStorage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void mostrarImgStorage(){
        Intent intent = new Intent(MainActivity.this, RetrofitActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        miProgressBar.setVisibility(View.GONE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri)
                    .centerCrop().fit()
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);

            SecureRandom random = new SecureRandom();
            final String imgID = new BigInteger(32, random).toString();
            String path = "firememes/" + imgID + ".png";
            final StorageReference ref = storage.getReference(path);


            miProgressBar.setVisibility(View.VISIBLE);

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setCustomMetadata("nombre", "memesjpg")
                    .build();

            UploadTask uploadTask = ref.putFile(mImageUri,metadata);


            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    miProgressBar.setVisibility(View.GONE);
                    Uri url = taskSnapshot.getDownloadUrl();
                    DatabaseReference ref = database.getReference("images/");
                    String uploadID = ref.push().getKey();
                    Imagen img = new Imagen(imgID,url.toString());
                    ref.child(uploadID).setValue(img);

                    imageView.setVisibility(View.INVISIBLE);


                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

}