package it.unipd.dei.eis.UI;

import it.unipd.dei.eis.analyze.ArticleAnalyzer;
import it.unipd.dei.eis.analyze.WeightedToken;
import it.unipd.dei.eis.record.AssetsUtils;
import it.unipd.dei.eis.source.CSV.CSVWrapper;
import it.unipd.dei.eis.source.Guardian.GuardianWrapper;
import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SourceWrapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Controller {
    private Properties properties;
    private MainFrame mainFrame;
    private Options options;
    public void start(){
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
    }

    public void useModel(Options options) {
        mainFrame.logToTextArea("Loading configuration...");
        if (!loadConfiguration()) {
            return;
        }
        if (options.isSf()) {
            mainFrame.logToTextArea("Retrieving and saving articles...");
            if (!retrieveAndSave(options.isGa(), options.isCf(), options.getFilePath(), options.getQuery())) {
                return;
            }
        }
        if (options.isRt()) {
            mainFrame.logToTextArea("Extracting tokens and saving results...");
            if (!loadArticlesAndExtract())
                return;
        }
        mainFrame.logToTextArea("All done");
    }
    private boolean loadConfiguration() {
        properties = new Properties();
        InputStream input = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            input = loader.getResourceAsStream("application.properties");
            properties.load(input);
        } catch (IOException e) {
            mainFrame.logToTextArea("ERROR - Cannot load default configuration:\n" + e.getMessage());
            return false;
        } catch (NullPointerException e) {
            mainFrame.logToTextArea("ERROR - Must create property file:\n" + e.getMessage());
            return false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private boolean retrieveAndSave(boolean ga, boolean cf, String filePath, String guardianQuery) {
        List<SourceWrapper> sourceWrappers = new ArrayList<>();
        if (ga) {
            String query = guardianQuery;
            if (query == null)
                query = properties.getProperty("guardian_api_query");
            sourceWrappers.add(new GuardianWrapper(properties.getProperty("guardian_api_key"),
                    query,
                    Integer.parseInt(properties.getProperty("guardian_api_articles_per_page")),
                    Integer.parseInt(properties.getProperty("guardian_api_pages"))));
        }
        if (cf) {
            String csvFileUrl = filePath;
            if (csvFileUrl == null)
                csvFileUrl = properties.getProperty("csv_file_url");
            sourceWrappers.add(new CSVWrapper(csvFileUrl,
                    Integer.parseInt(properties.getProperty("csv_file_articles"))));
        }

        //Other sources

        if (sourceWrappers.isEmpty()) {
            mainFrame.logToTextArea("ERROR - Must specify sources");
            return false;
        }
        try {
            for (SourceWrapper sourceWrapper : sourceWrappers) {
                sourceWrapper.retriveArticles();
            }
        } catch (Exception e) {
            mainFrame.logToTextArea("ERROR - Could not retrive articles from source: ");
            mainFrame.logToTextArea(e.getMessage());
            return false;
        }
        try {
            for (SourceWrapper sourceWrapper : sourceWrappers) {
                AssetsUtils.saveArticlesToFile(sourceWrapper.iterator());
            }
        } catch (IOException | IllegalArgumentException e) {
            mainFrame.logToTextArea("ERROR - Could not save articles to file: ");
            mainFrame.logToTextArea(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean loadArticlesAndExtract() {
        List<SimpleArticle> articleList;
        try {
            articleList = AssetsUtils.loadArticlesFromFile();
        } catch (IOException e) {
            mainFrame.logToTextArea("ERROR - Could not load saved articles: ");
            mainFrame.logToTextArea(e.getMessage());
            return false;
        }

        String stopListFile = properties.getProperty("stop_words_file");
        if (stopListFile != null && !stopListFile.isEmpty()) {
            try {
                List<String> stopWords = AssetsUtils.loadStopList(stopListFile);
                ArticleAnalyzer.setStopList(stopWords);
            } catch (FileNotFoundException e) {
                mainFrame.logToTextArea("ERROR - Could not load given stop words list: ");
                mainFrame.logToTextArea(e.getMessage());
                return false;
            }
        }
        List<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrencesPerArticle(articleList);
        try {
            AssetsUtils.saveWeightedWords(weightedTokens,
                    properties.getProperty("results_file_name") + ".txt",
                    Integer.parseInt(properties.getProperty("words_to_print")));
        } catch (IOException e) {
            mainFrame.logToTextArea("ERROR - Could not save results locally: ");
            mainFrame.logToTextArea(e.getMessage());
            return false;
        }
        return true;
    }
}
