package engine.interfaces;

import engine.dto.ComponentResult;
import engine.dto.GridSizeInfo;

public interface ComponentCounter {

    GridSizeInfo inspect(String file) throws Exception;

    ComponentResult compute(String file, boolean uesWQU_PC) throws Exception;

}
