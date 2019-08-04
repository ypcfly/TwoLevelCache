package com.ypc.spring.customcache.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_user")
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty
    private Integer id;

    @Column
    private Integer age;

    @Column
    private String username;

    @Column
    private String sex;

    @Column
    private String userid;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}