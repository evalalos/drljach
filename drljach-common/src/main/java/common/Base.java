package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Base {

    public static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        Base.driver = driver;
    }

    public void createChromeDriver() {

        String DRIVER_NAME_CHROME = "webdriver.chrome.driver";
        String DRIVER_PATH = "src/chromedriver";
        System.setProperty(DRIVER_NAME_CHROME, DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080",
                "--ignore-certificate-errors");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        driver = new ChromeDriver(options);

        if (getDriver() == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void openUrl(String url) {
        getDriver().get(url);
    }

    public void switchToWindow() {

        String parent = getDriver().getWindowHandle();

        Set<String> s = driver.getWindowHandles();

        for (String child_window : s) {

            if (!parent.equals(child_window)) {
                driver.switchTo().window(child_window);
            }
        }
    }

    public static void switchTab() {

        try {
            Set<String> windows = driver.getWindowHandles();

            Iterator<String> iterator = windows.iterator();

            String[] winNames = new String[windows.size()];

            int i = 0;
            while (iterator.hasNext()) {
                winNames[i] = iterator.next();
                i++;
            }

            if (winNames.length > 1) {
                for (i = winNames.length; i > 1; i--) {
                    driver.switchTo().window(winNames[i - 1]);
                    driver.close();
                }
            }
            driver.switchTo().window(winNames[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeLastOpenedWindow() {

        try {
            Set<String> windows = driver.getWindowHandles();

            Iterator<String> iterator = windows.iterator();

            String[] winNames = new String[windows.size()];

            int i = 0;
            while (iterator.hasNext()) {
                winNames[i] = iterator.next();
                i++;
            }

            if (winNames.length > 1) {
                for (i = winNames.length; i > 2; i--) {
                    driver.switchTo().window(winNames[i - 1]);
                    driver.close();
                }
            }
            driver.switchTo().window(winNames[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForElementToBeClickable(By locator) {

        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void quitDriver() {
        driver.quit();
        setDriver(null);
    }
}
