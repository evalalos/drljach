package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

    public String getPlayerAndClubNames() {
        return getDriver().findElement(Locators.playerAndClubNames).getText();
    }

    public String getMatchStartDateAndTime() {
        return getDriver().findElement(Locators.matchStartDateAndTime).getText();
    }

    public Collector firstPlayerInList() throws InterruptedException {
        Thread.sleep(2000);
        WebElement firstPlayer = getDriver().findElement(Locators.firstPlayerInList);
        firstPlayer.click();
        return this;
    }

    public Collector expandPlayerDropdownList(){
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
        WebElement player = getDriver().findElement(By.xpath("//div[@class='special-dropdown-item-match ng-binding'][text()='"+playerAndClubNames+"']"));
        player.click();
        Thread.sleep(700);
        return this;
    }

    public Collector collect() throws InterruptedException, IOException {
        String fileLocation = FileName.PLAYERS();
        FileWriter fw = new FileWriter(fileLocation, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);

        acceptCookies();
        kladjenje();
        kosarkaSpecijal();
        igraciUsaNba();
        firstPlayerInList();
        expandPlayerDropdownList();
        getListOfPlayers();

        for (int i = 0; i < getListOfPlayers().size(); i++){
            openPlayerFromList(getListOfPlayers().get(i));
            out.print(getPlayerAndClubNames() + ", " + getMatchStartDateAndTime());
            List<WebElement> allBorders = getDriver().findElements(Locators.playerBorders);
            for (WebElement singleBorder : allBorders) {
                out.print(", " + singleBorder.getText());
                out.println("");
            }
            out.close();
            expandPlayerDropdownList();
        }

        return this;
    }


}