package com.example.blog;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorService {
    ArrayList<Author> authors;
    int latestAuthorID;

    public AuthorService() {
        authors = new ArrayList<>();
        latestAuthorID=0;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public Author createAuthor(Author newAuthor) {
        latestAuthorID++;
        newAuthor.setId(latestAuthorID);
        authors.add(newAuthor);
        return newAuthor;
    }

    public Author updateAuthorByID(int id, Author updatedAuthor) {
        for (int i = 0; i < authors.size(); i++) {
            Author currentAuthor = authors.get(i);
            if (currentAuthor.getId() == id) {
                authors.set(i, updatedAuthor);
                return authors.get(i);
            }
        }
        return new Author();
    }

    public Author getAuthorsById(int id) {

        for(Author currentAuthor: authors) {
            if (currentAuthor.getId() == id) {
                return currentAuthor;
            }
        }

        return new Author();
    }
}
