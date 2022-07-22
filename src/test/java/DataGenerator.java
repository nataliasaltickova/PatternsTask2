import com.github.javafaker.Faker;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String getRandomLogin() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    public static String getRandomPassword() {
        Faker faker = new Faker();
        return faker.internet().password();
    }

    public static UserInfo generateUser(String status) {

        var userBlocked = new UserInfo(getRandomLogin(), getRandomPassword(), "blocked");
        return userBlocked;
    }

    public static UserInfo generateRegisteredUser(String status) {
        var user = new UserInfo(getRandomLogin(), getRandomPassword(), "active");
        return user;
    }
}





