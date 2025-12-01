package engine.interfaces;

import engine.ComponentResult;
import engine.dto.GridSizeInfo;

public interface ComponentCounter {

    GridSizeInfo inspect(String file) throws Exception;

    ComponentResult compute(String file, boolean forceDSU) throws Exception;

}
