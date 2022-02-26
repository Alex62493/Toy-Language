package View;

import Controller.Controller;
import Model.CyclicBarrier.AwaitStmt;
import Model.CyclicBarrier.CyclicBarrier;
import Model.CyclicBarrier.NewBarrierStmt;
import Model.CyclicBarrier.Pair;
import Model.PrgState;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.*;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import java.io.IOException;

public class GraphicalMenu extends Application {
    private HashMap<String, Command> commands;
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static GraphicalMenu gm = null;
    Button prgStart;
    Button oneStep;
    TextField tf;
    TableView<Map.Entry<String,String>> ht;
    ListView<String> out;
    ListView<String> ft;
    ListView<String> threads;
    TableView<Map.Entry<String,String>> sym;
    ListView<String> exe;
    ListView<String> listView;
    java.util.List<PrgState> states;
    java.util.List<PrgState> endStates;
    RunExampleCommand rec;

    public GraphicalMenu() throws Exception {
        commands = new HashMap<>();
        // ex 1:  int v; v = 2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(
                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
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

        IStack<IStmt> exeStackT1 = new ExeStack<>();
        IStmt stmtT1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(new VarDeclStmt("y", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))), new CompStmt(new RepeatStmt(new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))), new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))), new RelExp("==", new VarExp("v"), new ValueExp(new IntValue(3)))), new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))), new CompStmt(new NopStmt(), new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(3))), new CompStmt(new NopStmt(), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))))))))));
        exeStackT1.push(stmtT1);
        stmtT1.typecheck(new Dict<String, IType>());

        IDict<String, IValue> symTableT1 = new Dict<String, IValue>();
        IDict<String, BufferedReader> fileTableT1 = new FileTable<String, BufferedReader>();
        IList<IValue> outT1 = new List<IValue>();
        IDict<Integer, IValue> heapT1 = new Heap<>();

        PrgState myPrgStateT1 = new PrgState(exeStackT1, symTableT1, outT1, stmtT1, fileTableT1, heapT1, new CyclicBarrier<Integer, Pair<Integer, ArrayList<Integer>>>());

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

        Repo repoT1 = new Repo("outputT1.txt");
        Controller controllerT1 = new Controller(repoT1);
        controllerT1.addProgram(myPrgStateT1);

        this.addCommand(new RunExampleCommand("1", "int v; v = 2; Print(v)", controller1));
        this.addCommand(new RunExampleCommand("2", "a=2+3*5;b=a+1;Print(b)", controller2));
        this.addCommand(new RunExampleCommand("3", "bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)", controller3));
        this.addCommand(new RunExampleCommand("a3-2", "string varf; varf=\"test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc) closeRFile(varf)", controller32));
        this.addCommand(new RunExampleCommand("a4-1", "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)", controller41));
        this.addCommand(new RunExampleCommand("a4-2", "Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)", controller42));
        this.addCommand(new RunExampleCommand("a4-3", "Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5)", controller43));
        this.addCommand(new RunExampleCommand("a4-4", "Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))", controller44));
        this.addCommand(new RunExampleCommand("a4-5", "int v; v=4; (while (v>0) print(v); v=v-1); print(v)", controller45));
        this.addCommand(new RunExampleCommand("a5", " int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a))", controller5));
        this.addCommand(new RunExampleCommand("T1", "int v;int x;int y;v=0;repeat fork(Print(v);v=v - 1);v=v + 1 until v == 3;x=1;Nop;y=3;Nop;Print(v * 10)", controllerT1));
    }

    public void addCommand(Command c) {commands.put(c.getKey(),c);}

    @Override
    public void start(Stage stage) throws IOException {
        listView = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList(commands.keySet());
        listView.setPrefWidth(200);
        listView.setPrefHeight(100);
        listView.setItems(items);
        prgStart = new Button();
        prgStart.setText("Start the program");

        oneStep = new Button("One Step");
        oneStep.setOnAction(ev -> {
            if (endStates.size() == 0)
            {
                oneStep.setDisable(true);
                try {
                    rec.endOneStep();
                } catch (Exception e) {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
            else
            {
                try {
                    rec.oneStep();
                } catch (Exception e) {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                states = rec.getOG();
                endStates = rec.getPrgStates();
                updateHt(ht, states.get(0));
                updateExe(exe, states.get(0));
                updateFt(ft, states.get(0));
                updateOut(out, states.get(0));
                updateSym(sym, states.get(0));
                updateThreads(threads, states);
                updateTf(tf, states);
                threads.getSelectionModel().select(0);
            }
        });

        tf = new TextField();
        tf.setEditable(false);

        TableColumn<HashMap.Entry<String, String>, String> htAddress = new TableColumn<>("Address");
        TableColumn<HashMap.Entry<String, String>, String> htValue = new TableColumn<>("Value");
        htAddress.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });
        htValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });
        ObservableList<Map.Entry<String, String>> htItems = FXCollections.observableArrayList((new HashMap<String, String>()).entrySet());
        ht = new TableView<>(htItems);
        ht.getColumns().setAll(htAddress, htValue);
        ht.setEditable(false);

        out = new ListView<>();
        out.setEditable(false);

        ft = new ListView<String>();
        ft.setEditable(false);

        TableColumn<HashMap.Entry<String, String>, String> symName = new TableColumn<>("Name");
        TableColumn<HashMap.Entry<String, String>, String> symValue = new TableColumn<>("Value");
        symName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });
        symValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap.Entry<String, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });
        ObservableList<Map.Entry<String, String>> symItems = FXCollections.observableArrayList((new HashMap<String, String>()).entrySet());
        sym = new TableView<>(symItems);
        sym.getColumns().setAll(symName, symValue);
        sym.setEditable(false);

        exe = new ListView<String>();
        exe.setEditable(false);

        threads = new ListView<String>();
        threads.setEditable(false);

        threads.setOnMouseClicked(mouseEvent -> {
            int index = threads.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                updateHt(ht, states.get(index));
                updateExe(exe, states.get(index));
                updateFt(ft, states.get(index));
                updateOut(out, states.get(index));
                updateSym(sym, states.get(index));
                updateThreads(threads, states);
                updateTf(tf, states);
            }
        });

        prgStart.setOnAction( event -> {
            Command com = commands.get(listView.getSelectionModel().getSelectedItem());
            if (com!=null) {
                rec = (RunExampleCommand) com;
                states = rec.getOG();
                endStates = rec.getPrgStates();
                try {
                    rec.startOneStep();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                updateHt(ht, states.get(0));
                updateExe(exe, states.get(0));
                updateFt(ft, states.get(0));
                updateOut(out, states.get(0));
                updateSym(sym, states.get(0));
                updateThreads(threads, states);
                updateTf(tf, states);
                threads.getSelectionModel().select(0);

                VBox bpt = new VBox();
                bpt.setSpacing(30);
                bpt.getChildren().addAll(tf, oneStep);

                HBox layout2 = new HBox();
                layout2.setSpacing(10);
                layout2.setPadding(new Insets(10, 0, 0, 10));
                layout2.getChildren().add(bpt);
                layout2.getChildren().add(ht);
                layout2.getChildren().add(out);
                layout2.getChildren().add(ft);
                layout2.getChildren().add(threads);
                layout2.getChildren().add(sym);
                layout2.getChildren().add(exe);
                Scene scene2 = new Scene(layout2, 1700, 400);
                stage.setScene(scene2);
            }
        });

        HBox layout = new HBox();
        layout.setSpacing(10);
        Group root = new Group(new Box());
        layout.getChildren().add(listView);
        layout.getChildren().add(prgStart);


        Scene scene = new Scene(layout, 500, 500);
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }

    public void updateHt(TableView<Map.Entry<String,String>> ht1, PrgState prgState) {
        HashMap<String, String> hm = prgState.getHeap().forGUI();
        ObservableList<Map.Entry<String, String>> htItems = FXCollections.observableArrayList(hm.entrySet());
        ht1.setItems(htItems);
    }

    public void updateSym(TableView<Map.Entry<String,String>> sym1, PrgState prgState) {
        HashMap<String, String> hm = prgState.getSymTable().forGUI();
        ObservableList<Map.Entry<String, String>> symItems = FXCollections.observableArrayList(hm.entrySet());
        sym1.setItems(symItems);
    }

    public void updateOut(ListView<String> out1, PrgState prgState) {
        Vector<String> v = prgState.getOut().forFile();
        ObservableList<String> outItems = FXCollections.observableArrayList(v);
        out1.setItems(outItems);
    }

    public void updateFt(ListView<String> ft1, PrgState prgState) {
        Vector<String> v = prgState.getFileTable().forFile();
        ObservableList<String> ftItems = FXCollections.observableArrayList(v);
        ft1.setItems(ftItems);
    }

    public void updateThreads(ListView<String> threads1, java.util.List<PrgState> states) {
        Vector<String> v = new Vector<>();
        for (PrgState i : states) {
            v.add(String.valueOf(i.getId()));
        }
        ObservableList<String> threadItems = FXCollections.observableArrayList(v);
        threads1.setItems(threadItems);
    }

    public void updateExe(ListView<String> exe1, PrgState prgState) {
        Vector<String> v = prgState.getStack().forFile();
        ObservableList<String> exeItems = FXCollections.observableArrayList(v);
        exe1.setItems(exeItems);
    }

    public void updateTf(TextField tf1, java.util.List<PrgState> states) {
        tf1.setText(String.valueOf(states.size()));
    }

    public static GraphicalMenu waitForStart() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return gm;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
