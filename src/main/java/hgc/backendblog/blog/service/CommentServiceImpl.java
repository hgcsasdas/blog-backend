package hgc.backendblog.blog.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import hgc.backendblog.Jwt.JwtService;
import hgc.backendblog.User.UserRepository;
import hgc.backendblog.blog.DTO.CommentDeleteDto;
import hgc.backendblog.blog.DTO.CommentDto;
import hgc.backendblog.blog.DTO.CommentUpdateDto;
import hgc.backendblog.blog.Entity.Blog;
import hgc.backendblog.blog.Entity.Comment;
import hgc.backendblog.blog.Responses.CommentCUDResponse;
import hgc.backendblog.Jwt.JwtService;

@Service
public class CommentServiceImpl implements CommentService {

	private final Firestore firestore;
	private final CollectionReference commentsCollection;
	private final UserRepository userRepository;
	private final JwtService jwtService;

	@Autowired
	public CommentServiceImpl(FirebaseApp firebaseApp, UserRepository userRepository,  JwtService jwtService) {
		this.firestore = FirestoreClient.getFirestore(firebaseApp);
		this.commentsCollection = firestore.collection("comments");
		this.userRepository = userRepository;
		this.jwtService = jwtService;

	}

	@Override
	public CommentCUDResponse addCommentToBlog(String blogId, CommentDto commentDto) {
		CommentCUDResponse response = new CommentCUDResponse();
		String token = jwtService.refreshToken(commentDto.getToken());
		response.setToken(token);
		
		// Verificar si el autor del comentario existe
		String authorName = commentDto.getAuthor();
		boolean isAuthor = userRepository.existsByUsername(authorName);

		if (!isAuthor) {
			response.setMessage("El autor del comentario no existe");
			return response;
		}

		// Obtener el blog
		DocumentReference blogRef = firestore.collection("blogs").document(blogId);
		DocumentSnapshot blogSnapshot;
		try {
			blogSnapshot = blogRef.get().get();
			if (!blogSnapshot.exists()) {
				response.setMessage("El blog no existe");
				return response;
			}

			// Obtener el mapa de comentarios del blog
			Blog blog = blogSnapshot.toObject(Blog.class);
			Map<String, Comment> commentsMap = blog.getComments();

			// Si el mapa de comentarios es null, inicializarlo como un nuevo HashMap
			if (commentsMap == null) {
				commentsMap = new HashMap<>();
			}

			// Crear el comentario
			Comment comment = new Comment();
			comment.setAuthor(authorName);
			comment.setContent(commentDto.getContent());

			// Generar un ID único para el comentario
			String commentId = UUID.randomUUID().toString();
			comment.setId(commentId);
			comment.setBlogId(blogId); // Asignar el ID del blog al comentario

			// Agregar el comentario al mapa de comentarios
			commentsMap.put(commentId, comment);

			// Actualizar el blog en Firestore
			blogRef.update("comments", commentsMap);

			response.setDone(true);
			response.setMessage("Comentario agregado correctamente");
			return response;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			response.setMessage("Error al agregar el comentario al blog");
			return response;
		}
	}

	@Override
	public CommentCUDResponse updateComment(String commentId, CommentUpdateDto commentDto) {
		CommentCUDResponse response = new CommentCUDResponse();
		String token = jwtService.refreshToken(commentDto.getToken());
		response.setToken(token);
		// Verificar si el autor del comentario existe
		String authorName = commentDto.getAuthor();
		boolean isAuthor = userRepository.existsByUsername(authorName);

		if (!isAuthor) {
			response.setMessage("El autor del comentario no existe");
			return response;
		}

		// Obtener el comentario
		Optional<Comment> commentOptional = getCommentById(commentDto.getBlogId(), commentId);
		if (!commentOptional.isPresent()) {
			response.setMessage("El comentario no existe");
			return response;
		}

		Comment comment = commentOptional.get();

		// Verificar si el usuario es el autor del comentario
		if (!comment.getAuthor().equals(authorName)) {
			response.setMessage("No tiene permisos para actualizar este comentario");
			return response;
		}

		// Actualizar el contenido del comentario
		comment.setContent(commentDto.getContent());

		// Obtener el blog asociado al comentario
		String blogId = commentDto.getBlogId();
		DocumentReference blogRef = firestore.collection("blogs").document(blogId);

		try {
			// Obtener el documento del blog
			DocumentSnapshot blogSnapshot = blogRef.get().get();
			if (blogSnapshot.exists()) {
				// Obtener el objeto del blog
				Blog blog = blogSnapshot.toObject(Blog.class);
				if (blog != null && blog.getComments() != null) {
					// Actualizar el comentario dentro del mapa de comentarios del blog
					blog.getComments().put(commentId, comment);

					// Actualizar el blog en Firestore
					blogRef.set(blog);

					response.setDone(true);
					response.setMessage("Comentario actualizado correctamente");
					return response;
				}
			}
			response.setMessage("No se pudo actualizar el comentario");
			return response;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			response.setMessage("Error al actualizar el comentario");
			return response;
		}
	}

	@Override
	public CommentCUDResponse deleteComment(String commentId, CommentDeleteDto commentDeleteDto) {
		CommentCUDResponse response = new CommentCUDResponse();
		response.setDone(false);

		// Verificar si el autor del comentario existe
		String authorName = commentDeleteDto.getAuthor();
		boolean isAuthor = userRepository.existsByUsername(authorName);

		if (!isAuthor) {
			response.setMessage("El autor del comentario no existe");
			return response;
		}

		// Verificar si el ID del blog está presente en el DTO
		String blogId = commentDeleteDto.getBlogId();
		if (blogId == null || blogId.isEmpty()) {
			response.setMessage("El ID del blog no está presente");
			return response;
		}

		// Obtener el blog asociado al comentario
		DocumentReference blogRef = firestore.collection("blogs").document(blogId);
		try {
			// Obtener el mapa de comentarios del blog
			DocumentSnapshot blogSnapshot = blogRef.get().get();
			if (!blogSnapshot.exists()) {
				response.setMessage("El blog asociado al comentario no existe");
				return response;
			}

			Blog blog = blogSnapshot.toObject(Blog.class);
			Map<String, Comment> commentsMap = blog.getComments();

			// Eliminar el comentario del mapa de comentarios del blog
			commentsMap.remove(commentId);

			// Actualizar el blog en Firestore
			blogRef.update("comments", commentsMap);

			// Eliminar el comentario de Firestore
			commentsCollection.document(commentId).delete();

			response.setDone(true);
			response.setMessage("Comentario eliminado correctamente");
			return response;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			response.setMessage("Error al eliminar el comentario");
			return response;
		}
	}

	private Optional<Comment> getCommentById(String blogId, String commentId) {
		// Obtener la referencia del blog
		DocumentReference blogRef = firestore.collection("blogs").document(blogId);

		try {
			// Obtener el documento del blog
			DocumentSnapshot blogSnapshot = blogRef.get().get();
			if (blogSnapshot.exists()) {
				// Obtener el objeto del blog
				Blog blog = blogSnapshot.toObject(Blog.class);
				if (blog != null && blog.getComments() != null) {
					// Obtener el comentario del mapa de comentarios del blog
					Comment comment = blog.getComments().get(commentId);
					return Optional.ofNullable(comment);
				}
			}
			return Optional.empty();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

}
