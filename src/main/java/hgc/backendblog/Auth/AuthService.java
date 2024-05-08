package hgc.backendblog.Auth;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;

import hgc.backendblog.Jwt.JwtService;
import hgc.backendblog.User.UserRepository;
import hgc.backendblog.User.DTO.UserFirebaseDTO;
import hgc.backendblog.User.Entitys.User;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public AuthResponse login(LoginRequest request) {
		try {
			FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
			UserRecord userRecord;

			// Verificar si el login se realiza con email o username
			if (request.getUser() != null && request.getUser().contains("@")) {
				// Iniciar sesión con email
				userRecord = firebaseAuth.getUserByEmail(request.getUser());
			} else {
				// Iniciar sesión con username
				Optional<User> optionalUser = userRepository.findByUsername(request.getUser());
				if (optionalUser.isPresent()) {
					String uid = optionalUser.get().getFirebaseId();
					userRecord = firebaseAuth.getUser(uid);
				} else {
					return new AuthResponse("Usuario no encontrado", false);
				}
			}

			String uid = userRecord.getUid();
			Optional<User> optionalUser = userRepository.findByFirebaseId(uid);

			if (optionalUser.isPresent()) {
				String username = optionalUser.get().getUsername();
				authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
				UserDetails userDetails = userRepository.findByUsername(username)
						.orElseThrow(() -> new RuntimeException("User details not found"));

				String token = jwtService.getToken(userDetails);
				return new AuthResponse(token, true);
			} else {
				return new AuthResponse("Usuario no encontrado", false);
			}
		} catch (FirebaseAuthException e) {
			return new AuthResponse("Error en el login, contacte con el administrador", false);
		} catch (Exception e) {
			return new AuthResponse("Error en el login, contacte con el administrador", false);
		}
	}

	public RegisterResponse register(RegisterRequest request) {
		RegisterResponse response = new RegisterResponse();

		if (request.getPassword().length() < 8) {
			response.setMessage("La contraseña debe tener al menos 8 caracteres");
			return response;
		}

		if (userRepository.existsByUsername(request.getUsername())) {
			response.setMessage("El nombre de usuario ya está en uso");
			return response;
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			response.setMessage("El email ya está en uso");
			return response;
		}

		try {
			// Crear usuario en Firebase Authentication
			UserRecord.CreateRequest createUserRequest = new UserRecord.CreateRequest().setEmail(request.getEmail())
					.setPassword(request.getPassword());
			UserRecord userRecord = FirebaseAuth.getInstance().createUser(createUserRequest);

			// Guardar usuario en la base de datos local
			User user = new User(userRecord.getUid(), request.getUsername(), request.getEmail(),
					passwordEncoder.encode(request.getPassword()));
			userRepository.save(user);

			// Crear documento en Firestore para almacenar información adicional del usuario
			UserFirebaseDTO userFirebaseDTO = new UserFirebaseDTO(request.getUsername(), new ArrayList<>(),
					new ArrayList<>(), new ArrayList<>());
			Firestore firestore = FirestoreClient.getFirestore();
			CollectionReference usersCollection = firestore.collection("users");
			DocumentReference newUserDoc = usersCollection.document(userRecord.getUid());
			newUserDoc.set(userFirebaseDTO);

			response.setMessage("Usuario registrado correctamente. Se ha enviado un correo de verificación.");
			response.setRegister(true);
		} catch (FirebaseAuthException e) {
			response.setMessage("Error al registrar el usuario. Por favor, inténtalo de nuevo.");
		} catch (Exception e) {
			response.setMessage("Error al registrar el usuario. Por favor, inténtalo de nuevo.");
		}

		return response;
	}
}
