package be.ucm.cas.nasca.web.pagefactory.login;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(name = "j_username")
    private static WebElement username;

    @FindBy(name = "j_password")
    private static WebElement password;

    @FindBy(name = "loginForm")
    private static WebElement btnLogin;

    @FindBy(id = "_selectEquipeForm_equipeId")
    private static WebElement selectTeam;

    @FindBy(id = "selectEquipeForm_glob_btn_save")
    private WebElement btnSave;

    public LoginPage(WebDriver drv) {
        PageFactory.initElements(drv, this);
    }

    public void doSelectTeam(String team) {
        try {
            ActionUtils.sendInComboWithoutLoading(selectTeam, team);
            ActionUtils.clickAndLoading(btnSave);
        } catch (Exception e) {
        }
    }

    public void clickLogin(String strUsername, String strPassword) {
        ActionUtils.sendInTextField(username, strUsername);
        ActionUtils.sendInTextField(password, strPassword);
        btnLogin.submit();
    }
}