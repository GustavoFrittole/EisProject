package it.unipd.dei.eis.record;

import it.unipd.dei.eis.analyze.WeightedToken;
import it.unipd.dei.eis.source.SimpleArticle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AssetsUtilsTest {
    //se viene creato un file a questo path, vine poi eliminato automaticamente
    static String filePath;

    @Test
    void saveWeightedWordsInvalidPathTest() {
        filePath = "this/path/does/not/exist.txt";
        assertThrows(IOException.class, () -> {
            AssetsUtils.saveWeightedWords(new ArrayList<WeightedToken>(), filePath, 6);
        });
    }
    @Test
    void saveArticlesToFileTestInvalidPathTest() {
        filePath = "this/path/does/not/exist.txt";
        AssetsUtils.setFilePath(filePath);
        assertThrows(IOException.class, () -> {
            AssetsUtils.saveArticlesToFile(new ArrayList<SimpleArticle>().iterator());
        });
    }
    @Test
    void loadArticlesFromFileInvalidPathTest() {
        filePath = "this/path/does/not/exist.txt";
        AssetsUtils.setFilePath(filePath);
        assertThrows(IOException.class, AssetsUtils::loadArticlesFromFile);
    }
    @Test
    void saveWeightedWordsValidArgumentsTest() {
        filePath = "src/test/resources/record.txt";
        List<String> expectedList = Arrays.asList(new String[]{"dieci", "10", "cinque", "5", "tre", "3", "due", "2"});
        Iterator<String> expected = expectedList.iterator();
        List<WeightedToken> weightedTokens = Arrays.asList(
                new WeightedToken("dieci", 10),
                new WeightedToken("cinque", 5),
                new WeightedToken("tre", 3),
                new WeightedToken("due", 2));
        try {
            //wordsToPrint > weightedToken.size()
            AssetsUtils.saveWeightedWords(weightedTokens, filePath, 6);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (Scanner scan = new Scanner(new File(filePath))) {
            while (scan.hasNextLine()) {
                Scanner scanLine = new Scanner(scan.nextLine());
                assertEquals(scanLine.next(), expected.next());
                assertEquals(scanLine.next(), expected.next());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        expected = expectedList.iterator();
        try {
            //wordsToPrint < weightedToken.size()
            AssetsUtils.saveWeightedWords(weightedTokens, filePath, 2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (Scanner scan = new Scanner(new File(filePath))) {
            while (scan.hasNextLine()) {
                Scanner scanLine = new Scanner(scan.nextLine());
                assertEquals(scanLine.next(), expected.next());
                assertEquals(scanLine.next(), expected.next());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void saveArticlesToFileTest() {
        filePath = filePath = "src/test/resources/testAAS.csv";
        AssetsUtils.setFilePath(filePath);
        try {
            //crea file + header
            AssetsUtils.saveArticlesToFile(Arrays.asList(new SimpleArticle[]{
                    new SimpleArticle("This is a title", "This is a body"),
                    new SimpleArticle("This is, \"another\", title", "This is another a body")
            }).iterator());
            //append
            AssetsUtils.saveArticlesToFile(Arrays.asList(new SimpleArticle[]{
                    new SimpleArticle("Third", "Article"),
            }).iterator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Scanner scan = new Scanner(new File(filePath))) {
            assertEquals(scan.nextLine(), "Title,Body");
            assertEquals(scan.nextLine(), "This is a title,This is a body");
            assertEquals(scan.nextLine(), "\"This is, \"\"another\"\", title\",This is another a body");
            assertEquals(scan.nextLine(), "Third,Article");
            if(scan.hasNextLine()) {
                assertEquals(scan.nextLine(), "");
                assertFalse(scan.hasNextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadArticlesFromFileTest() {
        AssetsUtils.setFilePath("src/test/resources/testAAL.csv");
        List<SimpleArticle> simpleArticles;
        try {
            simpleArticles = AssetsUtils.loadArticlesFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(simpleArticles);
        assertEquals("This is a titleThis is a body",
                simpleArticles.get(0).getTitle() + simpleArticles.get(0).getBody());
        assertEquals("",
                simpleArticles.get(1).getTitle() + simpleArticles.get(1).getBody());
        assertEquals("This is, \"another\", titleThis is another a body",
                simpleArticles.get(2).getTitle() + simpleArticles.get(2).getBody());
        assertEquals(simpleArticles.size(), 3);
    }

    @Test
    void loadStopListValidArgumentTest() {
        Iterator<String> stopWords = null;
        try {
             stopWords= AssetsUtils.loadStopList("src/test/resources/stoplist.txt").iterator();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(stopWords);
        Iterator<String> expected = Arrays.asList(new String[]{
                "a",
                "about",
                "above",
                "after",
                "again"
        }).iterator();
        while(stopWords.hasNext() || expected.hasNext()){
            assertEquals(stopWords.next(), expected.next());
        }
    }

    @AfterEach
    void cleanUp(){
        if(filePath != null) {
            File file = new File(filePath);
            if(file.exists())
                file.delete();
        }
    }
}