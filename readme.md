# Progetto esame EIS

### Compilare il progetto

Scaricare il progetto da github
    
    git clone https://github.com/GustavoFrittole/EisProject.git

Prima di compilare, modificare il file delle proprietà
compilando i campi a cui si è interessati (vedi sezione dedicata).

    EisProject/src/main/resources/application.properties.example

Una volta fatto, rinominarlo in "application.properties". In alternativa è possibile
importare un file di proprietà esterno a ogni esecuzione tramite l'opzione (-pf).
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

E' possibile eseguire il programma dalla cartella principale tramite il comando:

(sostituire a \<version> la versione utilizzata)

    java -jar target/EisProject-<version>-jar-with-dependencies.jar <args>

#### Esempi di utilizzo
Con facoltativo e necesario ci si riferisce algli argomenti delle proprietà, non alle proprietà stesse.
E necessaria la presenza di -sf oppure -et oppure entrambe.

    java -jar target/EisProject-<version>-jar-with-dependencies.jar
        -sf -cf [facoltativo-file] -pf [necessario-file_esterno_delle_proprietà]
Recupera il numero impostato (file delle proprietà) di articoli dal dato file
e li salva in un file locale. Se il file non è indicato nell'argomento
allora viene letto dal file delle proprietà.

    java -jar target/EisProject-<version>-jar-with-dependencies.jar
        -sf -gu [facoltativo-query] -et
Recupera il numero impostato (file delle proprietà) di articoli tramite le API
di The Guardian Open Platform e li salva in un file locale. 
Se la query non è indicata nell'argomento allora viene letta dal file delle proprietà.
Successivamente legge tale file, e stampa su di un altro file una lista dei termini
presenti associati al loro peso (numero di documenti in cui appaiono).

    java -jar target/EisProject-<version>-jar-with-dependencies.jar
        -et
Legge il file su cui sono stati precedentemente salvati degli articoli
e stampa su di un altro file una lista dei termini
presenti associati al loro peso (numero di documenti in cui appaiono).

#### File delle proprietà

    src/main/java/resources/application.properties.example

    #necessari per -gu
        guardian_api_key=<your-api-key>
        guardian_api_pages=5
        guardian_api_articles_per_page=200

    #necessario per -cf
        csv_file_articles=1000

    #sovrascritti se specificati come argomento
        #argomento di -gu
            guardian_api_query="your query"
        #argomento di -cf
            csv_file_url=path/to/your/articles.csv

    #se inserito viene letto e la finzione stoplist attivata
        stop_words_file=path/to/your/stoplist.txt

    #necessari per -et
        results_file_name=results
        words_to_print=50

### NOTE
I file presenti sotto la carella src/main/java/com/apiguardian/
non sono proprietà dell'autore di questo file,
che ha solo apportato delle modifiche a essi (indicate nei commenti).
Vedi: https://github.com/matarrese/content-api-the-guardian.git





