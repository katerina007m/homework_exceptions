import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserInputApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, "ibm866")) {
            System.out.println("Введите данные, разделенные пробелом: Фамилия Имя Отчество Дата_рождения Номер_телефона Пол");

            String input = scanner.nextLine();
            String[] inputData = input.split(" ");

            if(inputData.length != 6) {
                System.out.println("Ошибка: Неверное количество данных. Требуется ввести Фамилия Имя Отчество Дата_рождения Номер_телефона Пол");
            } else {
                String lastName = inputData[0];
                String firstName = inputData[1];
                String middleName = inputData[2];
                String dateOfBirth = inputData[3];
                long phoneNumber;
                char gender;

                try {
                    phoneNumber = Long.parseLong(inputData[4]);
                    if (!isValidPhoneNumber(phoneNumber)) {
                        throw new IllegalArgumentException("Неверный формат номера телефона (номер телефона состоит из 11 цифр и начинается с 7)");
                    }

                    gender = extractGender(inputData[5].charAt(0));
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    return;
                }

                String fileName = lastName + ".txt";
                String fileContent = lastName + " " + firstName + " " + middleName + " " + dateOfBirth + " " + phoneNumber + " " + gender + "\n";
                writeFile(fileName, fileContent);

                System.out.println("Данные успешно записаны в файл: " + fileName);
            }
        }
    }

    public static boolean isValidPhoneNumber(long phoneNumber) {
        String phoneNumberString = String.valueOf(phoneNumber);
        return phoneNumberString.matches("^7\\d{10}$");
    }

    public static char extractGender(char genderChar) {
        char gender = Character.toUpperCase(genderChar);
        if (gender == 'M') {
            return 'M';
        } else if (gender == 'F') {
            return 'F';
        } else {
            throw new IllegalArgumentException("Неверное значение пола. Допустимые значения: M (мужской), F (женский)");
        }
    }

    public static void writeFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Произошла ошибка ввода-вывода при записи в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }
}