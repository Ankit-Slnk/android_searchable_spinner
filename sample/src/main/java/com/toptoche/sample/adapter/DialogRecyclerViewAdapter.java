package com.toptoche.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.toptoche.sample.R;
import com.toptoche.sample.listener.RecyclerViewListener;
import com.toptoche.sample.models.DataPolicyNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DialogRecyclerViewAdapter extends RecyclerView.Adapter<DialogRecyclerViewAdapter.MyViewHolder> {

    private List<DataPolicyNumber> listData = new ArrayList<>();
    private List<DataPolicyNumber> tempListData = new ArrayList<>();
    Context context;
    RecyclerViewListener recyclerViewListener;

    public DialogRecyclerViewAdapter(Context context, List<DataPolicyNumber> listData, RecyclerViewListener recyclerViewListener) {
        this.context = context;
        this.listData = listData;
        this.recyclerViewListener = recyclerViewListener;
        this.tempListData = new ArrayList<>();
        this.tempListData.addAll(listData);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchable_list_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        DataPolicyNumber data = listData.get(position);

        holder.tvContent.setText(data.gettag_no());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewListener != null) {
                    recyclerViewListener.onItemViewTap(position);
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;

        public MyViewHolder(View view) {
            super(view);
            tvContent = view.findViewById(R.id.tvContent);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listData.clear();
        if (charText.length() == 0) {
            listData.addAll(tempListData);
        } else {
            for (DataPolicyNumber data : tempListData) {
                if (data.gettag_no().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listData.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }
}
