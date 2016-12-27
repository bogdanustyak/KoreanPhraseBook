package com.leoart.koreanphrasebook.ui.vocabulary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary;
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Word;

import org.zakariya.stickyheaders.SectioningAdapter;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class DictionaryAdapter extends SectioningAdapter {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    private Dictionary dictionary;
    private Character[] letters;
    private Context context;

    public DictionaryAdapter(Context context, Dictionary dictionary) {
        this.context = context;
        this.dictionary = dictionary;
        this.letters = dictionary.data().keySet().toArray(new Character[dictionary.data().size()]);
    }

    @Override
    public int getNumberOfSections() {
        if (letters == null) {
            return 0;
        }
        return letters.length;
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        if (dictionary == null) {
            return 0;
        }
        return dictionary.data().get(letters[sectionIndex]).size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public DictViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.dict_item, parent, false);
        return new DictViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.dict_header, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        if (dictionary != null) {
            char s = letters[sectionIndex];
            ((DictViewHolder) viewHolder)
                    .text
                    .setText(dictionary.data()
                            .get(s)
                            .get(itemIndex)
                            .get("word")
                           );
        }

    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        if (dictionary != null) {
            Character s = letters[sectionIndex];
            ((HeaderViewHolder) viewHolder)
                    .charHeader
                    .setText(s.toString());
        }
    }


    @Override
    public int getItemCount() {
        if (dictionary != null) {
            return dictionary.totalCount();
        }
        return 0;
    }

    public static class DictViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView text;

        public DictViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        TextView charHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            charHeader = (TextView) itemView.findViewById(R.id.charHeader);
        }
    }
}
