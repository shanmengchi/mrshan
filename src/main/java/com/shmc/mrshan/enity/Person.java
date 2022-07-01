package com.shmc.mrshan.enity;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {
    private String name;
    private Integer id;
    private String note;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate() {
        this.date = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
