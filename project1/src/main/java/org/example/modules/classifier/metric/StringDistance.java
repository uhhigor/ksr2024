package org.example.modules.classifier.metric;

public class StringDistance {
    private static double trigramSimilarity(String s1, String s2) {
        int s1Length = s1.length();
        int s2Length = s2.length();
        if(s1.equals(s2)) {
            return 1;
        }

        int maxLength = Math.max(s1Length, s2Length);
        int numberOfTrigrams = maxLength - 2;

        int trigramSum = 0;
        for(int i = 0; i < numberOfTrigrams; i++) {
            if(i + 3 > s1Length) {
                break;
            }
            String trigram1 = s1.substring(i, i + 3);
            if(s2.contains(trigram1)) {
                trigramSum += 1;
            }
        }
        return (double) trigramSum / (double) numberOfTrigrams;
    }

    public static double calculateDistance(String s1, String s2) {
        return 1 - trigramSimilarity(s1, s2);
    }
}
