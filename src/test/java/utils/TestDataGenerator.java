package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Generates unique test data for registration scenarios.
 * Each run creates a new email to avoid "email already exists" errors.
 */
public final class TestDataGenerator {

    private TestDataGenerator() {
    }

    /**
     * Creates a new user with a unique email address.
     */
    public static Map<String, String> createNewUser() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "Auto");
        user.put("lastName", "Test" + uniqueId);
        user.put("email", "autotest_" + uniqueId + "@mail.com");
        user.put("password", "Test@123456");
        return user;
    }
}
