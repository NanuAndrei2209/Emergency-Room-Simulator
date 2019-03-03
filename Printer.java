import java.util.Observable;
import java.util.Observer;

public class Printer implements Observer {
    /**
     * Printer este un observator al lui Hospital, menit sa afiseze mesajele cerute.
     * @param o - spitalul
     * @param arg
     */
    @Override
    // este notificator pentru spital
    public void update(Observable o, Object arg) {
        Hospital hospital = (Hospital) o;
        // afiseaza mesajele cerute folosindu-se de listele printPatients, hospitalized si doctors
        System.out.println("~~~~ Patients in round " + (hospital.getRounds() + 1) + " ~~~~");
        Patient.sortByName(hospital.getPrintPatients());
        for (Patient patient : hospital.getPrintPatients()) {
            System.out.println(patient.getName() + " is " + patient.getFinalState().getValue());
        }

        System.out.println("\n~~~~ Nurses treat patients ~~~~");
        Patient.sortByName(hospital.getHospitalized());
        int contor = 0;
        for (Patient patient : hospital.getHospitalized()) {
            if (patient.getRoundsInHospital() == 1) {
                System.out.println("Nurse " + contor % hospital.getNurses()
                        + " treated " + patient.getName()
                        + " and patient has " + patient.getRoundsInHospital() + " more round");
            } else {
                System.out.println("Nurse " + contor % hospital.getNurses()
                        + " treated " + patient.getName()
                        + " and patient has " + patient.getRoundsInHospital() + " more rounds");
            }
            contor++;
        }
        System.out.println("\n~~~~ Doctors check their "
                + "hospitalized patients and give verdicts ~~~~");

            for (int i = 0; i < hospital.getDoctors().size(); ++i) {
                Patient.sortByDone(hospital.getDoctors().get(i).getMyPatients());
                for (int j = 0; j < hospital.getDoctors().get(i).getMyPatients().size(); ++j) {
                    if (hospital.getDoctors().get(i).getMyPatients().get(j).getIsTreatmentDone()) {
                        hospital.getDoctors().get(i).getMyPatients().get(j).setDone(true);
                        hospital.getDoctors().get(i).getMyPatients().get(j)
                                .setFinalState(FinalState.HOME_DONE_TREATMENT);
                        String doctorsName = "nimic";
                        if (hospital.getDoctors().get(i).getType().contentEquals("CARDIOLOGIST")) {
                            doctorsName = "Cardiologist";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("ER_PHYSICIAN")) {
                            doctorsName = "ERPhysician";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("GASTROENTEROLOGIST")) {
                            doctorsName = "Gastroenterologist";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("GENERAL_SURGEON")) {
                            doctorsName = "General Surgeon";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("INTERNIST")) {
                            doctorsName = "Internist";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("NEUROLOGIST")) {
                            doctorsName = "Neurologist";
                        }
                        System.out.println(doctorsName + " sent "
                                + hospital.getDoctors().get(i).getMyPatients()
                                .get(j).getName() + " home");
                        hospital.getDoctors().get(i).getMyPatients().remove(j);
                        j--;
                    } else {
                        String doctorsName = "nimic";
                        if (hospital.getDoctors().get(i).getType()
                                .contentEquals("CARDIOLOGIST")) {
                            doctorsName = "Cardiologist";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("ER_PHYSICIAN")) {
                            doctorsName = "ERPhysician";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("GASTROENTEROLOGIST")) {
                            doctorsName = "Gastroenterologist";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("GENERAL_SURGEON")) {
                            doctorsName = "General Surgeon";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("INTERNIST")) {
                            doctorsName = "Internist";
                        } else if (hospital.getDoctors().get(i).getType()
                                .contentEquals("NEUROLOGIST")) {
                            doctorsName = "Neurologist";
                        }
                        System.out.println(doctorsName + " says that "
                                + hospital.getDoctors().get(i).getMyPatients().get(j).getName()
                                + " should remain in hospital");
                    }
                }
            }
            for (int i = 0; i < hospital.getHospitalized().size(); ++i) {
                if (hospital.getHospitalized().get(i).getIsTreatmentDone()) {
                    hospital.getHospitalized().remove(i);
                    i--;
                }
            }
            System.out.print("\n");
    }
}
