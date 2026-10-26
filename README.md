# CodeTown

A Java desktop application that visualizes the structure of Java source code in a Git repository.

CodeTown combines **JGit** for cloning repositories, **JavaParser** for analyzing Java files, **JGraphX** for rendering relationships, and **Swing** for the user interface.

## Features

- Clone a Java repository and locate its `.java` source files.
- Parse Java classes and relationships.
- Display an interactive graphical view of the project.
- Enter another repository URL in the application to load a different project.

## Build and run

Requires **JDK 17+**, Maven, and a desktop environment with graphical display.

```sh
mvn compile
mvn exec:java
```

The current implementation expects a Git clone URL ending in `.git` and uses the `main` branch. The initial URL in the source is an example; you can change it in the application's input field.

**Note:** The current repository-cloning implementation may delete an existing local folder whose name matches the target repository. Run it from a workspace without valuable same-named directories until that behavior is made safer.

## Source layout

- [`Main.java`](src/main/java/com/javiergs/codetown/Main.java) — Swing application entry point.
- [`model/`](src/main/java/com/javiergs/codetown/model/) — Git cloning and Java source parsing.
- [`data/`](src/main/java/com/javiergs/codetown/data/) — Graph and source data models.
- [`ui/`](src/main/java/com/javiergs/codetown/ui/) — Visualization panel.

The independent Processing animation example has moved to [`edu-processing`](https://github.com/javiergs/edu-processing).
