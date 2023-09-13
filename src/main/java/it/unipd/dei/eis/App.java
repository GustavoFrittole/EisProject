package it.unipd.dei.eis;

import it.unipd.dei.eis.UI.Controller;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Permette all'utente di interfacciarsi
 * alle funzionalità dell'app tramite cli
 */
//NOTA: in realtà permette anche l'interazione tra le varie parti/classi/moduli
//      il che si sarebbe potuto implementare a parte (es. pattern controller).
public class App {
    public static void main(String[] args) {
            Controller appUI = new Controller();
            appUI.start();
    }
}