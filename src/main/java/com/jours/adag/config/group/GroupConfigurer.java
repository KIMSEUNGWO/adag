package com.jours.adag.config.group;

import com.jours.adag.entity.ApiDocInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.function.Consumer;

public class GroupConfigurer {

    @Getter
    private GroupType type = GroupType.NONE;
    private SortTypeConfig sortType = new SortTypeConfig();

    public GroupConfigurer type(GroupType type) {
        assert type != null;
        this.type = type;
        return this;
    }

    public GroupConfigurer sort(Consumer<SortTypeConfig> sort) {
        sort.accept(sortType);
        assert sortType != null;
        return this;
    }

    public enum GroupType {

        CONTROLLER,
        NONE,
    }

    public Comparator<ApiDocInfo> sort() {
        return sortType.getSortType().sort(sortType.getSort());
    }

}
