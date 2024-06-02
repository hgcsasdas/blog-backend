package hgc.backendblog.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hgc.backendblog.User.Entitys.Role;
import hgc.backendblog.User.Entitys.User;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByFirebaseId(String firebaseId);
    Optional<User> findByEmail(String firebaseId);

<<<<<<< HEAD
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
	@Query("SELECT u.firebaseId FROM User u WHERE u.username = :username")
	Optional<String> findFirebaseIdByUsername(@Param("username") String username);
=======
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
>>>>>>> 7c15b610c3a6838d467816a8c2437e29a4284690

    @Query("SELECT u.firebaseId FROM User u WHERE u.username = :username")
    Optional<String> findFirebaseIdByUsername(@Param("username") String username);

    @Query("SELECT u.blogEntries FROM User u WHERE u.username = :username")
    Optional<Integer> findBlogEntriesByUsername(@Param("username") String username);

    @Query("SELECT u.role FROM User u WHERE u.username = :username")
    Optional<Role> findRoleByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.blogEntries = :blogEntries WHERE u.username = :username")
    void updateBlogEntriesByUsername(@Param("username") String username, @Param("blogEntries") Integer blogEntries);

   /* @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username AND u.role = 'ROLE_ADMIN'")
    boolean isAdmin(@Param("username") String username);*/

}
