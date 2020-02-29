package praca_dyplomowa.praca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import praca_dyplomowa.praca.entity.Parameter;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
    @Query("select p from Parameter p where name like lower(concat('%', :name, '%')) order by p.name asc")
    List<Parameter> findByName(String name);

}
