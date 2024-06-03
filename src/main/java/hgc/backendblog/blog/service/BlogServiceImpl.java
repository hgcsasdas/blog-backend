package hgc.backendblog.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import hgc.backendblog.User.UserRepository;
import hgc.backendblog.User.Entitys.User;
import hgc.backendblog.blog.DTO.BlogDto;
import hgc.backendblog.blog.DTO.CommentDto;
import hgc.backendblog.blog.Entity.Blog;
import hgc.backendblog.blog.Responses.BlogCUDResponse;

@Service
public class BlogServiceImpl implements BlogService {

	private final Firestore firestore;
	private final CollectionReference blogsCollection;
	private final CollectionReference usersCollection;
	private final UserRepository userRepository;

	@Autowired
	public BlogServiceImpl(FirebaseApp firebaseApp, UserRepository userRepository) {
		this.firestore = FirestoreClient.getFirestore(firebaseApp);
		this.blogsCollection = firestore.collection("blogs");
		this.usersCollection = firestore.collection("users");
		this.userRepository = userRepository;
	}

	/**
	 * Obtiene todos los blogs almacenados en Firestore.
	 * 
	 * @return Una lista de objetos Blog.
	 */
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

	/**
	 * Obtiene un blog por su ID.
	 * 
	 * @param blogId El ID del blog a buscar.
	 * @return El objeto Blog correspondiente al ID especificado.
	 */
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

	/**
	 * Crea un nuevo blog en Firestore y lo asocia al autor correspondiente.
	 * 
	 * @param blogDto Los datos del blog a crear.
	 * @return Un objeto BlogCUDResponse que indica si se creó el blog
	 *         correctamente.
	 */
	@Override
	public BlogCUDResponse createBlog(BlogDto blogDto) {

		BlogCUDResponse blogCUDResponse = new BlogCUDResponse();

		String authorName = blogDto.getAuthor();
		String userId = getUserIdByAuthorName(authorName);

		// Verificar si el usuario tiene blogs disponibles
		Optional<Integer> blogEntries = userRepository.findBlogEntriesByUsername(authorName);
		if (blogEntries.isPresent() && blogEntries.get() > 0) {
			Blog blog = new Blog();
			blog.setTitle(blogDto.getTitle());
			blog.setAuthor(blogDto.getAuthor());
			blog.setContent(blogDto.getContent());

			// Añadir el id del blog al usuario en firebase
			Blog savedBlog = save(blog);
			addUserBlog(userId, savedBlog.getId());

			// Restar uno a la cantidad de blogs disponibles para el usuario
			userRepository.updateBlogEntriesByUsername(authorName, blogEntries.get() - 1);

			blogCUDResponse.setMessage("Blog guardado correctamente");
			blogCUDResponse.setDone(true);
			return blogCUDResponse;
		} else {
			blogCUDResponse.setMessage("El usuario no tiene blogs disponibles para crear uno nuevo");
			return blogCUDResponse;
		}
	}

