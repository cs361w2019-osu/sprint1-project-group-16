package cs361.battleships.models;

public class SonarResult {
    private Square loc;
    private SonarStatus sonarStatus;

    public SonarResult () {}

    public SonarResult(Square location, SonarStatus status){
        this.loc = location;
        this.sonarStatus = status;
    }

    public Square getLocation(){ return loc; }
    public SonarStatus getStatus() { return sonarStatus; }
    public void setLocation(Square location) { this.loc = location; }
    public void setStatus(SonarStatus status) { this.sonarStatus = status; }

}
