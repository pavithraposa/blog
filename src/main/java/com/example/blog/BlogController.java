package com.example.blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value="/api/v1/blog/")
public class BlogController {
    ArrayList<Blog> myBlogs;
    private Logger logger;
    int latestId;

    public BlogController(){
        myBlogs= new ArrayList<>();
        latestId=0;
        logger = LoggerFactory.getLogger(BlogController.class);

    }

    //CRUD - Create
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        if(blog.getTitle()==""){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else{

            latestId++;
            blog.setId(latestId);
            myBlogs.add(blog);
            System.out.println("The new Blog has been added with the Title: ' " + blog.getTitle() + " ' ");
            //return blog;
            logger.info("The new Blog has been added with the Title: '  " +blog.getTitle() + " ' ");
            return new ResponseEntity<Blog>(blog, HttpStatus.CREATED);

        }


    }

    //CRUD - Read
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ArrayList<Blog> listBlog() {

        if(myBlogs.size() == 0){
            logger.warn(" The list is Empty");
            //return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {

            System.out.println("The list of Blogs are as follows.");
            logger.info("The list of blogs are displayed successfully");

        }
        return myBlogs;
    }

    //CRUD - Read
    @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
    public ResponseEntity<Blog> viewBlog(@PathVariable ("id") int id) {
        System.out.println("Getting Blog details with id: " +id);
        //return getBlogById(id);
        Blog getBlog = getBlogById(id);
        if(getBlog ==null){
            logger.warn("The List is Empty.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        else if(getBlogById(id) == null) {
            logger.warn("Could not find Blog with ID : " + id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {

            logger.info("view Blog: " + getBlog.getTitle());
            return new ResponseEntity<Blog>(getBlog, HttpStatus.OK);

        }

    }

    //CRUD - Update
    @RequestMapping(value = "update/{id}",method = RequestMethod.POST)
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") int id, @RequestBody Blog blogChanges) {
        System.out.println("Updating Blog with Id :" + id);
        Blog blogToUpdate=getBlogById(id);

        if(blogToUpdate==null) {
            logger.warn("The BlogList is Empty");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        else if(getBlogById(id) == null) {
            logger.warn("Could not find Blog with ID : " + id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {

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
            logger.info("Blog ' " + blogChanges.getTitle() + " ' is Updated Sucessfully :) ");
            //return blogToUpdate;
            return new ResponseEntity<Blog>(blogToUpdate,HttpStatus.OK);
        }

    }

    //CRUD - Delete
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ArrayList<Blog> deleteBlogbyId(@PathVariable("id") int id) {

        System.out.println("Getting Blog details with id :" + id);
        Blog blogToDelete=getBlogById(id);
        if(blogToDelete==null) {

            logger.warn("The List is Empty.");

        } else if(getBlogById(id) == null) {

            logger.warn("Could not find Blog with ID : " + id);
        } else {

            myBlogs.remove(blogToDelete);
            System.out.println("The Blog with id : ' " + id + " ' is successfully Deleted.");
            logger.info("The Blog is deleted successfully. ");
        }

            return myBlogs;
    }

    //CRUD - Delete
    @RequestMapping(value = "/clear",method = RequestMethod.DELETE)
    public void deleteAllBlogs() {

        myBlogs.clear();
        System.out.println("The Blog list is cleared!!!");
        logger.info("The list is Cleared!!");

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
