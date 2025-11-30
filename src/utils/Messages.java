package utils;

public final class Messages {
    public static final String ERR_QUERY_NOT_AVAILABLE =
            "Query API not available for streaming CCL.";

    public static final String PROMPT_CHOOSE_ALGO =
            "Grid is small enough for either DSU or CCL.\n" +
                    "Choose algorithm:\n" +
                    "1) DSU (Union-Find, slower but supports connected(a,b) and find(p))\n" +
                    "2) CCL (Streaming, memory-efficient, components only)\n" +
                    "Enter choice (1 or 2): ";

    public static final String INVALID_CHOICE =
            "Invalid choice. Please enter '1' or '2'.";
}
