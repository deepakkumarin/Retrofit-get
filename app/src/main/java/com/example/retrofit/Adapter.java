package com.example.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder> implements Filterable {

    private List<PostPojo> dataList;
    private Context context;

    List<PostPojo> postListAll;

    public Adapter(List<PostPojo> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        this.postListAll = new ArrayList<>(dataList);
    }


    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.design, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder( Adapter.CustomViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.posts.setText(dataList.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
   private final Filter filter = new Filter() {
       @Override
       protected FilterResults performFiltering(CharSequence constraint) {
           List<PostPojo> postList = new ArrayList<>();

           if (constraint==null || constraint.length()==0)
           {
               postList.addAll(postListAll);
           }
           else
           {
               String filterPattern = constraint.toString().toLowerCase().trim();

               for (PostPojo post:postListAll)
               {
                   if (post.getTitle().toLowerCase().contains(filterPattern))
                   {
                       postList.add(post);
                   }
               }
           }
           FilterResults results =  new FilterResults();
           results.values = postList;
           results.count = postList.size();
           return results;
       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {
           dataList.clear();
           dataList.addAll((ArrayList) results.values);
           notifyDataSetChanged();

       }
   };

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title, posts;

        public CustomViewHolder( View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            posts = itemView.findViewById(R.id.body);


        }
    }
}
