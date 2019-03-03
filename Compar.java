import java.util.Comparator;

public final class Compar implements Comparator {
    private static Compar instance;
    private Compar() {

    }
    // Singleton
    public static Compar getInstance() {
        if (instance == null) {
            return new Compar();
        }
        return instance;
    }
    @Override
    // IMMEDIATE < URGENT < LESS_URGENT < NON_URGENT < NOT_DETERMINED
    // daca urgenta e aceeasi, in functie de severitate
    // daca si aceasta e aceeasi, in ordine invers alfabetica
    public int compare(Object o1, Object o2) {
        Patient p1 = (Patient) o1;
        Patient p2 = (Patient) o2;
        if (p1.getUrgency() == Urgency.IMMEDIATE) {
            if (p2.getUrgency() != Urgency.IMMEDIATE) {
                return -1;
            }
            if (p1.getState().getSeverity() > p2.getState().getSeverity()) {
                return -1;
            } else if (p1.getState().getSeverity() == p2.getState().getSeverity()) {
                if (p1.getName().compareTo(p2.getName()) < 0) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return 1;
            }
        }
        if (p1.getUrgency() == Urgency.URGENT) {
            if (p2.getUrgency() == Urgency.IMMEDIATE) {
                return 1;
            }
            if (p2.getUrgency() == Urgency.URGENT) {
                if (p1.getState().getSeverity() > p2.getState().getSeverity()) {
                    return -1;
                } else if (p1.getState().getSeverity() == p2.getState().getSeverity()) {
                    if (p1.getName().compareTo(p2.getName()) < 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return 1;
            }
            return -1;
        }
        if (p1.getUrgency() == Urgency.LESS_URGENT) {
            if (p2.getUrgency() == Urgency.IMMEDIATE || p2.getUrgency() == Urgency.URGENT) {
                return 1;
            }
            if (p2.getUrgency() == Urgency.LESS_URGENT) {
                if (p1.getState().getSeverity() > p2.getState().getSeverity()) {
                    return -1;
                } else if (p1.getState().getSeverity() == p2.getState().getSeverity()) {
                    if (p1.getName().compareTo(p2.getName()) < 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return 1;
            }
            return -1;
        }
        if (p1.getUrgency() == Urgency.NON_URGENT) {
            if (p2.getUrgency() == Urgency.LESS_URGENT || p2.getUrgency() == Urgency.URGENT
                    || p2.getUrgency() == Urgency.IMMEDIATE) {
                return 1;
            }
            if (p2.getUrgency() == Urgency.NOT_DETERMINED) {
                return -1;
            }
            if (p1.getState().getSeverity() > p2.getState().getSeverity()) {
                return -1;
            }  else if (p1.getState().getSeverity() == p2.getState().getSeverity()) {
                if (p1.getName().compareTo(p2.getName()) < 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 1;
        }
        return 1;
    }
}
