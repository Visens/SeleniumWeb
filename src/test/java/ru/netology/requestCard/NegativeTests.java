package ru.netology.requestCard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NegativeTests {

	private WebDriver driver;

	@BeforeAll
	public static void setupAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void BeforeEach() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
		driver.get("http://localhost:9999");
	}

	@AfterEach
	public void teardown() {
		driver.quit();
		driver = null;
	}

	@Test
	public void shouldNotSuccessName() {
		driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Vladimirov Vladimir");
		driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79279348516");
		driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
		driver.findElement(By.cssSelector("button.button")).click();
		String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
		assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
	}

	@Test
	public void shouldNotSuccessEmptyName() {
		driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79279348516");
		driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
		driver.findElement(By.cssSelector("button.button")).click();
		assertEquals("Поле обязательно для заполнения",
				driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
	}

	@Test
	public void shouldNotSuccessEmptyPhone() {
		driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимиров Владимир");
		driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
		driver.findElement(By.cssSelector("button.button")).click();
		assertEquals("Поле обязательно для заполнения",
				driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
	}

	@Test
	public void shouldNotSuccessPhone() {
		driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимиров Владимир");
		driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79279348516000");
		driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
		driver.findElement(By.cssSelector("button.button")).click();
		assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
				driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
	}

	@Test
	public void shouldNotSuccessCheckBox() {
		driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимиров Владимир");
		driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79279348516");
		driver.findElement(By.cssSelector("button.button")).click();
		assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
	}
}