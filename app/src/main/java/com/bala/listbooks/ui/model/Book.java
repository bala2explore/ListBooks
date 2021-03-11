package com.bala.listbooks.ui.model;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private String contributor;
    private String description;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getContributor() {
        return contributor;
    }

    public String getDescription() {
        return description;
    }

    public static class BookBuilder {
        private String title;
        private String author;
        private String publisher;
        private String contributor;
        private String description;

        public BookBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder setPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder setContributor(String contributor) {
            this.contributor = contributor;
            return this;
        }

        public BookBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Book build(){
            Book book = new Book();
            book.title = this.title;
            book.author = this.author;
            book.publisher = this.publisher;
            book.contributor = this.contributor;
            book.description = this.description;
            return book;
        }
    }
}
