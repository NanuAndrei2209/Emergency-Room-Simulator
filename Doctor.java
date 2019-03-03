import java.util.ArrayList;

public final class Doctor {

    private String type;
    private boolean isSurgeon;
    private int maxForTreatment;
    private ArrayList<Patient> myPatients;
    private int initialPosition;
    public Doctor() {
        myPatients = new ArrayList<>();
    }

    public boolean getIsSurgeon() {
        return isSurgeon;
    }

    public int getMaxForTreatment() {
        return maxForTreatment;
    }

    public String getType() {
        return type;
    }

    public void setMaxForTreatment(int maxForTreatment) {
        this.maxForTreatment = maxForTreatment;
    }

    public void setIsSurgeon(boolean surgeon) {
        isSurgeon = surgeon;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Patient> getMyPatients() {
        return myPatients;
    }

    public void setMyPatients(ArrayList<Patient> myPatients) {
        this.myPatients = myPatients;
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }
}
