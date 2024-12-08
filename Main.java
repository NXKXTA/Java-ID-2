import java.io.IOException; // Для обработки исключений
import java.util.ArrayList; // Для использования списка объектов
import java.util.Scanner;   // Для ручного ввода

public class Main {
    public static void main(String[] args) {
        try {
            // Загрузка данных из файла
            ArrayList<Book> books = BookUtils.readBooksFromFile("C:\\Фигня всякая\\Java ID2\\src\\data_book.txt");

            // Вывод всех книг
            System.out.println("Все книги:");
            books.forEach(System.out::println);

            Scanner scanner = new Scanner(System.in);

            // Задача 1: Найти книгу по названию
            System.out.println("\nВведите название книги:");
            String userInput = scanner.nextLine().trim();

            boolean found = false;
            for (Book book : books) {
                // Нормализуем названия (убираем лишние пробелы и приводим к нижнему регистру)
                String normalizedTitle = book.getTitle().replaceAll("\\s+", " ").trim().toLowerCase();
                String normalizedInput = userInput.replaceAll("\\s+", " ").trim().toLowerCase();

                if (normalizedTitle.equals(normalizedInput)) {
                    System.out.println("Книга найдена: " + book);

                    // Поиск остальных книг первого автора
                    String[] authors = book.getAuthors().split("/");
                    String mainAuthor = authors[0].trim();

                    System.out.println("\nДругие книги автора \"" + mainAuthor + "\":");
                    books.stream()
                            .filter(b -> b.getAuthors().contains(mainAuthor) && !b.getTitle().equals(book.getTitle()))
                            .forEach(System.out::println);

                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Книга не найдена.");
            }

            // Задача 2: Вывести 10 самых тонких и 10 самых толстых книг
            System.out.println("\n10 самых тонких книг:");
            books.stream()
                    .sorted((b1, b2) -> Integer.compare(b1.getPageCount(), b2.getPageCount()))
                    .limit(10)
                    .forEach(System.out::println);

            System.out.println("\n10 самых толстых книг:");
            books.stream()
                    .sorted((b1, b2) -> Integer.compare(b2.getPageCount(), b1.getPageCount()))
                    .limit(10)
                    .forEach(System.out::println);

            // Задача 3: Списки книг с несколькими соавторами и обработка
            BookUtils.processBooksByCoauthors(books);

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}