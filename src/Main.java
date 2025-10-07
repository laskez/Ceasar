import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.println("=== Шифр Цезаря ===");

        try {
            System.out.println("Выберите режим:");
            System.out.println("1) Шифрование файла");
            System.out.println("2) Расшифровка файла");
            System.out.print("Ваш выбор: ");
            String choice = sc.nextLine().trim();

            System.out.print("Введите путь к исходному файлу: ");
            Path in = Paths.get(sc.nextLine().trim());
            if (!Files.exists(in) || !Files.isReadable(in)) {
                System.out.println("Файл не существует или недоступен для чтения: " + in);
                return;
            }

            System.out.print("Введите путь к выходному файлу: ");
            Path out = Paths.get(sc.nextLine().trim());
            try {
                Path parent = out.toAbsolutePath().getParent();
                if (parent != null && !Files.exists(parent)) {
                    Files.createDirectories(parent);
                }
            } catch (IOException e) {
                System.out.println("Не удалось создать директории: " + e.getMessage());
                return;
            }

            System.out.print("Введите ключ: ");
            int key;
            try {
                key = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ключ должен быть целым числом.");
                return;
            }

            if ("1".equals(choice)) {
                process(in, out, key);
                System.out.println("Готово. Зашифровано в: " + out);
            } else if ("2".equals(choice)) {
                process(in, out, -key);
                System.out.println("Готово. Расшифровано в: " + out);
            } else {
                System.out.println("Некорректный выбор режима.");
            }

        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }

    private static void process(Path in, Path out, int key) {
        try (BufferedReader br = Files.newBufferedReader(in, StandardCharsets.UTF_8);
             BufferedWriter bw = Files.newBufferedWriter(out, StandardCharsets.UTF_8,
                     StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {

            String line;
            while ((line = br.readLine()) != null) {
                bw.write(Caesar.shiftText(line, key));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении/записи: " + e.getMessage());
        }
    }
}
