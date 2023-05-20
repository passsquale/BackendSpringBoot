package ru.roslyackov.springboot.business.ropository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.roslyackov.springboot.business.entity.Stat;

@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {
    Stat findByUserEmail(String email);
}
