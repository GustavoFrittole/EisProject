package it.unipd.dei.eis.source.Guardian;

import com.apitheguardian.GuardianContentApi;
import com.apitheguardian.bean.Article;
import com.apitheguardian.bean.Response;
import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SourceWrapper;

import java.util.Iterator;

/**
 * Questa implementazione is fa carico della responsabilità
 * dell'ottenimento degli articoli e li mantiene come {@link Article Article}
 */
public class GuardianWrapper implements SourceWrapper {

    private final int articlesPerPage;
    private final String query;
    private final String apiKey;
    private final Article[] articles;
    private int totPages;
    private String order;

    /**
     * @param apiKey     una chiave di accesso alle API di <a href="https://open-platform.theguardian.com/">The Guardian Open Platform</a>
     * @param query      parole chiave della ricerca
     * @param artPerPage numero di articoli per pagina, da 1 a 200, default 10
     * @param totPages   numero di pagine da richiedere
     */
    public GuardianWrapper(String apiKey, String query, int artPerPage, int totPages) {
        this.articlesPerPage = artPerPage;
        this.totPages = totPages;
        if (query == null)
            throw new IllegalArgumentException("Null query String");
        if (apiKey == null)
            throw new IllegalArgumentException("Null api key String");
        this.query = query;
        this.apiKey = apiKey;
        articles = new Article[totPages * articlesPerPage];
    }

    /**
     * Scarica articoli tramite le Guardian API secondo i valori impostati nelle variabili membro
     * e li conserva in {@link #articles articles}. Ridimensiona totPages se
     * non vi sono abbastanza articoli da richiedere
     */
    public void retriveArticles() {
        GuardianContentApi api = new GuardianContentApi(apiKey);
        api.setPageSize(articlesPerPage);
        int temp = 0;
        if (order != null)
            api.setOrder(order);
        for (int i = 0; i < totPages; i++) {
            Response response = api.getContent(query, i + 1);
            //condizione che impedisca di fare più richieste del possibile
            if (i == 0 && totPages > response.getPages())
                totPages = response.getPages();
            System.arraycopy(response.getResults(), 0, articles, articlesPerPage * i, response.getResults().length);
        }
    }

    @Override
    public Iterator<SimpleArticle> iterator() {
        return new GuardianSimpleIterator(this);
    }

    public String getQuery() {
        return query;
    }

    /**
     * Accesso agli articoli ottenuti tramite indice
     *
     * @param page  pagina
     * @param index indice
     * @return l'articolo che si trova al dato indice o
     * null se l'elemento non è popolato
     */
    public Article getArticle(int page, int index) {
        return articles[page * articlesPerPage + index];
    }

    /**
     * Accesso agli articoli ottenuti tramite indice.
     *
     * @param index indice articolo
     * @return l'articolo che si trova al dato indice o
     * null se l'elemento non è popolato o è fuori range
     */
    public Article getArticle(int index) {
        if (index < 0 || index >= articlesPerPage * totPages)
            return null;
        return articles[index];
    }

    /**
     * Imposta l'ordine con il quale gli articoli richiesti saranno scaricati.
     * Se non impostato l'ordine di default è per rilevanza
     * Vedi <a href="https://open-platform.theguardian.com/documentation/search">documentazione Guardian API</a>, Field orderBy
     *
     * @param order - ordine download articoli
     */
    public void setOrder(String order) {
        this.order = order;
    }

    public int getArticlesPerPage() {
        return articlesPerPage;
    }

    public int getTotPages() {
        return totPages;
    }
}

