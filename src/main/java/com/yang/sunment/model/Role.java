package com.yang.sunment.model;

import lombok.Data;

/**
 * @author: OYY
 * @Date: 2019/1/7 19:41
 * Describe: 权限
 */
@Data
public class Role {

    private int id;

    private String name;

    public Role(){

    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
