package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;
import Model.types.StringType;
import Model.value.StringValue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class OpenRFileStmt implements IStmt {

    private final Exp fileExpression;

    public OpenRFileStmt(Exp fe) {
        fileExpression = fe;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (!Objects.equals(fileExpression.eval(state.getSymTable(), state.getHeap()).getType().toString(), "string"))
            throw new Exception("File name must be a string!");

        StringValue expressionValue = (StringValue) this.fileExpression.eval(state.getSymTable(), state.getHeap());
        String filePath = expressionValue.getValue();

        if (state.getFileTable().isDefined(filePath))
            throw new Exception("File path already exists!");

        FileReader in = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(in);
        state.getFileTable().add(filePath, reader);
        return null;

    }

    @Override
    public String toString() {
        return "openRFile(" + fileExpression.toString() + ")";
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








