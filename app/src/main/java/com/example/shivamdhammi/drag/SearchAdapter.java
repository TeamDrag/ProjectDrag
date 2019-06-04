package com.example.shivamdhammi.drag;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{


    Context context;
    private ArrayList<String> ssoNameList;
    private ArrayList<String> addressList;
    private ArrayList<String> profilepicList;
    StorageReference storageReference;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView ssoname,address;
        RelativeLayout parentLayout;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = (ImageView)itemView.findViewById(R.id.id_pic1);
            ssoname = (TextView)itemView.findViewById(R.id.id_ssoname1);
            address = (TextView)itemView.findViewById(R.id.id_address1);
            parentLayout = (RelativeLayout)itemView.findViewById(R.id.id_relative);

        }
    }


    //Constructor
    public SearchAdapter(Context context, ArrayList<String> ssoNameList, ArrayList<String> addressList,ArrayList<String> profilepicList) {
        this.context = context;
        this.ssoNameList = ssoNameList;
        this.addressList = addressList;
        this.profilepicList = profilepicList;
    }




    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_layout,viewGroup,false);

        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder searchViewHolder,final int i) {

        Log.d("vasu","1");

        searchViewHolder.ssoname.setText(ssoNameList.get(i));
        searchViewHolder.address.setText(addressList.get(i));


        Log.d("vasu","2");

        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_menu_gallery);

        Log.d("vasu","3");

        storageReference = FirebaseStorage.getInstance().getReference();

        Log.d("vasu","4");

        storageReference.child("profilepics/"+profilepicList.get(i)+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .asBitmap()
                        .load(uri)
                        .into(searchViewHolder.profileImage);
            }
        });

        Log.d("vasu","5");

        Log.d("problem",profilepicList.get(i));


        searchViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SProfile.class);

                intent.putExtra("Uid",profilepicList.get(i));
                context.startActivity(intent);
            }
        });

        Log.d("vasu","6");

    }


    @Override
    public int getItemCount() {

        return ssoNameList.size();
    }
}
