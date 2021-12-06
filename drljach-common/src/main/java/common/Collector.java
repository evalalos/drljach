package common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Collector extends Base {

    private Collector acceptCookies() throws InterruptedException {
        Thread.sleep(3000);
        WebElement locator = getDriver().findElement(Locators.acceptCookies);
        locator.click();
        return this;
    }

    public Collector kladjenje() throws InterruptedException {
        Thread.sleep(3000);
        WebElement locator = getDriver().findElement(Locators.kladjenje);
        locator.click();
        return this;
    }

    public Collector kosarkaSpecijal() throws InterruptedException {
        Thread.sleep(3000);
        WebElement locator = getDriver().findElement(Locators.kosarkaSpecijal);
        locator.click();
        return this;
    }

    public Collector igraciUsaNba() throws InterruptedException {
        Thread.sleep(3000);
        WebElement locator = getDriver().findElement(Locators.igraciUsaNba);
        locator.click();
        return this;
    }

    public String getClubName() throws IOException {
        String clubNameAndPlayerName = getDriver().findElement(Locators.playerAndClubNames).getText();
        int minus = clubNameAndPlayerName.indexOf("-") + 2;
        String clubName = clubNameAndPlayerName.substring(minus);
        BufferedReader reader = new BufferedReader(new FileReader(
                "/Users/milos/QA/drljach/drljach-common/src/main/resources/nba.txt"));
        HashMap<String, String> map = new HashMap<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            String[] parts = line.split(",");
            for (int i = 1; i < parts.length; i++) {
                map.put(parts[1], parts[0]);
            }
        }
        reader.close();
        return map.get(clubName);
    }

    public String getPlayerName() {
        String playerName = getDriver().findElement(Locators.playerAndClubNames).getText();
        String[] t1 = playerName.split(" ");
        String s = t1[0];
        return s;
    }

    /*public String getMatchStartDateAndTime() {
        return getDriver().findElement(Locators.matchStartDateAndTime).getText();
    }*/

    public Collector firstPlayerInList() throws InterruptedException {
        Thread.sleep(500);
        WebElement locator = getDriver().findElement(Locators.firstPlayerInList);
        locator.click();
        return this;
    }

    public Collector expandPlayerDropdownList() throws InterruptedException {
        Thread.sleep(500);
        WebElement locator = getDriver().findElement(Locators.arrowNextToPlayer);
        locator.click();
        return this;
    }

    public List<String> getListOfPlayers() {
        List<WebElement> allPlayers = getDriver().findElements(Locators.playersInDropdownList);
        List<String> playerAndClubNames = new ArrayList<>();
        for (WebElement allPlayer : allPlayers) {
            String playerName = allPlayer.getText();
            playerAndClubNames.add(playerName);
        }
        return playerAndClubNames;
    }

    public Collector openPlayerFromList(String playerAndClubNames) throws InterruptedException {
        Thread.sleep(2000);
        WebElement player = getDriver().findElement(By.xpath("//div[@class='special-dropdown-item-match ng-binding'][text()='" + playerAndClubNames + "']"));
        player.click();
        Thread.sleep(700);
        return this;
    }

    public boolean isElementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 4);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    public String getPoints() {
        List<String> matchList;
        if (isElementVisible(Locators.points)) {
            WebElement points = getDriver().findElement(Locators.points);
            String ass = points.getText();
            matchList = new ArrayList<>();
            Pattern regex = Pattern.compile("\\((.*?)\\)");
            Matcher regexMatcher = regex.matcher(ass);
            while (regexMatcher.find()) {
                matchList.add(regexMatcher.group(1));
            }
        } else {
            matchList = Collections.singletonList("");
        }
        String numberToParse = String.valueOf(matchList);
        return StringUtils.substringBetween(numberToParse, "[", "]");
    }

    public String getAssist() {
        List<String> matchList;
        if (isElementVisible(Locators.assist)) {
            WebElement assist = getDriver().findElement(Locators.assist);
            String ass = assist.getText();
            matchList = new ArrayList<>();
            Pattern regex = Pattern.compile("\\((.*?)\\)");
            Matcher regexMatcher = regex.matcher(ass);
            while (regexMatcher.find()) {
                matchList.add(regexMatcher.group(1));
            }
        } else {
            matchList = Collections.singletonList("");
        }
        String numberToParse = String.valueOf(matchList);
        return StringUtils.substringBetween(numberToParse, "[", "]");
    }

    public String getRebounds() {
        List<String> matchList;
        if (isElementVisible(Locators.rebounds)) {
            WebElement rebounds = getDriver().findElement(Locators.rebounds);
            String ass = rebounds.getText();
            matchList = new ArrayList<>();
            Pattern regex = Pattern.compile("\\((.*?)\\)");
            Matcher regexMatcher = regex.matcher(ass);
            while (regexMatcher.find()) {
                matchList.add(regexMatcher.group(1));
            }
        } else {
            matchList = Collections.singletonList("");
        }
        String numberToParse = String.valueOf(matchList);
        return StringUtils.substringBetween(numberToParse, "[", "]");
    }

    public void scroll(By locator) throws InterruptedException {
        WebElement element = getDriver().findElement(locator);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

    public Collector collect() throws InterruptedException, IOException {
        String fileLocation = FileName.PLAYERS();
        FileWriter fw = new FileWriter(fileLocation, true);
        BufferedWriter bw = new BufferedWriter(fw);
        acceptCookies();
        kladjenje();
        kosarkaSpecijal();
        igraciUsaNba();
        firstPlayerInList();
        expandPlayerDropdownList();
        getListOfPlayers();
        try (PrintWriter out = new PrintWriter(bw)) {
            for (int i = 0; i < getListOfPlayers().size(); i++) {
                openPlayerFromList(getListOfPlayers().get(i));
                out.print(getClubName() + "," + getPlayerName() + ",");
                out.print(getPoints() + ",");
                out.print(getAssist() + ",");
                out.print(getRebounds());
                out.print('\n');
                expandPlayerDropdownList();
            }
        }
        return this;
    }
}