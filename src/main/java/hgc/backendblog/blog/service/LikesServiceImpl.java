package hgc.backendblog.blog.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import hgc.backendblog.User.DTO.UserFirebaseDTO;
import hgc.backendblog.blog.Entity.Blog;

@Service
public class LikesServiceImpl implements UserLikesDislikesService {

    private final Firestore firestore;

    @Autowired
    public LikesServiceImpl(FirebaseApp firebaseApp) {
        this.firestore = FirestoreClient.getFirestore(firebaseApp);
    }

    @Override
    public boolean likeBlog(String username, String blogId) {
        return updateLikesDislikes(username, blogId, true, false);
    }

    @Override
    public boolean unlikeBlog(String username, String blogId) {
        return updateLikesDislikes(username, blogId, false, false);
    }

    @Override
    public boolean dislikeBlog(String username, String blogId) {
        return updateLikesDislikes(username, blogId, false, true);
    }

    @Override
    public boolean undislikeBlog(String username, String blogId) {
        return updateLikesDislikes(username, blogId, false, false);
    }

    private boolean updateLikesDislikes(String username, String blogId, boolean like, boolean dislike) {
        try {
            // Update user document
            DocumentReference userRef = firestore.collection("users").document(username);
            ApiFuture<DocumentSnapshot> userFuture = userRef.get();
            DocumentSnapshot userDocument = userFuture.get();

            if (userDocument.exists()) {
                UserFirebaseDTO user = userDocument.toObject(UserFirebaseDTO.class);
                if (user != null) {
                    List<String> likedBlogs = user.getLikedBlogs();
                    List<String> dislikedBlogs = user.getDisLikedBlogs();

                    // Update liked/disliked blogs list based on like/dislike action
                    if (like) {
                        if (!likedBlogs.contains(blogId)) {
                            likedBlogs.add(blogId);
                        }
                        if (dislikedBlogs.contains(blogId)) {
                            dislikedBlogs.remove(blogId);
                        }
                    } else if (dislike) {
                        if (!dislikedBlogs.contains(blogId)) {
                            dislikedBlogs.add(blogId);
                        }
                        if (likedBlogs.contains(blogId)) {
                            likedBlogs.remove(blogId);
                        }
                    } else {
                        likedBlogs.remove(blogId);
                        dislikedBlogs.remove(blogId);
                    }

                    // Update the user document in Firestore
                    user.setLikedBlogs(likedBlogs);
                    user.setDisLikedBlogs(dislikedBlogs);
                    userRef.set(user).get(); // Ensure the write is completed
                }
            }

            // Update blog document
            DocumentReference blogRef = firestore.collection("blogs").document(blogId);
            ApiFuture<DocumentSnapshot> blogFuture = blogRef.get();
            DocumentSnapshot blogDocument = blogFuture.get();

            if (blogDocument.exists()) {
                Blog blog = blogDocument.toObject(Blog.class);
                if (blog != null) {
                    // Update like/dislike count based on like/dislike action
                    int likes = blog.getLikes();
                    int dislikes = blog.getDislikes();

                    if (like) {
                        likes++;
                    } else if (dislike) {
                        dislikes++;
                    } else {
                        likes--;
                        dislikes--;
                    }

                    // Update the blog document in Firestore
                    blogRef.update("likes", likes);
                    blogRef.update("dislikes", dislikes);
                }
            }

            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
