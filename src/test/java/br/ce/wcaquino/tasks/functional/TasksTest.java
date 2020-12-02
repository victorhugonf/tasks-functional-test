package br.ce.wcaquino.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    private WebDriver acessarAplicacao() throws MalformedURLException {
        WebDriver driver = obterWebDriver();
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    private WebDriver obterWebDriver() throws MalformedURLException {
        //Necessário definir o executável do driver para que funcione no Jenkins
        //File file = new File("D:\\UDEMY\\Devops-CI-CD-Jenkins\\chromedriver_win32\\chromedriver.exe");
        //ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(file).build();
        //return new ChromeDriver(service);

        //Implementação utilizando Selenium grid
        return new RemoteWebDriver(new URL("http://localhost:4445/wd/hub"), new ChromeOptions());
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //Escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Escrever data
            int year = LocalDate.now().plusYears(1).getYear();
            driver.findElement(By.id("dueDate")).sendKeys(String.format("01/01/%s", year));

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } finally {
            //Fechar o navegador
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //Escrever data
            int year = LocalDate.now().plusYears(1).getYear();
            driver.findElement(By.id("dueDate")).sendKeys(String.format("01/01/%s", year));

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        } finally {
            //Fechar o navegador
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //Escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        } finally {
            //Fechar o navegador
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //Escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Escrever data
            driver.findElement(By.id("dueDate")).sendKeys("01/01/2020");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            //Fechar o navegador
            driver.quit();
        }
    }

}
