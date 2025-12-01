package utils;

public final class Messages {
    public static final String ERR_QUERY_NOT_AVAILABLE =
            "Query API not available for streaming SW_UF.";

    public static final String PROMPT_CHOOSE_ALGO =
            "Grid is small enough for either WQU_PC or SW_UF.\n" +
                    "Choose algorithm:\n" +
                    "1) WQU_PC (Union-Find, slower but supports connected(a,b) and find(p))\n" +
                    "2) SW_UF (Streaming, memory-efficient, components only)\n" +
                    "Enter choice (1 or 2): ";

    public static final String INVALID_CHOICE =
            "Invalid choice. Please enter '1' or '2'.";
}
