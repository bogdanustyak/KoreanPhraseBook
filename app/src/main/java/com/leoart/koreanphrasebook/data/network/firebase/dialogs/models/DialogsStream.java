package com.leoart.koreanphrasebook.data.network.firebase.dialogs.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class DialogsStream {
    private final InputStream fstream;

    public DialogsStream(InputStream inputStream) {
        this.fstream = inputStream;
    }

    public Dialog parse() throws IOException {

        Dialog dialog = new Dialog();

        // Open the file
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        int counter = 0;
        int number = 0;
        String ukrainian;
        String korean = "";
        while ((ukrainian = br.readLine()) != null && (korean = br.readLine()) != null) {
            number++;
            counter++;
            dialog.addMessage(new Replic(korean, ukrainian, number));
        }

        //Close the input stream
        br.close();

        return dialog;
    }

}
