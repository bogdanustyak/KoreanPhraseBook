package com.leoart.koreanphrasebook.ui.chapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.ui.chapters.models.Chapter;

import java.util.List;

/**
 * Created by bogdan on 11/5/16.
 */
public class ChaptersRecyclerAdapter extends RecyclerView.Adapter<ChaptersRecyclerAdapter.ChapterViewHolder> {

    private List<Chapter> chapters;
    private ChaptersInteractionListener interactionListener;

    public ChaptersRecyclerAdapter(List<Chapter> chapters, ChaptersInteractionListener interactionListener) {
        this.chapters = chapters;
        this.interactionListener = interactionListener;
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_chapter, parent, false);
        final ChapterViewHolder viewHolder = new ChapterViewHolder(itemView);
        viewHolder.ll_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interactionListener != null) {
                    interactionListener.onChapterClick(chapters.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        final Chapter chapter = chapters.get(position);
        if (chapter != null) {
            if (!TextUtils.isEmpty(chapter.getName())) {
                holder.tvChapterName.setText(chapter.getName());
            } else {
                holder.tvChapterName.setText("");
            }
        }
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (chapters != null) {
            return chapters.size();
        }
        return 0;
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder {
        CardView ll_chapter;
        TextView tvChapterName;

        ChapterViewHolder(View itemView) {
            super(itemView);
            ll_chapter = (CardView) itemView.findViewById(R.id.ll_chapter);
            tvChapterName = (TextView) itemView.findViewById(R.id.tv_chapter_name);
        }
    }

    public interface ChaptersInteractionListener {
        void onChapterClick(Chapter chapter);
    }
}
