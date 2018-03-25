package com.leoart.koreanphrasebook.ui.search;

/**
 * SectionOrRow
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

public class SectionOrRow {
    private String row;
    private String section;
    private boolean isRow;

    public static SectionOrRow createRow(String row) {
        SectionOrRow ret = new SectionOrRow();
        ret.row = row;
        ret.isRow = true;
        return ret;
    }

    public static SectionOrRow createSection(String section) {
        SectionOrRow ret = new SectionOrRow();
        ret.section = section;
        ret.isRow = false;
        return ret;
    }

    public String getRow() {
        return row;
    }

    public String getSection() {
        return section;
    }

    public boolean isRow() {
        return isRow;
    }
}
