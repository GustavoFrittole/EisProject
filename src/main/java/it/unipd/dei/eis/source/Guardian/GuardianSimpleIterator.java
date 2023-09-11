package it.unipd.dei.eis.source.Guardian;


import com.apitheguardian.bean.Article;
import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SimpleArticleIterator;

public class GuardianSimpleIterator implements SimpleArticleIterator {
    private final GuardianWrapper guardianWrapper;
    private int current = 0;

    /**
     * @param guardianWrapper iterazione
     * @throws IllegalArgumentException se l'argomento è nullo
     */
    GuardianSimpleIterator(GuardianWrapper guardianWrapper) {
        if (guardianWrapper == null)
            throw new IllegalArgumentException("Null argument");
        this.guardianWrapper = guardianWrapper;
    }

    @Override
    public boolean hasNext() {
        //essendo gli articoli mantenuti nei wrapper come array e non liste è
        //possibile che i campi non siano stati tutti popolati -> ci si ferma al primo valore nullo
        return current != guardianWrapper.getArticlesPerPage() * guardianWrapper.getTotPages()
                && guardianWrapper.getArticle(current) != null;
    }

    /**
     * @return il prossimo elemento dell'iterazione
     * o null se {@link #hasNext() hasNext()} ritorna false
     */
    @Override
    public SimpleArticle next() {
        if (!hasNext())
            return null;
        Article article = guardianWrapper.getArticle(current++);
        return new SimpleArticle(article.getWebTitle(), article.getBodyText());
    }

}
