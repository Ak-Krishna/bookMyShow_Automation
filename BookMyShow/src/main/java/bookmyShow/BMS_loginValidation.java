package bookmyShow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BMS_loginValidation {
    public static WebDriver driver;  //created static & public variable to access everywhere
    //creating utility method.
    public static WebElement getElements(By by)
    {      // created method for access element
        return driver.findElement(by);
    }
    private static String  receive_otp() throws InterruptedException{
        driver.get("https://yopmail.com/");
        getElements(By.id("login")).sendKeys("seleniumauto@yopmail.com");
        getElements(By.xpath("//button/i")).click();
        driver.navigate().refresh();
        Thread.sleep(3000l);
        driver.switchTo().frame("ifmail");
        WebElement otp=getElements(By.xpath("(//table)[7]//tr/td"));
        String otp_txt=otp.getText();
        System.out.println("Received OTP "+otp_txt);
        return otp_txt;
    }
    public static void main(String[] args) throws InterruptedException {
        //initialize web driver and open links
         driver=new EdgeDriver();
        driver.get("https://in.bookmyshow.com/explore/home/");
//        driver.get("https://in.bookmyshow.com/explore/home/bengaluru");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000l));

        //Getting Elements from DOM(By Xpath) and Clicking the particular element
        getElements(By.xpath("//img[@alt='BANG']")).click();                       //get city by their xpath and click
        getElements(By.xpath("//div[@class='bwc__sc-1nbn7v6-14 khhVFa']")).click();        //getting signIn button and click
        getElements(By.xpath("(//span[@class='bwc__sc-dh558f-12 dLwbjU'])[2]")).click(); //fetching email button
        getElements(By.id("emailId")).sendKeys("seleniumauto@yopmail.com");         //fetching email input and pass email and click on continue
        Thread.sleep(3000l);
        getElements(By.xpath("//button")).click();                               //click on continue button

        //open new tab for yopmail.com and signin
        driver.switchTo().newWindow(WindowType.TAB);

        //getting window handles for new tab
        Set<String> set=driver.getWindowHandles();
        Iterator<String> itor=set.iterator();
        String first=itor.next();
        String second=itor.next();

        //switching to yopmail.com
         driver.switchTo().window(second);
         String otp_txt=receive_otp();
         Thread.sleep(7000l);
         driver.close();

        //Switch to main tab and create method for entering OTP
        driver.switchTo().window(first);
        Thread.sleep(3999l);
        List<WebElement> list=driver.findElements(By.xpath("//input[@type='tel']"));

//        getElements(By.xpath("(//input[@type='tel'])[2]")).sendKeys("1");
        String actual_txt=getElements(By.xpath("//span[@class='sc-gEkIjz iBRucH']")).getText();
        System.out.println("username of use: "+actual_txt);
    }

}
