package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.Tag;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired(required = true)
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    // This controller method is called when the request pattern is of type
    // 'addComment' and also the incoming request is of POST Type
    // The method calls the createComment() method in the business logic passing the
    // comments, image id, image title to be add comment
    // Looks for a controller method with mapping of type
    // '/image/{imageId}/{imageTitle}'
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@RequestParam("comment") String commentText, @PathVariable("imageId") Integer imageId,
                             @PathVariable("imageTitle") String imageTitle, Model model, HttpSession session,
                             final RedirectAttributes redirectAttributes)  throws IOException, IndexOutOfBoundsException, ArrayIndexOutOfBoundsException{
        Image image = imageService.getImage(imageId);
        String tags = convertTagsToString(image.getTags());
        User loggedInuser = (User) session.getAttribute("loggeduser");
        Comment comment = new Comment();
        comment.setImage(image);
        comment.setUser(loggedInuser);
        comment.setText(commentText);
        comment.setCreatedDate(new Date());
        commentService.createComment(comment);

        // Boolean isLoggedUSer = userSameAsLoggedInUser(image.getUser(), session);
        model.addAttribute("image", image);
        model.addAttribute("tags", tags);

        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }

    // The method receives the list of all tags
    // Converts the list of all tags to a single string containing all the tags
    // separated by a comma
    // Returns the string
    private String convertTagsToString(List<Tag> tags) {
        StringBuilder tagString = new StringBuilder();
        if (tags.size() > 0) {
            for (int i = 0; i <= tags.size() - 2; i++) {
                tagString.append(tags.get(i).getName()).append(",");
            }

            Tag lastTag = tags.get(tags.size() - 1);
            tagString.append(lastTag.getName());
            return tagString.toString();
        } else {
            return "";
        }

    }

}