package hgc.backendblog.User;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hgc.backendblog.Jwt.JwtService;
import hgc.backendblog.User.DTO.UserDTO;
import hgc.backendblog.User.Entitys.Role;
import hgc.backendblog.User.Requests.FindUserRequest;
import hgc.backendblog.User.Requests.UserRequest;
import hgc.backendblog.User.Responses.UserResponse;
import hgc.backendblog.User.Responses.UserResponseDTO;
import hgc.backendblog.User.Responses.UserRoleResponse;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final JwtService jwtService;

	public UserService(UserRepository userRepository, JwtService jwtService) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}

	@Transactional
	public UserResponse updateUser(UserRequest userRequest) {

		/*User user = new User(userRequest.getUsername(), userRequest.getLastname(),
				userRequest.getFirstname(), userRequest.getCountry());

		userRepository.updateUser(user.getId(), user.getFirstname(), user.getLastname(), user.getCountry());

		UserDetails userDetails = userRepository.findByUsername(user.getUsername()).orElseThrow();
		String newToken = jwtService.getToken(userDetails);
*/
		//return new UserResponse("El usuario se actualizó satisfactoriamente", newToken);
		return null;

	}

	public UserDTO getUser(Integer id) {
		/*User user = userRepository.findById(id).orElse(null);

		if (user != null) {
			UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(),
					user.getCountry());
			return userDTO;
		}*/
		return null;
	}

	public UserDTO searchUserByUsername(String username) {
		/*User user = userRepository.findByUsername(username).orElse(null);

		if (user != null) {
			UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(),
					user.getCountry());
			return userDTO;
		}*/
		return null;
	}

	public UserResponseDTO userIsUser(FindUserRequest findUserRequest) {
		try {
			Optional<Role> userRole = userRepository.findRoleByUsername(findUserRequest.getUsernameSearching());

			System.out.println(findUserRequest.getUsernameSearching() + " 0 " + findUserRequest.getUsernameToSearch());

			if (userRole.isPresent() && (userRole.get() == Role.ROLE_ADMIN
					|| (findUserRequest.getUsernameSearching().equals(findUserRequest.getUsernameToSearch())))) {

				System.out.println("dentro");

				UserDetails userDetails = userRepository.findByUsername(findUserRequest.getUsernameSearching())
						.orElseThrow();
				String newToken = jwtService.getToken(userDetails);

				UserDTO userDTO = searchUserByUsername(findUserRequest.getUsernameToSearch());

				UserResponseDTO userResponseDTO = new UserResponseDTO(newToken, userDTO);

				return userResponseDTO;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public UserRoleResponse getUserRole(FindUserRequest findUserRequest) {
		try {
			Optional<Role> userRoleSearching = userRepository.findRoleByUsername(findUserRequest.getUsernameSearching());
			Optional<Role> userRoleToSearch = userRepository.findRoleByUsername(findUserRequest.getUsernameToSearch());
			UserRoleResponse userRoleResponse = new UserRoleResponse();
			if (userRoleSearching.isPresent() && (userRoleSearching.get() == Role.ROLE_ADMIN
					|| (findUserRequest.getUsernameSearching().equals(findUserRequest.getUsernameToSearch())))) {

				userRoleResponse.setRol(userRoleToSearch.get());
				return userRoleResponse;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
