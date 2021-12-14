package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.activities.R;
import com.example.activities.postMoreDeatailsActivity;
import com.example.model.post;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    Context context;
    String uid;
    List<post> PostList;
    private List<String> postIdsList;

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

        holder.txtAddress.setText(PostList.get(position).getAddress());
        holder.txtFromDate.setText(PostList.get(position).getFromDate());
        holder.txtToDate.setText(PostList.get(position).getToDate());

        holder.btnSeeMoreDetails.setOnClickListener(view -> {
            Intent intent = new Intent(context, postMoreDeatailsActivity.class);
            intent.putExtra("user_id",uid);
           // intent.putExtra("post_id",PostList.get(position).);
            context.startActivity(intent);
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



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtAddress,txtFromDate,txtToDate,txtCapacity, txtRestrictions;
        private Button btnSeeMoreDetails;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          // txtNameAssociation = itemView.findViewById(R.id.nameMessage);
            txtAddress = itemView.findViewById(R.id.PostAdressRight);
            txtFromDate = itemView.findViewById(R.id.PostFromDateRight);
            txtToDate = itemView.findViewById(R.id.PostToDateRight);

            parent = itemView.findViewById(R.id.parent);
            btnSeeMoreDetails = itemView.findViewById(R.id.btnseedetails);



        }
    }


}
