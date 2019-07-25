package com.example.nishanth0042.hitlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


public class MainActivity extends AppCompatActivity {
    Toolbar mytoolbar;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    myAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new myAdapter(firebaseDatabase,firebaseStorage,context);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id== R.id.add){
            Intent adderIntent;
            adderIntent=new Intent(MainActivity.this,AdderActivity.class);
            Toast.makeText(MainActivity.this, "button clicked", Toast.LENGTH_LONG).show();
            startActivity(adderIntent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

