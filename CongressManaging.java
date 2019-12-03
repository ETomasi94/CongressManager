package gestionecongressoserver;

/*ASSIGNMENT NUMERO 10 LABORATORIO DI RETI A.A 2019-2020
Nome Assignment : GestioneCongresso (Lato Server)

Autore : Enrico Tomasi
Numero Matricola : 503527

OVERVIEW : Implementazione di un programma Server che gestisce le registrazioni
ad un congresso considerando gli speaker, cinque in totale, e le sessioni, dodici
in totale, presenti in una giornata.
*/

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/*
    @INTERFACE CongressManaging
    @OVERVIEW Interfaccia che dichiara i metodi accessibili da remoto per la
    gestione dei congressi
*/
public interface CongressManaging extends Remote
{
    void AddSpeaker(int session, int day, String Speaker) throws RemoteException;
    
    HashMap<Integer,ArrayList<String>> GetProgram(int day) throws RemoteException;
}
