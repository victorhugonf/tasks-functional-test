package br.ce.wcaquino.tasks.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

    private static final String MEU_IP = "192.168.86.10";

    private WebDriverFactory() {
    }

    public static WebDriverFactory newInstance(){
        return new WebDriverFactory();
    }

    public WebDriver acessarAplicacao(Integer porta) throws MalformedURLException {
        WebDriver driver = obterWebDriver();
        driver.navigate().to(String.format("http://%s:%s/tasks/", MEU_IP, porta.toString()));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }

    private WebDriver obterWebDriver() throws MalformedURLException {
        //Necessário definir o executável do driver para que funcione no Jenkins
        //File file = new File("D:\\UDEMY\\Devops-CI-CD-Jenkins\\chromedriver_win32\\chromedriver.exe");
        //ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(file).build();
        //return new ChromeDriver(service);

        //Implementação utilizando Selenium grid
        return new RemoteWebDriver(new URL(String.format("http://%s:4444/wd/hub", MEU_IP)), new ChromeOptions());
    }

}
