package it.unipd.dei.eis;

import it.unipd.dei.eis.analyze.ArticleAnalyzer;
import it.unipd.dei.eis.analyze.WeightedToken;
import it.unipd.dei.eis.record.AssetsUtils;
import it.unipd.dei.eis.source.CSV.CSVWrapper;
import it.unipd.dei.eis.source.Guardian.GuardianWrapper;
import it.unipd.dei.eis.source.SimpleArticle;
import it.unipd.dei.eis.source.SourceWrapper;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

//TODO: SISTEMARE IL CONTATORE DI PAROLE COME DA COMSEGNA (CONTARE SINGOLARMENTE PER OGNI ARTICOLO)
public class App {
    private static Options options;
    private static CommandLine cmd;
    private static HelpFormatter formatter;
    private static Properties properties;

    private static void loadOptions() {
        options = new Options();
        options.addOption(new Option("h", "help",
                 false, "Print the help"));
        options.addOption(new Option("sa", "save-articles",
                false, "Retrive articles from source and save them on a local file"));
        options.addOption(new Option("et", "extract-terms",
                false, "Extract terms from previously saved articles and save the results on a local file"));
        options.addOption(new Option("pf", "property-file",
                true, "Load custom configuration from given file"));

        OptionGroup optionGroup = new OptionGroup();
        options.addOption(Option.builder("ga")
                .longOpt("guardian-api")
                .hasArg(true)
                .optionalArg(true)
                .desc("Set Guardian API as source, query as optional argument")
                .build());
        options.addOption(Option.builder("cf")
                .longOpt("csv-file")
                .hasArg(true)
                .optionalArg(true)
                .desc("Set CSV FILE as source, file path as optional argument")
                .build());
        options.addOptionGroup(optionGroup);
    }

    private static void printHelp() {
        if (formatter == null)
            formatter = new HelpFormatter();
        formatter.printHelp("app -sa -{ga, cf} <optional_argument> [options]" +
                "\tto retrieve and save, or\n" +
                "app -et [options]" +
                "\tto parse and save, or\n" +
                "app -sa -{ga, cf} <optional_argument> -et [options]" +
                "\tto do both\n" +
                "\tOPTIONS:\n"
                ,options);
    }

    private static CommandLine parseCmd(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException ex) {
            System.err.println("ERROR - parsing command line:");
            System.err.println(ex.getMessage());
            printHelp();
            return null;
        }
    }

    private static boolean loadConfiguration() {
        properties = new Properties();
        InputStream input = null;
        if (cmd.hasOption("pf")) {
            try {
                input = Files.newInputStream(Paths.get(cmd.getOptionValue("pf")));
                properties.load(input);
            } catch (IOException e) {
                System.err.println("ERROR - Cannot open or read specified configuration file:\n" + e.getMessage());
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
        } else {
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                input = loader.getResourceAsStream("application.properties");
                properties.load(input);
            } catch (IOException e) {
                System.err.println("ERROR - Cannot load default configuration:\n" + e.getMessage());
                return false;
            } catch (NullPointerException e){
                System.err.println("ERROR - No property file has been specified and no default configuration has benn set:\n" + e.getMessage());
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
        }
        return true;
    }

    private static boolean retrieveAndSave() {
        SourceWrapper sourceWrapper = null;
        if (cmd.hasOption("ga")) {
            //Unnecessary
            /*if (!cmd.hasOption("pf")) {
                System.err.println("ERROR - This action (-ga) requires to load a custom configuration (-pf): ");
                printHelp();
                return false;
            }*/
            String query = cmd.getOptionValue("ga");
            if (query == null)
                query = properties.getProperty("guardian_api_query");
            sourceWrapper = new GuardianWrapper(properties.getProperty("guardian_api_key"),
                    query,
                    Integer.parseInt(properties.getProperty("guardian_api_articles_per_page")),
                    Integer.parseInt(properties.getProperty("guardian_api_pages")));
        }
        if (cmd.hasOption("cf")) {
            String csvFileUrl = cmd.getOptionValue("cf");
            if (csvFileUrl == null)
                csvFileUrl = properties.getProperty("csv_file_url");
            sourceWrapper = new CSVWrapper(csvFileUrl,
                    Integer.parseInt(properties.getProperty("csv_file_articles")));
        }
        if (sourceWrapper == null) {
            System.err.println("ERROR - Must specify source");
            return false;
        }
        try {
            sourceWrapper.retriveArticles();
        } catch (Exception e) {
            System.err.println("ERROR - Could not retrive articles from source: ");
            System.err.println(e.getMessage());
            return false;
        }
        Iterator<SimpleArticle> iterator = sourceWrapper.iterator();
        try {
            AssetsUtils.saveArticlesToFile(iterator);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ERROR - Could not save article to file: ");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean loadArticlesAndExtract() {
        List<SimpleArticle> articleList;
        try {
            articleList = AssetsUtils.loadArticlesFromFile();
        } catch (IOException e) {
            System.err.println("ERROR - Could not load saved articles: ");
            System.err.println(e.getMessage());
            return false;
        }

        String stopListFile = properties.getProperty("stop_words_file");
        if (stopListFile != null && !stopListFile.isEmpty()) {
            try {
                List<String> stopWords = AssetsUtils.loadStopList(stopListFile);
                ArticleAnalyzer.setStopList(stopWords);
            } catch (FileNotFoundException e) {
                System.err.println("ERROR - Could not load given stop words list: ");
                System.err.println(e.getMessage());
                return false;
            }
        }
        List<WeightedToken> weightedTokens = ArticleAnalyzer.countOccurrences(articleList);
        try {
            AssetsUtils.saveWeightedWords(weightedTokens,
                    properties.getProperty("results_file_name") + ".txt",
                    Integer.parseInt(properties.getProperty("words_to_print")));
        } catch (IOException e) {
            System.err.println("ERROR - Could not save results locally: ");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        loadOptions();
        cmd = parseCmd(args);
        if (cmd == null)
            return;
        if (cmd.hasOption("h")) {
            printHelp();
        } else {
            System.out.println("Loading configuration...");
            if (!loadConfiguration()) {
                return;
            }
            if (!cmd.hasOption("et") && !cmd.hasOption("sa")) {
                System.err.println("ERROR - Must specify one or both actions (-et, -sa): ");
                printHelp();
                return;
            } else {
                if (cmd.hasOption("sa")) {
                    System.out.println("Retrieving and saving articles...");
                    if (!retrieveAndSave()) {
                        return;
                    }
                }
                if (cmd.hasOption("et")) {
                    System.out.println("Extracting tokens and saving results...");
                    loadArticlesAndExtract();
                }
            }
            System.out.println("All done");
        }
    }
}