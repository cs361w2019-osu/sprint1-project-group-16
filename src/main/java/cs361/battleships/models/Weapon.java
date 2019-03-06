package cs361.battleships.models;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, property="@class")
public abstract class Weapon {

    protected String type;

    public Weapon() {}

    public Weapon(String type) {
        this();
        this.type = type;
    }
}
