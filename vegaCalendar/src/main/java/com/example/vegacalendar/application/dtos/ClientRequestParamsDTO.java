package com.example.vegacalendar.application.dtos;

import com.example.vegacalendar.application.Constants;

import java.beans.ConstructorProperties;

public class ClientRequestParamsDTO {
    private Integer page;
    private Integer pageSize;
    private String letter;
    private String search;

    @ConstructorProperties({"page", "pageSize", "letter", "search"})
    public ClientRequestParamsDTO(Integer page, Integer pageSize, String letter, String search) {
        this.page = page != null? page : Integer.valueOf(Constants.DEFAULT_PAGE);
        this.pageSize = pageSize != null? pageSize : Integer.valueOf(Constants.DEFAULT_PAGE_SIZE);
        this.letter = letter != null? letter : Constants.DEFAULT_START_WITH_LETTER;
        this.search = search != null? search : Constants.DEFAULT_SEARCH;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
