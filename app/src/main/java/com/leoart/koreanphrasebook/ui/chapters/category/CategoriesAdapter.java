package com.leoart.koreanphrasebook.ui.chapters.category;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.ui.chapters.models.Category;

import java.util.List;

/**
 * Created by bogdan on 11/5/16.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private List<Category> chapterCategories;
    private CategoryInteractionListener interactionListener;

    public CategoriesAdapter(List<Category> chapterCategories, CategoryInteractionListener interactionListener) {
        this.chapterCategories = chapterCategories;
        this.interactionListener = interactionListener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_category, parent, false);
        final CategoryViewHolder viewHolder = new CategoryViewHolder(itemView);
        viewHolder.ll_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interactionListener != null) {
                    interactionListener.onCategoryClick(chapterCategories.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = chapterCategories.get(position);
        if (category != null) {
            if (!TextUtils.isEmpty(category.getName())) {
                holder.tvCategoryName.setText(category.getName());
            } else {
                holder.tvCategoryName.setText("");
            }
        }
    }

    public void setCategories(List<Category> categories) {
        this.chapterCategories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (chapterCategories != null) {
            return chapterCategories.size();
        }
        return 0;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView ll_chapter;
        TextView tvCategoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ll_chapter = (CardView) itemView.findViewById(R.id.ll_chapter);
            tvCategoryName = (TextView) itemView.findViewById(R.id.tv_category_name);
        }
    }

    public interface CategoryInteractionListener {
        void onCategoryClick(Category category);
    }
}
