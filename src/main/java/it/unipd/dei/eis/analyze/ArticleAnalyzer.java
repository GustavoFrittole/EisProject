package it.unipd.dei.eis.analyze;

import it.unipd.dei.eis.source.SimpleArticle;

import java.util.*;

public class ArticleAnalyzer {
    private static List<String> stopList;

    public static void setStopList(List<String> stopList) {
        ArticleAnalyzer.stopList = stopList;
    }

    public static List<WeightedToken> countOccurrences(Iterable<SimpleArticle> iterable) {
        String text = mergeArticles(iterable);
        String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        return wordsOccurrencesInString(words);
    }
    public static List<WeightedToken> countOccurrencesPerFIle(Iterable<SimpleArticle> iterable) {
        List<Set<String>> wordsPerArticles = new LinkedList<>();
        for(SimpleArticle simpleArticle : iterable){
            String[] words = (simpleArticle.getTitle() + " " + simpleArticle.getBody())
                    .replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            wordsPerArticles.add(new HashSet<>(Arrays.asList(words)));
        }
        return wordsOccurrencesInSets(wordsPerArticles);
    }

    private static String mergeArticles(Iterable<SimpleArticle> iterable) {
        StringBuilder stringBuilder = new StringBuilder();
        for (SimpleArticle simpleArticle : iterable) {
            stringBuilder.append(simpleArticle.getTitle());
            stringBuilder.append(" ");
            stringBuilder.append(simpleArticle.getBody());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private static List<WeightedToken> wordsOccurrencesInString(String[] words)
    {
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            int val = map.getOrDefault(word, 0);
            map.put(word, val + 1);
        }
        removeStopWords(map);

        ArrayList<WeightedToken> weightedTokens = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            weightedTokens.add(new WeightedToken(entry.getKey(), entry.getValue()));
        }
        weightedTokens.sort(Collections.reverseOrder());
        return weightedTokens;
    }

    private static List<WeightedToken> wordsOccurrencesInSets(List<Set<String>> wordsInArticles)
    {
        HashMap<String, Integer> map = new HashMap<>();
        for (Set<String> wordsInArticle : wordsInArticles) {
            for (String word : wordsInArticle) {
                int val = map.getOrDefault(word, 0);
                map.put(word, val + 1);
            }
        }
        removeStopWords(map);

        ArrayList<WeightedToken> weightedTokens = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            weightedTokens.add(new WeightedToken(entry.getKey(), entry.getValue()));
        }
        weightedTokens.sort(Collections.reverseOrder());
        return weightedTokens;
    }

    private static void removeStopWords(Map<String, Integer> map){
        if (stopList != null && !stopList.isEmpty()) {
            for (String stopWord : stopList) {
                map.remove(stopWord);
            }
        }
    }
}
