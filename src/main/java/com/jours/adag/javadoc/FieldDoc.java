package com.jours.adag.javadoc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class FieldDoc {

    private String summary;
    private String description;

    public FieldDoc(String summary, String description) {
        this.summary = summary;
        this.description = description;
    }

}
