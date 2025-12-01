package commands;

import engine.dto.ComponentResult;

public final class CommandContext {
    private ComponentResult result;

    public void setResult(ComponentResult r) { this.result = r; }
    public ComponentResult result()          { return result;   }
}
