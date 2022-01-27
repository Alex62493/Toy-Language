package Repo;
import Model.PrgState;
import Model.adt.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Repo implements IRepo {

    java.util.List<PrgState> myPrgStates;
    String logFilePath;

    public Repo(String path) throws Exception{
        myPrgStates = new ArrayList<>();
        logFilePath = path;
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.flush();
        logFile.close();
    }


    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public java.util.List<PrgState> getPrgList() {
        return myPrgStates;
    }

    @Override
    public void setPrgList(java.util.List<PrgState> states) {
        myPrgStates = states;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws Exception {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        Vector<String> exe = state.getStack().forFile();
        Vector<String> sym = state.getSymTable().forFile();
        Vector<String> out = state.getOut().forFile();
        Vector<String> ft = state.getFileTable().forFile();
        Vector<String> h = state.getHeap().forFile();

        logFile.println("Program state: " + state.getId());
        logFile.println("ExeStack:");
        for (String i : exe)
            logFile.println(i);
        logFile.println();

        logFile.println("SymTable:");
        for (String i : sym)
            logFile.println(i);
        logFile.println();

        logFile.println("Out:");
        for (String i : out)
            logFile.println(i);
        logFile.println();

        logFile.println("FileTable:");
        for (String i : ft)
            logFile.println(i);
        logFile.println();

        logFile.println("Heap:");
        for (String i : h)
            logFile.println(i);
        logFile.println();

        logFile.println(state.getCyclicBarrier().toString());
        logFile.println();

        logFile.println("--------------------------------------------------");
        logFile.flush();
        logFile.close();
    }
}
