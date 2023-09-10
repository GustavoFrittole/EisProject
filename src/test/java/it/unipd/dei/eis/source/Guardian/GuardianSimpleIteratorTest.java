package it.unipd.dei.eis.source.Guardian;

import com.apitheguardian.bean.Article;
import it.unipd.dei.eis.source.SimpleArticle;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GuardianSimpleIteratorTest {

    @Test
    void shouldSayHasNextTest() {
        GuardianWrapper mockGuardianWrapper = mock(GuardianWrapper.class);
        Article mockArt = mock(Article.class);
        when(mockArt.getBodyText()).thenReturn("testB");
        when(mockArt.getWebTitle()).thenReturn("testT");
        when(mockGuardianWrapper.getSimpleArticle(0, 0)).thenReturn(mockArt);
        when(mockGuardianWrapper.getArticlesPerPage()).thenReturn(1);
        when(mockGuardianWrapper.getTotPages()).thenReturn(1);
        Iterator<SimpleArticle> GuardianSimpleArticleIterator = new GuardianSimpleIterator(mockGuardianWrapper);
        assertFalse(GuardianSimpleArticleIterator.hasNext());
    }
 /*   @Test
    void hasNextNullTest() {
        GuardianWrapper mockGuardianWrapper = mock(GuardianWrapper.class);
        when(mockGuardianWrapper.getSimpleArticle(0)).thenReturn(null);
        when(mockGuardianWrapper.getArticlesNumber()).thenReturn(1);
        Iterator<SimpleArticle> GuardianSimpleArticleIterator = new GuardianSimpleIterator(mockGuardianWrapper);
        assertFalse(GuardianSimpleArticleIterator.hasNext());
    }
    @Test
    void hasNextOutOfRangeTest() {
        GuardianWrapper mockeGuardianWrapper = mock(GuardianWrapper.class);
        when(mockeGuardianWrapper.getSimpleArticle(0)).thenReturn(new SimpleArticle("test", "test"));
        when(mockeGuardianWrapper.getArticlesNumber()).thenReturn(0);
        Iterator<SimpleArticle> GuardianSimpleArticleIterator = new GuardianSimpleIterator(mockeGuardianWrapper);
        assertFalse(GuardianSimpleArticleIterator.hasNext());
    }

    @Test
    void shouldReturnNextTest() {
        GuardianWrapper mockeGuardianWrapper = mock(GuardianWrapper.class);
        when(mockeGuardianWrapper.getSimpleArticle(0)).thenReturn(new SimpleArticle("testT", "testB"));
        when(mockeGuardianWrapper.getArticlesNumber()).thenReturn(1);
        Iterator<SimpleArticle> GuardianSimpleArticleIterator = new GuardianSimpleIterator(mockeGuardianWrapper);
        SimpleArticle result = GuardianSimpleArticleIterator.next();
        assertTrue(result.getTitle().equals("testT") && result.getBody().equals("testB"));
    }
    @Test
    void hasNext() {
    }

    @Test
    void next() {
    }*/
}