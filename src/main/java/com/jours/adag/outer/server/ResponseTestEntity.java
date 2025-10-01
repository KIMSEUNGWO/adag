package com.jours.adag.outer.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseTestEntity {

    private String message;
    private List<Entity> entities;

    @AllArgsConstructor
    static class Entity {

        private String name;
        private String address;
    }
}
