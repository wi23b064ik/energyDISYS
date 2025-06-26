package at.dissys.springrest.dto;

public class CurrentEnergy {
    public String communityPoolUsed;
    public String gridPortion;

    public CurrentEnergy(String communityPoolUsed, String gridPortion) {
        this.communityPoolUsed = communityPoolUsed;
        this.gridPortion = gridPortion;
    }
    public CurrentEnergy() {
        // Default constructor for serialization/deserialization
    }

    public String getCommunityPoolUsed() {
        return communityPoolUsed;
    }

    public void setCommunityPoolUsed(String communityPoolUsed) {
        this.communityPoolUsed = communityPoolUsed;
    }

    public String getGridPortion() {
        return gridPortion;
    }

    public void setGridPortion(String gridPortion) {
        this.gridPortion = gridPortion;
    }
}
