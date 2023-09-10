package it.unipd.dei.eis.source.CSV;

import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SourceWrapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * Questa implementazione is fa carico della responsabilità
 * dell'ottenimento degli articoli e li mantiene come {@link SimpleArticle SimpleArticle}
 */
//NOTA: a posteriori sarebbe stato più sensato contenere una variabile che indicasse il numero di articoli
public class CSVWrapper implements SourceWrapper {

    private int articlesNumber;
    private final String csvFileUrl;
    private SimpleArticle[] articles;

    /**
     *
     * @param csvFileUrl file da cui reperire gli articoli
     * @param articlesNumber numero massimo di articoli da reperire dal file
     */
    public CSVWrapper(String csvFileUrl, int articlesNumber) {
        this.csvFileUrl = csvFileUrl;
        this.articlesNumber = articlesNumber;
    }

    @Override
    public Iterator<SimpleArticle> iterator() {
        return new CSVSimpleIterator(this);
    }

    /**
     * Legge {@link #articlesNumber articlesNumber} articoli dal dato {@link #csvFileUrl file}
     * e li conserva in {@link #articles articles}.
     * @throws RuntimeException in caso di problemi in apertura/lettura/chiusura file
     */
    //si sarebbe potuto cedere la responsabilità ad AssetsUtil e gestire meglio le eccezioni
    public void retriveArticles() {
        try (Reader reader = new FileReader(csvFileUrl)) {
            articles = new SimpleArticle[articlesNumber];

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);
            Iterator<CSVRecord> recordsIterator = records.iterator();
            int i;
            for (i = 0; i < articlesNumber && recordsIterator.hasNext(); i++) {
                CSVRecord record = recordsIterator.next();
                articles[i] = new SimpleArticle(record.get(2), record.get(3));
            }
            articlesNumber = i;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Accesso agli articoli ottenuti tramite indice
     * @param index indice articolo
     * @return l'articolo che si trova al dato indice o
     * null se l'elemento non è popolato
     */
    public SimpleArticle getSimpleArticle(int index) {
        if (index < 0 || index >= articlesNumber)
            return null;
        return articles[index];
    }
    public int getArticlesNumber() {
        return articlesNumber;
    }
}
