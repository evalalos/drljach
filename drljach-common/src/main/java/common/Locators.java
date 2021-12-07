package common;

import org.openqa.selenium.By;

public class Locators {

    public static final By acceptCookies = By.xpath("//div[@class='cookie-confirm ng-binding']");
    public static final By kladjenje = By.xpath("//div[text()='Klađenje']");
    public static final By kosarkaSpecijal = By.xpath("//div[text()='Košarka Specijal']");
    public static final By igraciUsaNba = By.xpath("//div[text()='Igraci ~ USA NBA']");
    public static final By players = By.xpath("//div[@class='txt-left ng-binding']");
    public static final By borders = By.xpath("//div[@class='tip-with-odd handicap-number ng-binding ng-scope' or @class='tip-with-odd handicap-number ng-scope']");
    public static final By igre = By.xpath("//div[@title='Igre']");
    public static final By poeniIgraca = By.xpath("//div[@class='w-33 f-09 tip-type-game ng-scope']//div[@class='tip-type-inner ng-binding'][text()='Poeni Igraca']");
    public static final By asistencijeIgraca = By.xpath("//div[@class='w-33 f-09 tip-type-game ng-scope']//div[@class='tip-type-inner ng-binding'][text()='Asistencije Igraca']");
    public static final By skokoviIgraca = By.xpath("//div[@class='w-33 f-09 tip-type-game ng-scope']//div[@class='tip-type-inner ng-binding'][text()='Skokovi Igraca']");
}
