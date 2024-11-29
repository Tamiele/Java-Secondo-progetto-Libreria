package libreria;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Archivio {

    private static HashSet<Integer> isbnSet = new HashSet<>(); // Per tenere traccia degli ISBN univoci

    // Metodo creazione libro
    public static Libro creazioneLibro(Scanner scanner) throws ArchivioEx {
        System.out.println("Inserisci ISBN:");
        int ISBN = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        // Controlla se l'ISBN è già stato usato
        if (!isbnSet.add(ISBN)) {
            throw new ArchivioEx("ISBN Già esistente: " + ISBN);
        }

        System.out.println("Inserisci il titolo:");
        String titolo = scanner.nextLine();

        System.out.println("Inserisci l'anno di pubblicazione:");
        int annoPubblicazione = scanner.nextInt();

        System.out.println("Inserisci il numero di pagine:");
        int numeroPagine = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        System.out.println("Inserisci l'autore:");
        String autore = scanner.nextLine();

        System.out.println("Inserisci il genere:");
        String genere = scanner.nextLine();

        return new Libro(ISBN, titolo, annoPubblicazione, numeroPagine, autore, genere);
    }

    // Metodo creazione rivista
    public static Rivista creazioneRivista(Scanner scanner) throws ArchivioEx {
        System.out.println("Inserisci ISBN:");
        int ISBN = scanner.nextInt();
        scanner.nextLine();

        // Controlla se l'ISBN è già stato usato
        if (!isbnSet.add(ISBN)) {
            throw new ArchivioEx("ISBN Già esistente: " + ISBN);
        }

        System.out.println("Inserisci il titolo:");
        String titolo = scanner.nextLine();

        System.out.println("Inserisci l'anno di pubblicazione:");
        int annoPubblicazione = scanner.nextInt();

        System.out.println("Inserisci il numero di pagine:");
        int numeroPagine = scanner.nextInt();

        System.out.println("Scegli la periodicità (1 = Settimanale, 2 = Mensile, 3 = Semestrale):");
        int sceltaPeriodicita = scanner.nextInt();

        Rivista.Periodicita periodicita;
        switch (sceltaPeriodicita) {
            case 1:
                periodicita = Rivista.Periodicita.SETTIMANALE;
                break;
            case 2:
                periodicita = Rivista.Periodicita.MENSILE;
                break;
            case 3:
                periodicita = Rivista.Periodicita.SEMESTRALE;
                break;
            default:
                periodicita = Rivista.Periodicita.MENSILE;
                System.out.println("Scelta non valida, impostato MENSILE come periodicità.");
        }

        Rivista rivista = new Rivista(ISBN, titolo, annoPubblicazione, numeroPagine);
        rivista.setPeriodicita(periodicita);
        return rivista;
    }

    public static ElementiCatalogo ricercaElementoIsbn(Scanner scanner, HashSet<ElementiCatalogo> archivio) {
        System.out.print("inserisci Isbn per la ricerca");
        int isbn = scanner.nextInt();
        return archivio.stream()
                .filter(elemento -> elemento.getCodiceISBN() == isbn)
                .findFirst()
                .orElse(null);

    }

    public void rimuoviElemento(Scanner scanner, HashSet<ElementiCatalogo> archivio) {
        System.out.print("inserisci Isbn per eliminare un elemento");
        int isbn = scanner.nextInt();
        ElementiCatalogo elemntoDaEliminare = archivio.stream()
                .filter(elemento -> elemento.getCodiceISBN() == isbn)
                .findFirst()
                .orElse(null);

        if (elemntoDaEliminare != null) {
            archivio.remove(elemntoDaEliminare);
            isbnSet.remove(isbn);
            System.out.println("Elemento" + isbn + " eliminato con successo");
        } else {
            System.out.println("Nessuna elemento trovata con l'ISBN :" + isbn);
        }

    }

    //ricerca per anno di pubblicazione
    public static void ricercaPerAnno(Scanner scanner, HashSet<ElementiCatalogo> archivio) {
        System.out.println("Inserisci anno di pubblicazione:");
        int annoDiPubblicazione = scanner.nextInt();

        List<ElementiCatalogo> pubblicazioniTrovate = archivio.stream()
                .filter(pubblicazione -> pubblicazione.getAnnoPubblicazione() == annoDiPubblicazione)
                .collect(Collectors.toList());

        if (!pubblicazioniTrovate.isEmpty()) {
            System.out.println("Pubblicazioni trovate:");
            pubblicazioniTrovate.forEach(System.out::println);
        } else {
            System.out.println("Nessuna pubblicazione trovata per l'anno " + annoDiPubblicazione);
        }
    }


    // Ricerca per autore
    public static void ricercaPerAutore(Scanner scanner, HashSet<Libro> archivio) {
        System.out.println("Inserisci nome autore:");
        String nome = scanner.nextLine();

        List<Libro> libriTrovati = archivio.stream()
                .filter(libro -> libro.getAutore().equalsIgnoreCase(nome))
                .collect(Collectors.toList());

        if (!libriTrovati.isEmpty()) {
            System.out.println("Libri trovati dell'autore " + nome + ":");
            libriTrovati.forEach(System.out::println);
        } else {
            System.out.println("Nessun libro trovato con l'autore " + nome);
        }
    }

    public void modificaElemento(Scanner scanner, HashSet<ElementiCatalogo> archivio) {
        System.out.print("Inserisci l'ISBN dell'elemento da modificare: ");
        int isbn = scanner.nextInt();
        scanner.nextLine();

        ElementiCatalogo elementoDaModificare = archivio.stream()
                .filter(elemento -> elemento.getCodiceISBN() == isbn)
                .findFirst()
                .orElse(null);

        if (elementoDaModificare != null) {
            System.out.println("Elemento trovato: " + elementoDaModificare);
            boolean continua = true;

            while (continua) {
                System.out.println("\nQuale attributo vuoi modificare?");
                System.out.println("1. Titolo");
                System.out.println("2. Anno di pubblicazione");
                System.out.println("3. Numero di pagine");

                if (elementoDaModificare instanceof Libro) {
                    System.out.println("4. Autore");
                    System.out.println("5. Genere");
                } else if (elementoDaModificare instanceof Rivista) {
                    System.out.println("4. Periodicità");
                }
                System.out.println("6. Esci dalla modifica");

                System.out.print("Inserisci il numero dell'attributo da modificare: ");
                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        System.out.print("Inserisci il nuovo titolo: ");
                        String nuovoTitolo = scanner.nextLine();
                        elementoDaModificare.setTitolo(nuovoTitolo);
                        break;

                    case 2:
                        System.out.print("Inserisci il nuovo anno di pubblicazione: ");
                        int nuovoAnno = scanner.nextInt();
                        scanner.nextLine();
                        elementoDaModificare.setAnnoPubblicazione(nuovoAnno);
                        break;

                    case 3:
                        System.out.print("Inserisci il nuovo numero di pagine: ");
                        int nuovePagine = scanner.nextInt();
                        scanner.nextLine();
                        elementoDaModificare.setNumeroPagine(nuovePagine);
                        break;

                    case 4:
                        if (elementoDaModificare instanceof Libro) {
                            System.out.print("Inserisci il nuovo autore: ");
                            String nuovoAutore = scanner.nextLine();
                            ((Libro) elementoDaModificare).setAutore(nuovoAutore);
                        } else if (elementoDaModificare instanceof Rivista) {
                            System.out.println("Scegli la nuova periodicità (1 = Settimanale, 2 = Mensile, 3 = Semestrale): ");
                            int nuovaPeriodicita = scanner.nextInt();
                            scanner.nextLine();
                            Rivista.Periodicita periodicita = switch (nuovaPeriodicita) {
                                case 1 -> Rivista.Periodicita.SETTIMANALE;
                                case 2 -> Rivista.Periodicita.MENSILE;
                                case 3 -> Rivista.Periodicita.SEMESTRALE;
                                default -> {
                                    System.out.println("Scelta non valida. Periodicità impostata su MENSILE.");
                                    yield Rivista.Periodicita.MENSILE;
                                }
                            };
                            ((Rivista) elementoDaModificare).setPeriodicita(periodicita);
                        }
                        break;

                    case 5:
                        if (elementoDaModificare instanceof Libro) {
                            System.out.print("Inserisci il nuovo genere: ");
                            String nuovoGenere = scanner.nextLine();
                            ((Libro) elementoDaModificare).setGenere(nuovoGenere);
                        } else {
                            System.out.println("Scelta non valida per il tipo di elemento.");
                        }
                        break;

                    case 6:
                        System.out.println("Uscita dalla modifica.");
                        continua = false;
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                }

                if (continua) {
                    System.out.println("Modifica completata. Elemento aggiornato: " + elementoDaModificare);
                }
            }
        } else {
            System.out.println("Nessun elemento trovato con l'ISBN: " + isbn);
        }
    }


    public void statisticheArchivio(HashSet<ElementiCatalogo> archivio) {
        long numeroLibri = archivio.stream()
                .filter(elemento -> elemento instanceof Libro)
                .count();

        long numeroRiviste = archivio.stream()
                .filter(elemento -> elemento instanceof Rivista)
                .count();


        ElementiCatalogo elementoConPiuPagine = archivio.stream()
                .max((a, b) -> Integer.compare(a.getNumeroPagine(), b.getNumeroPagine()))
                .orElse(null);

        double mediaPagine = archivio.stream()
                .mapToInt(ElementiCatalogo::getNumeroPagine)
                .average()
                .orElse(0.0);

        System.out.println("Statistiche del catalogo:");
        System.out.println("Numero totale di libri presenti: " + numeroLibri);
        System.out.println("Numero totale di riviste presenti: " + numeroRiviste);

        if (elementoConPiuPagine != null) {
            System.out.println("Elemento con il maggior numero di pagine: " + elementoConPiuPagine);
        }

        System.out.printf("Media del numero di pagine di tutti gli elementi: %.2f%n", mediaPagine);
    }


}
