package libreria;

public class Rivista extends ElementiCatalogo {
    public enum Periodicita {
        SETTIMANALE, MENSILE, SEMESTRALE
    }

    private Periodicita periodicita;

    public Rivista(int codiceISBN, String titolo, int annoPubblicazione, int numeroPagine ) {
        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);

    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return super.toString() + ", Periodicità: " + periodicita;
    }
}
