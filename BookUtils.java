import java.io.BufferedReader; // Для построчного чтения файла
import java.io.BufferedWriter; // Для записи текста в файл
import java.io.FileReader;     // Для открытия файла на чтение
import java.io.FileWriter;     // Для открытия файла на запись
import java.io.IOException;    // Для обработки исключений
import java.util.ArrayList;    // Для использования списка объектов
import java.util.List;         // Для использования списка (интерфейс)
import java.util.Map;          // Для использования отображений (Map)
import java.util.stream.Collectors; // Для функциональных операций, таких как фильтрация и группировка

public class BookUtils {

    // Метод для чтения книг из текстового файла
    public static ArrayList<Book> readBooksFromFile(String fileName) throws IOException {
        ArrayList<Book> books = new ArrayList<>(); // Создаем список для хранения объектов книг
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { // Открываем файл с ресурсами
            String line; // Переменная для хранения текущей строки файла
            while ((line = reader.readLine()) != null) { // Читаем файл построчно до конца
                try {
                    // Считываем и парсим данные о книге
                    int index = Integer.parseInt(line.trim()); // Парсим индекс книги
                    String title = reader.readLine().trim(); // Считываем и очищаем название книги
                    String authors = reader.readLine().trim(); // Считываем и очищаем авторов
                    double averageRating = Double.parseDouble(reader.readLine().trim()); // Парсим средний рейтинг
                    String id = reader.readLine().trim(); // Считываем идентификатор книги
                    String isbn = reader.readLine().trim(); // Считываем ISBN
                    String languageCode = reader.readLine().trim(); // Считываем код языка
                    int pageCount = Integer.parseInt(reader.readLine().trim()); // Парсим количество страниц
                    int totalRatings = Integer.parseInt(reader.readLine().trim()); // Парсим общее количество оценок
                    int totalReviews = Integer.parseInt(reader.readLine().trim()); // Парсим общее количество рецензий

                    // Создаем объект книги и добавляем его в список
                    books.add(new Book(index, title, authors, averageRating, id, isbn, languageCode, pageCount, totalRatings, totalReviews));
                } catch (Exception e) {
                    // Обрабатываем ошибки при парсинге данных книги
                    System.err.println("Ошибка чтения данных книги: " + e.getMessage());
                }
            }
        }
        return books; // Возвращаем список книг
    }

    // Метод для обработки книг с несколькими соавторами
    public static void processBooksByCoauthors(ArrayList<Book> books) throws IOException {
        // Группируем книги по количеству соавторов
        Map<Integer, List<Book>> coauthorGroups = books.stream()
                .filter(book -> book.getAuthors().contains("/")) // Выбираем только книги с соавторами
                .collect(Collectors.groupingBy(book -> book.getAuthors().split("/").length)); // Группируем по количеству авторов

        // Проходимся по каждой группе
        for (Map.Entry<Integer, List<Book>> entry : coauthorGroups.entrySet()) {
            int coauthorCount = entry.getKey(); // Количество соавторов в текущей группе
            List<Book> groupBooks = entry.getValue(); // Список книг текущей группы

            String fileName = "coauthors_" + coauthorCount + ".txt"; // Формируем имя файла для группы
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { // Открываем файл для записи
                for (Book book : groupBooks) { // Проходимся по каждой книге в группе
                    String[] authors = book.getAuthors().split("/"); // Разделяем список авторов
                    String topAuthor = null; // Переменная для хранения автора с наивысшим рейтингом
                    double maxAverage = -1; // Переменная для хранения наивысшего среднего рейтинга

                    for (String author : authors) { // Для каждого автора книги
                        // Вычисляем среднее количество оценок для книг автора
                        double average = books.stream()
                                .filter(b -> b.getAuthors().contains(author)) // Выбираем книги, где указан данный автор
                                .mapToInt(Book::getTotalRatings) // Получаем общее количество оценок
                                .average() // Вычисляем среднее
                                .orElse(-1); // Если книг нет, возвращаем -1

                        // Проверяем, является ли текущий автор лидером по рейтингу
                        if (average > maxAverage) {
                            maxAverage = average; // Обновляем максимальный рейтинг
                            topAuthor = author.trim(); // Сохраняем имя автора
                        }
                    }

                    // Записываем результаты в файл
                    writer.write("Книга: " + book.getTitle() + "\n"); // Название книги
                    if (maxAverage > -1) { // Если у автора есть другие книги
                        writer.write("Автор с наибольшим рейтингом: " + topAuthor + " (Средний рейтинг: " + maxAverage + ")\n");
                    } else { // Если у соавторов нет других книг
                        writer.write("Других книг соавторов не найдено.\n");
                    }
                    writer.write("\n"); // Пустая строка для разделения записей
                }
            }
        }
    }
}