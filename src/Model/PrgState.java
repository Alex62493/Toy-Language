package Model;
import Exceptions.StatementExecutionException;
import Model.CyclicBarrier.ICyclicBarrier;
import Model.CyclicBarrier.Pair;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrgState {

    public PrgState(IStack<IStmt> e, IDict<String, IValue> s, IList<IValue> o, IStmt op, IDict<String, BufferedReader> ft, IDict<Integer, IValue> h, ICyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cb) {
        exeStack = e;
        symTable = s;
        out = o;
        originalProgram = op;
        fileTable = ft;
        heap = h;
        cyclicBarrier = cb;
        synchronized (this) {
            id = unique_id;
            unique_id++;
        }
    }

    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<IValue> out;
    IDict<String, BufferedReader> fileTable;
    IStmt originalProgram;
    IDict<Integer, IValue> heap;
    ICyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> cyclicBarrier;
    int id;

    static int unique_id = 1;

    public IDict<String, IValue> getSymTable() {
        return symTable;
    }
    public PrgState deepCopy() {return new PrgState(exeStack, symTable, out, originalProgram, fileTable, heap, cyclicBarrier);}
    public IStmt pop() {return exeStack.pop();}
    public void pushToStack(IStmt stmt) {exeStack.push(stmt);}
    public void addToOut(IValue value) {out.add(value);}
    public IList<IValue> getOut() {return out;}
    public IDict<String, BufferedReader> getFileTable() {return fileTable;};
    public IStack<IStmt> getStack() {
        return exeStack;
    }
    public IDict<Integer, IValue> getHeap() {return heap;}
    public int getHeapCurrent() {
        try {
            return Collections.max(heap.getContent().keySet()) + 1;
        } catch (Exception e) {
            return 1;
        }
    }
    public Boolean isNotCompleted() {return !exeStack.isEmpty();}

    public PrgState oneStep() throws Exception{
        if (exeStack.isEmpty())
            throw new StatementExecutionException("PrgState stack is empty!");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public int getId () {return id;}

    public ICyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>> getCyclicBarrier() {
        return cyclicBarrier;
    }
}