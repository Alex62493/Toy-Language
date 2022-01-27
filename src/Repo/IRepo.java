package Repo;
import Model.PrgState;
import Model.adt.List;


public interface IRepo {
    void addPrg(PrgState newPrg);
    void logPrgStateExec(PrgState state) throws Exception;
    java.util.List<PrgState> getPrgList();
    void setPrgList(java.util.List<PrgState> states);
    }
