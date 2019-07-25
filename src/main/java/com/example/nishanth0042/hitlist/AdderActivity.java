package com.example.nishanth0042.hitlist;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Button browse;
    ImageView personImage;
    StorageReference ref;
    FirebaseStorage storage;

    EditText nametext;
    DatabaseReference dataref;

    EditText reason;
    public static int count=0;
    person person;
    String index;

    Uri fullPhotoUri;
    UploadTask uploadTask;

    static final int REQUEST_IMAGE_OPEN = 1;
    static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adder);
        toolbar=(Toolbar)findViewById(R.id.menuAdd);
        setSupportActionBar(toolbar);
        browse=(Button)findViewById(R.id.browse);
        personImage=(ImageView)findViewById(R.id.addedimage);
        nametext=(EditText)findViewById(R.id.editTextName);
        reason=(EditText)findViewById(R.id.editTextReason);
        index=Integer.toString(count);
        person=new person();
        storage=FirebaseStorage.getInstance();



        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }
    public void selectImage() {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            fullPhotoUri = data.getData();
            personImage.setImageURI(fullPhotoUri);



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addermenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id== R.id.done){
            if(isnotempty(nametext.getText().toString(),reason.getText().toString())){
                person.setPersonreason(reason.getText().toString());
                person.setPersonname(nametext.getText().toString());
                dataref=FirebaseDatabase.getInstance().getReference();
                dataref.child(index).setValue(person);
                ref=storage.getReference().child(index);
                uploadTask=ref.putFile(fullPhotoUri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdderActivity.this,"Upload failed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AdderActivity.this,"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                        count++;
                        finish();

                    }
                });


            }
            else{
                Toast.makeText(AdderActivity.this,"Enter the details",Toast.LENGTH_SHORT).show();
            }



        }
        return super.onOptionsItemSelected(item);
    }
    boolean isnotempty(String s1,String s2){
        return ((!s1.equals(""))&&(!s2.equals("")));
    }
}
