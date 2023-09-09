package it.unipd.dei.eis.record;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AssetsUtilsTest {

    @Test
    void saveArticlesToFile() {
    }

    @Test
    void loadArticlesFromFile() {
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
    @Test
    void loadStopListWrongURLTest() {
        assertThrows(FileNotFoundException.class, () -> {
            AssetsUtils.loadStopList("src/test/resources/idontexist.txt");
        });
    }
    @Test
    void saveWeightedWords() {
    }
}