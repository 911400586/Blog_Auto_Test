package com.example.blog_test;

import lombok.SneakyThrows;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static java.lang.Thread.sleep;

public class Tool {
    public static String Pub_blog = "写博客";
    public static String Index = "主页";
    public static String My_blog = "我的相关博客";

    @SneakyThrows
    public static void login(Driver driver)
    {
        String name = "user1";
        String pwd = "123456";
        driver.MaxWindow();
        driver.get(PublicVariable.LoginUrl);
        //服务器性能太低 加载页面慢
        driver.Wait(5);
        //输入用户名账号
        WebElement UserName = driver.findElementByCss("#username");
        WebElement password = driver.findElementByCss("#password");
        UserName.sendKeys(name);
        password.sendKeys(pwd);
        //点击登录按钮
        WebElement button = driver.findElementByCss("#submit");
        button.click();
        sleep(2000);
    }
    @SneakyThrows
    public static void pub_blog(Driver driver,String title,String content)
    {
        Tool.login(driver);
        //获取导航栏元素列表
        List<WebElement> webElementList = driver.findElements("#app > div > div > a");
        //选取写博客功能元素
        WebElement target = null;
        for(int i = 0 ;i<webElementList.size();i++)
        {
            WebElement element = webElementList.get(i);
            if(element.getText().equals(Tool.Pub_blog))
            {
                target = element;
                continue;
            }
        }
        //进入博客发布页面 填写内容
        target.click();
        sleep(5000);
        WebElement Title = driver.findElementByCss("#add_title");
        Actions actions = new Actions(driver.getWebDriver());
        //输入内容
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(content).perform();
        //输入标题
        Title.sendKeys(title);
        //点击发布
        List<WebElement> lists = driver.findElements(".title > button");
        for(int i  =0;i<lists.size();i++)
        {
            WebElement element = lists.get(i);
            if(element.getText().equals("发布文章"))
            {
                element.click();
                sleep(1000);
                driver.reject();
                break;
            }
        }
        sleep(500);
        driver.quit();
    }

}
