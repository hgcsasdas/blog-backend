package hgc.backendblog.blog.Entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Blog {

    private String id;
    private String title;
    private String author;
    private String content;
    private Map<String, String> comments;
    private int likes;
    private int dislikes;
    private Date date;
}
