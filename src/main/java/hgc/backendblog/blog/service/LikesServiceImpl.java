package hgc.backendblog.blog.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import hgc.backendblog.User.UserRepository;
import hgc.backendblog.User.DTO.UserFirebaseDTO;
import hgc.backendblog.blog.Entity.Blog;

@Service
public class LikesServiceImpl implements UserLikesDislikesService {

    private final Firestore firestore;
    private final UserRepository userRepository;

    @Autowired
    public LikesServiceImpl(FirebaseApp firebaseApp, UserRepository userRepository) {
        this.firestore = FirestoreClient.getFirestore(firebaseApp);
        this.userRepository = userRepository;
    }

    @Override
    public boolean likeBlog(String username, String blogId) {
        return updateLikesDislikes(username, blogId, true, false, false, false);
    }

    @Override
    public boolean unlikeBlog(String username, String blogId) {
        return updateLikesDislikes(username, blogId, false, false, true, false);
    }

    @Override
    public boolean dislikeBlog(String username, String blogId) {
        return updateLikesDislikes(username, blogId, false, true, false, false);
    }

    @Override
    public boolean undislikeBlog(String username, String blogId) {
        return updateLikesDislikes(username, blogId, false, false, false, true);
    }

    private boolean updateLikesDislikes(String username, String blogId, boolean like, boolean dislike, boolean unlike, boolean undislike) {
        try {
            // Obtener el ID del usuario
            Optional<String> userId = userRepository.findFirebaseIdByUsername(username);
            if (!userId.isPresent()) {
                // Manejar si el usuario no está presente
                return false;
            }

            // Referencia al documento del usuario
            DocumentReference userRef = firestore.collection("users").document(userId.get());
            ApiFuture<DocumentSnapshot> userFuture = userRef.get();
            DocumentSnapshot userDocument = userFuture.get();

            if (userDocument.exists()) {
                UserFirebaseDTO user = userDocument.toObject(UserFirebaseDTO.class);
                if (user != null) {
                    List<String> likedBlogs = user.getLikedBlogs();
                    List<String> dislikedBlogs = user.getDisLikedBlogs();

                    // Verificar si el blog ya está en likedBlogs o dislikedBlogs
                    boolean blogAlreadyLiked = likedBlogs.contains(blogId);
                    boolean blogAlreadyDisliked = dislikedBlogs.contains(blogId);

                    // Referencia al documento del blog
                    DocumentReference blogRef = firestore.collection("blogs").document(blogId);
                    ApiFuture<DocumentSnapshot> blogFuture = blogRef.get();
                    DocumentSnapshot blogDocument = blogFuture.get();

                    if (blogDocument.exists()) {
                        Blog blog = blogDocument.toObject(Blog.class);
                        int likes = blog.getLikes();
                        int dislikes = blog.getDislikes();

                        // Actualizar lista de liked/disliked blogs
                        if (like) {
                            if (!blogAlreadyLiked) {
                                likedBlogs.add(blogId);
                                likes++;
                                // Si el blog ya había sido dislikeado, restar un dislike
                                if (blogAlreadyDisliked) {
                                    dislikedBlogs.remove(blogId);
                                    dislikes--;
                                }
                            }
                        } else if (unlike) {
                            if (blogAlreadyLiked) {
                                likedBlogs.remove(blogId);
                                likes--;
                            }
                        } else if (dislike) {
                            if (!blogAlreadyDisliked) {
                                dislikedBlogs.add(blogId);
                                dislikes++;
                                // Si el blog ya había sido likeado, restar un like
                                if (blogAlreadyLiked) {
                                    likedBlogs.remove(blogId);
                                    likes--;
                                }
                            }
                        } else if (undislike) {
                            if (blogAlreadyDisliked) {
                                dislikedBlogs.remove(blogId);
                                dislikes--;
                            }
                        }

                        // Actualizar los conteos de likes y dislikes en el documento del blog
                        blogRef.update("likes", likes);
                        blogRef.update("dislikes", dislikes);
                    }

                    // Actualizar el documento del usuario en Firestore
                    userRef.update("likedBlogs", likedBlogs);
                    userRef.update("dislikedBlogs", dislikedBlogs);
                }
            }

            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

}
