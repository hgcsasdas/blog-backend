package hgc.backendblog.blog.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hgcBackendBlogs/users/outer/api/blogs/")
//Reemplazar con url de despliegue del frontend
@CrossOrigin(origins = { "http://localhost:4200" })
@RequiredArgsConstructor
public class BlogUserOuterController {

}
