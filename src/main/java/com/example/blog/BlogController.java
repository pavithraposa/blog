package com.example.blog;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value="/api/v1/blog/")
public class BlogController {
    ArrayList<Blog> myBlogs;
    int latestId;

    public BlogController(){
        myBlogs= new ArrayList<>();
        latestId=0;

    }

    //CRUD - Create
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public Blog createBlog(@RequestBody Blog blog) {
        latestId++;
        blog.setId(latestId);
        myBlogs.add(blog);
        System.out.println("The new Blog has been added.");
        return blog;

    }

    //CRUD - Read
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ArrayList<Blog> listBlog() {
        System.out.println("The list of Blogs are as follows.");
        return myBlogs;

    }

    //CRUD - Read
    @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
    public Blog viewBlog(@PathVariable ("id") int id) {
        System.out.println("Getting Blog details with id: " +id);
        return getBlogById(id);

    }

    //CRUD - Update
    @RequestMapping(value = "update/{id}",method = RequestMethod.PATCH )
    public Blog updateBlog(@PathVariable("id") int id, @RequestBody Blog blogChanges) {
        System.out.println("Updating Blog with Id :" + id);
        Blog blogToUpdate=getBlogById(id);

        if (blogChanges.getTitle() != null) {
            blogToUpdate.setTitle(blogChanges.getTitle());
        }
        if (blogChanges.getDescription() != null) {
            blogToUpdate.setDescription(blogChanges.getDescription());
        }
        if (blogChanges.getAuthor() != null) {
            blogToUpdate.setAuthor(blogChanges.getAuthor());
        }

        updateMovieByID(id, blogToUpdate);
        System.out.println("The Blog with id :" + id + "has been updated successfully.");
        return blogToUpdate;
    }

    //CRUD - Delete
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ArrayList<Blog> deleteBlogbyId(@PathVariable("id") int id) {

        System.out.println("Getting Blog details with id :" + id);
        Blog blogToDelete=getBlogById(id);
        myBlogs.remove(blogToDelete);
        System.out.println("The Blog with id : ' " + id + " ' is successfully Deleted.");
        return myBlogs;

    }

    //CRUD - Delete
    @RequestMapping(value = "/clear",method = RequestMethod.DELETE)
    public void deleteAllBlogs() {

        myBlogs.clear();
        System.out.println("The Blog list is cleared!!!");

    }



    private Blog getBlogById(int id) {
        for (Blog currentBlog : myBlogs) {
            if (currentBlog.getId() == id) {
                return currentBlog;
            }
        }

        return new Blog();
    }

    private Blog updateMovieByID(int id, Blog updatedBlog) {
        for (int i = 0; i < myBlogs.size(); i++) {
            Blog currentMovie = myBlogs.get(i);
            if (currentMovie.getId() == id) {
                myBlogs.set(i, updatedBlog);
                return myBlogs.get(i);
            }
        }
        return new Blog();
    }

}
