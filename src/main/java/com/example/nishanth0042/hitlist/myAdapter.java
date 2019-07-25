package com.example.nishanth0042.hitlist;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class myAdapter extends RecyclerView.Adapter<myAdapter.personViewHolder> {
    private int numberofitems;
    Context context;
    FirebaseDatabase database;
    FirebaseStorage storage;

    public myAdapter(FirebaseDatabase firebaseDatabase,FirebaseStorage firebaseStorage,Context context){
        this.database=firebaseDatabase;
        this.storage=firebaseStorage;
        this.context=context;
    }

    @NonNull
    @Override
    public myAdapter.personViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new personViewHolder(itemView);
    }
    public class personViewHolder extends RecyclerView.ViewHolder{
        TextView personName,personReason;
        ImageView personImage;
        public personViewHolder(View itemView){
            super(itemView);
            personName=itemView.findViewById(R.id.rv_name);
            personReason=itemView.findViewById(R.id.rv_reason);
            personImage=itemView.findViewById(R.id.image);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final personViewHolder holder, int position) {
        DatabaseReference dataref=database.getReference();
        StorageReference ref=storage.getReference();
        String pos=Integer.toString(position);

        dataref.child(pos).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                person person1;
                person1=dataSnapshot.getValue(person.class);
                holder.personName.setText(person1.getPersonname());
                holder.personReason.setText(person1.getPersonreason());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Glide.with(context).using(new FirebaseImageLoader()).load(ref.child(pos)).into(holder.personImage);



    }

    @Override
    public int getItemCount() {
        return numberofitems;
    }
}
