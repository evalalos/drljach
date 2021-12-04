package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Collector extends Base {

    public Collector acceptCookies() throws InterruptedException {
        Thread.sleep(2000);
        WebElement acceptCookies = getDriver().findElement(Locators.acceptCookies);
        acceptCookies.click();
        return this;
    }

    public Collector kladjenje() throws InterruptedException {
        Thread.sleep(2000);
        WebElement kladjenje = getDriver().findElement(Locators.kladjenje);
        kladjenje.click();
        return this;
    }

    public Collector kosarkaSpecijal() throws InterruptedException {
        Thread.sleep(2000);
        getDriver().findElement(Locators.kosarkaSpecijal).click();
        return this;
    }

    public Collector igraciUsaNba() throws InterruptedException {
        Thread.sleep(2000);
        getDriver().findElement(Locators.igraciUsaNba).click();
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
        Thread.sleep(2000);
        WebElement firstPlayer = getDriver().findElement(Locators.firstPlayerInList);
        firstPlayer.click();
        return this;
    }

    public Collector expandPlayerDropdownList() {
        WebElement arrow = getDriver().findElement(Locators.arrowNextToPlayer);
        arrow.click();
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
        WebElement player = getDriver().findElement(By.xpath("//div[@class='special-dropdown-item-match ng-binding'][text()='" + playerAndClubNames + "']"));
        player.click();
        Thread.sleep(700);
        return this;
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
                out.print(getClubName() + "," + getPlayerName());
                List<WebElement> allBorders = getDriver().findElements(Locators.playerBorders);
                for (WebElement singleBorder : allBorders) {
                    out.print("," + singleBorder.getText());
                }
                out.print('\n');
                expandPlayerDropdownList();
            }
        }
        return this;
    }
}