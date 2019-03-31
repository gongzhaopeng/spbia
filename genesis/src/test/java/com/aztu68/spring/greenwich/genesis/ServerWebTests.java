package com.aztu68.spring.greenwich.genesis;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class ServerWebTests {


    @Value("${local.server.port}")
    private int port;

    private static ChromeDriver browser;

    @BeforeClass
    public static void openBrowser() {

        System.setProperty("webdriver.chrome.driver", "/Users/gongzhaopeng/Dists/chromedriver");

        browser = new ChromeDriver();
        browser.manage()
                .timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void addBookToEmptyList() {

        var baseUrl = String.format("http://localhost:%d", port);

        browser.get(baseUrl);

        assertNotNull(browser.findElementById("loginForm"));

        browser.findElementByName("username")
                .sendKeys("craig");
        browser.findElementByName("password")
                .sendKeys("password");
        browser.findElementByTagName("form")
                .submit();

        assertEquals("You have no books in your book list",
                browser.findElementByTagName("div").getText());
        assertNotNull(browser.findElementByName("bookForm"));

        browser.findElementByName("title")
                .sendKeys("BOOK TITLE");
        browser.findElementByName("author")
                .sendKeys("BOOK AUTHOR");
        browser.findElementByName("isbn")
                .sendKeys("1234567890");
        browser.findElementByName("description")
                .sendKeys("DESCRIPTION");
        browser.findElementByName("bookForm")
                .submit();

        var dt = browser.findElementByCssSelector("dt.bookHeadline");
        assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)",
                dt.getText());
        var dd = browser.findElementByCssSelector("dd.bookDescription");
        assertEquals("DESCRIPTION", dd.getText());
    }

    @AfterClass
    public static void closeBrowser() {
        browser.quit();
    }
}
