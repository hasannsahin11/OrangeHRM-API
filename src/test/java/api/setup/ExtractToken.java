package api.setup;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Request;

import java.util.Optional;

public class ExtractToken {

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        ChromeDriver driver = new ChromeDriver(options);
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            Request req = request.getRequest();
            if (req.getUrl().contains("/validate")) {
                String postData = req.getPostData().orElse("");
                if (postData.contains("_token")) {
                    // Extract the _token from the postData
                   String csrfToken = postData.split("_token=")[1].split("&")[0];
                    System.out.println("Captured _token: " + csrfToken);
                }
            }
        });

        try {
            // Navigate to the login page
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            Thread.sleep(2000);

            // Proceed with the rest of the login process if necessary
            driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
            driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin123");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            // Allow time for the request to be captured
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}

