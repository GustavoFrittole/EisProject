package it.unipd.dei.eis.analyze;

import it.unipd.dei.eis.source.SimpleArticle;

import java.util.*;

/**
 * Metodi orientati all'analisi di articoli. L'unico formato attualmente
 * supportato è {@link SimpleArticle SimpleArticle}
 */
public class ArticleAnalyzer {
    private static List<String> stopList;

    /**
     * Imposta una lista di parole che non saranno contate nelle successive esecuzioni
     * dei metodi {@link #countOccurrences(Iterable) countOccurrences} e
     * {@link #countOccurrencesPerArticle(Iterable) countOccurrencesPerFIle}
     *
     * @param stopList lista delle stop words
     */
    public static void setStopList(List<String> stopList) {
        if (stopList == null)
            throw new IllegalArgumentException("Null argument");
        ArticleAnalyzer.stopList = stopList;
    }

    /**
     * Estrae le parole utilizzate nei dati articoli e associa a esse un peso.
     * Il peso corrisponde al numero di volte in cui appaiono complessivamente.
     *
     * @param iterable struttura contenente gli articoli da analizzare
     * @return una lista di WeightedToken, ovvero coppie parola-peso
     */
    @Deprecated
    public static List<WeightedToken> countOccurrences(Iterable<SimpleArticle> iterable) {
        String text = mergeArticles(iterable);
        String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        return wordsOccurrencesInString(words);
    }

    /**
     * Estrae le parole utilizzate nei dati articoli e associa a esse un peso.
     * Tutti i caratteri diversi da lettere sono ignorati.
     * Non è cap sensitive.
     * Il peso corrisponde al numero di documenti in cui appaiono.
     *
     * @param iterable struttura contenente gli articoli da analizzare
     * @return una lista di {@link WeightedToken WeightedToken}
     * @throws IllegalArgumentException se l'argomento è nullo
     */
    public static List<WeightedToken> countOccurrencesPerArticle(Iterable<SimpleArticle> iterable) {
        if (iterable == null)
            throw new IllegalArgumentException("Null argument");
        List<Set<String>> wordsPerArticles = new LinkedList<>();
        for (SimpleArticle simpleArticle : iterable) {
            String[] words = (simpleArticle.getTitle() + " " + simpleArticle.getBody())
                    .replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            wordsPerArticles.add(new HashSet<>(Arrays.asList(words)));
        }
        return wordsOccurrencesInSets(wordsPerArticles);
    }

    /**
     * Funzione accessoria di {@link #countOccurrences(Iterable) countOccurrences}
     *
     * @param iterable
     * @return
     */
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

    /**
     * Funzione accessoria di {@link #countOccurrences(Iterable) countOccurrences}
     *
     * @param words
     * @return
     */
    private static List<WeightedToken> wordsOccurrencesInString(String[] words) {
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

    /**
     * Funzione accessoria di {@link #countOccurrencesPerArticle(Iterable) countOccurrencesPerFIle}
     *
     * @param wordsInArticles
     * @return
     */
    private static List<WeightedToken> wordsOccurrencesInSets(List<Set<String>> wordsInArticles) {
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

    /**
     * Funzione accessoria, rimuove le stop words se impostate
     *
     * @param map
     */
    private static void removeStopWords(Map<String, Integer> map) {
        if (stopList != null && !stopList.isEmpty()) {
            for (String stopWord : stopList) {
                map.remove(stopWord);
            }
        }
    }
}
