package View;
import Controller.Controller;
import Model.CyclicBarrier.CyclicBarrier;
import Model.CyclicBarrier.Pair;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.*;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import Model.PrgState;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;


public class Main{
    public static void main(String[] args) throws Exception{
        // ex 1:  int v; v = 2; Print(v)
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                        new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));

        IStack<IStmt> exeStack1 = new ExeStack<IStmt>();
        exeStack1.push(ex1);
        IDict<String, IValue> symTable1 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable1 = new FileTable<String, BufferedReader>();
        IList<IValue> out1 = new List<IValue>();
        IDict<Integer, IValue> heap1 = new Heap<>();

        IStack<IStmt> exeStack2 = new ExeStack<IStmt>();
        exeStack2.push(ex2);
        IDict<String, IValue> symTable2 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable2 = new FileTable<String, BufferedReader>();
        IList<IValue> out2 = new List<IValue>();
        IDict<Integer, IValue> heap2 = new Heap<>();

        IStack<IStmt> exeStack3 = new ExeStack<IStmt>();
        exeStack3.push(ex3);
        IDict<String, IValue> symTable3 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable3 = new FileTable<String, BufferedReader>();
        IList<IValue> out3 = new List<IValue>();
        IDict<Integer, IValue> heap3 = new Heap<>();

        PrgState myPrgState1 = new PrgState(exeStack1, symTable1, out1, ex1, fileTable1, heap1, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());
        PrgState myPrgState2 = new PrgState(exeStack2, symTable2, out2, ex2, fileTable2, heap2, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());
        PrgState myPrgState3 = new PrgState(exeStack3, symTable3, out3, ex3, fileTable3, heap3, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

        IStack<IStmt> exeStack32 = new ExeStack<IStmt>();
        exeStack32.push(new CloseRFileStmt(new VarExp("varf")));
        exeStack32.push(new PrintStmt(new VarExp("varc")));
        exeStack32.push(new ReadFileStmt(new VarExp("varf"), "varc"));
        exeStack32.push(new PrintStmt(new VarExp("varc")));
        exeStack32.push(new ReadFileStmt(new VarExp("varf"), "varc"));
        exeStack32.push(new VarDeclStmt("varc", new IntType()));
        exeStack32.push(new OpenRFileStmt(new VarExp("varf")));
        exeStack32.push(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))));
        exeStack32.push(new VarDeclStmt("varf", new StringType()));

        IDict<String, IValue> symTable32 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable32 = new FileTable<String, BufferedReader>();
        IList<IValue> out32 = new List<IValue>();
        IDict<Integer, IValue> heap32 = new Heap<>();

        PrgState myPrgState32 = new PrgState(exeStack32, symTable32, out32, new NopStmt(), fileTable32, heap32, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

        IStack<IStmt> exeStack41 = new ExeStack<IStmt>();
        exeStack41.push(new PrintStmt(new VarExp("a")));
        exeStack41.push(new PrintStmt(new VarExp("v")));
        exeStack41.push(new HeapAllocStmt("a", new VarExp("v")));
        exeStack41.push(new VarDeclStmt("a", new RefType(new RefType(new IntType()))));
        exeStack41.push(new HeapAllocStmt("v", new ValueExp(new IntValue(20))));
        exeStack41.push(new VarDeclStmt("v", new RefType(new IntType())));

        IDict<String, IValue> symTable41 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable41 = new FileTable<String, BufferedReader>();
        IList<IValue> out41 = new List<IValue>();
        IDict<Integer, IValue> heap41 = new Heap<>();

        PrgState myPrgState41 = new PrgState(exeStack41, symTable41, out41, new NopStmt(), fileTable41, heap41, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

        IStack<IStmt> exeStack42 = new ExeStack<>();
        exeStack42.push(new PrintStmt(new ArithExp('+', new HeapReadExp(new HeapReadExp(new VarExp("a"))), new ValueExp(new IntValue(5)))));
        exeStack42.push(new PrintStmt(new HeapReadExp(new VarExp("v"))));
        exeStack42.push(new HeapAllocStmt("a", new VarExp("v")));
        exeStack42.push(new VarDeclStmt("a", new RefType(new RefType(new IntType()))));
        exeStack42.push(new HeapAllocStmt("v", new ValueExp(new IntValue(20))));
        exeStack42.push(new VarDeclStmt("v", new RefType(new IntType())));

        IDict<String, IValue> symTable42 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable42 = new FileTable<String, BufferedReader>();
        IList<IValue> out42 = new List<IValue>();
        IDict<Integer, IValue> heap42 = new Heap<>();

        PrgState myPrgState42 = new PrgState(exeStack42, symTable42, out42, new NopStmt(), fileTable42, heap42, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

        IStack<IStmt> exeStack43 = new ExeStack<>();
        exeStack43.push(new PrintStmt(new ArithExp('+', new HeapReadExp(new VarExp("v")), new ValueExp(new IntValue(5)))));
        exeStack43.push(new HeapWriteStmt("v", new ValueExp(new IntValue(30))));
        exeStack43.push(new PrintStmt(new HeapReadExp(new VarExp("v"))));
        exeStack43.push(new HeapAllocStmt("v", new ValueExp(new IntValue(20))));
        exeStack43.push(new VarDeclStmt("v", new RefType(new IntType())));

        IDict<String, IValue> symTable43 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable43 = new FileTable<String, BufferedReader>();
        IList<IValue> out43 = new List<IValue>();
        IDict<Integer, IValue> heap43 = new Heap<>();

        PrgState myPrgState43 = new PrgState(exeStack43, symTable43, out43, new NopStmt(), fileTable43, heap43, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

        IStack<IStmt> exeStack44 = new ExeStack<>();
        exeStack44.push(new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a")))));
        exeStack44.push(new HeapAllocStmt("v", new ValueExp(new IntValue(30))));
        exeStack44.push(new HeapAllocStmt("a", new VarExp("v")));
        exeStack44.push(new VarDeclStmt("a", new RefType(new RefType(new IntType()))));
        exeStack44.push(new HeapAllocStmt("v", new ValueExp(new IntValue(20))));
        exeStack44.push(new VarDeclStmt("v", new RefType(new IntType())));

        IDict<String, IValue> symTable44 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable44 = new FileTable<String, BufferedReader>();
        IList<IValue> out44 = new List<IValue>();
        IDict<Integer, IValue> heap44 = new Heap<>();

        PrgState myPrgState44 = new PrgState(exeStack44, symTable44, out44, new NopStmt(), fileTable44, heap44, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

        IStack<IStmt> exeStack45 = new ExeStack<>();
        exeStack45.push(new PrintStmt(new VarExp("v")));
        exeStack45.push(new WhileStmt(new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))));
        exeStack45.push(new AssignStmt("v", new ValueExp(new IntValue(4))));
        exeStack45.push(new VarDeclStmt("v", new IntType()));

        IDict<String, IValue> symTable45 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable45 = new FileTable<String, BufferedReader>();
        IList<IValue> out45 = new List<IValue>();
        IDict<Integer, IValue> heap45 = new Heap<>();

        PrgState myPrgState45 = new PrgState(exeStack45, symTable45, out45, new NopStmt(), fileTable45, heap45, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

        IStack<IStmt> exeStack5 = new ExeStack<>();
        IStmt stmt5 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))), new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a"))))))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a")))))))));
        exeStack5.push(stmt5);
        stmt5.typecheck(new Dict<String, IType>());

        IDict<String, IValue> symTable5 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTable5 = new FileTable<String, BufferedReader>();
        IList<IValue> out5 = new List<IValue>();
        IDict<Integer, IValue> heap5 = new Heap<>();

        PrgState myPrgState5 = new PrgState(exeStack5, symTable5, out5, new NopStmt(), fileTable5, heap5, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

        Repo repo1 = new Repo("log1.txt");
        Controller controller1 = new Controller(repo1);
        controller1.addProgram(myPrgState1);

        Repo repo2 = new Repo("log2.txt");
        Controller controller2 = new Controller(repo2);
        controller2.addProgram(myPrgState2);

        Repo repo3 = new Repo("log3.txt");
        Controller controller3 = new Controller(repo3);
        controller3.addProgram(myPrgState3);

        Repo repo32 = new Repo("output.txt");
        Controller controller32 = new Controller(repo32);
        controller32.addProgram(myPrgState32);

        Repo repo41 = new Repo("output4-1.txt");
        Controller controller41 = new Controller(repo41);
        controller41.addProgram(myPrgState41);

        Repo repo42 = new Repo("output4-2.txt");
        Controller controller42 = new Controller(repo42);
        controller42.addProgram(myPrgState42);

        Repo repo43 = new Repo("output4-3.txt");
        Controller controller43 = new Controller(repo43);
        controller43.addProgram(myPrgState43);

        Repo repo44 = new Repo("output4-4.txt");
        Controller controller44 = new Controller(repo44);
        controller44.addProgram(myPrgState44);

        Repo repo45 = new Repo("output4-5.txt");
        Controller controller45 = new Controller(repo45);
        controller45.addProgram(myPrgState45);

        Repo repo5 = new Repo("output5.txt");
        Controller controller5 = new Controller(repo5);
        controller5.addProgram(myPrgState5);

        /*
        TextMenu tm = new TextMenu();
        tm.addCommand(new ExitCommand("0", "exit"));
        tm.addCommand(new RunExampleCommand("1", "int v; v = 2; Print(v)", controller1));
        tm.addCommand(new RunExampleCommand("2", "a=2+3*5;b=a+1;Print(b)", controller2));
        tm.addCommand(new RunExampleCommand("3", "bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)", controller3));
        tm.addCommand(new RunExampleCommand("a3-2", "string varf; varf=\"test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc) closeRFile(varf)", controller32));
        tm.addCommand(new RunExampleCommand("a4-1", "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)", controller41));
        tm.addCommand(new RunExampleCommand("a4-2", "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)", controller42));
        tm.addCommand(new RunExampleCommand("a4-3", "Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5)", controller43));
        tm.addCommand(new RunExampleCommand("a4-4", "Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))", controller44));
        tm.addCommand(new RunExampleCommand("a4-5", "int v; v=4; (while (v>0) print(v); v=v-1); print(v)", controller45));
        tm.addCommand(new RunExampleCommand("a5", " int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a))", controller5));
        tm.show();
        */
        /*
        GraphicalMenu gm = new GraphicalMenu();
        gm.addCommand(new ExitCommand("0", "exit"));
        gm.addCommand(new RunExampleCommand("1", "int v; v = 2; Print(v)", controller1));
        gm.addCommand(new RunExampleCommand("2", "a=2+3*5;b=a+1;Print(b)", controller2));
        gm.addCommand(new RunExampleCommand("3", "bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)", controller3));
        gm.addCommand(new RunExampleCommand("a3-2", "string varf; varf=\"test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc) closeRFile(varf)", controller32));
        gm.addCommand(new RunExampleCommand("a4-1", "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)", controller41));
        gm.addCommand(new RunExampleCommand("a4-2", "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)", controller42));
        gm.addCommand(new RunExampleCommand("a4-3", "Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5)", controller43));
        gm.addCommand(new RunExampleCommand("a4-4", "Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))", controller44));
        gm.addCommand(new RunExampleCommand("a4-5", "int v; v=4; (while (v>0) print(v); v=v-1); print(v)", controller45));
        gm.addCommand(new RunExampleCommand("a5", " int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a))", controller5));
        */
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GraphicalMenu.class);
            }
        }.start();
        GraphicalMenu gm = GraphicalMenu.waitForStart();
        /*
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GraphicalMenu.class);
            }
        }.start();
        GraphicalMenu gm = GraphicalMenu.waitForStart();
         */
    }
}
