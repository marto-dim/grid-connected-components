package commands;

import engine.ComponentCounter;
import engine.ComponentResult;

public final class CountComponentsCommand implements Command {

    private final String file;
    private final boolean useDSU;

    public CountComponentsCommand(String file, boolean useDSU) {
        this.file = file;
        this.useDSU = useDSU;
    }

    @Override
    public void execute() throws Exception {
        ComponentCounter counter = new ComponentCounter();

        ComponentResult result = counter.compute(file, useDSU);

        System.out.println("======================================");
        System.out.println("Components: " + result.components());
        System.out.println("Algorithm: " + (result.usedDSU() ? "WQU_PC (DSU)" : "SW_UF (streaming)"));
        System.out.println("======================================");
    }
}
