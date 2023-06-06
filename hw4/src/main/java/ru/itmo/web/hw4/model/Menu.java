package ru.itmo.web.hw4.model;

public class Menu {
    private final String href;
    private final String name;
    private final String reference;

    public Menu(String ref, String name, String reference) {
        this.href = ref;
        this.name = name;
        this.reference = reference;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }

    public String getReference() {
        return reference;
    }
}
