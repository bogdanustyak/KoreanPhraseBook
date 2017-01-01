package com.leoart.koreanphrasebook.data.parsers.categories;

import com.leoart.koreanphrasebook.data.parsers.DataStream;
import com.leoart.koreanphrasebook.data.parsers.TextFileParser;
import com.leoart.koreanphrasebook.ui.chapters.models.Category;
import com.leoart.koreanphrasebook.ui.chapters.models.Phrase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khrystyna on 12/30/16.
 */

public class CategoryStream extends DataStream implements TextFileParser<List<Category>> {


    public CategoryStream(InputStream fstream) {
        super(fstream);
    }

    @Override
    public List<Category> parse() throws IOException {
        List<Category> categories = new ArrayList<>();

        // Open the file
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String word = "";
        String translation = "";
        String transcription = "";
        int count = 0;

        while ((word = br.readLine()) != null
                && (transcription = br.readLine()) != null
                && (translation = br.readLine()) != null
                ) {
            Phrase phrase = new Phrase(word, translation, transcription, 0);
            Category category = new Category("category" + count++, phrase.toMap());
            categories.add(category);
        }

        //Close the input stream
        br.close();

        return categories;
    }
}
