package it.unipd.dei.eis.source.CSV;

import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SimpleArticleIterator;

public class CSVSimpleIterator implements SimpleArticleIterator {
    private final CSVWrapper csvWrapper;
    private int current;

    public CSVSimpleIterator(CSVWrapper csvWrapper) {
        if (csvWrapper == null)
            throw new IllegalArgumentException("Argomento nullo");
        this.csvWrapper = csvWrapper;
    }

    @Override
    public boolean hasNext() {
        return current != csvWrapper.articlesNumber && csvWrapper.getSimpleArticle(current) != null;
    }

    @Override
    public SimpleArticle next() {
        if (hasNext())
            return csvWrapper.getSimpleArticle(current++);
        return null;
    }
}
