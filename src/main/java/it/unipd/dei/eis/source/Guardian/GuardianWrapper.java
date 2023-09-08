package it.unipd.dei.eis.source.Guardian;

import com.apiguardian.GuardianContentApi;
import com.apiguardian.bean.Article;
import com.apiguardian.bean.Response;
import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SourceWrapper;

import java.util.Iterator;

public class GuardianWrapper implements SourceWrapper {
    public final int articlesPerPage;
    private final String query;
    private final String apiKey;
    private final Article[] articles;
    public int totPages;
    private String order;

    public GuardianWrapper(String apiKey, String query, int artPerPage, int totPages) {
        this.articlesPerPage = artPerPage;
        this.totPages = totPages;
        this.query = query;
        this.apiKey = apiKey;
        articles = new Article[totPages * articlesPerPage];
    }

    public void retriveArticles() {
        GuardianContentApi api = new GuardianContentApi(apiKey);
        api.setPageSize(articlesPerPage);
        if (order != null)
            api.setOrder(order);
        for (int i = 0; i < totPages; i++) {
            Response response = api.getContent(query, i + 1);
            if (i == 0 && totPages > response.getPages()) totPages = response.getPages();
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

    public Article getArticle(int page, int index) {
        return articles[page * articlesPerPage + index];
    }

    public Article getArticle(int index) {
        return articles[index];
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

