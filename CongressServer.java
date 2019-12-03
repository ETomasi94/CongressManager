/*ASSIGNMENT NUMERO 10 LABORATORIO DI RETI A.A 2019-2020
Nome Assignment : GestioneCongresso (Lato Server)

Autore : Enrico Tomasi
Numero Matricola : 503527

OVERVIEW : Implementazione di un programma Server che gestisce le registrazioni
ad un congresso considerando gli speaker, cinque in totale, e le sessioni, dodici
in totale, presenti in una giornata.
*/ 

package gestionecongressoserver;


import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
/*
    @CLASS CongressServer
    @OVERVIEW Classe che implementa il servizio mediante oggetto remoto di gestione
    delle giornate di congressi.
*/
public class CongressServer extends RemoteServer implements CongressManaging
{
    //HashMap che associa ad ogni giornata una struttura dati di tipo ConferenceDay.
    HashMap<Integer,ConferenceDay> Congresses;
    
    /*
        @METHOD CongressServer
        @OVERVIEW Metodo costruttore che inizializza l'implementazione del 
        server remoto.
        @THROWS RemoteException in caso di errori nell'accesso o nell'inizializzazione
        degli oggetti remoti.
    */
    public CongressServer() throws RemoteException
    {
        //Creazione di una nuova HashMap per registrare tutte le giornate.
        Congresses = new HashMap<>();
        
        //Creazione ed inserimento di tre nuove giornate.
        Congresses.put(1,new ConferenceDay());
        Congresses.put(2,new ConferenceDay());
        Congresses.put(3,new ConferenceDay());
    }

    /*
        @METHOD AddSpeaker.
        @OVERVIEW Metodo concreto che registra, quando possibile, uno speaker
        ad una sessione di una determinata giornata.
        
        @PAR Session intero che rappresenta la sessione a cui si vuole iscrivere lo speaker.
        @PAR Day intero che rappresenta la giornata in cui si vuole che lo speaker conferisca.
        @PAR Speaker Stringa che contiene il nome dello speaker.
    
        @THROWS RemoteException nel caso vengano riscontrati errori nell'esecuzione di questo metodo
        remoto.
    */
    @Override
    public void AddSpeaker(int session,int day, String Speaker) throws RemoteException,IllegalArgumentException 
    {
        if(day<1 || day>3 || session <1 || session>12 || Speaker == null)
        {
            System.out.println("ERRORE: Parametri di registrazione speaker non validi");
            throw new IllegalArgumentException();
        }
        
        try 
        {
            //Creazione oggetto di supporto di tipo ConferenceDay
            ConferenceDay Day = (ConferenceDay) Congresses.get(day);
            
            Day.RegisterSpeaker(session, Speaker);
        } 
        catch (IllegalArgumentException | IOException ex) 
        {
            System.out.println("ERRORE: Impossibile aggiungere lo speaker");
        }
    }

    /*
        @METHOD GetProgram
        @OVERVIEW Metodo che restituisce il programma di una determinata giornata
        del congresso.
        
        @PAR day intero che rappresenta la giornata del congresso
    
        @THROWS RemoteException nel caso vengano riscontrati errori nell'esecuzione di questo metodo
        remoto.
    */
    @Override
    public HashMap<Integer,ArrayList<String>> GetProgram(int day) throws RemoteException 
    {
       //Gestione dell'errore.
       if(day<1 || day>3)
       {
           System.out.println("Giornata non valida");
           throw new IllegalArgumentException();
       }
        
       ConferenceDay Day = (ConferenceDay) Congresses.get(day);
        
       HashMap<Integer,ArrayList<String>> Table = Day.GetDayTable();
       
       Print(day);
       
       return Table;
    }
    
    /*
        @METHOD Print
        @OVERVIEW Metodo che, dato un intero rappresentante una giornata in input
        ne stampa il programma in modo da tenere traccia degli aggiornamenti, è
        finalizzato al debug.
    
        @PAR day Intero che rappresenta la giornata di cui si vuole visionare il programma.
    */
    public void Print(int day) throws IllegalArgumentException
    {
       //Gestione dell'errore.
       if(day<1 || day>3)
       {
           System.out.println("Giornata non valida");
           throw new IllegalArgumentException();
       }
       
       //Estrazione ed analisi della giornata.
       ConferenceDay Day = (ConferenceDay) Congresses.get(day);
       HashMap<Integer,ArrayList<String>> Table = Day.Table;
       
       System.out.println("--------------------------------------------\n");
       System.out.println("LISTA DELLE SESSIONI NELLA "+day+"a GIORNATA");
       
       //Stampa di tutte le sessioni.
       for(int i=0; i<12; i++)
       {
           ArrayList<String> Session;
           Session = Table.get(i);
           
            //Stampa della lista degli speaker della sessione corrente.
           System.out.println("SESSIONE "+(i+1)+": "+Session.toString());
       }
       
       System.out.println("--------------------------------------------\n");
    }
}
