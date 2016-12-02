package com.leoart.koreanphrasebook.data.network.firebase.dialogs.models;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class DialogParser {
    private final String filePath;

    public DialogParser(String filePath) {
        this.filePath = filePath;
    }

    public Dialog parse() throws IOException {

        Dialog dialog = new Dialog();

        // Open the file
        FileInputStream fstream = new FileInputStream(filePath);
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
