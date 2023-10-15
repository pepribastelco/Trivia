import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREY = "\u001B[37m";

    public static void main(String[] args) {
        System.out.println("Hello and welcome al trivia! \u001B[0m");
        ArrayList<String> preguntes = new ArrayList<>();
        readLinesFromFile("preguntas.txt", preguntes);
        ArrayList<String> respostes = new ArrayList<>();
        readLinesFromFile("respuestas.txt", respostes);
        String again = "S";
        do{
            Play(preguntes, respostes);
            again=askPlayAgain();
        }while (again.equalsIgnoreCase("S"));
        System.out.println("A reveure!");
    }
    private static String askPlayAgain(){
        System.out.println(ANSI_GREEN + "Vols tornar a jugar? (S/n)" + ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String answer="";
        while ((!(answer.equalsIgnoreCase("S"))) && (!(answer.equalsIgnoreCase("N")))) {
            answer = scanner.nextLine();
            if ((!(answer.equalsIgnoreCase("S"))) && (!(answer.equalsIgnoreCase("N")))) {
                System.out.println(ANSI_GREEN + "Hauries d'escriure 's' o 'n'" + ANSI_RESET);
            }
        }
        return answer;
    }
    private static void Play(ArrayList<String> preguntes, ArrayList<String> respostes) {
        int numPregunta=1;
        int respostesCorrectes=0;
        Scanner scanner = new Scanner(System.in);
        while (numPregunta<=10) //Fem un while de 10 per fer 10 preguntes
        {
             //pensem un numero aleatori de 0 al numero de preguntes menys 1
            int IndexRand = GetIndexRandPregunta(preguntes);
            String pregunta= preguntes.get(IndexRand);
            System.out.println("Pregunta "+numPregunta+":"); //Imprimim la pregunta corresponent al numero
            String respostaCorrecte= respostes.get(IndexRand);
            System.out.println(pregunta+  " ("+ANSI_GREY+respostaCorrecte+ANSI_RESET+")");
            System.out.print("Resposta: "+ ANSI_RESET);
            String resposta = scanner.nextLine(); // Llegeix una linia des del teclat
            if (respostaCorrecte.equals(resposta)){//camparem paraula del teclat amb la resposta i si es ok, incrementem punts en 1
                System.out.println(ANSI_GREEN+ "Correcte!"+ANSI_RESET);
                respostesCorrectes++;
            } else {
                System.out.println(ANSI_RED +"Incorrecte. Las resposta correcte és "+ ANSI_RESET + respostaCorrecte);
            }
            numPregunta++;
            try {
                Thread.sleep(2000); // Pausa de 2 segons (2000 mil·lisegons)
            } catch (InterruptedException e) {
                System.out.println("S'ha produït un error durant la pausa.");
            }
        }
        System.out.println ("Has respòs "+ respostesCorrectes+" correctament de 10");
        if (respostesCorrectes>7){
            System.out.println ("Bona Feina!");
        }
        else if (respostesCorrectes<5){
            System.out.println ("Ànims, la propera jugada poder n'encertes més!");
        }
    }
    private static int GetIndexRandPregunta(ArrayList<String> llistaLinies) {
        int numLinies = llistaLinies.size();
        Random rand = new Random();
        int numeroAleatori = rand.nextInt(numLinies);
        return (numeroAleatori);
    }
    private static void readLinesFromFile(String file, ArrayList<String> lineas) {
        try { //Llegeix preguntes de fitxer i posa les preguntes en un array de Strings de preguntes
            String archivo = file;
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
