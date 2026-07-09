# 1DMeshParallel

`1DMeshParallel` is a small Java teaching project for trace theory classes and
concurrent execution of graph productions.

The program demonstrates how graph productions can be grouped into execution
blocks. Productions from one block may run concurrently, but the next block
starts only after every production from the previous block has finished.

The example builds a one-dimensional mesh represented as a mutable doubly
linked graph. The default execution uses Java 26 virtual threads and
`StructuredTaskScope`.

## Requirements

- JDK 26, for example OpenJDK 26
- Maven 3.9 or newer, or the included Gradle Wrapper
- A POSIX shell for `./gradlew` on Linux/macOS, or `gradlew.bat` on Windows

This project uses `StructuredTaskScope`, which is a preview API in JDK 26.
Maven and Gradle are already configured with `--enable-preview` for
compilation, execution, tests, and Javadoc generation.

Check the installed Java version:

```bash
java -version
javac -version
```

If Maven or Gradle cannot find JDK 26, set `JAVA_HOME` to the OpenJDK 26
installation directory.

## Quick Start With Gradle

Gradle is the most convenient option because the repository includes the
wrapper.

Build the project:

```bash
./gradlew clean build
```

Run the application:

```bash
./gradlew run
```

Run the generated JAR:

```bash
java --enable-preview -jar build/libs/1DMeshParallel-1.0-SNAPSHOT.jar
```

On Windows use:

```bat
gradlew.bat clean build
gradlew.bat run
```

## Quick Start With Maven

Build the project:

```bash
mvn clean package
```

Run the generated JAR:

```bash
java --enable-preview -jar target/1DMeshParallel-1.0-SNAPSHOT.jar
```

## Javadoc

Generate Javadoc with Gradle:

```bash
./gradlew javadoc
```

Gradle writes the HTML documentation to:

```text
build/docs/javadoc
```

A full Gradle build also creates:

```text
build/libs/1DMeshParallel-1.0-SNAPSHOT-javadoc.jar
```

Generate Javadoc with Maven:

```bash
mvn javadoc:javadoc
```

Maven writes the HTML documentation to:

```text
target/reports/apidocs
```

The Maven `package` phase also creates:

```text
target/1DMeshParallel-1.0-SNAPSHOT-javadoc.jar
```

Both build configurations enable `doclint`, so invalid or incomplete Javadoc
should be reported while generating the documentation.

## What The Program Does

The program starts from an axiom, represented by a single vertex labelled `S`.
It then applies graph productions in blocks:

1. `P1` creates the initial mesh `T1--T1`.
2. `P2` and `P3` run in one block and insert `T2` vertices next to the `T1`
   vertices.
3. `P5` and `P6` run in one block and relabel vertices as finite elements
   `|e1|` and `|e2|`.

The final mesh is:

```text
|e1|--|e2|--|e2|--|e1|
```

The project also contains `P4`, which inserts another `T2` vertex after a
matched `T2` vertex. This production is implemented, but it is not used by the
default execution flow in `Executor`.

## Example Output

The default runner executes productions inside a block concurrently, so the
order of the `p2`/`p3` and `p5`/`p6` log lines may differ between runs. The
final mesh should remain the same.

One possible output is:

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

## Project Structure

```text
.
|-- build.gradle
|-- gradlew
|-- gradlew.bat
|-- pom.xml
|-- settings.gradle
`-- src/main/java/pl/edu/agh/macwozni/dmeshparallel
    |-- Application.java
    |-- Executor.java
    |-- mesh
    |-- myProductions
    |-- parallelism
    `-- production
```

Main packages:

- `pl.edu.agh.macwozni.dmeshparallel` - application entry point and execution
  orchestration.
- `mesh` - mutable one-dimensional mesh model and graph printing.
- `myProductions` - concrete graph productions `P1` through `P6`.
- `parallelism` - strategies for executing production blocks.
- `production` - shared production interfaces and base classes.

## Execution Model

The central abstraction is `BlockRunner`. It receives a list of independent
productions and executes them as one block.

Available implementations:

- `ConcurrentBlockRunner` runs each production on a virtual thread through
  `StructuredTaskScope`. A `CountDownLatch` starts tasks after the entire block
  has been submitted, and the structured task scope ensures that all subtasks
  finish before the method exits.
- `SerialBlockRunner` runs productions one after another on the current
  thread. It is useful for debugging or comparing sequential and concurrent
  execution.
- `ConcurentBlockRunner` is a deprecated compatibility alias that preserves
  the old misspelled class name.

Graph mutations and graph printing use a shared lock in the `Vertex` class.
This keeps the example safe for concurrent execution while keeping the code
focused on production scheduling rather than low-level graph synchronization.

## Switching To Sequential Execution

The default entry point uses:

```java
new Executor(new ConcurrentBlockRunner()).run();
```

To execute blocks sequentially, change `Application` to use:

```java
new Executor(new SerialBlockRunner()).run();
```

and add the `SerialBlockRunner` import.

## Build Artifacts

Gradle creates:

```text
build/libs/1DMeshParallel-1.0-SNAPSHOT.jar
build/libs/1DMeshParallel-1.0-SNAPSHOT-javadoc.jar
build/docs/javadoc/
```

Maven creates:

```text
target/1DMeshParallel-1.0-SNAPSHOT.jar
target/1DMeshParallel-1.0-SNAPSHOT-javadoc.jar
target/reports/apidocs/
```

## Cleaning

Remove Gradle outputs:

```bash
./gradlew clean
```

Remove Maven outputs:

```bash
mvn clean
```

## Troubleshooting

If `./gradlew` is not executable on Linux/macOS, run:

```bash
chmod +x gradlew
```

If compilation fails with a Java version error, check that both `java` and
`javac` come from JDK 26 and that `JAVA_HOME` points to the same installation.

If running the JAR reports an error about preview features, add the
`--enable-preview` flag:

```bash
java --enable-preview -jar target/1DMeshParallel-1.0-SNAPSHOT.jar
```

If the printed production order differs between runs, this is expected for
productions executed concurrently inside one block. Check the final mesh line
instead.

## Usage

For noncommercial use only.
