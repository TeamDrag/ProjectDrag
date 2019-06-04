package com.example.shivamdhammi.drag;

public class EventInfo {
    private String name,description;

    EventInfo()
    {

    }

    public EventInfo(String name,String desc)
    {
        this.name=name;
        this.description=desc;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
