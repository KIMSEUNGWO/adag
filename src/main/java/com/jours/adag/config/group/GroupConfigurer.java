package com.jours.adag.config.group;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

public class GroupConfigurer {

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

    public ReadOnlyGroupConfigurer readOnly() {
        return new ReadOnlyGroupConfigurer(
            type,
            sortType.getSortType(),
            sortType.getSort()
        );
    }


    @Getter
    @AllArgsConstructor
    public static class ReadOnlyGroupConfigurer {
        private final GroupType type;
        private final SortType sortType;
        private final Sort sort;
    }
}
