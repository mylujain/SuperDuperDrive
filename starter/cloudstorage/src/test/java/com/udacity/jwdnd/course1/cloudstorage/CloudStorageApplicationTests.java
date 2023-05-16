package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		//Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}
	public void createNote() {
		String noteTitle = "test";
		String noteDescription = "This is test note.";

		// Create a test account
		doMockSignUp("Lujain","Majed","lu","123");
		doLogIn("lu", "123");

		// Try to create new note
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("buttonAddNewNote")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("buttonAddNewNote")));
		driver.findElement(By.id("buttonAddNewNote")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModalLabel")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		driver.findElement(By.id("note-title")).clear();
		driver.findElement(By.id("note-title")).sendKeys(noteTitle);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
		driver.findElement(By.id("note-description")).clear();
		driver.findElement(By.id("note-description")).sendKeys("desc");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("noteModalSaveChanges")));
		driver.findElement(By.id("noteModalSaveChanges")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated( By.id("buttonAddNewNote")));

		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-id='changeSuccessLink']")));

	}
	@Test
	public void testCreateNote() {
		createNote();
		Assertions.assertEquals( "Result",  driver.getTitle());
	//	Assertions.assertEquals(noteTitle,  driver.findElement(By.id("noteTitle")).getText());

	}

	@Test
	public void testEditNote() {
		String EditedNoteTitle = "new title";
		//create new note
		createNote();


		// Try to edit note
		WebDriverWait wait = new WebDriverWait(driver, 2);

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-id='changeSuccessLink']")));
		driver.findElement(By.cssSelector("[data-id='changeSuccessLink']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("editNote")));
		driver.findElement(By.id("editNote")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		driver.findElement(By.id("note-title")).clear();
		driver.findElement(By.id("note-title")).sendKeys(EditedNoteTitle);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("noteModalSaveChanges")));
		driver.findElement(By.id("noteModalSaveChanges")).click();
		Assertions.assertEquals( "Result",  driver.getTitle());



	}

	@Test
	public void testDeleteNote() {
		//create new note
		createNote();

		// Try to delete note
		WebDriverWait wait = new WebDriverWait(driver, 2);

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-id='changeSuccessLink']")));
		driver.findElement(By.cssSelector("[data-id='changeSuccessLink']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteNote")));
		driver.findElement(By.id("deleteNote")).click();
		Assertions.assertEquals( "Result",  driver.getTitle());
	}

	public void createCredential() throws InterruptedException {
		String URL = "udacity.com";
		String username = "Lujain";
		String password = "123";

		// Create a test account
		doMockSignUp("Lujain","Majed","lu","123");
		doLogIn("lu", "123");

		// Try to create new credential
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("buttonAddNewCredential")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("buttonAddNewCredential")));
		driver.findElement(By.id("buttonAddNewCredential")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModalLabel")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		//driver.findElement(By.id("credential-url")).clear();
		driver.findElement(By.id("credential-url")).sendKeys(URL);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
		//driver.findElement(By.id("credential-username")).clear();
		driver.findElement(By.id("credential-username")).sendKeys(username);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		//driver.findElement(By.id("credential-username")).clear();
		driver.findElement(By.id("credential-password")).sendKeys(password);


		wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModalSaveChanges")));
		driver.findElement(By.id("credentialModalSaveChanges")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated( By.id("buttonAddNewCredential")));

		Thread.sleep(1 * 1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-id='changeSuccessLink']")));

	}
	@Test
	public void testCreateCredential() throws InterruptedException {
		createCredential();
		Assertions.assertEquals( "Result",  driver.getTitle());
		//	Assertions.assertEquals(noteTitle,  driver.findElement(By.id("noteTitle")).getText());

	}

	@Test
	public void testEditCredential() throws InterruptedException {
		String EditedURL = "https://learn.udacity.com/";
		//create new credential
		createCredential();


		// Try to edit Credential
		WebDriverWait wait = new WebDriverWait(driver, 2);

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-id='changeSuccessLink']")));
		driver.findElement(By.cssSelector("[data-id='changeSuccessLink']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredential")));
		driver.findElement(By.id("editCredential")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		driver.findElement(By.id("credential-url")).clear();
		driver.findElement(By.id("credential-url")).sendKeys(EditedURL);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModalSaveChanges")));
		driver.findElement(By.id("credentialModalSaveChanges")).click();
		Assertions.assertEquals( "Result",  driver.getTitle());



	}
	@Test
	public void tesDeleteCredential() throws InterruptedException {
		//create new credential
		createCredential();


		// Try to delete Credential
		WebDriverWait wait = new WebDriverWait(driver, 2);

		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-id='changeSuccessLink']")));
		driver.findElement(By.cssSelector("[data-id='changeSuccessLink']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteCred")));
		driver.findElement(By.id("deleteCred")).click();

		Assertions.assertEquals( "Result",  driver.getTitle());



	}

	public void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
