import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    public static void Start() throws InterruptedException {
        Scanner lukija = new Scanner(System.in);

        //antaa sanojen joukon tekstitiedostosta sanat.txt
        ArrayList<String> sanat = annaSanat();
        String sana = "";
        int sananPituus = 0;


        int elamaMaara = 5;             //elamien lukumaara
        int oikeinMaara = 0;            //oikeiden arvausten lukumaara
        int vanhaOikeidenMaara = 0;     //muistipaikka edelliselle lukumaaralle

        while (true) {
            System.out.println("\n1. Pelaaja VS Tietokone");
            System.out.println("2. Pelaaja VS Pelaaja");

            String syote = lukija.nextLine();

            if (syote.equals("1")) {
                Menu.cls();
                //Arpoo satunnaisen sanan sanojen joukosta
                Random random = new Random();
                sana = sanat.get(random.nextInt(sanat.size()));
                sananPituus = sana.length();
                break;
            } else if (syote.equals("2")) {
                Menu.cls();
                System.out.println("Toinen pelaaja syöttaa sanan: (Ei yli 12 kirjaimen sanoja)");

                sana = lukija.nextLine();
                sananPituus = sana.length();
                if (sananPituus > 12) {
                    System.out.println("Virheellinen syöte");
                    continue;
                }
                break;

            } else {
                Menu.cls();
                System.out.println("Virheellinen syöte");
                continue;
            }
        }


        //sanojen pituus on maksimissaan 12, oikein arvattu kirjain muuttuu taulukkossa ykkoseksi
        int[] arvaukset = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ArrayList<Character> vaaratKirjaimet = new ArrayList<>();
        ArrayList<Character> oikeatKirjaimet = new ArrayList<>();

        //kun lopeta=1, peli paattyy
        int lopeta = 0;
        Menu.cls();

        //Peli looppi, paattyy kun oikeiden arvausten maara on yhta suurin kuin sanan pituus
        while (oikeinMaara < sananPituus) {
            System.out.println("\n");
            hirsipuuKuva(elamaMaara);

            //Kaydaan sana lapi kirjain kirjaimelta
            for (int i = 0; i < sananPituus; i++) {
                if (arvaukset[i] == 1) {
                    //Jos kirjain on arvattu oikein, tulostetaan se kirjain
                    System.out.print(sana.charAt(i));
                } else {
                    //Muuten tulostetaan viiva
                    System.out.print("-");
                }

            }


            System.out.println("\n\nVäärät kirjaimet:\n");
            System.out.println(vaaratKirjaimet);
            System.out.println("\nSyötä kirjain: (\"arvaa\", jos haluat arvata sanan. \"lopeta\" lopettaa pelin)");

            String syote = lukija.nextLine();

            //Syotteella "lopeta" ohjelman toiminta paattyy
            if (syote.equals("lopeta")) {
                lopeta = 1;
                break;
            }

            if (syote.equals("arvaa")) {
                System.out.println("Syöta arvauksesi");
                String arvaus = lukija.nextLine();
                if (arvaus.equals(sana)) {
                    System.out.println(("\nVoitit pelin, oikea sana oli: " + sana));
                    Thread.sleep(2500);
                    Menu.cls();
                    Menu.kaynnistaMenu();
                } else {
                    elamaMaara--;
                    System.out.println("\nVäärin arvattu. Elämia jäljellä: " + elamaMaara + "\n");
                    Thread.sleep(2500);
                    Menu.cls();
                    //Jos elamat loppuvat siirrytaan ulos while-loopista
                    if (elamaMaara == 0) {
                        break;
                    }
                    continue;
                }
            }


            //Syotteen ensimmainen kirjain lasketaan arvaukseksi
            char kirjain = syote.charAt(0);


            //Laitetaan muistiin aikaisempi oikeiden arvausten lukumaara
            vanhaOikeidenMaara = oikeinMaara;


            if (vaaratKirjaimet.contains(kirjain) || oikeatKirjaimet.contains(kirjain)) {
                System.out.println("\nKirjain jo syötetty\n");
                Thread.sleep(2500);
                Menu.cls();

            } else {

                //Kaydaan sana kirjain kirjaimelta lapi
                for (int i = 0; i < sananPituus; i++) {
                    //Jos arvattu kirjain loytyy sanasta oikeiden maara kasvaa yhdella
                    if (kirjain == sana.charAt(i)) {
                        arvaukset[i] = 1;   //taulukossa vastaava kohta muuttuu ykkoseksi, jolloin se tulostuu oikein
                        oikeinMaara++;

                    }
                }


                //Jos oikeiden arvausten lukumaara ei kasvanut viime loopissa vastaus oli vaarin
                if (vanhaOikeidenMaara == oikeinMaara) {
                    vaaratKirjaimet.add(kirjain);
                    elamaMaara--;
                    System.out.println("\nVäärin arvattu. Elämiä jäljellä: " + elamaMaara + "\n");
                    Thread.sleep(2500);
                    Menu.cls();
                    //Jos elamat loppuvat siirrytaan ulos while-loopista
                    if (elamaMaara == 0) {
                        break;
                    }

                } else {
                    System.out.println("\nOikein arvattu! \n");
                    oikeatKirjaimet.add(kirjain);
                    Thread.sleep(2500);
                    Menu.cls();

                }
            }

        }//while loop


        //Maaritellaan miten peli paattyy
        if (lopeta == 1) {
            System.out.println("\nPeli lopetettiin.\n");
            Thread.sleep(2500);
            Menu.cls();
            Menu.kaynnistaMenu();
        } else if (elamaMaara == 0) {
            System.out.println("________");
            System.out.println("|      O");
            System.out.println("|     /|\\");
            System.out.println("|     / \\");
            System.out.println("|");
            System.out.println("\n\n");
            System.out.println(("\nPeli päättyi, oikea sana oli: " + sana));
            Thread.sleep(2500);
            Menu.cls();
            Menu.kaynnistaMenu();
        } else {
            System.out.println(("\nVoitit pelin, oikea sana oli: " + sana));
            Thread.sleep(2500);
            Menu.cls();
            Menu.kaynnistaMenu();
        }
    }


    //Metodi hakee mahdolliset sanat tiedostosta sanat.txt
    public static ArrayList<String> annaSanat() {
        ArrayList<String> sanat = new ArrayList<>();

        // luodaan lukija tiedoston lukemista varten
        try (Scanner tiedostonLukija = new Scanner(Paths.get("sanat.txt"))) {

            // luetaan kaikki tiedoston rivit
            while (tiedostonLukija.hasNextLine()) {
                sanat.add(tiedostonLukija.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        return sanat;
    }


    public static void hirsipuuKuva(int elamaMaara) {
        if (elamaMaara == 4) {
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("\n\n");
        } else if (elamaMaara == 3) {
            System.out.println("________");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("\n\n");
        } else if (elamaMaara == 2) {
            System.out.println("________");
            System.out.println("|      O");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("\n\n");
        } else if (elamaMaara == 1) {
            System.out.println("________");
            System.out.println("|      O");
            System.out.println("|     /|\\");
            System.out.println("|");
            System.out.println("|");
            System.out.println("\n\n");
        }
    }
}
