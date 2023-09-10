package it.unipd.dei.eis;

import it.unipd.dei.eis.analyze.ArticleAnalyzer;
import it.unipd.dei.eis.analyze.WeightedToken;
import it.unipd.dei.eis.record.AssetsUtils;
import it.unipd.dei.eis.source.CSV.CSVWrapper;
import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SourceWrapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private static String filePath = "src/test/resources/test.txt";
    @Test
    public void systemTest(){
        //use example
        SourceWrapper sourceWrapper = new CSVWrapper("src/test/resources/exampleCsv.csv", 100);
        sourceWrapper.retriveArticles();
        AssetsUtils.setFilePath(filePath);
        try {
            AssetsUtils.saveArticlesToFile(sourceWrapper.iterator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<SimpleArticle>articleList = null;
        List<String> stopWords = null;
        try {
            articleList = AssetsUtils.loadArticlesFromFile();
            stopWords = AssetsUtils.loadStopList("src/test/resources/stoplist.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArticleAnalyzer.setStopList(stopWords);
        List<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(articleList);
        try {
            AssetsUtils.saveWeightedWords(weightedTokens, filePath, 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //assertions
        File file = new File(filePath);
        try (Scanner scan = new Scanner(new File(filePath))) {
            assertEquals(scan.nextLine(), "title           \t2");
            assertEquals(scan.nextLine(), "this            \t2");
            assertEquals(scan.nextLine(), "is              \t2");
            assertEquals(scan.nextLine(), "body            \t2");
            if(scan.hasNextLine()) {
                assertEquals(scan.nextLine(), "");
                assertFalse(scan.hasNextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //AssetsUtils farà append al fine dei risultati se non eliminato
        if(!file.delete())
            System.err.println("WARNING: src/test/resources/test.txt has to be deleted otherwise test will always fail.");
    }
}