import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

class AuthTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        UserInfo userActive = DataGenerator.generateRegisteredUser("active");
        Registration.registration(userActive);

        $("[data-test-id=\"login\"] input").val(userActive.getLogin());
        $("[data-test-id=\"password\"] input").val(userActive.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $x(".//h2").shouldBe(Condition.text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        UserInfo userNotRegistered = DataGenerator.generateRegisteredUser("active");

        $("[data-test-id=\"login\"] input").val(userNotRegistered.getLogin());
        $("[data-test-id=\"password\"] input").val(userNotRegistered.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("[class=\"notification__title\"]").shouldBe(Condition.text("Ошибка"));
        $("[class=\"notification__content\"]").shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        UserInfo userBlocked = DataGenerator.generateUser("blocked");
        Registration.registration(userBlocked);

        $("[data-test-id=\"login\"] input").val(userBlocked.getLogin());
        $("[data-test-id=\"password\"] input").val(userBlocked.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("[class=\"notification__title\"]").shouldBe(Condition.text("Ошибка"));
        $("[class=\"notification__content\"]").shouldBe(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        UserInfo userActive = DataGenerator.generateRegisteredUser("active");
        Registration.registration(userActive);
        var wrongLogin = DataGenerator.getRandomLogin();

        $("[data-test-id=\"login\"] input").setValue(wrongLogin);
        $("[data-test-id=\"password\"] input").val(userActive.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("[class=\"notification__title\"]").shouldBe(Condition.text("Ошибка"));
        $("[class=\"notification__content\"]").shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        UserInfo userActive = DataGenerator.generateUser("active");
        Registration.registration(userActive);
        var wrongPassword = DataGenerator.getRandomPassword();

        $("[data-test-id=\"login\"] input").val(userActive.getLogin());
        $("[data-test-id=\"password\"] input").setValue(wrongPassword);
        $("[data-test-id=\"action-login\"]").click();
        $("[class=\"notification__title\"]").shouldBe(Condition.text("Ошибка"));
        $("[class=\"notification__content\"]").shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}
