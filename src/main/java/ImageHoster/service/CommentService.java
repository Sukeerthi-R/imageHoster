package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //The method calls the createComment() method in the Repository and passes the comment to be persisted in the database
    public Comment createComment(Comment comment) {
        return commentRepository.createComment(comment);
    }

    //Call the getAllComments() method in the Repository and obtain a List of all the Comments in the database based on image id
    public List<Comment> getAllComments(Integer imageId, String imageTitle) {
        return commentRepository.getAllComments(imageId, imageTitle);
    }

}