	/**
	 * Actualiza un blog existente en Firestore.
	 * 
	 * @param blogId  El ID del blog a actualizar.
	 * @param blogDto Los nuevos datos del blog.
	 * @return Un objeto BlogCUDResponse que indica si se actualizó el blog
	 *         correctamente.
	 */
	@Override
	public BlogCUDResponse updateBlog(String blogId, BlogDto blogDto) {
		BlogCUDResponse blogCUDResponse = new BlogCUDResponse();

		Blog existingBlog;
		try {
			existingBlog = findById(blogId);
			if (existingBlog != null) {
				existingBlog.setTitle(blogDto.getTitle());
				existingBlog.setContent(blogDto.getContent());

				// Actualizar el documento existente en Firestore
				update(existingBlog, blogId); // Utiliza el ID existente

				blogCUDResponse.setMessage("Blog actualizado correctamente");
				blogCUDResponse.setDone(true);
				return blogCUDResponse;
			}
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void update(Blog blog, String blogId) {
		DocumentReference docRef = blogsCollection.document(blogId);
		docRef.set(blog);
	}

	/**
	 * Elimina un blog existente en Firestore.
	 * 
	 * @param blogId El ID del blog a eliminar.
	 * @return Un objeto BlogCUDResponse que indica si se eliminó el blog
	 *         correctamente.
	 */
	@Override
	public BlogCUDResponse deleteBlog(String blogId) {
		BlogCUDResponse blogCUDResponse = new BlogCUDResponse();

		try {
			Blog blogToDelete = getBlogById(blogId);
			if (blogToDelete != null) {
				// Eliminar el blog de la colección de blogs
				blogsCollection.document(blogId).delete();

				// Obtener el autor del blog
				String authorName = blogToDelete.getAuthor();
				String userId = getUserIdByAuthorName(authorName);

				// Actualizar la información del usuario
				if (userId != null) {
					removeUserBlog(userId, blogId);
					updateBlogEntriesForUser(authorName);
				}

				blogCUDResponse.setMessage("Blog eliminado correctamente");
				blogCUDResponse.setDone(true);
			} else {
				blogCUDResponse.setMessage("El blog no existe");
			}
		} catch (Exception e) {
			blogCUDResponse.setMessage("Error al eliminar el blog: " + e.getMessage());
			e.printStackTrace();
		}

		return blogCUDResponse;
	}

	private void removeUserBlog(String userId, String blogId) {
		try {
			// Obtener el documento del usuario
			DocumentReference userRef = usersCollection.document(userId);
			ApiFuture<DocumentSnapshot> userSnapshot = userRef.get();
			DocumentSnapshot userDocument = userSnapshot.get();

			// Obtener el array actual de blogs del usuario
			List<String> userBlogs = (List<String>) userDocument.get("blogs");

			// Remover el ID del blog del array de blogs del usuario
			userBlogs.remove(blogId);

			// Actualizar el documento del usuario con el nuevo array de blogs
			userRef.update("blogs", userBlogs);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void updateBlogEntriesForUser(String authorName) {
		try {
			// Obtener la cantidad actual de blogs disponibles para el usuario
			Optional<Integer> blogEntries = userRepository.findBlogEntriesByUsername(authorName);

			// Si hay blogs disponibles, incrementar en uno la cantidad
			if (blogEntries.isPresent()) {
				int newBlogEntries = blogEntries.get() + 1;
				userRepository.updateBlogEntriesByUsername(authorName, newBlogEntries);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

			Optional<String> userFirebaseIdFromSQLDb = userRepository.findFirebaseIdByUsername(authorName);

			if (userFirebaseIdFromSQLDb.isPresent()) {
				return userFirebaseIdFromSQLDb.get();
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return null;

		}
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

	@Override
	public List<Blog> getBlogsByUser(String username) {
	    List<Blog> blogs = new ArrayList<>();
	    try {
	        // Obtener el usuario por nombre de usuario
	        Optional<User> userOpt = userRepository.findByUsername(username);

	        if (userOpt.isPresent()) {
	            String firebaseId = userOpt.get().getFirebaseId();

	            // Obtener el documento del usuario en Firestore
	            DocumentReference userRef = usersCollection.document(firebaseId);
	            DocumentSnapshot userDocument = userRef.get().get();

	            if (userDocument.exists()) {
	                // Obtener el array de blogs del usuario
	                List<String> userBlogs = (List<String>) userDocument.get("blogs");

	                // Obtener cada blog por su ID y añadirlo a la lista de blogs
	                for (String blogId : userBlogs) {
	                    Blog blog = getBlogById(blogId);
	                    if (blog != null) {
	                        blogs.add(blog);
	                    }
	                }
	            }
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	        Thread.currentThread().interrupt(); // Re-interrupt the thread
	    }
	    return blogs;
	}

}
