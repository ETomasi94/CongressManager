/*ASSIGNMENT NUMERO 10 LABORATORIO DI RETI A.A 2019-2020
Nome Assignment : GestioneCongresso (Lato Server)

Autore : Enrico Tomasi
Numero Matricola : 503527

OVERVIEW : Implementazione di un programma Server che gestisce le registrazioni
ad un congresso considerando gli speaker, cinque in totale, e le sessioni, dodici
in totale, presenti in una giornata.
*/ 
package gestionecongressoserver;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
/*
    @CLASS MainClass
    @OVERVIEW Classe che implementa il ciclo di vita di un CongressServer.
*/
public class MainClass
{    
    /*
        @METHOD Main
        @OVERVIEW  Metodo che implementa il ciclo di vita del CongressServer.
    */
    public static void main(String[] args)
    {
        /*
            MainClass PORT
        
            PORT Porta su cui avviene la connessione
        
        */
        int PORT = Integer.parseInt(args[0]);
        
        try
        {
            CongressServer SRV = new CongressServer();
            
            //Esportazione oggetto remoto.
            CongressManaging STUB = (CongressManaging) UnicastRemoteObject.exportObject(SRV, 0);
            
            //Lancio del registro sulla porta.
            LocateRegistry.createRegistry(PORT);
            
            //Restituzione riferimento al registro RMI sulla porta.
            Registry Reg = LocateRegistry.getRegistry(PORT);
            
            //Registrazione dello stub
            Reg.rebind("CONGRESS_MANAGER",STUB);
            
            System.out.println("SERVER IN ESECUZIONE");     
        }
        catch(RemoteException e)
        {
            System.out.println("ERRORE NELLA CREAZIONE, NELL'ACCESSO ALL'OGGETTO REMOTO O NELL'ESECUZIONE DI UN SUO METODO");
        }
    }
}
