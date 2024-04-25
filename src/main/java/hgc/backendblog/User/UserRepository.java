package hgc.backendblog.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hgc.backendblog.User.Entitys.Role;
import hgc.backendblog.User.Entitys.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
    Optional<User> findByFirebaseId(String firebaseId); 
    Optional<User> findByEmail(String firebaseId); 

	boolean existsByUsername(String username);
	boolean existsByEmail(String email);

	@Query("SELECT u.role FROM User u WHERE u.username = :username")
	Optional<Role> findRoleByUsername(@Param("username") String username);

/*	@Modifying()
	@Query("update User u set u.firstname=:firstname, u.lastname=:lastname, u.country=:country where u.id = :id")
	void updateUser(@Param(value = "id") Integer id, @Param(value = "firstname") String firstname,
			@Param(value = "lastname") String lastname, @Param(value = "country") String country);
*/
}
