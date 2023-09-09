package it.unipd.dei.eis.source;

/**
 * Versione semplificata di un articolo che presenta solamente
 * titolo e body (testo dell'articolo)
 */
public class SimpleArticle {
    private String title;
    private String body;

    public SimpleArticle(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
