package com.jours.adag.config.group;

import lombok.Getter;

@Getter
public class SortTypeConfig {

    private SortType sortType = SortType.URL;
    private Sort sort = Sort.ASC;

    public SortTypeConfig config(SortType sortType, Sort sort) {
        this.sortType = sortType;
        this.sort = sort;
        return this;
    }

}
