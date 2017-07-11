package com.nowcoder;

/**
 * Created by Shirley on 2017/7/11.
 */
public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public User(String name){
        this.name=name;
    }
}
