package com.leoart.koreanphrasebook.data.parsers;

import java.io.InputStream;

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public abstract class DataStream {
    protected final InputStream fstream;

    public DataStream(InputStream fstream) {
        this.fstream = fstream;
    }
}
