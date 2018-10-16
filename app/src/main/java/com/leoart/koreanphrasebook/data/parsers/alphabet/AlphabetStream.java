package com.leoart.koreanphrasebook.data.parsers.alphabet;

import com.leoart.koreanphrasebook.data.parsers.DataStream;
import com.leoart.koreanphrasebook.data.parsers.TextFileParser;
import com.leoart.koreanphrasebook.ui.models.Letter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlphabetStream extends DataStream implements TextFileParser<List<Letter>> {


    public AlphabetStream(InputStream fstream) {
        super(fstream);
    }

    @Override
    public List<Letter> parse() throws IOException {
        List<Letter> phrases = new ArrayList<>();

        // Open the file
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String korrean = "";
        String translation = "";

        while ((korrean = br.readLine()) != null
                && (translation = br.readLine()) != null
        ) {
            phrases.add(new Letter(UUID.randomUUID().toString(), korrean, translation));
        }

        //Close the input stream
        br.close();

        return phrases;
    }
}
