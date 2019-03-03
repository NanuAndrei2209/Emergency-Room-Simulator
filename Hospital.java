import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public final class Hospital extends Observable {
    private static Hospital hospital;
    private int simulationLength;
    private int rounds;
    private int nurses;
    private int investigators;
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> incomingPatients;
    private ArrayList<Patient> triageQueue;
    private ArrayList<Patient> examinationQueue;
    private ArrayList<Doctor> copyDoctors;
    private ArrayList<Patient> investigationQueue;
    private ArrayList<Patient> printPatients;
    private ArrayList<Patient> hospitalized;
    private final int c1 = 75;
    private final int c2 = 40;
    private final int magic3 = 3;
    private final int magic22 = 22;
    private final float magic01f = 0.1f;
    private final float magic04f = 0.4f;
    private final float magic03f = 0.3f;
    private final float magic02f = 0.2f;
    private final float magic05f = 0.5f;
    private final float magic001f = 0.01f;
    private Hospital() {
        rounds = 0;
        triageQueue = new ArrayList<>();
        examinationQueue = new ArrayList<>();
        copyDoctors = new ArrayList<>();
        investigationQueue = new ArrayList<>();
        printPatients = new ArrayList<>();
        hospitalized = new ArrayList<>();
    }
    public void update() {
        this.setChanged();
        this.notifyObservers();
    }
    public static Hospital getInstance() {
        if (hospital == null) {
            return new Hospital();
        }
        return hospital;
    }

    public int getSimulationLength() {
        return simulationLength;
    }

    public int getNurses() {
        return nurses;
    }

    public int getInvestigators() {
        return investigators;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Patient> getIncomingPatients() {
        return incomingPatients;
    }

    public void setSimulationLength(int simulationLength) {
        this.simulationLength = simulationLength;
    }

    public void setNurses(int nurses) {
        this.nurses = nurses;
    }

    public void setInvestigators(int investigators) {
        this.investigators = investigators;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setIncomingPatients(ArrayList<Patient> pacients) {
        this.incomingPatients = pacients;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public ArrayList<Patient> getTriageQueue() {
        return triageQueue;
    }

    public void setTriageQueue(ArrayList<Patient> triageQueue) {
        this.triageQueue = triageQueue;
    }

    public ArrayList<Patient> getExaminationQueue() {
        return examinationQueue;
    }

    public void setExaminationQueue(ArrayList<Patient> examinationQueue) {
        this.examinationQueue = examinationQueue;
    }

    public ArrayList<Doctor> getCopyDoctors() {
        return copyDoctors;
    }

    public void setCopyDoctors(ArrayList<Doctor> copyDoctors) {
        this.copyDoctors = copyDoctors;
    }

    public ArrayList<Patient> getInvestigationQueue() {
        return investigationQueue;
    }

    public void setInvestigationQueue(ArrayList<Patient> investigationQueue) {
        this.investigationQueue = investigationQueue;
    }

    public ArrayList<Patient> getPrintPatients() {
        return printPatients;
    }

    public void setPrintPatients(ArrayList<Patient> printPatients) {
        this.printPatients = printPatients;
    }
    public ArrayList<Patient> getHospitalized() {
        return hospitalized;
    }
    public void setHospitalized(ArrayList<Patient> hospitalized) {
        this.hospitalized = hospitalized;
    }
    public static void sortBySeverity(ArrayList<Patient> list) {
        // sorteaza un arrayList cu pacienti in functie de severitatea acestora
        Patient aux;
        for (int i = 0; i < list.size() - 1; ++i) {
            for (int j = i + 1; j < list.size(); ++j) {
                if (list.get(i).getState().getSeverity() < list.get(j).getState().getSeverity()) {
                    aux = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, aux);
                }
            }
        }
    }
    public void triage() {
        // adaugam pacientii din ziua respectiva
        for (Patient patient : incomingPatients) {
            if (patient.getTime() == rounds) {
                patient.setFinalState(FinalState.TRIAGEQUEUE);
                triageQueue.add(patient);
            }
        }
        // ii sortam dupa severitate
        Hospital.sortBySeverity(triageQueue);
        int contor = 0;
        UrgencyEstimator estimator = UrgencyEstimator.getInstance();
        // celor nr nurses pacienti cu severitatea cea mai mare le calculam urgenta
        // si ii bagam in examinationQueue
        for (int i = 0; i < triageQueue.size(); ++i) {
            if (contor == nurses) {
                break;
            }
            triageQueue.get(i).setUrgency(estimator.estimateUrgency(
                    triageQueue.get(i).getState().getIllnessName(),
                    triageQueue.get(i).getState().getSeverity()));
            triageQueue.get(i).setFinalState(FinalState.EXAMINATIONSQUEUE);
            examinationQueue.add(triageQueue.get(i));
            contor++;
        }
        // ii eliminam din triageQueue pe cei care au fost transferati catre examinationQueue
        for (int i = 0; i < triageQueue.size(); ++i) {
            if (triageQueue.get(i).getFinalState() == FinalState.EXAMINATIONSQUEUE) {
                triageQueue.remove(i);
                i--;
            }
        }
        // vedem daca au ramas pacienti in triageQueue pentru a-i afisa
        for (Patient patient : triageQueue) {
            printPatients.add(patient);
        }
    }
    // cream o lista cu aceiasi doctori ca cei initiali si pastram pozitia lor
    // initial prin intermediul unui camp
    public void createCopy() {
        for (int i = 0; i < doctors.size(); ++i) {
            copyDoctors.add(doctors.get(i));
            copyDoctors.get(i).setInitialPosition(i);
        }
    }
    public void examine() {
        Collections.sort(examinationQueue, Compar.getInstance());
        // sortam pacientii din examinationQueue cu Comparatorul creat
        for (int i = 0; i < examinationQueue.size(); ++i) {
            boolean t = false;
            for (int j = 0; j < copyDoctors.size(); ++j) {
                // fiecarui pacient ii atribuim cate un doctor cu care e compatibil
                if (examinationQueue.get(i).compatible(copyDoctors.get(j))) {
                    examinationQueue.get(i).setDoctor(copyDoctors.get(j));
                    copyDoctors.add(copyDoctors.get(j));
                    copyDoctors.remove(j);
                    t = true;
                    break;
                }
            }
            if (!t) {
                // daca nu e compatibil cu nici un doctor, il transferam la alt spital
                examinationQueue.get(i).setFinalState(FinalState.OTHERHOSPITAL);
                examinationQueue.get(i).setDone(true);
                printPatients.add(examinationQueue.get(i));
                examinationQueue.remove(examinationQueue.get(i));
                i--;
            }
        }
        for (Patient patient : examinationQueue) {
            // daca nu e diagnosticat, e consultat si e trimis acasa sau se intoarce
            // de la investigatii cu tratament, il trimitem acasa
            if ((patient.getResult() == InvestigationResult.NOT_DIAGNOSED
                    && patient.getState().getSeverity() <= patient.getDoctor().getMaxForTreatment())
                    || patient.getResult() == InvestigationResult.TREATMENT) {
                    if (patient.getDoctor().getType().contentEquals("CARDIOLOGIST")) {
                        patient.setFinalState(FinalState.HOME_CARDIO);
                    }
                    if (patient.getDoctor().getType().contentEquals("ER_PHYSICIAN")) {
                        patient.setFinalState(FinalState.HOME_ERPHYSICIAN);
                    }
                    if (patient.getDoctor().getType().contentEquals("GASTROENTEROLOGIST")) {
                        patient.setFinalState(FinalState.HOME_GASTRO);
                    }
                    if (patient.getDoctor().getType().contentEquals("GENERAL_SURGEON")) {
                        patient.setFinalState(FinalState.HOME_SURGEON);
                    }
                    if (patient.getDoctor().getType().contentEquals("INTERNIST")) {
                        patient.setFinalState(FinalState.HOME_INTERNIST);
                    }
                    if (patient.getDoctor().getType().contentEquals("NEUROLOGIST")) {
                        patient.setFinalState(FinalState.HOME_NEURO);
                    }
                    patient.setDone(true);
                    printPatients.add(patient);
            } else if (patient.getResult() == InvestigationResult.NOT_DIAGNOSED
                    && patient.getState().getSeverity() > patient.getDoctor()
                    .getMaxForTreatment()) {
                    patient.setFinalState(FinalState.INVESTIGATIONSQUEUE);
                    investigationQueue.add(patient);
                    // daca e consultat si depaseste maxForTreatment, il trimitem la investigatii
            } else if (patient.getResult() == InvestigationResult.HOSPITALIZE
            || patient.getResult() == InvestigationResult.OPERATE) {
                // daca trebuie operat sau internat, ii recalculam severitatea
                // si calculam cate runde trebuie sa ramana in spital
                // si il adaugam il lista cu spitalizati
                calculateSeverity(patient);
                patient.setDone(true);
                doctors.get(patient.getDoctor().getInitialPosition()).getMyPatients().add(patient);
                printPatients.add(patient);
                hospitalized.add(patient);
            }
        }
        for (int i = 0; i < examinationQueue.size(); ++i) {
            if (examinationQueue.get(i).getResult() == InvestigationResult.TREATMENT
                    || examinationQueue.get(i).getFinalState() == FinalState.INVESTIGATIONSQUEUE
                    || examinationQueue.get(i).getFinalState() == FinalState.HOME_CARDIO
                    || examinationQueue.get(i).getFinalState() == FinalState.HOME_ERPHYSICIAN
                    || examinationQueue.get(i).getFinalState() == FinalState.HOME_GASTRO
                    || examinationQueue.get(i).getFinalState() == FinalState.HOME_INTERNIST
                    || examinationQueue.get(i).getFinalState() == FinalState.HOME_SURGEON
                    || examinationQueue.get(i).getFinalState() == FinalState.HOME_NEURO
                    || examinationQueue.get(i).getResult() == InvestigationResult.HOSPITALIZE
                    || examinationQueue.get(i).getFinalState() == FinalState.OPERATED_CARDIO
                    || examinationQueue.get(i).getFinalState() == FinalState.OPERATED_ERPHYSICIAN
                    || examinationQueue.get(i).getFinalState() == FinalState.OPERATED_NEURO
                    || examinationQueue.get(i).getFinalState() == FinalState.OPERATED_SURGEON) {
                // eliminam din examinationQueue toti pacientii care au fost internati
                // sau trimisi acasa
                examinationQueue.remove(i);
                i--;
            }
        }
    }
    public void investigate() {
        int contor = 0;
        Collections.sort(investigationQueue, Compar.getInstance());
        // sortam lista cu comparatorul facut
        for (int i = 0; i < investigationQueue.size(); ++i) {
            // primilor nr investigators pacienti le este calculat rezultatul
            // si sunt trimisi inapoi in examinationQueue
            if (contor == investigators) {
                break;
            }
            if (investigationQueue.get(i).getState().getSeverity() > c1) {
                investigationQueue.get(i).setFinalState(FinalState.EXAMINATIONSQUEUE);
                printPatients.add(investigationQueue.get(i));
                investigationQueue.get(i).setResult(InvestigationResult.OPERATE);
                examinationQueue.add(investigationQueue.get(i));
                investigationQueue.remove(i);
                i--;
            } else if (investigationQueue.get(i).getState().getSeverity() > c2) {
                investigationQueue.get(i).setFinalState(FinalState.EXAMINATIONSQUEUE);
                printPatients.add(investigationQueue.get(i));
                investigationQueue.get(i).setResult(InvestigationResult.HOSPITALIZE);
                examinationQueue.add(investigationQueue.get(i));
                investigationQueue.remove(i);
                i--;
            } else {
                investigationQueue.get(i).setFinalState(FinalState.EXAMINATIONSQUEUE);
                printPatients.add(investigationQueue.get(i));
                investigationQueue.get(i).setResult(InvestigationResult.TREATMENT);
                examinationQueue.add(investigationQueue.get(i));
                investigationQueue.remove(i);
                i--;
            }
            contor++;
        }
        // pe cei ramasi in investigationQueue ii printam
        for (int i = 0; i < investigationQueue.size(); ++i) {
            investigationQueue.get(i).setFinalState(FinalState.INVESTIGATIONSQUEUE);
            printPatients.add(investigationQueue.get(i));
        }
    }
    public void goNurses() {
        Patient.sortByName(hospitalized);
        // tratam pacientii din spital, micindu-le severitatea cu 22
        // si scazandu-le rundele ramase cu 1
        for (Patient patient : hospitalized) {
            patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
            patient.setRoundsInHospital(patient.getRoundsInHospital() - 1);
        }
    }
    public void doctorCheck() {
        // verificam daca nu cumva pacientii ar trebui sa iasa din spital
        for (Patient patient : hospitalized) {
            if (patient.getState().getSeverity() <= 0 || patient.getRoundsInHospital() == 0) {
                patient.setIsTreatmentDone(true);
            }
        }
    }
    public void clear() {
        // eliminam din lista de print pacientii care nu au statusul Done == true
        for (int i = 0; i < printPatients.size(); ++i) {
            if (!printPatients.get(i).getDone()) {
                printPatients.remove(i);
                i--;
            }
        }
    }
    public void calculateSeverity(Patient patient) {
        // operam sau spitalizam pacientii in functie de doctor si severitate
        // actualizandu-le severitatea si calculandu-le rundele ramase
        if (patient.getResult() == InvestigationResult.OPERATE) {
            float s = (float) patient.getState().getSeverity();
            if (patient.getDoctor().getType().contentEquals("CARDIOLOGIST")) {
                patient.getState().setSeverity(Math.round(s) - Math.round((s * magic01f)));
                s = (float) patient.getState().getSeverity();
                patient.setRoundsInHospital(Math.max(Math.round((s * magic04f)), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
                patient.setFinalState(FinalState.OPERATED_CARDIO);
            }
            if (patient.getDoctor().getType().contentEquals("ER_PHYSICIAN")) {
                patient.getState().setSeverity(Math.round(s) - Math.round(s * magic03f));
                s = (float) patient.getState().getSeverity();
                patient.setRoundsInHospital(Math.max(Math.round((s * magic01f)), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
                patient.setFinalState(FinalState.OPERATED_ERPHYSICIAN);
            }
            if (patient.getDoctor().getType().contentEquals("GENERAL_SURGEON")) {
                patient.getState().setSeverity(Math.round(s) - Math.round(s * magic02f));
                s = (float) patient.getState().getSeverity();
                patient.setRoundsInHospital(Math.max(Math.round(s * magic02f), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
                patient.setFinalState(FinalState.OPERATED_SURGEON);
            }
            if (patient.getDoctor().getType().contentEquals("NEUROLOGIST")) {
                patient.getState().setSeverity(Math.round(s) - Math.round(s * magic05f));
                s = (float) patient.getState().getSeverity();
                patient.setRoundsInHospital(Math.max(Math.round(s * magic01f), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
                patient.setFinalState(FinalState.OPERATED_NEURO);
            }
        } else if (patient.getResult() == InvestigationResult.HOSPITALIZE) {
            float s = (float) patient.getState().getSeverity();
            if (patient.getDoctor().getType().contentEquals("CARDIOLOGIST")) {
                patient.setFinalState(FinalState.HOSPITALIZED_CARDIO);
                patient.setRoundsInHospital(Math.max(Math.round((s * magic04f)), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
            }
            if (patient.getDoctor().getType().contentEquals("ER_PHYSICIAN")) {
                patient.setFinalState(FinalState.HOSPITALIZED_ERPHYSICIAN);
                patient.setRoundsInHospital(Math.max(Math.round((s * magic01f)), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
            }
            if (patient.getDoctor().getType().contentEquals("GASTROENTEROLOGIST")) {
                patient.setFinalState(FinalState.HOSPITALIZED_GASTRO);
                patient.setRoundsInHospital(Math.max(Math.round(s * magic05f), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
            }
            if (patient.getDoctor().getType().contentEquals("GENERAL_SURGEON")) {
                patient.setFinalState(FinalState.HOSPITALIZED_SURGEON);
                patient.setRoundsInHospital(Math.max(Math.round(s * magic02f), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
            }
            if (patient.getDoctor().getType().contentEquals("INTERNIST")) {
                patient.setFinalState(FinalState.HOSPITALIZED_INTERNIST);
                patient.setRoundsInHospital(Math.max(Math.round(s * magic001f), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
            }
            if (patient.getDoctor().getType().contentEquals("NEUROLOGIST")) {
                patient.setFinalState(FinalState.HOSPITALIZED_NEURO);
                patient.setRoundsInHospital(Math.max(Math.round(s * magic01f), magic3) - 1);
                patient.getState().setSeverity(patient.getState().getSeverity() - magic22);
            }
        }
    }

}
