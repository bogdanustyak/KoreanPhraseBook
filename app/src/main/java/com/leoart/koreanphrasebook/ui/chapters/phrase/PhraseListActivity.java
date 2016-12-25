package com.leoart.koreanphrasebook.ui.chapters.phrase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.leoart.koreanphrasebook.R;
import com.leoart.koreanphrasebook.ui.chapters.models.Phrase;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class PhraseListActivity extends AppCompatActivity implements PhrasesView {

    public static final String CATEGORY = "CATEGORY";
    private PhrasesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String category = "";
        if (getIntent().getExtras().containsKey(CATEGORY)) {
            category = getIntent().getExtras().getString(CATEGORY);
        }

        RecyclerView rvPhrases = (RecyclerView) findViewById(R.id.rv_phrases);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        rvPhrases.setLayoutManager(layoutManager);
        rvPhrases.setItemAnimator(new DefaultItemAnimator());

        adapter = new PhrasesAdapter(Collections.<Phrase>emptyList());
        rvPhrases.setAdapter(adapter);

        new PhrasesPresenter(
                this,
                category
        ).requestPhrases();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPhrases(@NotNull List<Phrase> phrases) {
        if (adapter != null) {
            adapter.updatePhrases(phrases);
        }
    }
}
