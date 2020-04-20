package praca_dyplomowa.praca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import praca_dyplomowa.praca.entity.Parameter;
import praca_dyplomowa.praca.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
    @Query("select p from Parameter p where name like lower(concat('%', :name, '%')) order by p.name asc")
    List<Parameter> findByNameLike(String name);

    Optional<Parameter> findByName(String name);
    @Query("select p from Parameter p where p.name = :name")
    Optional<Parameter> findByNameAndUserIn(String name);
}
