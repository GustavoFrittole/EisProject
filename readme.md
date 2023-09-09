# Progetto esame EIS

### Compilare il progetto

Scaricare il progetto da github
    
    git clone https://github.com/GustavoFrittole/EisProject.git

Prima di compilare, modificare il file delle proprietà
compilando i campi a cui si è interessati (vedi sezione dedicata).

    EisProject/src/main/resources/application.properties.example

Una volta fatto, rinominarlo in "application.properties". In alternativa è possibile
importare un file di proprietà esterno (il formato deve essere lo stesso di application.properties.example) a ogni esecuzione tramite l'opzione (-pf).
È necessario che almeno una delle due condizioni sia rispettata.

Per creare il file jar relativo al progetto e comprensivo delle dipendenze,
posizionarsi nella cartella principale e usare l'apposito comando Maven:
    
    cd EisProject
    mvn package

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
### NOTE
- Gli articoli vengono salvati su di un file, saved_articles.csv, che si trova nella cartella principale 
della repository, la scrittura è impostata su append. Se si vuole eliminare
gli articoli salvati in precedenza sarà necessario eliminare tale file. Il programma
non impedità il salvataggio di articoli in multipla copia.
- Non si assicura che il programma gestisca correttamente richieste di articoli
superiori a quelle disponibili da una data fonte. Il comportamento generale
prevede il salvataggio di tutti gli articoli disponibili.
- Il formato della stoplist deve essere lo stesso di quella fornita in 
example assets. Lo stesso vale per gli articoli letti da CSV





