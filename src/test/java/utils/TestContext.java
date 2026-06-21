package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores test data shared between steps within a scenario (e.g. newly registered user).
 * Cleared at the start of each scenario.
 */
public final class TestContext {

    private static final ThreadLocal<Map<String, String>> context = ThreadLocal.withInitial(HashMap::new);

    private TestContext() {
    }

    public static void set(String key, String value) {
        context.get().put(key, value);
    }

    public static String get(String key) {
        return context.get().get(key);
    }

    public static void clear() {
        context.get().clear();
    }
}
