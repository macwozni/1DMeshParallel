# 1DMeshParallel

`1DMeshParallel` to mały projekt dydaktyczny w Javie używany podczas zajęć z
teorii śladów i współbieżnego wykonywania produkcji grafowych.

Program pokazuje, jak podzielić produkcje na bloki. Produkcje z jednego bloku
mogą wykonać się równolegle, ale następny blok startuje dopiero wtedy, gdy
wszystkie produkcje z poprzedniego bloku zakończą pracę.

Przykład buduje jednowymiarową siatkę reprezentowaną jako mutowalny graf
dwukierunkowy. Domyślne uruchomienie używa wątków wirtualnych oraz
`StructuredTaskScope` z Javy 26.

## Wymagania

- JDK 26, na przykład OpenJDK 26
- Maven 3.9 lub nowszy, albo dołączony Gradle Wrapper
- Powłoka POSIX dla `./gradlew` na Linux/macOS albo `gradlew.bat` na Windows

Projekt korzysta z `StructuredTaskScope`, czyli preview API w JDK 26. Maven i
Gradle mają już skonfigurowane `--enable-preview` dla kompilacji, uruchamiania
i generowania Javadoca.

Sprawdzenie wersji Javy:

```bash
java -version
javac -version
```

Jeżeli Maven albo Gradle nie znajduje JDK 26, ustaw `JAVA_HOME` na katalog
instalacji OpenJDK 26.

## Szybki Start Z Gradle

Gradle jest najwygodniejszą opcją, bo projekt zawiera wrapper.

Budowanie projektu:

```bash
./gradlew clean build
```

Uruchomienie aplikacji:

```bash
./gradlew run
```

Uruchomienie zbudowanego pliku JAR:

```bash
java --enable-preview -jar build/libs/1DMeshParallel-1.0-SNAPSHOT.jar
```

Na Windows użyj:

```bat
gradlew.bat clean build
gradlew.bat run
```

## Szybki Start Z Maven

Budowanie projektu:

```bash
mvn clean package
```

Uruchomienie zbudowanego pliku JAR:

```bash
java --enable-preview -jar target/1DMeshParallel-1.0-SNAPSHOT.jar
```

## Javadoc

Wygenerowanie Javadoca przez Gradle:

```bash
./gradlew javadoc
```

Dokumentacja HTML powstaje w:

```text
build/docs/javadoc
```

Pełny build Gradle tworzy też archiwum:

```text
build/libs/1DMeshParallel-1.0-SNAPSHOT-javadoc.jar
```

Wygenerowanie Javadoca przez Maven:

```bash
mvn javadoc:javadoc
```

Dokumentacja HTML powstaje w:

```text
target/reports/apidocs
```

Faza `package` w Maven tworzy też archiwum:

```text
target/1DMeshParallel-1.0-SNAPSHOT-javadoc.jar
```

Obie konfiguracje mają włączone sprawdzanie `doclint`, więc błędny lub
niekompletny Javadoc powinien zostać zgłoszony podczas generowania
dokumentacji.

## Co Robi Program

Program zaczyna od aksjomatu, czyli pojedynczego wierzchołka z etykietą `S`.
Następnie wykonuje produkcje grafowe w blokach:

1. `P1` tworzy początkową siatkę `T1--T1`.
2. `P2` i `P3` wykonują się w jednym bloku i wstawiają wierzchołki `T2` obok
   wierzchołków `T1`.
3. `P5` i `P6` wykonują się w jednym bloku i zamieniają etykiety wierzchołków
   na elementy skończone `|e1|` oraz `|e2|`.

Końcowa siatka:

```text
|e1|--|e2|--|e2|--|e1|
```

W projekcie jest też produkcja `P4`, która wstawia kolejny wierzchołek `T2` za
dopasowanym wierzchołkiem `T2`. Produkcja jest zaimplementowana, ale nie jest
używana w domyślnym przebiegu klasy `Executor`.

## Przykładowe Wyjście

Domyślny runner wykonuje produkcje w obrębie bloku współbieżnie, dlatego
kolejność linii `p2`/`p3` oraz `p5`/`p6` może się różnić między
uruchomieniami. Końcowa siatka powinna pozostać taka sama.

Jedno z możliwych wyjść:

