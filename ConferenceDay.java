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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/*
    @CLASS ConferenceDay
    @OVERVIEW Classe di supporto all'oggetto in remoto che definisce gli elementi
    che costituiscono una giornata di congressi.
*/
public class ConferenceDay
{
    /*Creazione di una HashMap che associa ad ogni intero rappresentante una sessione
      un array di stringhe rappresentante gli speaker di quella sessione.
    */
    HashMap<Integer,ArrayList<String>> Table;
    
    public ConferenceDay()
    {
        Table = new HashMap<>();
        
        for(int i=0; i<12;i++)
        {
            //Inizializzazione della lista degli speakers.
            List<String> NoSpeakers = Arrays.asList("-","-","-","-","-");
            
            /*E' stata scelta la struttura ArrayList per la facilità di accesso
              e sostituzione degli elementi*/
            ArrayList<String> Session = new ArrayList(5);
            
            //Aggiunta della lista inizializzata degli speakers.
            Session.addAll(NoSpeakers);
            
            //Aggiunta della sessione all'indice corrente della tabella.
            Table.put(i, Session);          
        }
    }
    
    /*
        @METHOD RegisterSpeaker
        @OVERVIEW Metodo che, dati un intero rappresentante una determinata sessione
        ed una stringa rappresentante il nome dello speaker, registra, quando possibile
        lo speaker alla sessione della giornata corrente.
    
        @PAR session intero rappresentante la sessione a cui iscrivere lo speaker.
        @PAR Speaker stringa rappresentante il nome dello speaker.
    
        @THROWS IllegalArgumentException nel caso un parametro in ingresso non sia valido.
        @THROWS IOException nel caso non sia possibile aggiungere lo speaker.
    */
    public void RegisterSpeaker(int session,String Speaker) throws IllegalArgumentException, IOException
    {
        //Gestione dell'errore.
        if(session<1 || session>12 || Speaker == null)
        {
            System.out.println("ERRORE: Sessione inesistente");
            throw new IllegalArgumentException();          
        }
        
        //Session-1 è dovuto al fatto che l'utente seleziona le sezioni digitando da 1 a 12.
        ArrayList<String> Session = Table.get(session-1);
        
        /*Se la lista degli speaker non contiente almeno una cella con un trattino, allora
        non ci sono slot liberi e non è possibile registrare lo speaker*/
        if(!Session.contains("-"))
        {
            System.out.println("Impossibile collocare lo speaker "+Speaker+", la lista è al completo");
            throw new IOException();
        }
        else
        {
            //Inserimento dello speaker nel primo slot libero trovato.
            int Position = Session.indexOf("-");
            Session.set(Position, Speaker);
        }
        
        Table.put(session-1, Session);      
    }
    
    /*
        @METHOD GetDayTable
        @OVERVIEW Metodo che restituisce il programma della giornata corrente.
    
        @THROWS RemoteException nel caso vengano riscontrati errori nell'esecuzione
        di questo metodo da remoto.
    */
    public HashMap<Integer,ArrayList<String>> GetDayTable() throws RemoteException
    {
        return this.Table;
    }
}
