package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.FeaturesVectorException;

import java.util.*;

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
        featuresVector.set(1, extractCountryName(article));
        featuresVector.set(2, extractCity(article));
        featuresVector.set(3, extractContinentName(article));
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

    private String extractCountryName(Article article) {
        String[] westgermany = {"west-german", "german", "west german"};
        String[] usa = {"usa", "america", "united states", "states"};
        String[] france = {"france", "french"};
        String[] uk = {"uk", "united kingdom", "kingdom", "britain", "british"};
        String[] canada = {"canada", "canadian"};
        String[] japan = {"japan"};
        String text = article.getAllText();

        List<String> all = new ArrayList<>();
        all.addAll(Arrays.stream(westgermany).toList());
        all.addAll(Arrays.stream(usa).toList());
        all.addAll(Arrays.stream(france).toList());
        all.addAll(Arrays.stream(uk).toList());
        all.addAll(Arrays.stream(canada).toList());
        all.addAll(Arrays.stream(japan).toList());

        Map<String, Integer> wordsIndexes = new HashMap<>();
        for (String country : all) {
            int index = text.indexOf(country);
            if (index != -1) {
                wordsIndexes.put(country, index);
            }
        }
        if(wordsIndexes.isEmpty())
            return "";
        return Collections.min(wordsIndexes.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    private String extractCity(Article article) {
        String text = article.getAllText();
        List<String> german_cities = List.of("bonn", "berlin", "dortmund", "essen", "frankfurt", "hamburg", "hannover");
        List<String> usa_cities = List.of("washington", "new york", "los angeles", "chicago", "houston", "phoenix", "philadelphia");
        List<String> france_cities = List.of("paris", "marseille", "lyon", "toulouse", "nice", "strasbourg", "bordeaux");
        List<String> uk_cities = List.of("london", "birmingham", "manchester", "glasgow", "sheffield", "liverpool", "leeds", "bristol", "nottingham");
        List<String> canada_cities = List.of("ottawa", "toronto", "montreal", "vancouver", "calgary", "edmonton", "quebec");
        List<String> japan_cities = List.of("tokyo", "yokohama", "osaka", "nagoya", "sapporo", "fukuoka", "kobe", "kyoto", "kawasaki", "saitama");

        List<String> all = new ArrayList<>();
        all.addAll(german_cities);
        all.addAll(usa_cities);
        all.addAll(france_cities);
        all.addAll(uk_cities);
        all.addAll(canada_cities);
        all.addAll(japan_cities);

        Map<String, Integer> cityCount = new HashMap<>();

            for (String city : all)
                if(text.contains(city))
                    cityCount.put(city, cityCount.getOrDefault(city, 0) + 1);

        int max = 0;
        String mostFrequentCity = "";
        for (String s : cityCount.keySet()) {
            if (cityCount.get(s) > max) {
                max = cityCount.get(s);
                mostFrequentCity = s;
            }
        }
        return mostFrequentCity;
    }

    private String extractContinentName(Article article) {
        HashMap<String, Integer> continents = new HashMap<>();
        continents.put("europe", 0);
        continents.put("america", 0);
        continents.put("asia", 0);
        String text = article.getAllText();
            for(String continent : continents.keySet())
            {
                if(text.contains(continent))
                    continents.put(continent, continents.get(continent) + 1);
        }

        int max = 0;
        String continent = "";
        for (String s : continents.keySet()) {
            if (continents.get(s) > max) {
                max = continents.get(s);
                continent = s;
            }
        }
        return continent;
    }

    private Boolean isDollarInText(Article article) {
        String text = article.getBody();
        String[] dollarSynonyms = {"dollar", "usd", "dlr"};
        for (String s : dollarSynonyms) {
            if (text.contains(s))
                return true;
        }
        return false;
    }

    private Double countPoliticiansInText(Article article) {
        String text = article.getAllText();
        String[] politicians = {"reagan", "bush", "neill", "byrd", "quayle", "dukakis",
        "kohl", "genscher", "weizsäcker", "waigel", "stoltenberg", "sussmuth",
        "nakasone", "takeshita", "uno", "miyazawa", "abe", "tamura",
        "mulroney", "turner", "chrétien", "johnston", "wilson", "macdonald",
        "chirac", "mitterrand", "delors", "balladur", "juppé", "bérégovoy",
                "thatcher", "kinnock", "major", "heseltine", "hurd", "lawson"};
        int count = 0;
        for (String s : politicians) {
            if (text.contains(s))
                count++;
        }
        return (double) count;
    }

    private Double averageWordLength(Article article) {
        String text = article.getBody();
        int sum = 0;
        for (String s : text.split(" "))
            sum += s.length();
        return (double) sum / text.split(" ").length;
    }

    private Double countWordsInText(Article article) {
        String text = article.getBody();
        return (double) text.split(" ").length;
    }

    private Double numberOfEventsInText(Article article) {
        String text = article.getBody();
        String[] events = {"christmas", "thanksgiving", "independence", "memorial", "labor", "oktoberfest", "unity", "carnival", "easter", "pentecost", "golden", "obon", "tanabata", "hanami", "shichi-go-san", "canada", "victoria", "remembrance", "labour", "thanksgiving", "christmas", "bastille", "armistice", "ascension", "bonfire"};
        int count = 0;
        for (String s : events) {
                if (text.contains(s))
                    count++;
        }
        return (double) count;
    }

    private Double numberOfUniqueWords(Article article) {
        String text = article.getBody();
        HashMap<String, Integer> uniqueWords = new HashMap<>();
        for (String s : text.split(" ")) {
            if (uniqueWords.containsKey(s))
                uniqueWords.put(s, uniqueWords.get(s) + 1);
            else
                uniqueWords.put(s, 1);
        }
        return (double) uniqueWords.size();
    }

    private String mostFrequentWord(Article article) {
        String text = article.getBody();
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (String s : text.split(" ")) {
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
