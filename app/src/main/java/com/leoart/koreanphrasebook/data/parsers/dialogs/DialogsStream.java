package com.leoart.koreanphrasebook.data.parsers.dialogs;

import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Dialog;
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic;
import com.leoart.koreanphrasebook.data.parsers.DataStream;
import com.leoart.koreanphrasebook.data.parsers.TextFileParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class DialogsStream extends DataStream implements TextFileParser<Dialog> {

    public DialogsStream(InputStream fstream) {
        super(fstream);
    }

    public Dialog parse() throws IOException {

        Dialog dialog = new Dialog();

        // Open the file
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        int counter = 0;
        int number = 0;
        String ukrainian;
        String korean = "";
        String transcription = "";
        while ((ukrainian = br.readLine()) != null && (korean = br.readLine()) != null
                && (transcription = br.readLine()) != null) {
            number++;
            counter++;
            dialog.addMessage(new Replic(korean, ukrainian, number, transcription));
        }

        //Close the input stream
        br.close();

        return dialog;
    }

}
