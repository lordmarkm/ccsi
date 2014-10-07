package com.ccsi.app.reference;

/**
 * @author mbmartinez
 * Keyword must end with space
 */
public enum ReservedWord {

    UPDATE("UPDATE ");

    private String keyword;
    private ReservedWord(String keyword) {
        this.keyword = keyword;
    }
    public String getKeyword() {
        return keyword;
    }

}
