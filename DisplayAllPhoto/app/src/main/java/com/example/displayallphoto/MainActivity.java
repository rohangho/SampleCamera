package com.example.displayallphoto;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Task<Uri> uriOfImage;
    ArrayList<Uri> image=new ArrayList<>();
    RecyclerView aid;
    Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> name=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateList();


    }

    private void populateList() {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        ChildEventListener childCategory = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    String a = dataSnapshot.getKey();
                    name.add(a);





                populateImage();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        rootRef.addChildEventListener(childCategory);
    }

    private void populateImage() {
        for(int i=0;i<name.size();i++) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("Bot/" + name.get(i));
            uriOfImage = storageRef.getDownloadUrl();
            uriOfImage.addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                    image.add(uri);

                    sendAdapt();

                    //Glide.with(getApplicationContext()).load(uri).into(imgTest);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendAdapt() {

        aid=(RecyclerView) findViewById(R.id.Displayer);
        layoutManager = new GridLayoutManager(this,3);
        aid.setLayoutManager(layoutManager);
        adapter=new Adapter(image,this);
        adapter.notifyDataSetChanged();
        aid.setAdapter(adapter);
    }


}
