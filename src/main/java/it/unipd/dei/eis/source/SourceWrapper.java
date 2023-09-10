package it.unipd.dei.eis.source;

/**
 * Interfaccia comune per tutte le fonti di documenti
 */
public interface SourceWrapper extends Iterable<SimpleArticle> {
    public void retriveArticles();
}