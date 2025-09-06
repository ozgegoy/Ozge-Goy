package base;

import config.ConfigReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Hooks {
    private static final String baseUrl = ConfigReader.getProperty("baseUrl");
    private static final int THREAD_COUNT = Integer.parseInt(ConfigReader.getProperty("thread"));
    private static final AtomicInteger threadIndexCounter = new AtomicInteger(0);
    private static final List<WebDriver> drivers = new CopyOnWriteArrayList<>();
    private static final Boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
    private static final Boolean isRemote = Boolean.parseBoolean(ConfigReader.getProperty("remote"));
    private static final Boolean gitlab = Boolean.parseBoolean(ConfigReader.getProperty("gitlab"));

    static {
        for (int i = 0; i < THREAD_COUNT; i++) {
            drivers.add(null);
        }
    }

    private static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        if (headless) {
            options.addArguments("--headless=new");
        }

        try {
            if (!isRemote) {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);
            } else {
                String url = gitlab ? "http://gitlab-selenium-server:4545/wd/hub" : "http://localhost:4444/wd/hub";
                URL remoteUrl = new URL(url);
                return new RemoteWebDriver(remoteUrl, options);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @BeforeEach
    public void setUp() {
        int runnerIndex = getCurrentDriverIndex();
        WebDriver driver = getDriver(runnerIndex);
        if (driver == null) {
            drivers.set(runnerIndex, createDriver());
        }
        getDriver(runnerIndex).get(baseUrl);
        System.out.println("Driver initialized for thread: " + runnerIndex);
    }

    @AfterEach
    public void tearDown() {
        int runnerIndex = getCurrentDriverIndex();
        WebDriver driver = getDriver(runnerIndex);
        if (driver != null) {
            driver.quit();
            drivers.set(runnerIndex, null);
            System.out.println("Driver quit for thread: " + runnerIndex);
        }
    }

    public synchronized static WebDriver getDriver(int index) {
        return drivers.get(index);
    }

    private static int getCurrentDriverIndex() {
        return threadIndexCounter.getAndIncrement() % THREAD_COUNT;
    }
}
