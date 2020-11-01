package com.example.firebaseapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //we will use these constants later to pass the artist name and id to another activity
    public static final String ARTIST_NAME = "net.simplifiedcoding.firebasedatabaseexample.artistname";
    public static final String ARTIST_ID = "net.simplifiedcoding.firebasedatabaseexample.artistid";

    //view objects
    EditText editTextCode;
    EditText editTextDesignation;
    EditText editTextQuantite;
    Button buttonAddArtist;

    EditText editTextResult;
    //a list to store all the artist from firebase database

    //our database reference object
    FirebaseDatabase databaseArtists;
    DatabaseReference referenceArticles;
    public Integer nb=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance(); //.getReference("artists");

        //getting views
        editTextCode = (EditText) findViewById(R.id.editTextCode);
        editTextDesignation = (EditText) findViewById(R.id.editTextDesignation);
        editTextQuantite = (EditText) findViewById(R.id.editTextQuantite);

        buttonAddArtist = (Button) findViewById(R.id.buttonAdd);
        editTextResult = (EditText) findViewById(R.id.editTextResult);

        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addArtist();
            }
        });


        referenceArticles = FirebaseDatabase.getInstance().getReference("Articles");
        referenceArticles.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nb = (int) dataSnapshot.getChildrenCount();
                //editTextResult.setText(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    /*
     * This method is saving a new artist to the
     * Firebase Realtime Database
     * */
    private void addArtist() {
        //getting the values to save
        String code = editTextCode.getText().toString().trim();
        String designation = editTextDesignation.getText().toString();
        String quantite = editTextQuantite.getText().toString();

        if (!TextUtils.isEmpty(code)) {

            Article article = new Article( code, designation,quantite);
            referenceArticles.child(code).setValue(article);
          //  editTextResult.setText(nb);


            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
