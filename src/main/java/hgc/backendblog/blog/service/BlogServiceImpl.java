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

	private final Firestore firestore;
	private final CollectionReference blogsCollection;
	private final CollectionReference usersCollection;

	@Autowired
	public BlogServiceImpl(FirebaseApp firebaseApp) {
		this.firestore = FirestoreClient.getFirestore(firebaseApp);
		this.blogsCollection = firestore.collection("blogs");
		this.usersCollection = firestore.collection("users");
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

		Blog savedBlog = save(blog);

		String authorName = blogDto.getAuthor();
		String userId = getUserIdByAuthorName(authorName);


		if (userId != null) {
			addUserBlog(userId, savedBlog.getId());
		}

		return savedBlog;
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

	private String getUserIdByAuthorName(String authorName) {
		try {
			// Realizar una consulta para encontrar al usuario por su nombre
			ApiFuture<QuerySnapshot> querySnapshot = usersCollection.whereEqualTo("name", authorName).get();

			DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
			if (document.exists()) {
				return document.getId(); // Devolver el ID del usuario
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null; // Si no se encuentra el usuario, devolver null
	}

	private void addUserBlog(String userId, String blogId) {
		try {
			// Obtener el documento del usuario
			DocumentReference userRef = usersCollection.document(userId);
			ApiFuture<DocumentSnapshot> userSnapshot = userRef.get();
			DocumentSnapshot userDocument = userSnapshot.get();

			// Obtener el array actual de blogs del usuario
			List<String> userBlogs = (List<String>) userDocument.get("blogs");

			// Agregar el ID del nuevo blog al array de blogs del usuario
			userBlogs.add(blogId);

			// Actualizar el documento del usuario con el nuevo array de blogs
			userRef.update("blogs", userBlogs);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
