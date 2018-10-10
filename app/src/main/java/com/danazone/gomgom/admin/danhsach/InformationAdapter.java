package com.danazone.gomgom.admin.danhsach;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danazone.gomgom.admin.R;
import com.danazone.gomgom.admin.base.BaseAdapter;
import com.danazone.gomgom.admin.bean.Information;

import java.util.ArrayList;
import java.util.List;

public class InformationAdapter extends BaseAdapter implements Filterable {

    public interface OnUserClickListener {
        void onClickItem(Information position);
    }

    private List<Information> mList;
    private OnUserClickListener mListener;
    private List<Information> mFilterList;
    private Filter mFilter;

    public InformationAdapter(@NonNull Context context, List<Information> list, OnUserClickListener listener) {
        super(context);
        mList = list;
        mFilterList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_users, parent, false);
        return new ViewHolderItem(view);
    }

    /**
     * onBindHolder Item
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolderItem(ViewHolderItem holder, final int position) {
        //  Users mRun = mList.get(position);
        holder.mTvName.setText(mFilterList.get(position).getName());
        holder.mTvPhone.setText(mFilterList.get(position).getPhoneNumber());
        holder.mTvType.setText(mFilterList.get(position).getTypeDriver());
        holder.mTvDate.setText(mFilterList.get(position).getRegisterDay());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItem(mFilterList.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolderItem((ViewHolderItem) holder, position);
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new UserFilter();
        }
        return mFilter;
    }

    /**
     * ViewHolderItem
     */
    private class ViewHolderItem extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvPhone;
        private TextView mTvType;
        private TextView mTvDate;
        private LinearLayout mView;

        public ViewHolderItem(View view) {
            super(view);
            mTvName = (TextView) view.findViewById(R.id.mTvName);
            mTvPhone = (TextView) view.findViewById(R.id.mTvPhone);
            mTvType = (TextView) view.findViewById(R.id.mTvType);
            mTvDate = (TextView) view.findViewById(R.id.mTvDate);
            mView = (LinearLayout) view.findViewById(R.id.mView);
        }
    }

    /**
     * Filter list phone codes
     */
    private class UserFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                mFilterList = mList;
            } else {
                ArrayList<Information> filteredList = new ArrayList<>();
                for (Information data : mFilterList) {
                    if (data.getPhoneNumber().toLowerCase().contains(charString)
                            || data.getName().toLowerCase().contains(charString)
                            || data.getTypeDriver().toLowerCase().contains(charString)) {
                        filteredList.add(data);
                    }
                }
                mFilterList = filteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = mFilterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mFilterList = (ArrayList<Information>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}

