public class Fuvar {
    private String taxi_id;
    private String indulas;
    private int idotartam;
    private double tavolsag;
    private double viteldij;
    private double borravalo;
    private String fizetes_modja;

    public Fuvar(String taxi_id, String indulas, int idotartam, double tavolsag, double viteldij, double borravalo, String fizetes_modja) {
        this.taxi_id = taxi_id;
        this.indulas = indulas;
        this.idotartam = idotartam;
        this.tavolsag = tavolsag;
        this.viteldij = viteldij;
        this.borravalo = borravalo;
        this.fizetes_modja = fizetes_modja;
    }

    public Fuvar(String sor){
        String[] adatok = sor.split(";");
        this.taxi_id = adatok[0];
        this.indulas = adatok[1];
        this.idotartam = Integer.parseInt(adatok[2]);
        this.tavolsag = Double.parseDouble(adatok[3].replace(",", "."));
        this.viteldij = Double.parseDouble(adatok[4].replace(",", "."));
        this.borravalo = Double.parseDouble(adatok[5].replace(",", "."));
        this.fizetes_modja = adatok[6];
    }

    public String getTaxi_id() {
        return taxi_id;
    }

    public String getIndulas() {
        return indulas;
    }

    public int getIdotartam() {
        return idotartam;
    }

    public double getTavolsag() {
        return tavolsag;
    }

    public double getViteldij() {
        return viteldij;
    }

    public double getBorravalo() {
        return borravalo;
    }

    public String getFizetes_modja() {
        return fizetes_modja;
    }

    @Override
    public String toString() {
        return "Fuvar{" +
                "taxi_id='" + taxi_id + '\'' +
                ", indulas='" + indulas + '\'' +
                ", idotartam=" + idotartam +
                ", tavolsag=" + tavolsag +
                ", viteldij=" + viteldij +
                ", borravalo=" + borravalo +
                ", fizetes_modja='" + fizetes_modja + '\'' +
                '}';
    }
}
