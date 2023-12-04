package server;

import types.IServerMethods;
import types.ClientActions;
import types.PrimeResults;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class ServerImplementation implements IServerMethods {
    int id = -1;
    int [] users = new int[2];
    public ServerImplementation() throws RemoteException {
        super();
        Arrays.fill(users, -1);
    }

    @Override
    public int register() throws RemoteException {
        System.out.println("cliente conectado");
        id++;
       return id;
    }

    @Override
    public void setLenght(int id, int n) throws RemoteException {
       users[id] = n;
    }

    @Override
    public PrimeResults calculate(int id, ClientActions userAction) throws RemoteException {
        System.out.println(userAction);
        for(var user : users){
           if(-1 == user){
               return null;
           }
        }
        var ownLength = users[id];
        var otherLength = users[id == 0 ? 1 : 0];

        ArrayList<Integer> ownRes = null;
        ArrayList<Integer> otherRes = null;
        long startTime = System.nanoTime();
            switch (userAction) {
                case SEQUENTIAL:
                    ownRes = PrimeNumbers.main(ownLength);
                    otherRes = PrimeNumbers.main(otherLength);
                    break;
                case FORK_JOIN:
                    ownRes = PrimeNumbersWithForkJoin.main(ownLength);
                    otherRes = PrimeNumbersWithForkJoin.main(otherLength);
                    break;
                case EXECUTOR_SERVICE:
                    ownRes = PrimeNumbersWithExecutorService.main(ownLength);
                    otherRes = PrimeNumbersWithExecutorService.main(otherLength);
                    break;
            }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        return new PrimeResults(ownRes,otherRes,elapsedTime);
    }
}
