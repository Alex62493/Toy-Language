package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.*;


import java.io.BufferedReader;
import java.util.Objects;

public class ReadFileStmt implements IStmt {

    private final Exp fileExpression;
    private final String varName;

    public ReadFileStmt(Exp id, String name) {
        fileExpression=id;
        varName=name;
    }


    public PrgState execute(PrgState state) throws Exception {
        if (!Objects.equals(fileExpression.eval(state.getSymTable(), state.getHeap()).getType().toString(), "string"))
            throw new Exception("File name must be a string!");
        String filename = fileExpression.eval(state.getSymTable(), state.getHeap()).toString();
        BufferedReader reader = state.getFileTable().lookup(filename);
        int fileValue;
        try {
            fileValue = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            fileValue = 0;
        }

        if (!Objects.equals(state.getSymTable().lookup(varName).getType().toString(), "int"))
            throw new Exception("Variable must be an integer!");

        IntValue val = new IntValue(fileValue);

        state.getSymTable().update(varName, val);

        return null;
    }

    public String toString(){
        return "readFile(" + fileExpression.toString()+ "," + varName + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typevar = typeEnv.lookup(varName);
        IType typexp = fileExpression.typecheck(typeEnv);
        if (typevar instanceof IntType && typexp instanceof StringType)
            return typeEnv;
        else
            throw new Exception("READ FILE stmt: right hand side and left hand side have different types ");
    }
}
