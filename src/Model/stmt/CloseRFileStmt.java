package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.util.Objects;

public class CloseRFileStmt implements IStmt {

    Exp fileExpression;

    public CloseRFileStmt(Exp id) {
        fileExpression =id;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (!Objects.equals(fileExpression.eval(state.getSymTable(), state.getHeap()).getType().toString(), "string"))
            throw new Exception("File name must be a string!");

        StringValue expressionValue = (StringValue) this.fileExpression.eval(state.getSymTable(), state.getHeap());
        String filePath = expressionValue.getValue();
        if (!state.getFileTable().isDefined(filePath))
            throw new Exception("File path doesn't exist!");
        BufferedReader reader = state.getFileTable().lookup(filePath);
        reader.close();

        state.getFileTable().remove(filePath);
        return null;

    }

    public String toString(){
        return "closeRFile(" + fileExpression.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typexp = fileExpression.typecheck(typeEnv);
        if (typexp instanceof StringType)
            return typeEnv;
        else
            throw new Exception("READ FILE stmt: right hand side and left hand side have different types ");
    }
}