```text
p1
T1--T1
p2
p3
T1--T2--T1
T1--T2--T2--T1
p5
p5
p6
p6
T1--T2--T2--|e1|
|e1|--T2--T2--|e1|
|e1|--T2--|e2|--|e1|
|e1|--|e2|--|e2|--|e1|
done
|e1|--|e2|--|e2|--|e1|
```

## Struktura Projektu

```text
.
├── build.gradle
├── gradlew
├── gradlew.bat
├── pom.xml
├── settings.gradle
└── src/main/java/pl/edu/agh/macwozni/dmeshparallel
    ├── Application.java
    ├── Executor.java
    ├── mesh
    ├── myProductions
    ├── parallelism
    └── production
```

Najważniejsze pakiety:

- `pl.edu.agh.macwozni.dmeshparallel` - punkt wejścia aplikacji i orkiestracja
  przebiegu.
- `mesh` - model mutowalnej siatki jednowymiarowej i wypisywanie grafu.
- `myProductions` - konkretne produkcje grafowe `P1` do `P6`.
- `parallelism` - strategie wykonywania bloków produkcji.
- `production` - wspólne interfejsy i klasy bazowe dla produkcji.

## Model Wykonania

Centralną abstrakcją jest `BlockRunner`. Dostaje listę niezależnych produkcji i
wykonuje je jako jeden blok.

Dostępne implementacje:

- `ConcurrentBlockRunner` uruchamia każdą produkcję na osobnym wątku
  wirtualnym przez `StructuredTaskScope`. `CountDownLatch` pozwala wystartować
  zadania po zgłoszeniu całego bloku, a strukturalny zakres zadań pilnuje, żeby
  wszystkie podzadania zakończyły się przed wyjściem z metody.
- `SerialBlockRunner` uruchamia produkcje po kolei w bieżącym wątku. Przydaje
  się do debugowania albo porównania przebiegu sekwencyjnego i współbieżnego.
- `ConcurentBlockRunner` jest przestarzałym aliasem zachowanym dla zgodności ze
  starą nazwą klasy z literówką.

Mutacje grafu i wypisywanie siatki używają wspólnej blokady w klasie `Vertex`.
Dzięki temu przykład jest bezpieczny współbieżnie, a kod pozostaje skupiony na
harmonogramowaniu produkcji, nie na niskopoziomowej synchronizacji grafu.

## Przełączenie Na Wykonanie Sekwencyjne

Domyślny punkt wejścia używa:

```java
new Executor(new ConcurrentBlockRunner()).run();
```

Żeby wykonywać bloki sekwencyjnie, w `Application` użyj:

```java
new Executor(new SerialBlockRunner()).run();
```

i dodaj import `SerialBlockRunner`.

## Artefakty Builda

Gradle tworzy:

```text
build/libs/1DMeshParallel-1.0-SNAPSHOT.jar
build/libs/1DMeshParallel-1.0-SNAPSHOT-javadoc.jar
build/docs/javadoc/
```

Maven tworzy:

```text
target/1DMeshParallel-1.0-SNAPSHOT.jar
target/1DMeshParallel-1.0-SNAPSHOT-javadoc.jar
target/reports/apidocs/
```

## Czyszczenie

Usunięcie wyników Gradle:

```bash
./gradlew clean
```

Usunięcie wyników Maven:

```bash
mvn clean
```

## Rozwiązywanie Problemów

Jeżeli `./gradlew` nie ma prawa wykonywania na Linux/macOS, uruchom:

```bash
chmod +x gradlew
```

Jeżeli kompilacja zgłasza błąd wersji Javy, sprawdź, czy `java` i `javac`
pochodzą z JDK 26 oraz czy `JAVA_HOME` wskazuje tę samą instalację.

Jeżeli uruchomienie pliku JAR zgłasza błąd dotyczący preview features, dodaj
flagę `--enable-preview`:

```bash
java --enable-preview -jar target/1DMeshParallel-1.0-SNAPSHOT.jar
```

Jeżeli kolejność wypisywanych produkcji różni się między uruchomieniami, jest
to oczekiwane dla produkcji wykonywanych współbieżnie w jednym bloku. W takim
przypadku sprawdź końcową linię z siatką.

## Użycie

Tylko do użytku niekomercyjnego.
