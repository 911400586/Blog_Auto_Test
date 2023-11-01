package com.example.blog_test;

import lombok.Data;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

@Data
public class Driver {
    private WebDriver webDriver;
   public Driver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*").addArguments("--no-sandbox");

        webDriver = new ChromeDriver(options);
    }
    public void get(String url)
    {
        webDriver.get(url);
    }
    public String getUrl()
    {
      return  webDriver.getCurrentUrl();
    }
    /**
     * 隐式等待
     */
    public void Wait(int time)
    {
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
    }
    public void Wait()
    {
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }
    /**
     * 切换到最新打开的窗口
     */
    public void switchToWindow(){
        // getWindowHandles获取所以得窗口句柄
        // getWindowHandles获取当前页面的窗口句柄
        //   System.out.println(webDriver.getWindowHandle());
        Set<String> handles = webDriver.getWindowHandles();
        String target_handle = "";
        for (String handle : handles) {
            target_handle = handle;
        }
        webDriver.switchTo().window(target_handle);
    }
    /**
     * 切换frame(id或者name)
     */
    public void switchToFrame(String key)
    {
        webDriver.switchTo().frame(key);
    }

    /**
     *
     * @param css 通过css选择器选择元素
     * @return
     */
    public WebElement findElementByCss(String css)
    {
        return webDriver.findElement(By.cssSelector(css));
    }
    public List<WebElement> findElements(String css)
    {
        return webDriver.findElements(By.cssSelector(css));
    }
    public void switchToContent()
    {
        webDriver.switchTo().defaultContent();

    }
    public void flush()
    {
        webDriver.navigate().refresh();

    }
    public void MaxWindow()
    {
        webDriver.manage().window().maximize();

    }
    public void quit()
    {
        webDriver.close();
    }
    public void confirm(){
        Alert alert = webDriver.switchTo().alert();
        alert.accept();  // 接受弹框
    }
    public void reject()
    {
        Alert alert = webDriver.switchTo().alert();
        alert.dismiss();
    }

}
