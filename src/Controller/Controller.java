package Controller;
import Exceptions.StatementExecutionException;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.IRepo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    IRepo repo;
    ExecutorService executor;

    public Controller(IRepo r) {
        repo = r;
    }

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    public Map<Integer, IValue> garbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer,IValue> heap){
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream().filter(v-> v instanceof RefValue).map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}).collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<IValue> heapValues)
    {
        return heapValues.stream().filter(v-> v instanceof RefValue).map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}).collect(Collectors.toList());
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    void showAll(PrgState state) {
        System.out.println("Program state: " + state.getId() + "\n");
        System.out.println("ExeStack: " + state.getStack().toString());
        System.out.println("SymTable: " + state.getSymTable().toString());
        System.out.println("Out: " + state.getOut().toString());
        System.out.println("FileTable: " + state.getFileTable().toString());
        System.out.println("Heap: " + state.getHeap().toString());
        System.out.println();
    }

    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        List<Integer> addr = new ArrayList<>();
        prgList.forEach(prg -> {
            try {
                addr.addAll(getAddrFromSymTable(prg.getSymTable().getContent().values()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        garbageCollector(addr, getAddrFromHeap(prgList.get(0).getHeap().getContent().values()), prgList.get(0).getHeap().getContent());
        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();})).collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(p -> p!=null).collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        repo.setPrgList(prgList);
    }

    public void readyOneStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
    }

    public void oneStep() throws Exception {
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        if (prgList.size() > 0) {
            prgList.forEach(prg -> {
                try {
                    repo.logPrgStateExec(prg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            oneStepForAllPrg(prgList);
        }
    }

    public void endOneStep() throws Exception {
        executor.shutdownNow();
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState>  prgList=removeCompletedPrg(repo.getPrgList());
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        while(prgList.size() > 0) {
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public java.util.List<PrgState> getPrgStates()
    {
        return removeCompletedPrg(repo.getPrgList());
    }

    public java.util.List<PrgState> getOG()
    {
        return repo.getPrgList();
    }
}
