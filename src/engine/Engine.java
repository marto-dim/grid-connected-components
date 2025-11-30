package engine;

import commands.Command;
import commands.CountComponentsCommand;
import utils.Messages;

import java.util.Scanner;

public final class Engine {

    private static final Scanner scanner = new Scanner(System.in);

    public void start(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("Usage:\n  java Main <gridfile>");
            return;
        }

        String file = args[0];
        ComponentCounter counter = new ComponentCounter();
        GridSizeInfo info = counter.inspect(file);

        boolean forceDSU = false;

        if (info.dsuAllowed()) {
            forceDSU = (askUserChoice() == 1);
        } else {
            System.out.println("Grid too large for WQU_PC — using SW_UF.");
        }

        System.out.println();

        Command cmd = new CountComponentsCommand(file, forceDSU);
        cmd.execute();
    }

    private int askUserChoice() {
        while (true) {
            System.out.print(Messages.PROMPT_CHOOSE_ALGO);
            String in = scanner.nextLine().trim();
            if (in.equals("1") || in.equals("2"))
                return Integer.parseInt(in);
            System.out.println(Messages.INVALID_CHOICE);
        }
    }
}
