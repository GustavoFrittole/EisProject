package it.unipd.dei.eis.source.Guardian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuardianWrapperTest {
    //Difficile da rendere autonoma dall' API client senza modifiche
    //  strutturali non desiderabili
    private static String yorApiKey = "";

    @Test
    void retriveArticlesExampleTest() {
        int artPerPage = 3, totPages = 3;
        GuardianWrapper guardianWrapper = new GuardianWrapper(yorApiKey, "random query", artPerPage, totPages);
        guardianWrapper.retriveArticles();
        for (int i = 0; i < artPerPage * totPages; i++)
            assertNotNull(guardianWrapper.getArticle(0));
    }

    @Test
    void retriveArticlesOutOfBoundsTest() {
        int artPerPage = 3, totPages = 3;
        GuardianWrapper guardianWrapper= new GuardianWrapper(yorApiKey, "random query", artPerPage, totPages);
        guardianWrapper.retriveArticles();
        assertNull(guardianWrapper.getArticle(-1));
        assertNull(guardianWrapper.getArticle(artPerPage*totPages));
        assertEquals(guardianWrapper.getTotPages(), totPages);
    }

    @Test
    void retriveArticlesTooManyPagesTest() {
        int bigNumber = 10000;
        GuardianWrapper guardianWrapper = new GuardianWrapper(yorApiKey, "random query", 200, 10000);
        guardianWrapper.retriveArticles();
        assertTrue(guardianWrapper.getTotPages() < bigNumber);
    }
}