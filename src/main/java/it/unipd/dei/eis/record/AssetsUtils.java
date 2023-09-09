package it.unipd.dei.eis.record;

import it.unipd.dei.eis.analyze.WeightedToken;
import it.unipd.dei.eis.source.SimpleArticle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Responsabile delle operazioni di salvataggio / lettura su / di file locali
 */
public class AssetsUtils {
    private static String filePath = "./saved_articles.csv";

    /**
     * Salva i dati articoli sul file specificato in {@link #filePath filePath}
     * @param iterator da cui leggere i file da salvare
     * @throws IllegalArgumentException se l'argomento è nullo
     * @throws IOException in caso di problemi in apertura/scrittura/chiusura file
     */
    public static void saveArticlesToFile(Iterator<SimpleArticle> iterator) throws IllegalArgumentException, IOException {
        if (iterator == null) {
            throw new IllegalArgumentException("Argomento nullo");
        }

        CSVFormat csvFileFormat = CSVFormat.Predefined.Default.getFormat();

        File file = new File(filePath);
        boolean existed = file.exists();
        try (
                FileWriter writer = new FileWriter(file, true);
                CSVPrinter csvPrinter = new CSVPrinter(writer, csvFileFormat);
        ) {
            if(!existed)
                csvPrinter.printRecord("Title", "Body");
            while (iterator.hasNext()) {
                SimpleArticle simpleArticle = iterator.next();
                csvPrinter.printRecord(simpleArticle.getTitle(), simpleArticle.getBody());
            }
            csvPrinter.flush();
        }
    }

    /**
     * Carica gli articoli salvati sul file specificato in {@link #filePath filePath}
     * @return articoli caricati da salvataggio
     * @throws IllegalArgumentException se {@link #filePath filePath} nonè impostato
     * @throws IOException in caso di problemi in apertura/lettura/chiusura file
     */
    public static List<SimpleArticle> loadArticlesFromFile() throws IllegalArgumentException, IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path nullo o vuoto");
        }
        LinkedList<SimpleArticle> linkedList = new LinkedList<>();
        Iterable<CSVRecord> records = null;
        try (FileReader reader = new FileReader(filePath)) {
            records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);
            for (CSVRecord record : records)
                linkedList.add(new SimpleArticle(record.get("Title"), record.get("Body")));
        }
        return linkedList;
    }

    /**
     * Carica una stoplist da file locale
     * @param stopListFilePath l'URL assoluto della stoplist
     * @return lista di stop words
     * @throws FileNotFoundException se il file indicato viene trovato
     */
    public static List<String> loadStopList(String stopListFilePath) throws FileNotFoundException {
        List<String> stopWords;
        try (Scanner scan = new Scanner(new File(stopListFilePath))) {
            stopWords = new LinkedList<>();
            while (scan.hasNext()) {
                stopWords.add(scan.next());
            }
        }
        return stopWords;
    }

    /**
     * Salva una lista di {@link WeightedToken WeightedToken} su file
     * @param weightedTokens valori da scrivere/salvare su file
     * @param fileName su cui salvare i dati
     * @param wordsToPrint numero massimo coppie parola peso da salvare su file
     * @throws IOException in caso di problemi in apertura/scrittura/chiusura file
     */
    public static void saveWeightedWords(List<WeightedToken> weightedTokens, String fileName, int wordsToPrint) throws IOException {
        Iterator<WeightedToken> weightedTokenIterator = weightedTokens.iterator();
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                PrintWriter printWriter = new PrintWriter(fileWriter)
        ) {
            for (int i = 0; i < wordsToPrint && weightedTokenIterator.hasNext(); i++) {
                WeightedToken weightedToken = weightedTokenIterator.next();
                printWriter.printf("%-16s\t%d%n", weightedToken.getToken(), weightedToken.getWeight());
            }
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        AssetsUtils.filePath = filePath;
    }
}
