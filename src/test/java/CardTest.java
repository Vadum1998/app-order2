import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {


    public String dateOfCreation(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    public void openForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");

    }

    @Test
    void shouldValid() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input [@placeholder=\"Город\"]").val("Воронеж");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Дубровин Вадим");
        $x("//input[@name=\"phone\"]").val("+79012345678");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateOfMeet), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        //$x("//*[contains(text(),\"Встреча успешно забронирована на \")]").should(visible, Duration.ofSeconds(15));
        //$(withText("Встреча успешно забронирована на")).should(visible);

        //visable чтобы видели невидимый элемент
        //Duration.ofSeconds задержка

    }

    @Test
    void shouldValid2() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input [@placeholder=\"Город\"]").val("Воронеж");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Дубровин-Вадим");
        $x("//input[@name=\"phone\"]").val("+79012345678");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateOfMeet), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldBNoValid() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Dubrovin Vadim");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[(text()=\"Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.\")]").should(visible, Duration.ofSeconds(15));
    }
}





