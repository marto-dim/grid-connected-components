package commands;

import engine.ComponentCounterImpl;
import engine.dto.ComponentResult;
import engine.interfaces.ComponentCounter;

public final class CountComponentsCommand implements Command {

    private final CommandContext ctx;
    private final String file;
    private final boolean useWQU_PC;

    public CountComponentsCommand(String file, boolean usedWQU_PC, CommandContext ctx) {
        this.file = file;
        this.useWQU_PC = usedWQU_PC;
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
        ComponentCounter counter = new ComponentCounterImpl();
        ComponentResult result = counter.compute(file, useWQU_PC);

        ctx.setResult(result);

        System.out.println("======================================");
        System.out.println("Components: " + result.components());
        System.out.println("Algorithm: " + (result.usedWQU_PC() ? "WQU_PC (DSU)" : "SW_UF (streaming)"));
        System.out.println("======================================");
    }
}
