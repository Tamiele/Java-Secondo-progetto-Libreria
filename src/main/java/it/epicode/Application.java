package it.epicode;

import libreria.*;

import java.util.HashSet;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        HashSet<ElementiCatalogo> archivio = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        boolean continua = true;

        while (continua) {
            System.out.println("\n--- MENU PRINCIPALE ---");
            System.out.println("1. Aggiungi un libro");
            System.out.println("2. Aggiungi una rivista");
            System.out.println("3. Ricerca elemento per ISBN");
            System.out.println("4. Rimuovi elemento");
            System.out.println("5. Ricerca elemento per anno di pubblicazione");
            System.out.println("6. Ricerca libro per autore");
            System.out.println("7. Modifica elemento");
            System.out.println("8. Visualizza statistiche archivio");
            System.out.println("9. Visualizza tutti gli elementi");
            System.out.println("0. Esci");
            System.out.print("Inserisci la tua scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (scelta) {
                    case 1 -> {
                        ElementiCatalogo libro = Archivio.creazioneLibro(scanner);
                        if (libro != null) {
                            archivio.add(libro);
                            System.out.println("Libro aggiunto con successo.");
                        }
                    }
                    case 2 -> {
                        ElementiCatalogo rivista = Archivio.creazioneRivista(scanner);
                        if (rivista != null) {
                            archivio.add(rivista);
                            System.out.println("Rivista aggiunta con successo.");
                        }
                    }
                    case 3 -> {
                        ElementiCatalogo elemento = Archivio.ricercaElementoIsbn(scanner, archivio);
                        if (elemento != null) {
                            System.out.println("Elemento trovato: " + elemento);
                        } else {
                            System.out.println("Nessun elemento trovato con l'ISBN inserito.");
                        }
                    }
                    case 4 -> {
                        Archivio archivioObj = new Archivio();
                        archivioObj.rimuoviElemento(scanner, archivio);
                    }
                    case 5 -> {
                        Archivio.ricercaPerAnno(scanner, archivio);

                    }
                    case 6 -> {
                        HashSet<Libro> libri = new HashSet<>();
                        for (ElementiCatalogo elemento : archivio) {
                            if (elemento instanceof Libro) {
                                libri.add((Libro) elemento);
                            }
                        }
                        Archivio.ricercaPerAutore(scanner, libri);

                    }
                    case 7 -> {
                        Archivio archivioObj = new Archivio();
                        archivioObj.modificaElemento(scanner, archivio);
                    }
                    case 8 -> {
                        Archivio archivioObj = new Archivio();
                        archivioObj.statisticheArchivio(archivio);
                    }
                    case 9 -> {
                        if (archivio.isEmpty()) {
                            System.out.println("L'archivio è vuoto.");
                        } else {
                            System.out.println("Elementi presenti in archivio:");
                            for (ElementiCatalogo elemento : archivio) {
                                System.out.println(elemento);
                            }
                        }
                    }
                    case 0 -> {
                        System.out.println("Uscita dal programma. Arrivederci!");
                        continua = false;
                    }
                    default -> System.out.println("Scelta non valida. Riprova.");
                }
            } catch (ArchivioEx e) {
                System.err.println("Errore: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Errore imprevisto: " + e.getMessage());
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
