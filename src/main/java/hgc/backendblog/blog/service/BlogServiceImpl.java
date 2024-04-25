package hgc.backendblog.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import hgc.backendblog.blog.DTO.BlogDto;
import hgc.backendblog.blog.DTO.CommentDto;
import hgc.backendblog.blog.Entity.Blog;

@Service
public class BlogServiceImpl implements BlogService {
    /*Firestore firestore = FirestoreClient.getFirestore();
    CollectionReference blogsCollection = firestore.collection("blogs");*/
	
    private final Firestore firestore;
    private final CollectionReference blogsCollection;

    @Autowired
    public BlogServiceImpl(FirebaseApp firebaseApp) {
        this.firestore = FirestoreClient.getFirestore(firebaseApp);
        this.blogsCollection = firestore.collection("blogs");
    }
	@Override
	public List<Blog> getAllBlogs() {
		ApiFuture<QuerySnapshot> querySnapshot = blogsCollection.get();
		List<Blog> blogs = new ArrayList<>();
		try {
			for (var document : querySnapshot.get().getDocuments()) {
				blogs.add(document.toObject(Blog.class));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blogs;
	}

	@Override
	public Blog getBlogById(String blogId) {
		DocumentReference docRef = blogsCollection.document(blogId);
		ApiFuture<DocumentSnapshot> documentSnapshot = docRef.get();
		DocumentSnapshot document;
		try {
			document = documentSnapshot.get();
			if (document.exists()) {
				return document.toObject(Blog.class);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Blog createBlog(BlogDto blogDto) {
		Blog blog = new Blog();
		blog.setTitle(blogDto.getTitle());
		blog.setAuthor(blogDto.getAuthor());
		blog.setContent(blogDto.getContent());
		return save(blog);
	}

	@Override
	public Blog updateBlog(String blogId, BlogDto blogDto) {
		Blog existingBlog;
		try {
			existingBlog = findById(blogId);
			if (existingBlog != null) {
				existingBlog.setTitle(blogDto.getTitle());
				existingBlog.setAuthor(blogDto.getAuthor());
				existingBlog.setContent(blogDto.getContent());
				// Actualizar otros campos según la estructura de Firebase
				return save(existingBlog);
			}
		} catch (ExecutionException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void deleteBlog(String blogId) {
        blogsCollection.document(blogId).delete();
	}

	@Override
	public Blog addCommentToBlog(String blogId, CommentDto commentDto) {
		// Lógica para añadir un comentario al blog
		return null;
	}
	
	public Blog save(Blog blog) {
        DocumentReference docRef = blogsCollection.document();
        blog.setId(docRef.getId());
        docRef.set(blog);
        return blog;
    }
	 public Blog findById(String blogId) throws ExecutionException, InterruptedException {
	        DocumentReference docRef = blogsCollection.document(blogId);
	        ApiFuture<DocumentSnapshot> documentSnapshot = docRef.get();
	        DocumentSnapshot document = documentSnapshot.get();
	        if (document.exists()) {
	            return document.toObject(Blog.class);
	        }
	        return null;
	    }
}
