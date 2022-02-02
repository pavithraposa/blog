package com.example.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/api/v1/authors")

public class AuthorController {
    private AuthorService authorService;

    //Dependency Injection

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = new AuthorService();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<Author>list() {
        return authorService.getAuthors();
    }

    @RequestMapping(value = "create",method = RequestMethod.POST)
    public ResponseEntity<Author> createBlog(@RequestBody Author author) {
        Author newAuthor = authorService.createAuthor(author);

        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
    public ResponseEntity<Author> viewBlog(@PathVariable("id") int id) {

        Author getAuthor = authorService.getAuthorsById(id);
        return new ResponseEntity<Author>(getAuthor,HttpStatus.OK);
    }

    @RequestMapping(value = "update/{id}",method = RequestMethod.POST)
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") int id, @RequestBody Author authorChanges) {
        Author AuthorToUpdate=authorService.getAuthorsById(id);
        if (authorChanges.getName() != null) {
            AuthorToUpdate.setName(authorChanges.getName());
        }

        authorService.updateAuthorByID(id,AuthorToUpdate);
        return new ResponseEntity<Author>(AuthorToUpdate,HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ArrayList<Author> deleteAuthorbyId(@PathVariable("id") int id) {

        Author AuthorToDelete=authorService.getAuthorsById(id);
        authorService.authors.remove(AuthorToDelete);
        return authorService.authors;
    }

    @RequestMapping(value = "/clear",method = RequestMethod.DELETE)
    public void deleteAllAuthors() {

        authorService.authors.clear();
    }





    }
