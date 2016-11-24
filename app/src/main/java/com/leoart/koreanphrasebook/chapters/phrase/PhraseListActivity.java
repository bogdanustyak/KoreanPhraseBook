package com.leoart.koreanphrasebook.chapters.phrase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leoart.koreanphrasebook.DemoDataProvider;
import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.chapters.ChaptersRecyclerAdapter;
import com.leoart.koreanphrasebook.chapters.models.Phrase;

import java.util.List;

public class PhraseListActivity extends AppCompatActivity {

    List<Phrase> phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase);

        RecyclerView rvPhrases = (RecyclerView) findViewById(R.id.rv_phrases);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rvPhrases.setLayoutManager(layoutManager);
        rvPhrases.setItemAnimator(new DefaultItemAnimator());

        rvPhrases.setAdapter(new PhrasesAdapter(new DemoDataProvider().getPhrases()));
    }
}
