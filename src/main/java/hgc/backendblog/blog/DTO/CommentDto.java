package hgc.backendblog.blog.DTO;

public class CommentDto {

    private String commenterName;
    private String commentText;

    public CommentDto() {
    }

    public CommentDto(String commenterName, String commentText) {
        this.commenterName = commenterName;
        this.commentText = commentText;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "commenterName='" + commenterName + '\'' +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
