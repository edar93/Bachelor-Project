Instalace
Aby mohla být aplikace spuštěna, je nutno mít nainstalovaný Maven a Node.js. Také je potřeba mít k dispozici Derby databázi.
Pokud jsou tyto prerekvizity splněny, je možno začít s instalací. Nejprve je nutno nastavit připojení do databáze nastavením proměnné derby.jdbc.url. Ta se nastavuje v souborech jdbc.properties, jenž se nacházejí na cestě .\textbackslash src\textbackslash main\textbackslash resources a .\textbackslash src\textbackslash test\textbackslash resources.
Aby se část napsaná v AngularJS mohla připojit na server, je nutno změnit hodnotu proměnné serverAddress. Její aktuální hodnota je~"localhost", tudíž se může přihlásit pouze uživatel na zařízení, na němž je spuštěn server. Je zde nutno uvést IP adresu, na níž bude aplikace spuštěna. Proměnná se nachází na 10. řádku v souboru backendGateway.js, jehož umístění je .\textbackslash src\textbackslash main\textbackslash webapp\textbackslash components\textbackslash services\textbackslash .
Následně je nutno stáhnout závislosti pro část v AngularJS. To se provede v příkazovém řádku příkazem "npm install" na cestě .\textbackslash src\textbackslash main.

Spuštění
Pro spuštění aplikace je nutno mít volný port 8090. Poté stačí zadat v příkazovém řádku nad hlavním adresářem práce příkaz "mvn clean install tomcat7:run". Stahování všech závislostí může chvíli trvat. Po doběhnutí tohoto příkazu bude aplikace přístupná na adrese "http://localhost:8090/port-royal/". Přihlásit se lze například přihlašovacím jménem "admin"~a heslem také "admin".
Jasmine testy se spouštějí v příkazovém řádku na cestě \textbackslash src\textbackslash main příkazem "npm test". Protractor testy na stejné cestě příkazem "npm run protractor", tyto testy ovšem potřebují mít spuštěnou aplikaci na aplikačním serveru.
Pro spuštění Jersey testu se do příkazové řádky musí v hlavním adresáři napsat "mvn test".
