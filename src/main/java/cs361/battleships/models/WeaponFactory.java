package cs361.battleships.models;

public interface WeaponFactory {

    Weapon newInstance(Object obj);

//    public Weapon getWeapon(String type){
//        switch(type){
//            case "BOMB":
//                return new Bomb();
//            case "SPACELASER":
//                return new SpaceLaser();
//            case "SONAR":
//                return new Sonar();
//            default:
//                return null;
//        }
//    }
}
