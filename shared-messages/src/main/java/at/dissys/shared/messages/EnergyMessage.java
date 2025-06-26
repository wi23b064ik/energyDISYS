package at.dissys.shared.messages;

public class EnergyMessage {
    public enum Type {
        PRODUCER, USER
    }
    
    public enum Association {
        COMMUNITY, GRID
    }

    private Type type;
    private Association association;
    private double kwh;
    private String datetime;

    // Constructors
    public EnergyMessage() {}

    public EnergyMessage(Type type, Association association, double kwh, String datetime) {
        this.type = type;
        this.association = association;
        this.kwh = kwh;
        this.datetime = datetime;
    }

    // Getters and setters
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "EnergyMessage{" +
                "type=" + type +
                ", association=" + association +
                ", kwh=" + kwh +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
