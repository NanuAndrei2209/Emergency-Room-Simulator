import java.util.ArrayList;

public final class Patient {
    private int id;
    private String name;
    private int age;
    private int time;
    private State state;
    private InvestigationResult result;
    private Urgency urgency;
    private Doctor doctor;
    private FinalState finalState;
    private int roundsInHospital;
    private int nurseId;
    private boolean done;
    private boolean isTreatmentDone;

    class State {
        public State() {
        }
        private int severity;
        private IllnessType illnessName;

        public int getSeverity() {
            return severity;
        }

        public IllnessType getIllnessName() {
            return illnessName;
        }

        public void setSeverity(int severity) {
            this.severity = severity;
        }
        public void setIllnessName(IllnessType illnessName) {
            this.illnessName = illnessName;
        }
    }
    Patient() {
        state = new State();
        result = InvestigationResult.NOT_DIAGNOSED;
        urgency = Urgency.NOT_DETERMINED;
        doctor = new Doctor();
        done = false;
        isTreatmentDone = false;
    }

    public State getState() {
        return state;
    }

    public void setState(State state1) {
        this.state.setIllnessName(state1.getIllnessName());
        this.state.setSeverity(state1.getSeverity());
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public InvestigationResult getResult() {
        return result;
    }

    public void setResult(InvestigationResult result) {
        this.result = result;
    }
    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor.setType(doctor.getType());
        this.doctor.setMaxForTreatment(doctor.getMaxForTreatment());
        this.doctor.setIsSurgeon(doctor.getIsSurgeon());
        this.doctor.setInitialPosition(doctor.getInitialPosition());
        //this.doctor = doctor;
    }
    public FinalState getFinalState() {
        return finalState;
    }

    public void setFinalState(FinalState finalState) {
        this.finalState = finalState;
    }

    public int getRoundsInHospital() {
        return roundsInHospital;
    }

    public void setRoundsInHospital(int roundsInHospital) {
        this.roundsInHospital = roundsInHospital;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }
    public boolean getDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean getIsTreatmentDone() {
        return isTreatmentDone;
    }

    public void setIsTreatmentDone(boolean treatmentDone) {
        this.isTreatmentDone = treatmentDone;
    }

    public boolean compatible(Doctor doctor1) {
        // stabilim daca un doctor este compatibil cu boala pacientului conform enuntului
        if (this.getResult() == InvestigationResult.OPERATE && !doctor1.getIsSurgeon()) {
            return false;
        }
        if (doctor1.getType().contentEquals("CARDIOLOGIST")
                && (state.getIllnessName() == IllnessType.HEART_ATTACK
                || state.getIllnessName() == IllnessType.HEART_DISEASE)) {
            return true;
        }
        if (doctor1.getType().contentEquals("ER_PHYSICIAN")
                && (state.getIllnessName() == IllnessType.ALLERGIC_REACTION
                || state.getIllnessName() == IllnessType.BROKEN_BONES
                || state.getIllnessName() == IllnessType.BURNS
                || state.getIllnessName() == IllnessType.CAR_ACCIDENT
                || state.getIllnessName() == IllnessType.CUTS
                || state.getIllnessName() == IllnessType.HIGH_FEVER
                || state.getIllnessName() == IllnessType.SPORT_INJURIES)) {
            return true;
        }
        if (doctor1.getType().contentEquals("GASTROENTEROLOGIST")
                && (state.getIllnessName() == IllnessType.ABDOMINAL_PAIN
                || state.getIllnessName() == IllnessType.ALLERGIC_REACTION
                || state.getIllnessName() == IllnessType.FOOD_POISONING)
                && this.getResult() != InvestigationResult.OPERATE) {
            return true;
        }
        if (doctor1.getType().contentEquals("GENERAL_SURGEON")
                && (state.getIllnessName() == IllnessType.ABDOMINAL_PAIN
                || state.getIllnessName() == IllnessType.BURNS
                || state.getIllnessName() == IllnessType.CAR_ACCIDENT
                || state.getIllnessName() == IllnessType.CUTS
                || state.getIllnessName() == IllnessType.SPORT_INJURIES)) {
            return true;
        }
        if (doctor1.getType().contentEquals("INTERNIST")
                && (state.getIllnessName() == IllnessType.ABDOMINAL_PAIN
                || state.getIllnessName() == IllnessType.ALLERGIC_REACTION
                || state.getIllnessName() == IllnessType.FOOD_POISONING
                || state.getIllnessName() == IllnessType.HEART_DISEASE
                || state.getIllnessName() == IllnessType.HIGH_FEVER
                || state.getIllnessName() == IllnessType.PNEUMONIA)
                && this.getResult() != InvestigationResult.OPERATE) {
            return true;
        }
        if (doctor1.getType().contentEquals("NEUROLOGIST")
                && (state.getIllnessName() == IllnessType.STROKE)) {
            return true;
        }
        return false;
    }
    public static void sortByName(ArrayList<Patient> list) {
        // sorteaza o lista cu pacienti alfabetic dupa nume
        if (list.size() < 2) {
            return;
        }
        Patient aux;
        for (int i = 0; i < list.size() - 1; ++i) {
            for (int j = i + 1; j < list.size(); ++j) {
                if (list.get(i).getName().compareTo(list.get(j).getName()) > 0) {
                    aux = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, aux);
                }
            }
        }
    }
    public static void sortByDone(ArrayList<Patient> list) {
        // sorteaza o lista cu pacienti in functie de statusul Done
        // Done == false < Done == true
        if (list.size() < 2) {
            return;
        }
        Patient aux;
        for (int i = 0; i < list.size() - 1; ++i) {
            for (int j = i + 1; j < list.size(); ++j) {
                if (list.get(i).getDone() && !list.get(j).getDone()) {
                    aux = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, aux);
                } else {
                    if (list.get(i).getName().compareTo(list.get(j).getName()) > 0) {
                        aux = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, aux);
                    }
                }
            }
        }
    }

}
