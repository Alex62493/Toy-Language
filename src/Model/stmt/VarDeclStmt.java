package Model.stmt;

import Exceptions.ADTException;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;
import Model.types.RefType;
import Model.value.*;

import java.util.Objects;

public class VarDeclStmt implements IStmt{
    String id;
    IType a;

    public VarDeclStmt(String i, IType val) {
        id = i;
        a = val;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (state.getSymTable().isDefined(id))
            throw new ADTException("Variable already exists!");

        if (Objects.equals(a.toString(), "int"))
        {
            IntValue b = (IntValue) a.defaultValue();
            state.getSymTable().add(id, b);
        }
        else if (Objects.equals(a.toString(), "bool")){
            BoolValue b = (BoolValue) a.defaultValue();
            state.getSymTable().add(id, b);
        }
        else if (Objects.equals(a.toString(), "string")){
            StringValue b = (StringValue) a.defaultValue();
            state.getSymTable().add(id, b);
        }
        else
        {
            RefType c = (RefType) a;
            RefValue b = new RefValue(0, c.getInner());
            state.getSymTable().add(id, b);
        }
        return null;
    }

    @Override
    public String toString() {
        return a.toString() + " " + id;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        typeEnv.add(id, a);
        return typeEnv;
    }
}
