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

public class AssetsUtils {
    private static String filePath = "./saved_articles.csv";

    /**
     *
     * @param iterator
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static void saveArticlesToFile(Iterator<SimpleArticle> iterator) throws IllegalArgumentException, IOException {
        if (iterator == null) {
            throw new IllegalArgumentException("Argomento nullo");
        }

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader("Title", "Body");
        try (
                FileWriter writer = new FileWriter(filePath);
                CSVPrinter csvPrinter = new CSVPrinter(writer, csvFileFormat);
        ) {
            while (iterator.hasNext()) {
                SimpleArticle simpleArticle = iterator.next();
                csvPrinter.printRecord(simpleArticle.getTitle(), simpleArticle.getBody());
            }
            csvPrinter.flush();
        }
    }

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
