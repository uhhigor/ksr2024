package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.FeaturesVectorException;

import java.util.HashMap;
import java.util.List;

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

    public FeaturesVector extractFeatures(Article article) throws FeaturesVectorException {
        FeaturesVector featuresVector = new FeaturesVector();
        featuresVector.setCountry(article.getCountry());
        featuresVector.set(1, extractCountryNameFromTitle(article));
        featuresVector.set(2, extractCapitalNameFromText(article));
        featuresVector.set(3, extractContinentNameFromText(article));
        featuresVector.set(4, isDollarInText(article));
        featuresVector.set(5, countPoliticiansInText(article));
        featuresVector.set(6, averageWordLength(article));
        featuresVector.set(7, countWordsInText(article));
        featuresVector.set(8, numberOfEventsInText(article));
        featuresVector.set(9, numberOfUniqueWords(article));
        featuresVector.set(10, mostFrequentWord(article));
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
        List<String> title = article.getTitle();
        for (String s : title)
        {
            for (String country : westgermany)
                if (s.equals(country))
                    return "west-germany";
            for (String country : usa)
                if (s.equals(country))
                    return "usa";
            for (String country : france)
                if (s.equals(country))
                    return "france";
            for (String country : uk)
                if (s.equals(country))
                    return "uk";
            for (String country : canada)
                if (s.equals(country))
                    return "canada";
            for (String country : japan)
                if (s.equals(country))
                    return "japan";
        }
        return null;
    }

    private String extractCapitalNameFromText(Article article) {
        List<String> text = article.getBody();
        for (String s : text)
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
        List<String> text = article.getBody();
        for (String s : text) {
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

    private Boolean isDollarInText(Article article) {
        List<String> text = article.getBody();
        String[] dollarSynonyms = {"dollar", "usd", "us dollar", "us dollars", "dollar's", "dollars", "dlr", "dlrs"};
        for (String s : dollarSynonyms)
            if (text.contains(s))
                return true;
        return false;
    }

    private Double countPoliticiansInText(Article article) {
        List<String> text = article.getBody();
        String[] politicians = {"Ronald Reagan", "George Bush", "Tip O'Neill", "Robert Byrd", "Dan Quayle", "Michael Dukakis",
        "Helmut Kohl", "Hans-Dietrich Genscher", "Richard von Weizsäcker", "Theo Waigel", "Gerhard Stoltenberg", "Rita Sussmuth",
        "Yasuhiro Nakasone", "Noboru Takeshita", "Sosuke Uno", "Kiichi Miyazawa", "Shintaro Abe", "Hajime Tamura",
        "Brian Mulroney", "John Turner", "Jean Chrétien", "Donald Johnston", "Michael Wilson", "Flora MacDonald",
        "Jacques Chirac", "François Mitterrand", "Jacques Delors", "Édouard Balladur", "Alain Juppé", "Pierre Bérégovoy",
                "Margaret Thatcher", "Neil Kinnock", "John Major", "Michael Heseltine", "Douglas Hurd", "Nigel Lawson"};
        int count = 0;
        for (String s : politicians)
            if (text.contains(s.toLowerCase()))
                count++;
        return (double) count;
    }

    private Double averageWordLength(Article article) {
        List<String> text = article.getBody();
        int sum = 0;
        for (String s : text)
            sum += s.length();
        return (double) sum / text.size();
    }

    private Double countWordsInText(Article article) {
        List<String> text = article.getBody();
        return (double) text.size();
    }

    private Double numberOfEventsInText(Article article) {
        List<String> text = article.getBody();
        String[] events = {"Christmas", "Thanksgiving", "Independence Day", "New Year’s Day", "Memorial Day", "Labor Day", "Oktoberfest", "German Unity Day", "Carnival", "Easter", "Pentecost", "Golden Week", "Obon Festival", "Tanabata", "Hanami", "Shichi-Go-San", "Canada Day", "Victoria Day", "Remembrance Day", "Labour Day", "Thanksgiving", "Christmas", "Bastille Day", "Armistice Day", "Easter", "Christmas", "New Year’s Day", "Ascension Day", "Christmas", "New Year’s Day", "Remembrance Day", "Bonfire Night", "Easter", "Trooping the Colour"};
        int count = 0;
        for (String s : events)
            if (text.contains(s.toLowerCase()))
                count++;
        return (double) count;
    }

    private Double numberOfUniqueWords(Article article) {
        List<String> text = article.getBody();
        HashMap<String, Integer> uniqueWords = new HashMap<>();
        for (String s : text) {
            if (uniqueWords.containsKey(s))
                uniqueWords.put(s, uniqueWords.get(s) + 1);
            else
                uniqueWords.put(s, 1);
        }
        return (double) uniqueWords.size();
    }

    private String mostFrequentWord(Article article) {
        List<String> text = article.getBody();
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (String s : text) {
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
