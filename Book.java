public class Book {
    private int index;
    private String title;
    private String authors;
    private double averageRating;
    private String id;
    private String isbn;
    private String languageCode;
    private int pageCount;
    private int totalRatings;
    private int totalReviews;

    public Book(int index, String title, String authors, double averageRating, String id, String isbn,
                String languageCode, int pageCount, int totalRatings, int totalReviews) {
        this.index = index;
        this.title = title;
        this.authors = authors;
        this.averageRating = averageRating;
        this.id = id;
        this.isbn = isbn;
        this.languageCode = languageCode;
        this.pageCount = pageCount;
        this.totalRatings = totalRatings;
        this.totalReviews = totalReviews;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public String toString() {
        return String.format("Index: %d, Title: \"%s\", Authors: %s, AvgRating: %.2f, Pages: %d, Ratings: %d, Reviews: %d",
                index, title, authors, averageRating, pageCount, totalRatings, totalReviews);
    }
}