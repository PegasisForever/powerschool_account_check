import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.*
import java.util.logging.Level

fun main(args: Array<String>){
    val username=args[0]
    val password=args[1]
    val homePageUrl=if (args.size==3) args[2] else "https://powerschool.mapleleaf.cn/public/home.html"

    java.util.logging.Logger.getLogger("com.gargoylesoftware").level = Level.OFF;
    val webClient = WebClient()
    webClient.options.isCssEnabled = false
    webClient.options.isJavaScriptEnabled=true
    webClient.options.isRedirectEnabled = true
    webClient.options.isThrowExceptionOnScriptError=false

    val loginPage: HtmlPage = webClient.getPage(homePageUrl)

    val username_tv = loginPage.getElementById("fieldAccount") as HtmlTextInput
    val password_tv = loginPage.getElementById("fieldPassword") as HtmlPasswordInput
    username_tv.valueAttribute=username
    password_tv.valueAttribute=password

    val login_btn=loginPage.getElementById("btn-enter-sign-in") as HtmlButton
    val redPage=login_btn.click<HtmlPage>()

    if (redPage.titleText=="Grades and Attendance"){
        val nameH1= redPage.getElementsByTagName("h1")[0] as HtmlHeading1
        val rawNameText= nameH1.asText()
        val name = rawNameText.substring(23,rawNameText.indexOf(" - "))
        println(name)
    }else{
        println("error")
    }
}