package org.example.modules.featureExtraction;

import java.util.HashMap;

class FeaturesManager {
    private static FeaturesManager instance;

    private FeaturesManager() {
    }

    public static FeaturesManager getInstance() {
        if (instance == null) {
            instance = new FeaturesManager();
        }
        return instance;
    }

    public FeaturesVector extractFeatures(Article article) {
        FeaturesVector featuresVector = new FeaturesVector();
        featuresVector.setC1(extractCountryNameFromTitle(article));
        featuresVector.setC2(extractCapitalNameFromText(article));
        featuresVector.setC3(extractContinentNameFromText(article));
        featuresVector.setC4(isDollarInText(article));
        featuresVector.setC5(countPoliticiansInText(article));
        featuresVector.setC6(averageWordLength(article));
        featuresVector.setC7(countWordsInText(article));
        featuresVector.setC8(numberOfEventsInText(article));
        featuresVector.setC9(numberOfUniqueWords(article));
        featuresVector.setC10(mostFrequentWord(article));
        featuresVector.normalize();
        return featuresVector;

    }

    private String extractCountryNameFromTitle(Article article) {
        String[] westgermany = {"west-germany", "west-german", "german", "germany", "west germany", "west german"};
        String[] usa = {"usa", "us", "united states", "united states of america", "america", "american"};
        String[] france = {"france", "french"};
        String[] uk = {"uk", "united kingdom", "britain", "british", "great britain"};
        String[] canada = {"canada", "canadian"};
        String[] japan = {"japan", "japanese"};
        String title = article.getTitle();
        for (String s : title.toLowerCase().split(" "))
        {
            for (String s1 : westgermany)
                if (s.equals(s1))
                    return "west-germany";
            for (String s1 : usa)
                if (s.equals(s1))
                    return "usa";
            for (String s1 : france)
                if (s.equals(s1))
                    return "france";
            for (String s1 : uk)
                if (s.equals(s1))
                    return "uk";
            for (String s1 : canada)
                if (s.equals(s1))
                    return "canada";
            for (String s1 : japan)
                if (s.equals(s1))
                    return "japan";
        }
        return null;
    }

    private String extractCapitalNameFromText(Article article) {
        String text = article.getBody();
        for (String s : text.toLowerCase().split(" "))
            if(s.equals("bonn") || s.equals("washington")
                    || s.equals("paris") || s.equals("london")
                    || s.equals("ottawa") || s.equals("tokyo"))
                return s;
        return null;
    }

    private String extractContinentNameFromText(Article article) {
        HashMap<String, Integer> continents = new HashMap<>();
        continents.put("europe", 0);
        continents.put("america", 0);
        continents.put("asia", 0);
        String text = article.getBody();
        for (String s : text.toLowerCase().split(" ")) {
            if (continents.containsKey(s))
                continents.put(s, continents.get(s) + 1);
        }

        int max = 0;
        String continent = null;
        for (String s : continents.keySet()) {
            if (continents.get(s) > max) {
                max = continents.get(s);
                continent = s;
            }
        }
        return continent;
    }

    private boolean isDollarInText(Article article) {
        String text = article.getBody();
        String[] dollarSynonyms = {"dollar", "usd", "us dollar", "us dollars", "dollar's", "dollars", "dlr", "dlrs"};
        for (String s : dollarSynonyms)
            if (text.toLowerCase().contains(s))
                return true;
        return false;
    }

    private int countPoliticiansInText(Article article) {
        String text = article.getBody();
        String[] politicians = {"Ronald Reagan", "George Bush", "Tip O'Neill", "Robert Byrd", "Dan Quayle", "Michael Dukakis",
        "Helmut Kohl", "Hans-Dietrich Genscher", "Richard von Weizsäcker", "Theo Waigel", "Gerhard Stoltenberg", "Rita Sussmuth",
        "Yasuhiro Nakasone", "Noboru Takeshita", "Sosuke Uno", "Kiichi Miyazawa", "Shintaro Abe", "Hajime Tamura",
        "Brian Mulroney", "John Turner", "Jean Chrétien", "Donald Johnston", "Michael Wilson", "Flora MacDonald",
        "Jacques Chirac", "François Mitterrand", "Jacques Delors", "Édouard Balladur", "Alain Juppé", "Pierre Bérégovoy",
                "Margaret Thatcher", "Neil Kinnock", "John Major", "Michael Heseltine", "Douglas Hurd", "Nigel Lawson"};
        int count = 0;
        for (String s : politicians)
            if (text.contains(s))
                count++;
        return count;
    }

    private float averageWordLength(Article article) {
        String text = article.getBody();
        String[] words = text.split(" ");
        int sum = 0;
        for (String s : words)
            sum += s.length();
        return (float) sum / words.length;
    }

    private int countWordsInText(Article article) {
        String text = article.getBody();
        return text.split(" ").length;
    }

    private int numberOfEventsInText(Article article) {
        String text = article.getBody();
        String[] events = {"Christmas", "Thanksgiving", "Independence Day", "New Year’s Day", "Memorial Day", "Labor Day", "Oktoberfest", "German Unity Day", "Carnival", "Easter", "Pentecost", "Golden Week", "Obon Festival", "Tanabata", "Hanami", "Shichi-Go-San", "Canada Day", "Victoria Day", "Remembrance Day", "Labour Day", "Thanksgiving", "Christmas", "Bastille Day", "Armistice Day", "Easter", "Christmas", "New Year’s Day", "Ascension Day", "Christmas", "New Year’s Day", "Remembrance Day", "Bonfire Night", "Easter", "Trooping the Colour"};
        int count = 0;
        for (String s : events)
            if (text.contains(s))
                count++;
        return count;
    }

    private int numberOfUniqueWords(Article article) {
        String text = article.getBody();
        String[] words = text.split(" ");
        HashMap<String, Integer> uniqueWords = new HashMap<>();
        for (String s : words) {
            if (uniqueWords.containsKey(s))
                uniqueWords.put(s, uniqueWords.get(s) + 1);
            else
                uniqueWords.put(s, 1);
        }
        return uniqueWords.size();
    }

    private String mostFrequentWord(Article article) {
        String text = article.getBody();
        String[] words = text.split(" ");
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (String s : words) {
            if (wordCount.containsKey(s))
                wordCount.put(s, wordCount.get(s) + 1);
            else
                wordCount.put(s, 1);
        }
        int max = 0;
        String mostFrequentWord = null;
        for (String s : wordCount.keySet()) {
            if (wordCount.get(s) > max) {
                max = wordCount.get(s);
                mostFrequentWord = s;
            }
        }
        return mostFrequentWord;
    }
}
