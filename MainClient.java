/*ASSIGNMENT NUMERO 10 LABORATORIO DI RETI A.A 2019-2020
Nome Assignment : GestioneCongresso (Lato Client)

Autore : Enrico Tomasi
Numero Matricola : 503527

OVERVIEW : Implementazione di un programma Client che interagisce in remoto con
un Server che implementa la gestione delle registrazioni ad un congresso
*/ 
package Client;
import gestionecongressoserver.CongressManaging;
import java.awt.event.KeyEvent;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/*
    @CLASS MainClass
    @OVERVIEW Classe che implementa il ciclo di vita di un Client che si connette
    con un CongressServer.
*/
public class MainClient
{ 
    /*
        @METHOD main
        @OVERVIEW Metodo che implementa il ciclo di vita di un generico client.
    */
    public static void main(String[] args)
    {
        int PORT = 9000;
            
        CongressManaging Manager;
            
        Remote RMObject;
        
        try {
            //Reperimento del registro in remoto.
            Registry reg = LocateRegistry.getRegistry(PORT);
            
            //Ricerca dell'oggetto di tipo CongressManaging.
            RMObject = reg.lookup("CONGRESS_MANAGER");
            
            //Inizializzazione dell'oggetto remoto.
            Manager = (CongressManaging) RMObject;
            
            HashMap<Integer,HashMap<Integer,ArrayList<String>>> Table = null;
            
            boolean TerminationFlag = false;
            
            Scanner sc = new Scanner(System.in);
            
            //Messaggio di aiuto iniziale.
            Help();
            
            //Ciclo di vita del client.
            while(!TerminationFlag)
            {
                System.out.println("Inserire un comando in input ");
                
                /*Consideriamo il carattere successivo in ingresso come
                un singolo comando, trasformandolo in carattere maiuscolo
                per non rendere case-sensitive il riconoscimento del comando.
                */
                String cmd = sc.next().toUpperCase();
                int x = cmd.codePointAt(0);
                
                //Parsing ed esecuzione del comando
                switch(x)
                {
                    case(KeyEvent.VK_H):
                        Help();
                        break;
                    case(KeyEvent.VK_C):
                        TerminationFlag = true;
                        break;
                    case(KeyEvent.VK_S):
                        for(int i=1; i<=3; i++)
                        {
                            System.out.println("GIORNATA NUMERO"+i);
                            Print(Table.get(i));
                        }
                        break;
                    case(KeyEvent.VK_R):
                        Add(Manager);
                        break;
                    case(KeyEvent.VK_T):
                        Table = Get(Manager);
                         for(int i=1; i<=3; i++)
                        {
                            System.out.println("GIORNATA NUMERO"+i);
                            Print(Table.get(i));
                        }
                        break;
                    default:
                        System.out.println("Comando in input non valido, riprovare\n");
                        break;                 
                }
            }          
        } 
        catch (RemoteException ex) 
        {
           System.out.println("ERRORE NELL'ACCESSO ALL'OGGETTO REMOTO O NELL'ESECUZIONE DI UN SUO METODO");
        } 
        catch (NotBoundException ex) 
        {
           System.out.println("ERRORE: OGGETTO REMOTO NON REGISTRATO NEL REGISTRY DEL SERVER");
        } 
    }
        
    /*
        @METHOD Add
        @OVERVIEW Procedura per l'aggiunta di uno speaker al congresso mediante
        CongressManaging remoto.
    
        @PAR M CongressManaging corrente.
    
        @THROWS RemoteException nel caso vengano riscontrati errori nell'esecuzione
        di questo metodo da remoto. 
    */
    public static synchronized void Add(CongressManaging M) throws RemoteException
    {
        /*N.B: Dichiarare il metodo synchronized ha evitato la InputMismatchException 
        ed ha permesso che la richiesta di registrazione di uno speaker fosse sincrona.*/
        Scanner scr = new Scanner(System.in);
        
        System.out.println("Inserire il nome dello speaker: ");
        String Speaker = scr.nextLine();
        System.out.println("Inserire il giorno in cui lo speaker desidera conferire");
        int Day = Integer.parseInt(scr.nextLine());
        System.out.println("Inserire la sessione in cui lo speaker desidera conferire");
        int Session = Integer.parseInt(scr.nextLine());
        
        M.AddSpeaker(Session, Day, Speaker); 
    }
    
       /*
        @METHOD Get
        @OVERVIEW Procedura per il reperimento del programma del congresso mediante
        CongressManaging remoto.
    
        @PAR M CongressManaging corrente.
    
        @THROWS RemoteException nel caso vengano riscontrati errori nell'esecuzione
        di questo metodo da remoto. 
    */
    public static synchronized HashMap<Integer,HashMap<Integer,ArrayList<String>>> Get(CongressManaging M) throws RemoteException,IllegalArgumentException
    {
        /*Modifica dell'ultimo momento: il programma ottenuto è quello dell'intero congresso
        non di un singolo giorno
        */
        HashMap<Integer,HashMap<Integer,ArrayList<String>>> DayTable = new HashMap<>();
        
        for(int i=1; i<=3; i++)
        {
             DayTable.put(i,M.GetProgram(i));
        }
       
        return DayTable;
    }
    
    /*
        @METHOD Help
        @OVERVIEW Metodo che stampa su console la guida all'utilizzo del programma.
    */
    public static void Help()
    {
        System.out.println("----GUIDA ALL'UTILIZZO DEL CONGRESS MANAGER----");
        System.out.println("H: Apri Guida");
        System.out.println("C: Esci dal programma");
        System.out.println("S: Ristampa programma memorizzato");
        System.out.println("R: Registra speaker");
        System.out.println("T: Guarda programma delle giornate");
        System.out.println("----------------------------------------------\n");
    }
    
    /*
        @METHOD Print
        @OVERVIEW Metodo che stampa su console la tabella del programma memorizzata.
    */
    public static void Print(HashMap<Integer,ArrayList<String>> Table)
    {
       if(Table == null)
       {
           System.out.println("Nessuna tabella presente in memoria\n");
           return;
       }
        
       System.out.println("--------------------------------------------\n");
       System.out.println("LISTA DELLE SESSIONI NELLA GIORNATA RICHIESTA\n");
       
       for(int i=0; i<12; i++)
       {
           ArrayList<String> Session;
           Session = Table.get(i);
           System.out.println("SESSIONE "+(i+1)+": "+Session.toString());
       }
       
       System.out.println("--------------------------------------------\n");
    }
    
}
