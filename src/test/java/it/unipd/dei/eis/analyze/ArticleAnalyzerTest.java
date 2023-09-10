package it.unipd.dei.eis.analyze;

import it.unipd.dei.eis.source.SimpleArticle;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleAnalyzerTest {

    @Test
    @Order(1)
    void countOccurrencesPerArticleTestIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArticleAnalyzer.countOccurrencesPerArticle(null);
        });
    }

    @Test
    @Order(2)
    void setStopListTestIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArticleAnalyzer.setStopList(null);
        });
    }

    @Test
    @Order(3)
    void countOccurrencesPerArticleTestEmptyIterable() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        List<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles);
        assertTrue(weightedTokens.isEmpty());
    }

    @Test
    @Order(4)
    void countOccurrencesPerArticleTestEmptyArticles() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        simpleArticles.add(new SimpleArticle("",""));
        simpleArticles.add(new SimpleArticle("",""));
        List<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles);
        assertTrue(weightedTokens.isEmpty());
    }
    @Test
    @Order(5)
    void countOccurrencesPerArticleTestExamples() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        simpleArticles.add(new SimpleArticle("uno due","due tre quattro"));
        simpleArticles.add(new SimpleArticle("due","TRE TRE duE QUATTRO"));
        simpleArticles.add(new SimpleArticle("DUE","CINqUE"));

        Iterator<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles).iterator();
        Iterator<WeightedToken> expected = Arrays.asList(new WeightedToken[]{
                new WeightedToken("due", 3),
                new WeightedToken("tre", 2),
                new WeightedToken("quattro", 2),
                new WeightedToken("uno", 1),
                new WeightedToken("cinque", 1),
        }).iterator();
        //se ottengo più o meno parole del dovuto, un .next() invaliderà il test
        while(weightedTokens.hasNext() && expected.hasNext())
            assertEquals(weightedTokens.next(), expected.next());
    }
    @Test
    @Order(6)
    void countOccurrencesPerArticleTestExamplesWithSymbols() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        simpleArticles.add(new SimpleArticle("uno£(% \"due\"63798","due44, 3tre3, quattro%%%"));
        simpleArticles.add(new SimpleArticle("*due---","TRE ùùùùççç TRE .QUATTRO dUe"));
        simpleArticles.add(new SimpleArticle("...DU--E,","CINq0000UE"));

        Iterator<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles).iterator();
        Iterator<WeightedToken> expected = Arrays.asList(new WeightedToken[]{
                new WeightedToken("due", 3),
                new WeightedToken("tre", 2),
                new WeightedToken("quattro", 2),
                new WeightedToken("uno", 1),
                new WeightedToken("cinque", 1),
        }).iterator();
        //se ottengo più o meno parole del dovuto, un .next() invaliderà il test
        while(weightedTokens.hasNext() || expected.hasNext())
            System.out.println(weightedTokens.next().toString() + expected.next());
    }

    //Essendo la stop list variabile statica, l'ordine conta
    @Test
    @Order(7)
    void countOccurrencesPerArticleTestExamplesWithStopList() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        simpleArticles.add(new SimpleArticle("uno due","due tre quattro"));
        simpleArticles.add(new SimpleArticle("due","TRE TRE duE QUATTRO"));
        simpleArticles.add(new SimpleArticle("DUE","CINqUE"));

        ArticleAnalyzer.setStopList(Arrays.asList(new String[]{"uno", "tre"}));

        Iterator<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles).iterator();
        Iterator<WeightedToken> expected = Arrays.asList(new WeightedToken[]{
                new WeightedToken("due", 3),
                new WeightedToken("quattro", 2),
                new WeightedToken("cinque", 1),
        }).iterator();
        //se ottengo più o meno parole del dovuto, un .next() invaliderà il test
        while(weightedTokens.hasNext() || expected.hasNext())
            assertEquals(weightedTokens.next(), expected.next());
    }
}