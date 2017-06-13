package com.leoart.koreanphrasebook.ui.vocabulary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;

/**
 * Created by khrystyna on 1/20/17.
 */

public class FastScrollingAdapter extends RecyclerView.Adapter
        <FastScrollingAdapter.FastScrollViewHolder> {

    private Character[] letters;

    public FastScrollingAdapter(Character[] letters) {
        this.letters = letters;
    }

    @Override
    public FastScrollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FastScrollViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fastscrolling, parent, false));
    }

    @Override
    public void onBindViewHolder(FastScrollViewHolder holder, int position) {
        String text = "";
        if (letters != null && letters.length > position) {
            text = letters[position].toString();
        }
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        if (letters == null)
            return 0;
        return letters.length;
    }

    public class FastScrollViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public FastScrollViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvLetter);
        }
    }
}
