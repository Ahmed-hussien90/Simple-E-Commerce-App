package com.example.e_commerce;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class  baseAdapter extends RecyclerView.Adapter<baseAdapter.ViewHolder>  {

    private List<users> mData;
    private LayoutInflater mInflater;
    Context context;

    public baseAdapter(Context context,List<users> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context =context;
    }

    @NonNull
    @Override
    public baseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.base_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull baseAdapter.ViewHolder holder, int position) {
        users user = mData.get(position);
        holder.textView.setText(user.username);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,cartActivity.class);
                intent.putExtra("check","specficUser");
                intent.putExtra("id",user.id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.base_text);

        }

    }

}

