package cs361.battleships.models;

import java.util.HashMap;
import java.util.Map;

public abstract class UserAction {
    private String type;
    private Map<String, String> location;

    public UserAction(){
        this.location = new HashMap<>();
    }

    public UserAction(String type, int x, char y){
        this();
        location.put("x", Integer.toString(x));
        location.put("y", String.valueOf(y));
        this.type = type;
    }
}
