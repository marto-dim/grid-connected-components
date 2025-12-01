package engine.wqu_command_loop;

import algos.ConnectedComponentsQuery;
import models.Point2D;

import java.util.Scanner;

public final class CommandLoop {

    private static final Scanner scanner = new Scanner(System.in);

    // ---------- COMMAND KEYWORDS ----------
    private static final String CMD_FIND      = "find";
    private static final String CMD_CONNECTED = "connected";
    private static final String CMD_EXIT      = "exit";

    // ---------- MESSAGES ----------
    private static final String MSG_HELP = """
            WQU_PC interactive mode:
              find r c
              connected r1 c1 r2 c2
              exit

            """;

    private static final String MSG_UNKNOWN = "Unknown command. Type one of: find, connected, exit.";
    private static final String MSG_USAGE_FIND = "Usage: find <row> <col>";
    private static final String MSG_USAGE_CONNECTED = "Usage: connected <r1> <c1> <r2> <c2>";

    // ---------- STATE ----------
    private final ConnectedComponentsQuery wqu_pc;

    public CommandLoop(ConnectedComponentsQuery wqu_pc) {
        this.wqu_pc = wqu_pc;
    }

    // ---------- MAIN LOOP ----------
    public void runLoop() {
        System.out.print(MSG_HELP);

        while ( true ) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if ( input.isEmpty() ) continue;

            String[] parts = input.split("\\s+");
            String cmd = parts[0].toLowerCase();

            switch ( cmd ) {

                case CMD_EXIT      -> { System.out.println("Exiting interactive mode."); return; }
                case CMD_FIND      -> { handleFind(parts); }
                case CMD_CONNECTED -> { handleConnected(parts); }
                default            -> System.out.println(MSG_UNKNOWN);

            }
        }
    }

    // ---------- HANDLERS ----------
    private void handleFind(String[] p) {
        try {
            if (p.length != 3) {
                System.out.println(MSG_USAGE_FIND);
                return;
            }

            int r = Integer.parseInt(p[1]);
            int c = Integer.parseInt(p[2]);

            int result = wqu_pc.find(new Point2D(r, c));
            System.out.println("find → " + result);

        } catch (Exception e) {
            System.out.println(MSG_USAGE_FIND);
        }
    }

    private void handleConnected(String[] p) {
        try {
            if (p.length != 5) {
                System.out.println(MSG_USAGE_CONNECTED);
                return;
            }

            int r1 = Integer.parseInt(p[1]);
            int c1 = Integer.parseInt(p[2]);
            int r2 = Integer.parseInt(p[3]);
            int c2 = Integer.parseInt(p[4]);

            boolean result = wqu_pc.connected(
                    new Point2D(r1, c1),
                    new Point2D(r2, c2)
            );
            System.out.println("connected → " + result);

        } catch (Exception e) {
            System.out.println(MSG_USAGE_CONNECTED);
        }
    }
}
