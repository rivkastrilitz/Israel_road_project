package com.example.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.ChatActivity;
import com.example.activities.R;
import com.example.activities.ReservationsPopUpActivity;
import com.example.activities.restrictionsPopUpActivity;
import com.example.comperators.sortByFromDate;
import com.example.model.post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    Context context;
    String uid;
    List<post> PostList;
    List<String> postIdsList;
    List<String> publisherNamesList;
    private FirebaseAuth mAuth;
    int currCapacity;
    AlertDialog.Builder builder;




    public PostAdapter(Context context,String uid){
        this.context = context;
        this.uid=uid;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        mAuth = FirebaseAuth.getInstance();



        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtNameAngel.setText(publisherNamesList.get(position));

        holder.txtAddress.setText(PostList.get(position).getAddress());
        holder.txtFromDate.setText(PostList.get(position).getFromDate());
        holder.txtToDate.setText(PostList.get(position).getToDate());
        holder.txtCapacity.setText(Integer.toString(PostList.get(position).getCapacity()));
        holder.txtRestrictions.setText(PostList.get(position).getRestrictions());
        holder.txtPhoneNum.setText(PostList.get(position).getPhoneNum());

        //todo
        if(PostList.size()==0){
            Toast.makeText(context,"there is no hosting offers for this date ",Toast.LENGTH_SHORT).show();
        }


        int pos=position;
        holder.btnDeleteOffer.setOnClickListener(v-> {
            //only the user that published this post can delete it
            if (mAuth.getCurrentUser().getUid().equals( PostList.get(pos).getpublisherUid())){
                //dialog

                builder.setTitle("remove offer").setMessage("are you sure you want to delete this offer")
                    .setCancelable(true).setPositiveButton("delete",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.databaseRef.child("HostingOffer").child(postIdsList.get(pos)).getRef().removeValue();
                        Toast.makeText(context,"offer removed", Toast.LENGTH_SHORT).show();
                    }

                }).setNegativeButton("cancel",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });



                AlertDialog alterdialod = builder.create();
                alterdialod.show();


            }else{
                Toast.makeText(context,"you cant remove offer that you didn't post", Toast.LENGTH_SHORT).show();
            }

        });


        //todo button of undo reservation
        holder.btnReservePlace.setOnClickListener(v-> {
            currCapacity=PostList.get(position).getCapacity();
//            Intent intent=new Intent(context, ReservationsPopUpActivity.class);
//            intent.putExtra("capacity", currCapacity);
//            intent.putExtra("postList", (Serializable) PostList);
//            intent.putExtra("postIdList", (Serializable) postIdsList);
//            intent.putExtra("position", position);
//            context.startActivity(intent);

//            String reservationsNum =String.valueOf(holder.databaseRef.child("Reservations").child(mAuth.getCurrentUser().getUid()).get());
//            int numOfReservation=Integer.parseInt(reservationsNum);

            int updateCapacity=currCapacity-1;
            //update capacity in list
            PostList.get(position).setCapacity(updateCapacity);
            if(updateCapacity>=0){
                holder.databaseRef.child("HostingOffer").child(postIdsList.get(position)).child("capacity").setValue(updateCapacity);
            }else{
                Toast.makeText(context,"sorry we are full,you may search for a different Angel", Toast.LENGTH_SHORT).show();
            }

        });



        holder.btnRestrictions.setOnClickListener(v->{
            Intent intent=new Intent(context, restrictionsPopUpActivity.class);
            String restrictions=PostList.get(position).getRestrictions();
            intent.putExtra("restrictions", restrictions);
            context.startActivity(intent);

        });

        holder.btnChat.setOnClickListener(v->{
            Intent intentChat =new Intent(context,ChatActivity.class);
            context.startActivity(intentChat);
        });




    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }

    public void setPosts(List<post> posts){
        this.PostList = posts;
        notifyDataSetChanged();
    }

    public void setPostsIdList(List<String> PostsIdList){
        this.postIdsList = PostsIdList;
        notifyDataSetChanged();
    }

    public void setPublishersNameList(List<String> publisherNamesList){
        this.publisherNamesList = publisherNamesList;
        notifyDataSetChanged();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtAddress,txtFromDate,txtToDate,txtNameAngel,txtCapacity,txtPhoneNum,txtRestrictions;
        private Button  btnDeleteOffer,btnReservePlace,btnRestrictions ,btnChat ;
        private CardView parent;
        DatabaseReference databaseRef;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameAngel = itemView.findViewById(R.id.nameMessage);
            txtAddress = itemView.findViewById(R.id.PostAdressRight);
            txtFromDate = itemView.findViewById(R.id.PostFromDateRight);
            txtToDate = itemView.findViewById(R.id.PostToDateRight);
            txtCapacity=itemView.findViewById(R.id.PostCapacityRight);
            txtRestrictions=itemView.findViewById(R.id.PostRestrictionsRight);
            txtPhoneNum=itemView.findViewById(R.id.PostPhoneNumRight);

            parent = itemView.findViewById(R.id.parent);
            btnDeleteOffer=itemView.findViewById(R.id.deleteAOffer);
            btnReservePlace=itemView.findViewById(R.id.reservePlace);
            btnRestrictions=itemView.findViewById(R.id.btnRestrictions);
            btnChat=itemView.findViewById(R.id.btnChat);
            databaseRef=FirebaseDatabase.getInstance().getReference();

            builder = new AlertDialog.Builder(context);









        }
    }




}