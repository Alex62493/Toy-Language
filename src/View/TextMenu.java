package View;

import java.util.HashMap;
import java.util.Scanner;

public class TextMenu {
    private final HashMap<String, Command> commands;

    public TextMenu() {commands = new HashMap<>();}

    public void addCommand(Command c) {commands.put(c.getKey(),c);}

    private void printMenu() {
        for(Command com : commands.values()) {
            String line=String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show() throws Exception {
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null) {
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}
