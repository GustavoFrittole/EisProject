# Progetto esame EIS

### Compilare il progetto

Scaricare il progetto da github

    git clone https://github.com/GustavoFrittole/EisProject.git

Prima di compilare, modificare il file delle proprietà
compilando i campi a cui si è interessati (vedi sezione dedicata). Senza  una API KEY valida i test non potranno completare.

    EisProject/src/main/resources/application.properties.example

Una volta fatto, rinominarlo in "application.properties". In alternativa è possibile
importare un file di proprietà esterno (il formato deve essere lo stesso di application.properties.example) a ogni
esecuzione tramite l'opzione (-pf).
È necessario che almeno una delle due condizioni sia rispettata.

Per creare il file jar relativo al progetto e comprensivo delle dipendenze,
posizionarsi nella cartella principale e usare l'apposito comando Maven:

    cd EisProject
    mvn package

Se non si dispone di un'API KEY ma si vuole procedere ugualmente, aggiungere l'opzione -Dmaven.test.skip=true per saltare i test.
Nella cartella "target/" verranno generati

- EisProject-\<version>.jar
- EisProject-\<version>-jar-with-dependencies.jar

Il secondo file jar comprende anche tutte le dipendenze.

### Eseguire il progetto

È possibile eseguire il programma dalla cartella principale tramite il comando:

(sostituire a \<version> la versione utilizzata)

    java -jar target/EisProject-<version>-jar-with-dependencies.jar <args>

#### Esempi di utilizzo

Gli argomenti fondamentali sono:

- (-sf) (ottenere i documenti dalla/e fonte/i da specificare e salvarli localmente)
- (-et) (leggere i documenti salvati, analizzare i termini contenuti e salvare su file i risultati)

Almeno uno dei due deve essere presente.

    java -jar target/EisProject-<version>-jar-with-dependencies.jar
      -sf -cf [facoltativo-file] -pf [file_esterno_delle_proprietà]

Recupera il numero impostato (nel file delle proprietà) di articoli dal file specificato
(tramite argomenro o nel file delle proprietà) e li salva in un file locale.
Se il file non è indicato nell'argomento allora viene letto dal file delle proprietà.

    java -jar target/EisProject-<version>-jar-with-dependencies.jar
        -sf -ga [facoltativo-query] -et

Recupera il numero impostato (nel file delle proprietà) di articoli tramite le API
di The Guardian Open Platform e li salva in un file locale.
Se la query non è indicata nell'argomento allora viene letta dal file delle proprietà.
Successivamente legge tale file, e stampa su di un altro file una lista dei termini
presenti associati al loro peso (numero di documenti in cui appaiono).

    java -jar target/EisProject-<version>-jar-with-dependencies.jar
        -et

Legge il file su cui sono stati precedentemente salvati degli articoli
e stampa su di un altro file una lista dei termini
presenti associati al loro peso (numero di documenti in cui appaiono).

    java -jar target/EisProject-<version>-jar-with-dependencies.jar
        -sf -ga [facoltativo-query] -cf [facoltativo-file]

Come sopra specificando fonti multiple.

#### File delle proprietà

    src/main/java/resources/application.properties.example

    #necessari per -ga
        guardian_api_key=<your-api-key>
        guardian_api_pages=5
        guardian_api_articles_per_page=200

    #necessario per -cf
        csv_file_articles=1000

    #sovrascritti se specificati come argomento
        #argomento di -ga
            guardian_api_query="your query"
        #argomento di -cf
            csv_file_url=path/to/your/articles.csv

    #se inserito viene letto e la finzione stoplist attivata
        stop_words_file=path/to/your/stoplist.txt

    #necessari per -et
        results_file_name=results
        words_to_print=50

Se i campi necessari all'azione scelta non sono compilati, il corretto
funzionamento del programma non è assicurato

### NOTE SULL'UTILIZZO

- Gli articoli vengono salvati su di un file, saved_articles.csv, che si trova nella cartella
  corrente (quello contenente i risultati, se generato), da cui è stato eseguito il jar, 
  la cui scrittura è impostata su append. Se si vuole eliminare
  gli articoli salvati in precedenza sarà necessario eliminare tale file. Il programma
  non impedità il salvataggio di articoli in multipla copia.
- Nel caso il numero di articoli richiesti dovesse superare il numero di articoli disponibili
  dalla corrispettiva source, i documenti ottenuti saranno tutti i disponibili.
- Il formato della stop list deve essere lo stesso di quella fornita in
  example assets. Lo stesso vale per gli articoli letti da CSV
- Limitazioni dell'analisi degli articoli: nel raro caso fossero presenti
  parole rilevanti contenenti caratteri diversi da lettere, esse non saranno
  lette correttamente dal'algoritmo di conteggio (es. il modello di reattore
  nucleare "VVER-440/V-230" sarà registrato come "vverv"). Questo perché tutti i
  caratteri diversi da [^a-zA-Z ] (le lettere maiuscole e minuscole dalla
  "a" alla "z", e gli spazi, usati poi come caratteri separatori) vengono ignorati.
  Analizzare articoli da lingue diverse dall'inglese necessiterebbe una diversa
  selezione di caratteri.

### DOCUMENTAZIONE

#### Dipendenze

- Test
    - Junit Jupiter + Mockito;
- Release
    - Apache commons CLI: parsing linea di comando;
    - Apache commons CSV: lettura e scrittura file in formato CSV;
    - The Guardian API Client: interazione con le API della testata giornalistica,
      versione personalizzata tramite fork.

Per ulteriori dettagli (es. versione delle dipendenze usate) visitare la documentazione generata tramite Maven Site
Plugin.
Per farlo, eseguire i comandi

    mvn javadoc::javadoc

Per generare i javadoc, che saranno presenti nel sito sotto la voce reports,

    mvn site:site

Per generare il sito.
Locazione del file index: target/site/index.html.

#### Tests
Sono presenti test sulle unità: viene testato il funzionamento e alcuni
casi estremi di tutti i metodi pubblici con particolari funzionalità.
Nota: i test riguardanti la classe GuardianWrapper non passeranno senza inserire 
una api kay valida nello stesso file di testing. 
È anche presente un test di sistema (per praticità evita la source The Guardian,
quindi non necessita di api key) da cui possono essere dedotte le principali funzionalità
e interazioni tra le componenti del progetto.

#### Ulteriori documenti

Documento dei requisiti, domain model, design model disponibili qui:
https://docs.google.com/document/d/19NcjxfXUfiXnh2baKYWWbzCZt84BBtG6qkEGTBYkWCg/edit?usp=sharing


  




