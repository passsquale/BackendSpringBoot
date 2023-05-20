package ru.roslyackov.springboot.business.ropository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.roslyackov.springboot.business.entity.Priority;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    @Query("SELECT p FROM Priority p where" +
            "(:title is null or :title='' " +
            " or lower(p.title) like lower(concat('%', :title, '%')))" +
            " and p.user.email=:email " +
            "order by p.title asc")
    List<Priority> find(String title,String email);

    List<Priority> findByUserEmailOrderByIdAsc(String email);
}
