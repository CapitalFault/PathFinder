# PathFinder

PathFinder ist eine einfache Java-Swing-Anwendung zur Visualisierung von Pfadsuchalgorithmen. 
Sie erlaubt es, den Dijkstra‑Algorithmus und A* schrittweise oder automatisch auf einem Raster auszuführen. 

## Funktionen
* Auswahl zwischen Dijkstra und A*
* Interaktives Setzen von Start‑ und Zielknoten
* Platzierung von Wänden mittels Mausklick
* Anpassbare Rastergröße
* Animierte Darstellung des Suchprozesses und des gefundenen Pfades

## Voraussetzungen
* Java 21
* Maven

## Projekt bauen und starten
```bash
mvn package
java -cp target/classes org.example.Main
```
Beim Start öffnet sich ein Fenster mit dem Raster. Über die Steuerelemente können 
Start- und Endpunkt gesetzt, Wände platziert und der gewünschte Algorithmus gewählt werden. 
Per *Start* wird die Suche automatisch ausgeführt, während *Step* jeweils nur einen Schritt vornimmt.

## Ordnerstruktur
```
src/main/java/org/example
 ├─ gui      – Oberflächenelemente
 ├─ logic    – Implementierung der Suchalgorithmen
 └─ model    – Datenstrukturen für Raster und Knoten
```

## Lizenz
Dieses Projekt steht ohne besondere Lizenzbestimmungen zur Verfügung.
