package it.unipd.dei.eis.analyze;

import it.unipd.dei.eis.source.SimpleArticle;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleAnalyzerTest {

    @Test
    @Order(1)
    void countOccurrencesPerArticleIllegalArgumentTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArticleAnalyzer.countOccurrencesPerArticle(null);
        });
    }

    @Test
    @Order(2)
    void setStopListIllegalArgumentTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArticleAnalyzer.setStopList(null);
        });
    }

    @Test
    @Order(3)
    void countOccurrencesPerArticleEmptyIterableTest() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        List<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles);
        assertTrue(weightedTokens.isEmpty());
    }

    @Test
    @Order(4)
    void countOccurrencesPerArticleEmptyArticlesTest() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        simpleArticles.add(new SimpleArticle("", ""));
        simpleArticles.add(new SimpleArticle("", ""));
        List<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles);
        assertTrue(weightedTokens.isEmpty());
    }

    @Test
    @Order(5)
    void countOccurrencesPerArticleExamplesTest() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        simpleArticles.add(new SimpleArticle("uno due", "due tre quattro"));
        simpleArticles.add(new SimpleArticle("due", "TRE TRE duE QUATTRO"));
        simpleArticles.add(new SimpleArticle("DUE", "CINqUE"));

        Iterator<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles).iterator();
        Iterator<WeightedToken> expected = Arrays.asList(new WeightedToken[]{
                new WeightedToken("due", 3),
                new WeightedToken("tre", 2),
                new WeightedToken("quattro", 2),
                new WeightedToken("uno", 1),
                new WeightedToken("cinque", 1),
        }).iterator();
        //se ottengo più o meno parole del dovuto, un .next() invaliderà il test
        while (weightedTokens.hasNext() && expected.hasNext())
            assertEquals(weightedTokens.next(), expected.next());
    }

    @Test
    @Order(6)
    void countOccurrencesPerArticleExamplesWithSymbolsTest() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        simpleArticles.add(new SimpleArticle("uno£(% \"due\"63798", "due44, 3tre3, quattro%%%"));
        simpleArticles.add(new SimpleArticle("*due---", "TRE ùùùùççç TRE .QUATTRO dUe"));
        simpleArticles.add(new SimpleArticle("...DU--E,", "CINq0000UE"));

        Iterator<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles).iterator();
        Iterator<WeightedToken> expected = Arrays.asList(new WeightedToken[]{
                new WeightedToken("due", 3),
                new WeightedToken("tre", 2),
                new WeightedToken("quattro", 2),
                new WeightedToken("uno", 1),
                new WeightedToken("cinque", 1),
        }).iterator();
        //se ottengo più o meno parole del dovuto, un .next() invaliderà il test
        while (weightedTokens.hasNext() || expected.hasNext())
            assertEquals(weightedTokens.next(), expected.next());
    }

    @Test
    @Order(7)
    void countOccurrencesPerArticleExamplesWithStopListTest() {
        List<SimpleArticle> simpleArticles = new LinkedList<>();
        simpleArticles.add(new SimpleArticle("uno due", "due tre quattro"));
        simpleArticles.add(new SimpleArticle("due", "TRE TRE duE QUATTRO"));
        simpleArticles.add(new SimpleArticle("DUE", "CINqUE"));

        ArticleAnalyzer.setStopList(Arrays.asList(new String[]{"uno", "tre"}));

        Iterator<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(simpleArticles).iterator();
        Iterator<WeightedToken> expected = Arrays.asList(new WeightedToken[]{
                new WeightedToken("due", 3),
                new WeightedToken("quattro", 2),
                new WeightedToken("cinque", 1),
        }).iterator();
        //se ottengo più o meno parole del dovuto, un .next() invaliderà il test
        while (weightedTokens.hasNext() || expected.hasNext())
            assertEquals(weightedTokens.next(), expected.next());
    }
}