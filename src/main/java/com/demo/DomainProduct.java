package com.demo;

public class DomainProduct {
    private Long id;
    private String name;
    private String desc;

    public DomainProduct(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "DomainProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
