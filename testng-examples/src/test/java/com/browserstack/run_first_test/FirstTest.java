package com.browserstack.run_first_test;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

//import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
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
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
//import io.appium.java_client.TouchAction;

public class FirstTest extends BrowserStackTestNGTest {

    int SimulateWait=1000;

    @Test
    public void test() throws Exception {
        List<String>Addresses =ReadCsv();
        SetWait();
        String[] Parts= Addresses.get(1).split(",");
        Login(Parts);
//        SkipLogin();
        for (int i=1;i<Addresses.size();i++) {
           Parts= Addresses.get(i).split(",");
           performTest(Parts);
        }
        driver.quit();
    }

    void SetWait()
    {
        wait = new FluentWait(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(300, MILLISECONDS)
                .ignoring(Exception.class);
    }

    Wait wait;
    void Login(String[] Parts) throws Exception
    {
        WebElement SignInButton = GetElement(wait,"Sign in","android.widget.Button");
        SignInButton.click();

        WebElement AddAccount = GetElement(wait,"add account","android.widget.TextView");
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
        AuthCodeTextBox.sendKeys(TOTPGenerator.getTwoFactorCode(Parts[2]));
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.Button").click();

        //I Agree
        WebElement IAgree = FindEle(wait,SearchBy.ByButton,"I agree");
        if(IAgree!=null)
        {
            IAgree.click();
        }

        // Accept Backup and Storage
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.Button").click();


    }

    void SkipLogin() throws Exception
    {
        WebElement SkipButton = GetElement(wait,"skip","android.widget.Button");
        SkipButton.click();
    }
    public void performTest(String[] Parts) throws Exception
    {
        Scrolls=0;
        float Lat,Lng,Alt;
        System.out.println("Location: "+Double.parseDouble(Parts[5])+","+ Double.parseDouble(Parts[6]));
        driver.setLocation(new Location(Double.parseDouble(Parts[5]), Double.parseDouble(Parts[6]),0));

        SearchKeyword(Parts[3]);
        if(!FindShop(Parts[4],Integer.parseInt(Parts[21]))){ return; }
        StartDirectionsFromList();
        SimulateLocations(Parts);
        RestartDirection();
        FinishDirections();
    }

    void SearchKeyword(String Keyword) throws InterruptedException {
        WebElement SearchLocationTextView = GetElement(wait,"Search here","android.widget.TextView");
        SearchLocationTextView.click();

        WebElement SearchLocationEditText = GetElement(wait,"Search here","android.widget.EditText");
        SearchLocationEditText.click();
        Thread.sleep(200);
        SearchLocationEditText.sendKeys(Keyword);
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));

    }

    int Scrolls=0;
    boolean FindShop(String ShopName,int MaxScrollCount) throws InterruptedException {
        try {
            WebElement SearchLocationTextView = GetElement(wait,ShopName,"android.widget.TextView");
            if(SearchLocationTextView!=null)
            {
                SearchLocationTextView.click();
                return true;
            }
        }catch (Exception e){
            if(Scrolls<MaxScrollCount)
            {
                Scroll();
                Scrolls++;
                return FindShop(ShopName,MaxScrollCount);
            }
            else{
                driver.navigate().back();
                driver.navigate().back();
                System.out.println("Location Not found: "+ShopName);
                //go to home
                //FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.ImageView").click();

            }
        }

        return false;
    }

    void Scroll() throws InterruptedException {
        //WebElement RecyclerView = FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.view.ViewGroup/android.support.v7.widget.RecyclerView");

        TouchAction action = new TouchAction(driver);

        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

//        action. press(PointOption.point(RecyclerView.getLocation().getX()+150,RecyclerView.getLocation().getY()-150)).release().perform();
//        WebElement ShowList = GetElement(wait,"show list","android.widget.TextView");
//        ShowList.click();

       // List<AndroidElement> ListElementa =  RecyclerView.findElements(By.className("android.widget.TextView"));

//        PointOption to= PointOption.point(ListElementa.get(0).getLocation()) ;
//        PointOption from= PointOption.point(ListElementa.get(0).getLocation().getX(),height-100) ;

        PointOption to= PointOption.point(100,100) ;
        PointOption from= PointOption.point(100,height-100) ;
        action.longPress(from)
                .moveTo(to).release().perform();
        Thread.sleep(100);
        action.longPress(from)
                .moveTo(to).release().perform();
        Thread.sleep(100);
        action.longPress(from)
                .moveTo(to).release().perform();
        Thread.sleep(100);
        action.longPress(from)
                .moveTo(to).release().perform();

    }
    void StartDirectionsFromList()
    {
        WebElement DirectionsButton = GetElement(wait,"Directions","android.widget.TextView");
        if(DirectionsButton!=null)
        {
            DirectionsButton.click();
            ChooseStartLocation();
        }
    }

    void StartDirections()
    {
        //Click on Direction
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.support.design.chip.Chip").click();
        ChooseStartLocation();

    }

    void ChooseStartLocation()
    {
        //Click on Choose your starting point
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.FrameLayout[3]/android.widget.LinearLayout/android.widget.EditText[1]/android.widget.LinearLayout/android.widget.TextView").click();

        //Select your current location
        WebElement YourLocation = GetElement(wait,"Your location","android.widget.TextView");
        YourLocation.click();

        //Click On Start
        FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView").click();

        //Got It
        try {
            FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button").click();

        }catch(Exception e)
        {}
    }

    void RestartDirection()
    {
        try {
            //Close navigation
            FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.FrameLayout[3]/android.view.ViewGroup/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.ImageView[1]").click();

            //Click On Start
            FindEle(wait,SearchBy.ByXPath,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView").click();

        }catch (Exception e)
        {}
    }


    void SimulateLocations(String[] Parts) throws Exception
    {
        driver.setLocation(new Location(Double.parseDouble(Parts[7]), Double.parseDouble(Parts[8]), 0));
        Thread.sleep(Integer.parseInt(Parts[20]));

        driver.setLocation(new Location(Double.parseDouble(Parts[9]), Double.parseDouble(Parts[10]), 0));
        Thread.sleep(Integer.parseInt(Parts[20]));

        driver.setLocation(new Location(Double.parseDouble(Parts[11]), Double.parseDouble(Parts[12]), 0));
        Thread.sleep(Integer.parseInt(Parts[20]));

        driver.setLocation(new Location(Double.parseDouble(Parts[13]), Double.parseDouble(Parts[14]), 0));
        Thread.sleep(Integer.parseInt(Parts[20]));
        driver.setLocation(new Location(Double.parseDouble(Parts[15]), Double.parseDouble(Parts[16]), 0));
        Thread.sleep(Integer.parseInt(Parts[20]));
        driver.setLocation(new Location(Double.parseDouble(Parts[17]), Double.parseDouble(Parts[18]), 0));

        Thread.sleep(1000);
    }

    void FinishDirections()
    {
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