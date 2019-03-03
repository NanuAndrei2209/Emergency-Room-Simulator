import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public final class Main {
    private Main() {
    }
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Hospital hospital = objectMapper.readValue(new File(args[0]), Hospital.class);
        // realizam citirea folosind objectMapper
        hospital.addObserver(new Printer());
        // adaugam Printer ca observator al lui hospital
        hospital.createCopy();
        // cream copiile doctorilor
        int i = 0;
        // realizam simularea efectiva
        while (i < hospital.getSimulationLength()) {
            hospital.clear();
            hospital.triage();
            hospital.examine();
            hospital.investigate();
            hospital.update();
            hospital.goNurses();
            hospital.doctorCheck();
            hospital.setRounds(hospital.getRounds() + 1);
            i++;
        }
    }
}
