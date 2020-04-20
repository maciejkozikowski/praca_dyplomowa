package praca_dyplomowa.praca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import praca_dyplomowa.praca.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();
    @Query("select u from User u where u.name = :name and u.password = :password")
    Optional<User> findUserByNameAndPassword(String name, String password);

}
