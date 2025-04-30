package FlipBidder;

public class Buyer {
    private String name;
    private boolean isPreferred;
    private Integer participationCount;

    public Buyer(String name){
        this.name = name;
        this.isPreferred = false;
        this.participationCount = 0;
    }

    public String getName(){
        return name;
    }

    public Integer getParticipationCount(){
        return participationCount;
    }

    public boolean isPreferred(){
        return isPreferred;
    }

    public void incrementParticipation(){
        participationCount++;
        if(participationCount>2){
            isPreferred=true;
        }
    }

}
