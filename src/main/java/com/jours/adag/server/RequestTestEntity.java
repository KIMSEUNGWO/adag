package com.jours.adag.server;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestTestEntity {

    /**
     * 이름
     *
     * 이름이 작성됩니다.
     * 두번째 이름~~
     */
    private String name;

    /**
     * 나이
     *
     * 나이가 작성됩니다.
     */
    private int age;

    /**
     * 한줄만출력되어야함
     */
    private boolean test;

}
