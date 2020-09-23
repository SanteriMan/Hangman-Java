import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Menu {

    public static void kaynnistaMenu(){
        System.out.println("\nHirsipuu");
        System.out.println("\n1. Pelaa peli");
        System.out.println("2. Lisää sana");
        System.out.println("3. Kuinka pelata?");
        System.out.println("4. Poistu pelistä");
        System.out.print("\nAnna numero: ");
        valinta();
    }

    public static void valinta(){
        Scanner lukija = new Scanner(System.in);
        int numero= 0;
        try {
            numero = Integer.parseInt(lukija.nextLine());
            switch (numero) {
                case 1:
                    cls();
                    Game.Start();
                    break;
                case 2:
                    cls();
                    System.out.println("\nSyöta lisättävä sana: (Ei yli 12 kirjaimen sanoja)");
                    String sana = lukija.nextLine();
                    String rivivaihto = "\n";
                    if (sana.length() > 12) {
                        System.out.println("Virheellinen syöte!");
                        Thread.sleep(2500);
                        kaynnistaMenu();
                    } else {
                        try {
                            Files.write(Paths.get("sanat.txt"), sana.getBytes(), StandardOpenOption.APPEND);
                            Files.write(Paths.get("sanat.txt"), rivivaihto.getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            System.out.println("Virhe tapahtui");
                        }
                        cls();
                        kaynnistaMenu();
                    }
                    break;
                case 3:
                    cls();
                    System.out.println("\nTarkoituksena on arvata sana. Sanan jokaista kirjainta kuvaa (_)-merkki.");
                    System.out.println("Jos arvaat kirjaimen oikein, kirjain ilmestyy sanaan oikealle paikalleen.");
                    System.out.println("Väärästä arvauksesta menetät yhden viidesta elämästä.");
                    System.out.println("Kun tikku-ukkokuvio on valmis häviät pelin.");
                    System.out.println("\nKun olet valmis, paina enter");
                    String enter = lukija.nextLine();
                    if (enter.equals("")) {
                        cls();
                        kaynnistaMenu();
                    } else {
                        cls();
                        kaynnistaMenu();
                    }
                    break;

                case 4:
                    cls();
                    System.exit(1);
                default:
                    System.out.println("Epäkelpo syöte, syötä numero 1-4");
                    valinta();
            }
        }catch (Exception e){
            System.out.println("Epäkelpo syöte, syotä numero 1-4");
            valinta();
        }
    }

    public static void cls()
    {
        try
        {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        }catch(Exception E)
        {
            System.out.println(E);
        }
    }

}
