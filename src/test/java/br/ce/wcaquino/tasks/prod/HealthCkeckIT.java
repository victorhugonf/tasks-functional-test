package br.ce.wcaquino.tasks.prod;

import br.ce.wcaquino.tasks.util.WebDriverFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class HealthCkeckIT {

    private WebDriver acessarAplicacao() throws MalformedURLException {
        return WebDriverFactory.newInstance().acessarAplicacao(9999);
    }

    @Test
    public void healthCheck() throws MalformedURLException {
        WebDriver webDriver = acessarAplicacao();
        try {
            String mensagem = webDriver.findElement(By.id("version")).getText();
            Assert.assertTrue(mensagem.startsWith("build"));
        } finally {
            webDriver.quit();
        }
    }
}
