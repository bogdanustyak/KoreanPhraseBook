package com.leoart.koreanphrasebook.data.parsers.phrases;

import com.leoart.koreanphrasebook.data.parsers.DataStream;
import com.leoart.koreanphrasebook.data.parsers.TextFileParser;
import com.leoart.koreanphrasebook.ui.models.Phrase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class PhraseStream extends DataStream implements TextFileParser<List<Phrase>> {


    public PhraseStream(InputStream fstream) {
        super(fstream);
    }

    @Override
    public List<Phrase> parse() throws IOException {
        List<Phrase> phrases = new ArrayList<>();

        // Open the file
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String word = "";
        String translation = "";
        String transcription = "";
        int count = 0;

        while ((word = br.readLine()) != null
                && (translation = br.readLine()) != null
                && (transcription = br.readLine()) != null
                ) {
            phrases.add(new Phrase(word, translation, transcription, count++, false));
        }

        //Close the input stream
        br.close();

        return phrases;
    }
}
