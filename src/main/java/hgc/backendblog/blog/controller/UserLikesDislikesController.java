package hgc.backendblog.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hgc.backendblog.blog.Requests.UserBlogRequestDTO;
import hgc.backendblog.blog.service.UserLikesDislikesService;

@RestController
@RequestMapping("/users")
public class UserLikesDislikesController {

    private final UserLikesDislikesService userLikesDislikesService;

    @Autowired
    public UserLikesDislikesController(UserLikesDislikesService userLikesDislikesService) {
        this.userLikesDislikesService = userLikesDislikesService;
    }

    @PostMapping("/like")
    public ResponseEntity<String> likeBlog(@RequestBody UserBlogRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String blogId = requestDTO.getBlogId();
        boolean success = userLikesDislikesService.likeBlog(username, blogId);
        if (success) {
            return ResponseEntity.ok("Like añadido correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al añadir el like");
        }
    }

    @PostMapping("/unlike")
    public ResponseEntity<String> unlikeBlog(@RequestBody UserBlogRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String blogId = requestDTO.getBlogId();
        boolean success = userLikesDislikesService.unlikeBlog(username, blogId);
        if (success) {
            return ResponseEntity.ok("Like eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el like");
        }
    }

    @PostMapping("/dislike")
    public ResponseEntity<String> dislikeBlog(@RequestBody UserBlogRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String blogId = requestDTO.getBlogId();
        boolean success = userLikesDislikesService.dislikeBlog(username, blogId);
        if (success) {
            return ResponseEntity.ok("Dislike añadido correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al añadir el dislike");
        }
    }

    @PostMapping("/undislike")
    public ResponseEntity<String> undislikeBlog(@RequestBody UserBlogRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String blogId = requestDTO.getBlogId();
        boolean success = userLikesDislikesService.undislikeBlog(username, blogId);
        if (success) {
            return ResponseEntity.ok("Dislike eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el dislike");
        }
    }
}
