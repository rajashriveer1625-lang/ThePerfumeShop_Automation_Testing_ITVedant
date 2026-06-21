package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Master TestNG runner - runs ALL test scenarios tagged with @Smoke.
 * Use this to execute the complete test suite in one run.
 */
@CucumberOptions(
        features = {
                "src/test/resources/features/HomePage.feature",
                "src/test/resources/features/Login.feature",
                "src/test/resources/features/Register.feature",
                "src/test/resources/features/MenuNavigation.feature",
                "src/test/resources/features/AddToCart.feature",
                "src/test/resources/features/Checkout.feature",
                "src/test/resources/features/MyAccount.feature"
        },
        glue = {"stepdefinitions", "hooks"},
        tags = "@Smoke",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/master/report.html",
                "json:target/cucumber-reports/master/cucumber.json"
        }
)
public class MasterRunner extends AbstractTestNGCucumberTests {

    /**
     * Cucumber sorts discovered features alphabetically by URI, ignoring the
     * order declared in {@code @CucumberOptions(features = ...)}. Re-sort
     * scenarios here so the suite runs in the intended business flow.
     */
    private static final List<String> FEATURE_ORDER = List.of(
            "HomePage.feature",
            "Login.feature",
            "Register.feature",
            "MenuNavigation.feature",
            "AddToCart.feature",
            "Checkout.feature",
            "MyAccount.feature"
    );

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        Object[][] scenarios = super.scenarios();
        Arrays.sort(scenarios, Comparator.comparingInt(MasterRunner::featureOrderIndex));
        return scenarios;
    }

    private static int featureOrderIndex(Object[] scenario) {
        if (!(scenario[0] instanceof PickleWrapper pickleWrapper)) {
            return FEATURE_ORDER.size();
        }

        String uri = pickleWrapper.getPickle().getUri().toString();
        for (int i = 0; i < FEATURE_ORDER.size(); i++) {
            if (uri.endsWith(FEATURE_ORDER.get(i))) {
                return i;
            }
        }
        return FEATURE_ORDER.size();
    }
}
