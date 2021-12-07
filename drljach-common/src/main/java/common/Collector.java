package common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.*;

public class Collector extends Base {

    List<String> listOfAvailablePlayers = new ArrayList<>();
    Map<String, String> mapOfPlayersAndClubs = new LinkedHashMap<>();
    Map<String, String> mapWithAllBorders = new LinkedHashMap<>();
    List<String> points = new ArrayList<>();
    List<String> assists = new ArrayList<>();
    List<String> rebounds = new ArrayList<>();

    private Collector acceptCookies() throws InterruptedException {
        Thread.sleep(2000);
        WebElement locator = getDriver().findElement(Locators.acceptCookies);
        locator.click();
        return this;
    }

    private Collector kladjenje() throws InterruptedException {
        Thread.sleep(2000);
        WebElement locator = getDriver().findElement(Locators.kladjenje);
        locator.click();
        return this;
    }

    private Collector kosarkaSpecijal() throws InterruptedException {
        Thread.sleep(2000);
        WebElement locator = getDriver().findElement(Locators.kosarkaSpecijal);
        locator.click();
        return this;
    }

    private Collector igraciUsaNba() throws InterruptedException {
        Thread.sleep(2000);
        WebElement locator = getDriver().findElement(Locators.igraciUsaNba);
        locator.click();
        return this;
    }

    public void scroll(String playerAndClubNames) throws InterruptedException {
        WebElement element = getDriver().findElement(By.xpath("//div[@class='special-dropdown-item-match ng-binding'][text()='" + playerAndClubNames + "']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

    private List<String> getListOfAvailablePlayers() {
        List<WebElement> allPlayers = getDriver().findElements(Locators.players);
        for (WebElement allPlayer : allPlayers) {
            String playerName = allPlayer.getText();
            listOfAvailablePlayers.add(playerName);
        }
        return listOfAvailablePlayers;
    }

    private Map<String, String> convertClubNames() throws IOException {
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
        return map;
    }

    private Map<String, String> getPlayerAndClubName() throws IOException {
        for (String playerAndClubName : listOfAvailablePlayers) {
            String[] splitPlayerName = playerAndClubName.split("\\s+");
            String playerName = splitPlayerName[0].trim();
            String[] splitClubName = playerAndClubName.split("-");
            String clubName = splitClubName[1].trim();
            mapOfPlayersAndClubs.put(playerName, convertClubNames().get(clubName));
        }
        return mapOfPlayersAndClubs;
    }

    public Collector igre() throws InterruptedException {
        Thread.sleep(1500);
        WebElement igre = getDriver().findElement(Locators.igre);
        igre.click();
        return this;
    }

    public Collector poeniIgraca() throws InterruptedException {
        Thread.sleep(1500);
        WebElement poeniIgraca = getDriver().findElement(Locators.poeniIgraca);
        poeniIgraca.click();
        return this;
    }

    public Collector asistencijeIgraca() throws InterruptedException {
        Thread.sleep(1500);
        WebElement asistencijeIgraca = getDriver().findElement(Locators.asistencijeIgraca);
        asistencijeIgraca.click();
        return this;
    }

    public Collector skokoviIgraca() throws InterruptedException {
        Thread.sleep(1500);
        WebElement skokoviIgraca = getDriver().findElement(Locators.skokoviIgraca);
        skokoviIgraca.click();
        return this;
    }

    public List<String> getAllPoints() {
        List<WebElement> allPoints = getDriver().findElements(Locators.borders);
        for (WebElement p : allPoints) {
            String po = p.getText();
            points.add(po);
        }
        return points;
    }

    public List<String> getAllAssists() {
        List<WebElement> allAssists = getDriver().findElements(Locators.borders);
        for (WebElement a : allAssists) {
            String as = a.getText();
            assists.add(as);
        }
        return assists;
    }

    public List<String> getAllRebounds() {
        List<WebElement> allRebounds = getDriver().findElements(Locators.borders);
        for (WebElement r : allRebounds) {
            String re = r.getText();
            rebounds.add(re);
        }
        return rebounds;
    }

    public Map<String, String> collectAllBorders() {
        for (int i = 0; i < mapOfPlayersAndClubs.size(); i++) {
            String playerPoints = points.get(i);
            String playerAssists = assists.get(i);
            String playerRebounds = rebounds.get(i);
            mapWithAllBorders.put(mapOfPlayersAndClubs.values().toArray()[i] + "," +
                    mapOfPlayersAndClubs.keySet().toArray()[i], "," + playerPoints + "," + playerAssists + "," + playerRebounds);
        }
        return mapWithAllBorders;
    }

    public Collector collect() throws IOException, InterruptedException {
        String fileLocation = FileName.PLAYERS();
        FileWriter fw = new FileWriter(fileLocation, true);
        BufferedWriter bw = new BufferedWriter(fw);
        acceptCookies();
        kladjenje();
        kosarkaSpecijal();
        igraciUsaNba();
        igre();
        poeniIgraca();
        getListOfAvailablePlayers();
        getPlayerAndClubName();
        getAllPoints();
        igre();
        asistencijeIgraca();
        getAllAssists();
        igre();
        skokoviIgraca();
        getAllRebounds();
        collectAllBorders();
        try (PrintWriter out = new PrintWriter(bw)) {
            for (int i = 0; i < mapWithAllBorders.size(); i++) {
                String o1 = mapWithAllBorders.keySet().toArray()[i].toString();
                String o = mapWithAllBorders.values().toArray()[i].toString();
                out.print(o1 + o);
                out.print('\n');
            }
        }
        return this;
    }
}