# Jenkins CI Setup

This guide configures Jenkins to build the project and run the Selenium + Cucumber test suites.

## Prerequisites on the Jenkins Agent

| Requirement | Notes |
|-------------|-------|
| JDK 17 | Set `JAVA_HOME` or configure in Jenkins **Global Tool Configuration** |
| Maven 3.8+ | Configure as **Maven-3.9** (or update `Jenkinsfile` tool name) |
| Google Chrome | Must be installed on the agent machine |
| Network access | Tests hit `https://perfume-demov-uksm.vercel.app` |

### Jenkins Global Tool Configuration

1. **Manage Jenkins → Tools**
2. **JDK** — add installation named `JDK-17` pointing to JDK 17
3. **Maven** — add installation named `Maven-3.9`

If your tool names differ, edit the `tools { }` block in `Jenkinsfile`.

## Option A: Pipeline Job (recommended)

Uses the `Jenkinsfile` in the repo root.

1. **New Item** → name it (e.g. `perfume-shop-tests`) → **Pipeline** → OK
2. **Pipeline** section:
   - **Definition**: Pipeline script from SCM
   - **SCM**: Git
   - **Repository URL**: your repo URL
   - **Branch**: `main` (or your branch)
   - **Script Path**: `Jenkinsfile`
3. Save and **Build Now**

### Build parameters

| Parameter | Description |
|-----------|-------------|
| `full` | Runs all 7 page runners via `testng.xml` (~longer) |
| `smoke` | Runs `MasterRunner` only via `testng-master.xml` |

## Option B: Freestyle Job

1. **New Item** → **Freestyle project**
2. **Source Code Management** → Git → repo URL
3. **Build** → **Invoke top-level Maven targets**:

```bash
clean test -Dsurefire.suiteXmlFiles=testng.xml -Dheadless=true
```

For smoke only:

```bash
clean test -Dsurefire.suiteXmlFiles=testng-master.xml -Dheadless=true
```

4. **Post-build Actions**:
   - **Publish JUnit test result report**: `target/surefire-reports/*.xml`
   - **Archive the artifacts**: `target/cucumber-reports/**`, `target/screenshots/**`

## Credentials (optional)

Login and checkout tests need a registered user. Defaults live in `config.properties`. For Jenkins, use **Credentials** or environment variables (override without changing the repo):

| Config key | Environment variable |
|------------|----------------------|
| `sample.email` | `SAMPLE_EMAIL` |
| `sample.password` | `SAMPLE_PASSWORD` |
| `headless` | `HEADLESS` (set to `true` in CI) |

In the pipeline job, add under **Environment variables** or in `Jenkinsfile`:

```groovy
environment {
    HEADLESS = 'true'
    SAMPLE_EMAIL = credentials('perfume-shop-test-email')
    SAMPLE_PASSWORD = credentials('perfume-shop-test-password')
}
```

Register the test user on the live site before the first CI run.

## Reports after a build

| Report | Location |
|--------|----------|
| Cucumber HTML | `target/cucumber-reports/<area>/report.html` |
| Surefire / JUnit | `target/surefire-reports/` |
| Failure screenshots | `target/screenshots/` |

The pipeline archives these automatically. Download from the build’s **Artifacts** link.

## Troubleshooting

| Issue | Fix |
|-------|-----|
| Chrome / ChromeDriver errors | Install Chrome on the agent; WebDriverManager downloads the driver at runtime |
| `session not created` in headless | Ensure `HEADLESS=true` or `-Dheadless=true` is set |
| Login / checkout failures | Confirm `SAMPLE_EMAIL` / `SAMPLE_PASSWORD` and that the user exists on the site |
| Maven/JDK not found | Match tool names in Jenkins **Tools** with `Jenkinsfile` |
| Windows agent | `Jenkinsfile` uses `bat` on Windows and `sh` on Linux automatically |
| Timeouts | Increase `explicit.wait` in config or pipeline `timeout` option |

## Local CI simulation

Run the same command Jenkins uses:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Dheadless=true
```

Smoke suite:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng-master.xml -Dheadless=true
```
