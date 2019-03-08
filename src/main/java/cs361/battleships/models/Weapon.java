package cs361.battleships.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public abstract class Weapon {

    @JsonProperty private String name;
    @JsonProperty private String type;
    @JsonProperty private int ammo;
    @JsonProperty protected boolean unlocked;


    public Weapon() {
        this.type = null;
        this.name = null;
        this.ammo = -1;
    }

    public Weapon(String name, String type, int ammo){
        this.type = type;
        this.name = name;
        this.ammo = ammo;
        this.unlocked = false;
    }

    public abstract boolean use(Ship ship, Board board, Square square);

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setType(){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmmo(){
        return this.ammo;
    }


    public abstract Result getTargetResult();

    public abstract List<Result> getSonarResult();

    public abstract void unlock();
}
