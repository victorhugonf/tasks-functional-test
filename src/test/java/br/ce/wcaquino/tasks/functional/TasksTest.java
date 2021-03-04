package br.ce.wcaquino.tasks.functional;

import br.ce.wcaquino.tasks.util.WebDriverFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.time.LocalDate;

public class TasksTest {

    private WebDriver acessarAplicacao() throws MalformedURLException {
        return WebDriverFactory.newInstance().acessarAplicacao(8001);
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

    @Test
    public void deveRemoverTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            //inserir tarefa
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
            int year = LocalDate.now().plusYears(1).getYear();
            driver.findElement(By.id("dueDate")).sendKeys(String.format("01/01/%s", year));
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);

            //remover tarefa
            driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
            message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } finally {
            //Fechar o navegador
            driver.quit();
        }
    }
}
