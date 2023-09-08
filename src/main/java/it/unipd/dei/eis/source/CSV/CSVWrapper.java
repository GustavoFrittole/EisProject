package it.unipd.dei.eis.source.CSV;

import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SourceWrapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class CSVWrapper implements SourceWrapper {
    public final int articlesNumber;
    private final String csvFileUrl;
    private SimpleArticle[] articles;

    public CSVWrapper(String csvFileUrl, int articlesNumber) {
        this.csvFileUrl = csvFileUrl;
        this.articlesNumber = articlesNumber;
    }

    @Override
    public Iterator<SimpleArticle> iterator() {
        return new CSVSimpleIterator(this);
    }

    public void retriveArticles() {
        try (Reader reader = new FileReader(csvFileUrl)) {
            articles = new SimpleArticle[articlesNumber];

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);
            Iterator<CSVRecord> recordsIterator = records.iterator();

            for (int i = 0; i < articlesNumber && recordsIterator.hasNext(); i++) {
                CSVRecord record = recordsIterator.next();
                articles[i] = new SimpleArticle(record.get(2), record.get(3));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SimpleArticle getSimpleArticle(int index) {
        return articles[index];
    }

}
