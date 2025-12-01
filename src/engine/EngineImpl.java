package engine;

import commands.Command;
import commands.CommandContext;
import commands.CountComponentsCommand;
import engine.dto.GridSizeInfo;
import engine.interfaces.ComponentCounter;
import engine.interfaces.Engine;
import engine.wqu_command_loop.CommandLoop;
import utils.Messages;

import java.util.Scanner;

public final class EngineImpl implements Engine {

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void start(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("Usage:\n  java Main <gridfile>");
            return;
        }

        String file = args[0];
        ComponentCounter counter = new ComponentCounterImpl();
        GridSizeInfo info = counter.inspect(file);

        boolean useWQU_PC = false;

        if ( info.wqu_pcPossible() ) {
            useWQU_PC = ( askUserChoice() == 1 );
        } else {
            System.out.println("Grid too large for WQU_PC — using SW_UF.");
        }

        System.out.println();

        CommandContext ctx = new CommandContext();
        Command count = new CountComponentsCommand(file, useWQU_PC, ctx);
        count.execute();

        ComponentResult result = ctx.result();

        System.out.println();
        System.out.println("Components = " + result.components());
        System.out.println();

        // If WQU_PC: enter command loop
        if ( useWQU_PC ) {
            new CommandLoop(result.queryApi()).runLoop();
        }
    }

    private int askUserChoice() {
        while ( true ) {
            System.out.print(Messages.PROMPT_CHOOSE_ALGO);
            String in = scanner.nextLine().trim();
            if ( in.equals("1") || in.equals("2") )
                return Integer.parseInt(in);
            System.out.println(Messages.INVALID_CHOICE);
        }
    }
}
