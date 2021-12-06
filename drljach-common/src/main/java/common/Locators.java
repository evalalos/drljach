package common;

import org.openqa.selenium.By;

public class Locators {

    public static final By acceptCookies = By.xpath("//div[@class='cookie-confirm ng-binding']");
    public static final By kladjenje = By.xpath("//div[text()='Klađenje']");
    public static final By kosarkaSpecijal = By.xpath("//div[text()='Košarka Specijal']");
    public static final By igraciUsaNba = By.xpath("//div[text()='Igraci ~ USA NBA']");
    public static final By playerAndClubNames = By.xpath("//div[@class='special-teams-match row ng-binding']");
    public static final By matchStartDateAndTime = By.xpath("//div[@class='row special-details ng-binding']//span");
    public static final By playerBorders = By.xpath("//div[contains(@class,'special-game special-game-description')]//span");
    public static final By firstPlayerInList = By.xpath("//div[@class='home-game home-game-match-row ng-scope odd-row'][1]//match//div[@class='cc-w-teams tooltips row match-tooltip']//div[@class='txt-left ng-binding']");
    public static final By arrowNextToPlayer = By.xpath("//span[@class='special-dropdown-matches-arrow arrow-down-img']");
    public static final By playersInDropdownList = By.xpath("//div[@class='special-dropdown-item-match ng-binding']");
    public static final By assist = By.xpath("//div[@id='home-container']//div[@class='no-print ng-scope']//div[@class='special-table-game ng-scope'][contains(.,'Asistencije I')]");
    public static final By points = By.xpath("//div[@id='home-container']//div[@class='no-print ng-scope']//div[@class='special-table-game ng-scope'][contains(.,'Poeni I')]");
    public static final By rebounds = By.xpath("//div[@class='special-table-game ng-scope'][contains(.,'Skokovi Igraca')][not(contains(.,'Poeni'))][not(contains(.,'Asistencije'))]");
}
