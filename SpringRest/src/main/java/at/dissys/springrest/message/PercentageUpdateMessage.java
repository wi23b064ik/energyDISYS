package at.dissys.springrest.message;

import java.time.LocalDateTime;

public class PercentageUpdateMessage {
    private LocalDateTime hour;
    private double communityDepleted;
    private double gridPortion;

    // Constructors
    public PercentageUpdateMessage() {}

    public PercentageUpdateMessage(LocalDateTime hour, double communityDepleted, double gridPortion) {
        this.hour = hour;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }

    // Getters and setters
    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public double getCommunityDepleted() {
        return communityDepleted;
    }

    public void setCommunityDepleted(double communityDepleted) {
        this.communityDepleted = communityDepleted;
    }

    public double getGridPortion() {
        return gridPortion;
    }

    public void setGridPortion(double gridPortion) {
        this.gridPortion = gridPortion;
    }

    @Override
    public String toString() {
        return "PercentageUpdateMessage{" +
                "hour=" + hour +
                ", communityDepleted=" + communityDepleted +
                ", gridPortion=" + gridPortion +
                '}';
    }
}
