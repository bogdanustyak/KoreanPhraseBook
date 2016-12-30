package com.leoart.koreanphrasebook.ui.vocabulary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class DictionaryAdapter extends SectioningAdapter {

    private SortedMap<Character, List<HashMap<String, String>>> list;
    private Character[] letters;
    private int size = 0;

    public DictionaryAdapter(Dictionary dictionary) {
        this.list = dictionary.sort();
        this.letters = list.keySet().toArray(new Character[dictionary.data().size()]);
        this.size = dictionary.totalCount();
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
        if (list == null) {
            return 0;
        }
        return list.get(letters[sectionIndex]).size();
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
        if (list != null) {
            char s = letters[sectionIndex];
            HashMap<String, String> item = list
                    .get(s)
                    .get(itemIndex);
            String text = item.get("word")
                    .concat(" - ")
                    .concat(item.get("translation"));

            ((DictViewHolder) viewHolder)
                    .text
                    .setText(text);
        }

    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Character s = letters[sectionIndex];
        ((HeaderViewHolder) viewHolder)
                .charHeader
                .setText(s.toString());
    }


    @Override
    public int getItemCount() {
        return size;
    }

    private static class DictViewHolder extends SectioningAdapter.ItemViewHolder {
        TextView text;

        DictViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    private class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        TextView charHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
            charHeader = (TextView) itemView.findViewById(R.id.charHeader);
        }
    }
}
