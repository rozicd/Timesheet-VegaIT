package com.example.vegacalendar.core.model;



import org.springframework.data.domain.Page;

import java.util.List;

public class Paginated<T> {
    private List<T> items;
    private int currentPage;
    private int totalItems;
    private int totalPages;

    public Paginated() {
    }

    public Paginated(List<T> items, int page, int totalItems, int totalPages) {
        this.items = items;
        this.currentPage = page;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
