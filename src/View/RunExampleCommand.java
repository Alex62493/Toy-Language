package View;

import Controller.Controller;
import Model.PrgState;

import java.util.Scanner;

public class RunExampleCommand extends Command {
    private final Controller ctr;

    public RunExampleCommand(String key, String desc, Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }

    @Override
    public void execute(){
        try{
            ctr.allStep();
        }
        catch (Exception e)  {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void oneStep() {
        try{
            ctr.oneStep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void startOneStep() throws Exception {
        ctr.readyOneStep();
    }

    public void endOneStep() throws Exception {
        ctr.endOneStep();
    }

    public java.util.List<PrgState> getOG() {return ctr.getOG();}

    public java.util.List<PrgState> getPrgStates() {
        return ctr.getPrgStates();
    }
}
