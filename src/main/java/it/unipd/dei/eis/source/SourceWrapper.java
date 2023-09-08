package it.unipd.dei.eis.source;

public interface SourceWrapper extends Iterable<SimpleArticle> {
    public void retriveArticles();
}