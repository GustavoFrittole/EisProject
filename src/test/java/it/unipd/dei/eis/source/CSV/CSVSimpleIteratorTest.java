package it.unipd.dei.eis.source.CSV;

import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SimpleArticleIterator;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CSVSimpleIteratorTest {

    @Test
    void shouldSayHasNextTest() {
        CSVWrapper mockeCsvWrapper = mock(CSVWrapper.class);
        when(mockeCsvWrapper.getSimpleArticle(0)).thenReturn(new SimpleArticle("test", "test"));
        when(mockeCsvWrapper.getArticlesNumber()).thenReturn(1);
        Iterator<SimpleArticle> csvSimpleArticleIterator = new CSVSimpleIterator(mockeCsvWrapper);
        assertTrue(csvSimpleArticleIterator.hasNext());
    }
    @Test
    void hasNextNullTest() {
        CSVWrapper mockeCsvWrapper = mock(CSVWrapper.class);
        when(mockeCsvWrapper.getSimpleArticle(0)).thenReturn(null);
        when(mockeCsvWrapper.getArticlesNumber()).thenReturn(1);
        Iterator<SimpleArticle> csvSimpleArticleIterator = new CSVSimpleIterator(mockeCsvWrapper);
        assertFalse(csvSimpleArticleIterator.hasNext());
    }
    @Test
    void hasNextOutOfRangeTest() {
        CSVWrapper mockeCsvWrapper = mock(CSVWrapper.class);
        when(mockeCsvWrapper.getSimpleArticle(0)).thenReturn(new SimpleArticle("test", "test"));
        when(mockeCsvWrapper.getArticlesNumber()).thenReturn(0);
        Iterator<SimpleArticle> csvSimpleArticleIterator = new CSVSimpleIterator(mockeCsvWrapper);
        assertFalse(csvSimpleArticleIterator.hasNext());
    }

    @Test
    void shouldReturnNextTest() {
        CSVWrapper mockeCsvWrapper = mock(CSVWrapper.class);
        when(mockeCsvWrapper.getSimpleArticle(0)).thenReturn(new SimpleArticle("testT", "testB"));
        when(mockeCsvWrapper.getArticlesNumber()).thenReturn(1);
        Iterator<SimpleArticle> csvSimpleArticleIterator = new CSVSimpleIterator(mockeCsvWrapper);
        SimpleArticle result = csvSimpleArticleIterator.next();
        assertTrue(result.getTitle().equals("testT") && result.getBody().equals("testB"));
    }
    //next si basa su hasNext dunque non serve testare gli altri casi (white box)
}