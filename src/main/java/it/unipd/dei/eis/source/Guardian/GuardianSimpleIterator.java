package it.unipd.dei.eis.source.Guardian;


import com.apiguardian.bean.Article;
import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SimpleArticleIterator;

public class GuardianSimpleIterator implements SimpleArticleIterator {
    private final GuardianWrapper guardianWrapper;
    private int current = 0;

    GuardianSimpleIterator(GuardianWrapper guardianWrapper) {
        if (guardianWrapper == null)
            throw new IllegalArgumentException("Argomento nullo");
        this.guardianWrapper = guardianWrapper;
    }

    @Override
    public SimpleArticle next() {
        if (!hasNext())
            return null;
        Article art = guardianWrapper.getArticle(current++);
        return new SimpleArticle(art.getWebTitle(), art.getBodyText());
    }

    @Override
    public boolean hasNext() {
        return current != guardianWrapper.articlesPerPage * guardianWrapper.totPages
                && guardianWrapper.getArticle(current) != null;
    }
}
