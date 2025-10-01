package com.jours.adag.javadoc.parser;

import java.util.Map;

public interface JavaDocParser {

    Map<Class<?>, ClassDoc> load();
}
