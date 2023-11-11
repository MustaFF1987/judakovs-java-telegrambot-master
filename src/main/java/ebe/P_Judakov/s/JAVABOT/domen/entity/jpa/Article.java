package ebe.P_Judakov.s.JAVABOT.domen.entity.jpa;

public class Article {
    private int id;
    private String title;
    // Другие поля и методы

    public Article(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Article() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}