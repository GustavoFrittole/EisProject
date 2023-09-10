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
        when(mockGuardianWrapper.getArticle(0)).thenReturn(mockArt);
        when(mockGuardianWrapper.getArticlesPerPage()).thenReturn(1);
        when(mockGuardianWrapper.getTotPages()).thenReturn(1);
        Iterator<SimpleArticle> GuardianSimpleArticleIterator = new GuardianSimpleIterator(mockGuardianWrapper);
        assertTrue(GuardianSimpleArticleIterator.hasNext());
    }
    @Test
    void hasNextNullTest() {

        GuardianWrapper mockGuardianWrapper = mock(GuardianWrapper.class);
        when(mockGuardianWrapper.getArticle(0)).thenReturn(null);
        when(mockGuardianWrapper.getArticlesPerPage()).thenReturn(1);
        when(mockGuardianWrapper.getTotPages()).thenReturn(1);
        Iterator<SimpleArticle> GuardianSimpleArticleIterator = new GuardianSimpleIterator(mockGuardianWrapper);
        assertFalse(GuardianSimpleArticleIterator.hasNext());
    }

    @Test
    void hasNextOutOfRangeTest() {
        GuardianWrapper mockGuardianWrapper = mock(GuardianWrapper.class);
        Article mockArt = mock(Article.class);
        when(mockArt.getBodyText()).thenReturn("testB");
        when(mockArt.getWebTitle()).thenReturn("testT");
        when(mockGuardianWrapper.getArticle(0)).thenReturn(mockArt);
        when(mockGuardianWrapper.getArticlesPerPage()).thenReturn(1);
        when(mockGuardianWrapper.getTotPages()).thenReturn(0);
        Iterator<SimpleArticle> GuardianSimpleArticleIterator = new GuardianSimpleIterator(mockGuardianWrapper);
        assertFalse(GuardianSimpleArticleIterator.hasNext());
    }

    @Test
    void shouldReturnNextTest() {
        GuardianWrapper mockGuardianWrapper = mock(GuardianWrapper.class);
        Article mockArt = mock(Article.class);
        when(mockArt.getBodyText()).thenReturn("testB");
        when(mockArt.getWebTitle()).thenReturn("testT");
        when(mockGuardianWrapper.getArticle(0)).thenReturn(mockArt);
        when(mockGuardianWrapper.getArticlesPerPage()).thenReturn(1);
        when(mockGuardianWrapper.getTotPages()).thenReturn(1);
        Iterator<SimpleArticle> GuardianSimpleArticleIterator = new GuardianSimpleIterator(mockGuardianWrapper);
        SimpleArticle result = GuardianSimpleArticleIterator.next();
        assertTrue(result.getTitle().equals("testT") && result.getBody().equals("testB"));
    }
}