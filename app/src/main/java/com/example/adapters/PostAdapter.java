package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.activities.R;
import com.example.model.post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    Context context;
    String uid;
    List<post> PostList;
    List<String> postIdsList;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();



    public PostAdapter(Context context,String uid){
        this.context = context;
        this.uid=uid;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_shape, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //todo set text to publisher name or something else .
        //String postPublisheruid=PostList.get(position).getpublisherUid();

        holder.txtNameAngel.setText(PostList.get(position).getpublisherUid());
        holder.txtAddress.setText(PostList.get(position).getAddress());
        holder.txtFromDate.setText(PostList.get(position).getFromDate());
        holder.txtToDate.setText(PostList.get(position).getToDate());
        holder.txtCapacity.setText(Integer.toString(PostList.get(position).getCapacity()));
        holder.txtRestrictions.setText(PostList.get(position).getRestrictions());
        holder.txtPhoneNum.setText(PostList.get(position).getPhoneNum());
        //int selectedItem = position;

//        holder.btnDeleteOffer.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //only the user that published this post can delete it
//                if (uid == PostList.get(selectedItem).getpublisherUid()) {
//
//                    databaseRef.child("HostingOffers").child(postIdsList.get(selectedItem)).getRef().removeValue();
//                    notifyDataSetChanged();
//
//
//                }
//            }
//
//        });

//        holder.btnReservePlace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int currCapacity=PostList.get(selectedItem).getCapacity();
//                int updateCapacity=currCapacity-getNumOfReservation();
//                databaseRef.child("HostingOffers").child(PostList.get(selectedItem).getPostid()).getRef().setValue(updateCapacity);
//
//            }
//        });


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

    //todo
    public int getNumOfReservation(){
        return -1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtAddress,txtFromDate,txtToDate,txtNameAngel,txtCapacity,txtRestrictions,txtPhoneNum;
        private Button  btnDeleteOffer,btnReservePlace;
        private CardView parent;


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





        }
    }




}
