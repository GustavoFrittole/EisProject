package it.unipd.dei.eis.source.CSV;

import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SimpleArticleIterator;

public class CSVSimpleIterator implements SimpleArticleIterator {
    private final CSVWrapper csvWrapper;
    private int current;
    /**
     *
     * @param csvWrapper iterazione
     * @throws IllegalArgumentException se l'argomento è nullo
     */
    public CSVSimpleIterator(CSVWrapper csvWrapper) {
        if (csvWrapper == null)
            throw new IllegalArgumentException("Null argument");
        this.csvWrapper = csvWrapper;
    }

    @Override
    public boolean hasNext() {
        //essendo gli articoli mantenuti nei wrapper come array e non liste è
        //possibile che i campi non siano stati tutti popolati -> ci si ferma al primo valore nullo
        return current != csvWrapper.articlesNumber && csvWrapper.getSimpleArticle(current) != null;
    }

    /**
     *
     * @return il prossimo elemento dell'iterazione
     * o null se {@link #hasNext() hasNext()} ritorna false
     */
    @Override
    public SimpleArticle next() {
        if (hasNext())
            return csvWrapper.getSimpleArticle(current++);
        return null;
    }
}
