package praca_dyplomowa.praca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import praca_dyplomowa.praca.entity.Measurement;

import java.time.Instant;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAllByParameterId(Integer parameterId);
    @Query("select m from Measurement m where m.date between :startDate and :endDate")
    List<Measurement> findAllByDateBetween(Instant startDate, Instant endDate);

    List<Measurement> findAllByDateBetweenAndParameterId(Instant startDate, Instant endDate, Integer parameterId);
}
