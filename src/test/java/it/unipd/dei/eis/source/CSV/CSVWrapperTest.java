package it.unipd.dei.eis.source.CSV;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CSVWrapperTest {
    @Test
    void retriveArticlesInvalidPathTest() {
        CSVWrapper csvWrapper = new CSVWrapper("this/path/does/not/exist.txt", 3);
        assertThrows(RuntimeException.class, csvWrapper::retriveArticles);
    }
    @Test
    void retriveArticlesExampleTest() {
        CSVWrapper csvWrapper = new CSVWrapper("src/test/resources/testCsv.csv", 3);
        csvWrapper.retriveArticles();
        assertTrue(csvWrapper.getSimpleArticle(0).getTitle().equals("Title1"));
        assertTrue(csvWrapper.getSimpleArticle(0).getBody().equals("Body1"));
        assertTrue(csvWrapper.getSimpleArticle(1).getTitle().equals("Title2"));
        assertTrue(csvWrapper.getSimpleArticle(1).getBody().equals("Body2"));
        assertTrue(csvWrapper.getSimpleArticle(2).getTitle().equals("Title3"));
        assertTrue(csvWrapper.getSimpleArticle(2).getBody().equals("Body3"));
        assertTrue(csvWrapper.getSimpleArticle(4) == null);
        assertTrue(csvWrapper.getSimpleArticle(-1) == null);
    }
    @Test
    void retriveArticlesSmallerNumberTest() {
        CSVWrapper csvWrapper = new CSVWrapper("src/test/resources/testCsv.csv", 1);
        csvWrapper.retriveArticles();
        assertTrue(csvWrapper.getSimpleArticle(1) == null &&
                csvWrapper.getSimpleArticle(-1) == null &&
                csvWrapper.getSimpleArticle(0).getTitle().equals("Title1")
        );
    }
    void retriveArticlesBiggerNumberTest() {
        CSVWrapper csvWrapper = new CSVWrapper("src/test/resources/testCsv.csv", 6);
        csvWrapper.retriveArticles();
        assertTrue(csvWrapper.getArticlesNumber() == 3);
        assertTrue(csvWrapper.getSimpleArticle(3) == null);
    }
}