import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    private static List<Fuvar> fuvarok = new ArrayList<Fuvar>();
    private static DecimalFormat format = new DecimalFormat("0.##");

    public static void main(String[] args) {
        Beolvas("fuvar.csv");
        System.out.printf("Összesen %d utazás került feljegyzésre.\n", FuvarokSzama());
        System.out.printf("6185-ös taxi bevétele $%s volt, %d fuvar alatt.\n", format.format(Bevetel("6185")), FuvarokSzamaId("6185"));
        System.out.printf("A taxisok összesen %s mérföldet tettek meg.\n", format.format(TavolsagSum()));
        System.out.printf("A leghosszabb fuvar összesen %d másodpercet vett igénybe.\n", LeghosszabbFuvar());
        System.out.printf("A legbőkezűbb borravalójú fuvara adatai: \n%s\n", Legbokezubb());
        System.out.printf("A 4261-es taxis összesen %s km-t tett meg.\n", format.format(Tavolsag("4261")));
        System.out.printf("Hibás sorok száma: %d\n", HibasSorokSzama());
        System.out.printf("Hibás sorok időtartama: %dms\n", HibasSorokIdotartama());
        System.out.printf("Hibás sorok teljes bevétele: $%s\n", format.format(HibasSorokBevetele()));
        System.out.printf("A 1452-es taxi%s létezik.\n", TaxiLetezik("1452")?"":" nem");
        System.out.printf("A 3 időben legrövidebb fuvar: \n%s\n", Legrovidebb3());
        System.out.printf("December 24.-én %d fuvar történt.\n", KaracsonyiFuvarok());
        System.out.printf("December 31. fuvaroknál %s arányban adtak", December31Borravalo());

    }

    private static void Beolvas(String fileName){
        try {
            BufferedReader br = new BufferedReader( new FileReader( fileName ) );
            br.readLine();
            String sor = br.readLine();
            while(sor != null){
                fuvarok.add(new Fuvar(sor));
                sor = br.readLine();
            }

            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int FuvarokSzama(){
        return fuvarok.size();
    }

    private static double Bevetel(String id){
        return fuvarok.stream().filter(o -> o.getTaxi_id().equals(id)).mapToDouble(o -> o.getViteldij() + o.getBorravalo()).sum();
    }

    private static long FuvarokSzamaId(String id){
        return fuvarok.stream().filter(o -> o.getTaxi_id().equals(id)).count();
    }

    private static double TavolsagSum(){
        return fuvarok.stream().mapToDouble(Fuvar::getTavolsag).sum();
    }

    private static int LeghosszabbFuvar(){
        return fuvarok.stream().mapToInt(Fuvar::getIdotartam).max().orElse(0);
    }

    private static String Legbokezubb(){
        double maxRatio = fuvarok.stream()
                .mapToDouble(o -> o.getBorravalo() / o.getViteldij())
                .max()
                .orElse(0);

        return fuvarok.stream()
                .filter(o -> o.getBorravalo() / o.getViteldij() == maxRatio)
                .findFirst().orElse(null)
                .toString();
    }

    private static double Tavolsag(String id){
        return fuvarok.stream().filter(o -> o.getTaxi_id().equals(id)).mapToDouble(Fuvar::getTavolsag).sum()*1.6;
    }

    private static Stream<Fuvar> HibasSorok(){
        return fuvarok.stream().filter(o -> o.getViteldij() > 0 && o.getIdotartam() > 0 && o.getTavolsag() == 0);
    }

    private static long HibasSorokSzama(){
        return HibasSorok().count();
    }

    private static int HibasSorokIdotartama(){
        return HibasSorok().mapToInt(Fuvar::getIdotartam).sum();
    }

    private static double HibasSorokBevetele(){
        return HibasSorok().mapToDouble(o -> o.getViteldij() + o.getBorravalo()).sum();
    }

    private static boolean TaxiLetezik(String id){
        return fuvarok.stream().anyMatch(o -> o.getTaxi_id().equals(id));
    }

    private static String Legrovidebb3(){
        return fuvarok.stream().filter(o -> !HibasSorok().toList().contains(o)).sorted(Comparator.comparingInt(Fuvar::getIdotartam)).limit(3).toList().toString();
    }

    private static long KaracsonyiFuvarok(){
        return fuvarok.stream().filter(o -> o.getIndulas().contains("12-24")).count();
    }

    private static String December31Borravalo(){
        long borravaloval = fuvarok.stream().filter(o -> o.getIndulas().contains("12-31") && o.getBorravalo() > 0).count();
        long borravaloNelkul = fuvarok.stream().filter(o -> o.getIndulas().contains("12-31") && o.getBorravalo() == 0).count();
        return borravaloval + "/" + borravaloNelkul;
    }
}