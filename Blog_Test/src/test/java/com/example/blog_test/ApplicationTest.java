package com.example.blog_test;

import lombok.SneakyThrows;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import sun.security.util.Password;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationTest {
    /**
     * 测试登录模块
     */
    @Nested
    @Disabled
    public class LoginTest{
        @SneakyThrows
        @ParameterizedTest
        @CsvFileSource( files = "src/test/resources/TestSource/UserAcount.csv")
        void test(String name,String pwd)
        {
            Driver driver = new Driver();
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

            //预期结果:跳转主页
//            WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10)); // 设置等待时间为10秒
//            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#app")));
            sleep(100);
            String CurUrl = driver.getUrl();
            assertEquals(CurUrl,PublicVariable.IndexUrl);
            //关闭窗口
            driver.quit();
        }
    }
    /**
     * 测试注册模块
     */
    @Nested
    @Disabled
    public class RegisterTest{
    }
    /**
     * 测试注销模块
     */
    @Nested
    @Disabled
    public class LogoutTest{
        @SneakyThrows
        @Test
        void test()
        {

            Driver driver = new Driver();
            //登录
            Tool.login(driver);
            //点击注销按钮
            WebElement LogOutBtn = driver.findElementByCss("#app > div > div > a:nth-child(8)");
            LogOutBtn.click();
            driver.confirm();
            sleep(1000);
            //预期结果: 导航栏 只有注册 登录 主页三个按钮
            List<WebElement> lists = driver.findElements("#app > div > div > a");
            assertEquals(lists.size(),3);
        }
    }
    /**
     * 测试博客发布模块
     */
    @Nested
    @Disabled
    public class ReleaseTest{
        @SneakyThrows
        @ParameterizedTest
        @CsvFileSource( files = "src/test/resources/TestSource/BlogDetail.csv")
       public void test(String title, String content)
        {

            Driver driver = new Driver();
            //登录
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
            //预期结果:跳转到主我的博客页面 主页面第一篇文章为刚发布文章
            sleep(1000);
            String CurUrl = driver.getUrl();
            String IndexTitle = driver.findElementByCss("#app > div > section > div.el-scrollbar > div.el-scrollbar__wrap.el-scrollbar__wrap--hidden-default > div > section > section > main > section > header:nth-child(1) > header:nth-child(1) > h2").getText();
            String IndexContent = driver.findElementByCss("#app > div > section > div.el-scrollbar > div.el-scrollbar__wrap.el-scrollbar__wrap--hidden-default > div > section > section > main > section > main > span").getText();
            assertEquals(CurUrl,PublicVariable.MyBlogUrl);
            assertEquals(IndexTitle,title);
            assertEquals(IndexContent,content);
        }
    }
    /**
     * 测试博客删除模块
     */
    @Nested
    @Disabled
    public class DeleteTest{

        @Test
        void test()
        {

            Driver driver = new Driver();
            //先写入一篇文章
            Tool.pub_blog(driver,"测试","测试");
            //删除改文章
            List<WebElement> elements = driver.findElements("#app .el-main .el-main");
            WebElement el = elements.get(0);
            List<WebElement> lists = el.findElements(By.cssSelector("button"));
            for(int i = 0;i<lists.size();i++)
            {
                WebElement btn = lists.get(i);
                if(btn.getText().equals("删除文章"))
                {
                    btn.click();
                    driver.confirm();
                    break;
                }
            }
            //预期结果: 展台显示暂无内容
            WebElement h1 = driver.findElementByCss("h1");
            assertNotNull(h1);
            assertEquals(h1.getText(),"暂无内容");
        }
    }
    /**
     * 测试博客修改模块
     */
    @Nested
    @Disabled
    public class ModifyTest{
        @SneakyThrows
        @ParameterizedTest
        @CsvFileSource( files = "src/test/resources/TestSource/BlogDetail.csv")
        void test(String title,String content)
        {

            Driver driver = new Driver();
            //先写入一篇文章
            Tool.pub_blog(driver,"测试","测试");
            //修改文章
            List<WebElement> elements = driver.findElements("#app .el-main .el-main");
            WebElement el = elements.get(0);
            List<WebElement> lists = el.findElements(By.cssSelector("button"));
            for(int i = 0;i<lists.size();i++)
            {
                WebElement btn = lists.get(i);
                if(btn.getText().equals("修改文章"))
                {
                    btn.click();
                    break;
                }
            }
            sleep(100);
            //输入修改内容
            WebElement Title = driver.findElementByCss("#add_title");
            Actions actions = new Actions(driver.getWebDriver());
            //输入内容
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(content).perform();

            new Actions(driver.getWebDriver())
                    .click(Title)
                    .perform();
            //输入标题
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(title).perform();
            WebElement submitEl = driver.findElementByCss("#submit");
            submitEl.click();
            sleep(100);
            driver.reject();
            String CurUrl = driver.getUrl();
            sleep(500);
            //预期结果: 页面跳转到我的博客页面 且标题内容与修改相同
            String IndexTitle = driver.findElementByCss("#app > div > section > div.el-scrollbar > div.el-scrollbar__wrap.el-scrollbar__wrap--hidden-default > div > section > section > main > section > header:nth-child(1) > header:nth-child(1) > h2").getText();
            String IndexContent = driver.findElementByCss("#app > div > section > div.el-scrollbar > div.el-scrollbar__wrap.el-scrollbar__wrap--hidden-default > div > section > section > main > section > main > span").getText();
            assertEquals(CurUrl,PublicVariable.MyBlogUrl);
            assertEquals(IndexTitle,title);
            assertEquals(IndexContent,content);

        }
    }
    /**
     * 测试博客主页模块
     */
    @Nested

    public class IndexTest{
        @Test
        void test() {
            //插入多篇文章
            Tool.pub_blog(new Driver(), "测试", "测试");
            Tool.pub_blog(new Driver(), "测试", "测试");
            Driver driver  = new Driver();
            driver.MaxWindow();
            driver.get(PublicVariable.IndexUrl);
            WebElement pageNum = driver.findElementByCss("#app > div > section > div.pagination > div > ul > li");
            //预期结果 分页数目大于1
            assertNotEquals(Integer.parseInt(pageNum.getText()),1);
        }
    }

}

