package com.jours.adag.config.tag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TagRegistry {

    private final Map<Class<?>, TagInfo> registry = new HashMap<>();

    public TagRegistry register(Class<?> aClass, String name, String description) {
        registry.put(aClass, new TagInfo(aClass, aClass.getSimpleName(), name, description));
        return this;
    }

    public boolean isRegistered(Class<?> controllerClass) {
        return registry.containsKey(controllerClass);
    }

    public TagInfo getTagInfo(Class<?> controllerClass) {
        return registry.get(controllerClass);
    }

    public Set<Class<?>> getRegisteredControllers() {
        return registry.keySet();
    }

    public Collection<TagInfo> getAllTagInfos() {
        return registry.values();
    }
}
