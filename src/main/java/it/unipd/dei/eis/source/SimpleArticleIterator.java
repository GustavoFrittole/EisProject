package it.unipd.dei.eis.source;

import java.util.Iterator;

public interface SimpleArticleIterator extends Iterator<SimpleArticle> {


    public boolean hasNext();

    public SimpleArticle next();
}
