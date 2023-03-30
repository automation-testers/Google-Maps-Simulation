package com.browserstack.run_first_test;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class FirstTest extends BrowserStackTestNGTest {

    @Test
    public void test() throws Exception {
        List<String>Addresses =ReadCsv();
        for (int i=1;i<Addresses.size();i++) {
            performTest(Addresses.get(i));
        }
        driver.quit();
    }

    public void performTest(String Address) throws Exception
    {
        float Lat,Lng,Alt;
        String[] Parts= Address.split(",");
        System.out.println("Location: "+Double.parseDouble(Parts[5])+","+ Double.parseDouble(Parts[6]));
        driver.setLocation(new Location(Double.parseDouble(Parts[5]), Double.parseDouble(Parts[6]),0));

        Wait wait = new FluentWait(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(1000, MILLISECONDS)
                .ignoring(Exception.class);


        WebElement SignInButton = GetElement(wait,"Sign in","android.widget.Button");
        SignInButton.click();

        WebElement AddAccount = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.ImageView");
        AddAccount.click();

        WebElement EmailIdTextBox = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.view.View/android.widget.EditText");
        EmailIdTextBox.click();
        Thread.sleep(200);
        EmailIdTextBox.sendKeys(Parts[0]);

        WebElement SINextButton = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.Button");
        SINextButton.click();

        WebElement EIdPassword = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.view.View/android.widget.EditText");
        EIdPassword.sendKeys(Parts[1]);

        WebElement PSNextButton = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.Button");
        PSNextButton.click();



        WebElement EnterAuthCodeLabel = GetElement(wait,"Get a verification code from the Google Authenticator app@Check your","android.widget.TextView");

        if(EnterAuthCodeLabel!=null)
        {
            if (EnterAuthCodeLabel.getText().contains("Check your"))
            {
                //Try Another
                FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View/android.widget.Button").click();

            }
            EnterAuthCodeLabel = GetElement(wait,"Get a verification code from the Google Authenticator app","android.widget.TextView");

            EnterAuthCodeLabel.click();
        }

        WebElement AuthCodeTextBox = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText");
        AuthCodeTextBox.sendKeys(TOTPGenerator.getTwoFactorCode());
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.Button").click();

        //I Agree
        WebElement IAgree = FindEle(wait,SearchBy.ByButton,"I agree");
        if(IAgree!=null)
        {
            IAgree.click();
        }

        // Accept Backup and Storage
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button").click();


        WebElement SearchLocation = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText/android.widget.TextView");
        SearchLocation.click();

        WebElement SearchLocation1 = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText/android.widget.EditText");

        SearchLocation1.sendKeys(Parts[13]);

        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));

        //Click on Direction
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.support.design.chip.Chip").click();

        //Click on Choose your starting point
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.EditText[1]/android.widget.LinearLayout/android.widget.TextView").click();

        //Select your current location
        WebElement YourLocation = GetElement(wait,"Your location","android.widget.TextView");
        YourLocation.click();
//        //YourLocation.sendKeys(Parts[0]);

        //Click On Start
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView").click();



        //Got It
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button").click();

        driver.setLocation(new Location(Double.parseDouble(Parts[7]), Double.parseDouble(Parts[8]), 0));
        Thread.sleep(5000);

        driver.setLocation(new Location(Double.parseDouble(Parts[9]), Double.parseDouble(Parts[10]), 0));
        Thread.sleep(5000);

        driver.setLocation(new Location(Double.parseDouble(Parts[11]), Double.parseDouble(Parts[12]), 0));
        Thread.sleep(5000);


        try {
            //Close navigation
            FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.view.ViewGroup/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.ImageView[1]").click();

            //Click On Start
            FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView").click();


        }catch (Exception e)
        {}

        //After Reaching Click on Done
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView").click();


    }


    public static List<String> ReadCsv()
    {
        Path fileName = Paths.get("C:\\Users\\Patil\\Downloads\\Directions.csv");
        List<String> Rows=null;
        // Now calling Files.readString() method to
        // read the file
        try {
            Rows = Files.readAllLines(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Rows;
    }

    enum SearchBy {
        ById,
        ByXPath,
        ByLinkText,
        ByButton
    }

    public WebElement FindEle(Wait wait,SearchBy searchBy,String XpathOrId)
    {
        return (WebElement) wait.until(new Function<AndroidDriver, WebElement>() {
            @Override
            public WebElement apply(AndroidDriver driver) {

                if(searchBy==SearchBy.ById)
                {
                    return driver.findElement(By.id(XpathOrId));
                }
                else if (searchBy==SearchBy.ByXPath){
                    return driver.findElement(By.xpath(XpathOrId));
                }
                else if (searchBy==SearchBy.ByLinkText){
                    List<WebElement> ListElement=  driver.findElements(By.className("android.widget.TextView"));

                    WebElement ele=  ListElement.stream().filter((item -> ((WebElement)item).getText().contains(XpathOrId))).collect(Collectors.toList()).get(0);
                    //  driver.findElements(By.className("android.widget.TextView")).stream().filter((item -> ((WebElement)item).getText().contains("Google Authenticator"))).collect(Collectors.toList());
                    return ele;
                    // return ListElement.get(0);
                    //(WebElement) driver.findElements(By.className("android.widget.TextView")).stream().filter((item -> ((WebElement)item).getText().equals("google"))).collect(Collectors.toList());
                }
                else if (searchBy==SearchBy.ByButton){
                    List<WebElement> ListElement=  driver.findElements(By.className("android.widget.Button"));

                    for(WebElement we : ListElement)
                    {
                        for(String str :XpathOrId.split("@"))
                        {
                            if(we.getText().contains(str))
                            {
                                return  we;
                            }
                        }
                    }
                    return null;
                }

                return null;
            }
        });
    }


    public WebElement GetElement(Wait wait,String Label,String Xpath)
    {
        return (WebElement) wait.until(new Function<AndroidDriver, WebElement>() {
            @Override
            public WebElement apply(AndroidDriver driver) {

                String[] XpathParts=Xpath.split("/");
                List<WebElement> ListElement =  driver.findElements(By.className(XpathParts[XpathParts.length-1]));

                for(WebElement we : ListElement)
                {
                    for(String str :Label.split("@"))
                    {
                        if(we.getText().toLowerCase().contains(str.toLowerCase()))
                        {
                            return  we;
                        }
                    }
                }
                return null;
            }
        });
    }
}