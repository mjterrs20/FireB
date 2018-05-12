package com.example.asus.fireb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;


    private DatabaseReference databaseReference;
    private EditText editTextName, editTextAddres;
    private Button buttonsSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_profile );

        firebaseAuth = FirebaseAuth.getInstance ();

        if(firebaseAuth.getCurrentUser () == null){
            finish ();
            startActivity ( new Intent ( this,LoginActivity.class ) );
        }
        databaseReference = FirebaseDatabase.getInstance ().getReference ();

        editTextAddres = (EditText) findViewById ( R.id.editTexAddress );
        editTextName = (EditText) findViewById ( R.id.editTextName );
        buttonsSave = (Button) findViewById ( R.id.buttonSave );

        FirebaseUser user = firebaseAuth.getCurrentUser ();
        textViewUserEmail = (TextView) findViewById ( R.id.textViewUserEmail );
        textViewUserEmail.setText ( "Welcome"+user.getEmail () );
        buttonLogout = (Button) findViewById ( R.id.buttonLogout );
        buttonLogout.setOnClickListener ( this );
        buttonsSave.setOnClickListener ( this );


    }

    private void save(){
        String name = editTextName.getText ().toString ().trim ();
        String add = editTextAddres.getText ().toString ().trim ();

        UserInformation userInformation = new UserInformation ( name,add );

        FirebaseUser user = firebaseAuth.getCurrentUser ();
        databaseReference.child ( user.getUid ()).setValue ( userInformation );


    }
    @Override
    public void onClick(View v) {
        if(v == buttonLogout){
            firebaseAuth.signOut ();
            finish ();
            startActivity ( new Intent(this, LoginActivity.class) );
        }

        if (v == buttonsSave){
            save ();
        }

    }
}
