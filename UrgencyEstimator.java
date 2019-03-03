
import java.util.HashMap;
import java.util.Map;


/**
 * Estimates the urgency based on the patient's illness and how severe the illness is manifested.
 */
public final class UrgencyEstimator {

    private static UrgencyEstimator instance;
    private Map<Urgency, HashMap<IllnessType, Integer>> algorithm;
    private final int magic10 = 10;
    private final int magic20 = 20;
    private final int magic30 = 30;
    private final int magic70 = 70;
    private final int magic40 = 40;
    private final int magic60 = 60;
    private final int magic50 = 50;
    private final int magic80 = 80;
    private UrgencyEstimator() {
        algorithm = new HashMap<Urgency, HashMap<IllnessType, Integer>>() {
            {
                put(Urgency.IMMEDIATE,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, magic60);
                                put(IllnessType.ALLERGIC_REACTION, magic50);
                                put(IllnessType.BROKEN_BONES, magic80);
                                put(IllnessType.BURNS, magic40);
                                put(IllnessType.CAR_ACCIDENT, magic30);
                                put(IllnessType.CUTS, magic50);
                                put(IllnessType.FOOD_POISONING, magic50);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.HEART_DISEASE, magic40);
                                put(IllnessType.HIGH_FEVER, magic70);
                                put(IllnessType.PNEUMONIA, magic80);
                                put(IllnessType.SPORT_INJURIES, magic70);
                                put(IllnessType.STROKE, 0);

                            }
                        });

                put(Urgency.URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, magic40);
                                put(IllnessType.ALLERGIC_REACTION, magic30);
                                put(IllnessType.BROKEN_BONES, magic50);
                                put(IllnessType.BURNS, magic20);
                                put(IllnessType.CAR_ACCIDENT, magic20);
                                put(IllnessType.CUTS, magic30);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.FOOD_POISONING, magic20);
                                put(IllnessType.HEART_DISEASE, magic20);
                                put(IllnessType.HIGH_FEVER, magic40);
                                put(IllnessType.PNEUMONIA, magic50);
                                put(IllnessType.SPORT_INJURIES, magic50);
                                put(IllnessType.STROKE, 0);
                            }
                        });

                put(Urgency.LESS_URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, magic10);
                                put(IllnessType.ALLERGIC_REACTION, magic10);
                                put(IllnessType.BROKEN_BONES, magic20);
                                put(IllnessType.BURNS, magic10);
                                put(IllnessType.CAR_ACCIDENT, magic10);
                                put(IllnessType.CUTS, magic10);
                                put(IllnessType.FOOD_POISONING, 0);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.HEART_DISEASE, magic10);
                                put(IllnessType.HIGH_FEVER, 0);
                                put(IllnessType.PNEUMONIA, magic10);
                                put(IllnessType.SPORT_INJURIES, magic20);
                                put(IllnessType.STROKE, 0);
                            }
                        });

            }
        };
    }

    public static UrgencyEstimator getInstance() {
        if (instance == null) {
            instance = new UrgencyEstimator();
        }
        return instance;
    }

    //called by doctors and nurses
    public Urgency estimateUrgency(IllnessType illnessType, int severity) {

        if (severity >= algorithm.get(Urgency.IMMEDIATE).get(illnessType)) {
            return Urgency.IMMEDIATE;
        }
        if (severity >= algorithm.get(Urgency.URGENT).get(illnessType)) {
            return Urgency.URGENT;
        }
        if (severity >= algorithm.get(Urgency.LESS_URGENT).get(illnessType)) {
            return Urgency.LESS_URGENT;
        }
        return Urgency.NON_URGENT;
    }
}
