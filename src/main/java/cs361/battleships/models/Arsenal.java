package cs361.battleships.models;

public class Arsenal {

    public Arsenal(){}

    public Weapon getWeapon(String type){
        switch(type){
            case "BOMB":
                return new Bomb();
            case "SPACELASER":
                return new SpaceLaser();
            default:
                return null;
        }
    }
}
