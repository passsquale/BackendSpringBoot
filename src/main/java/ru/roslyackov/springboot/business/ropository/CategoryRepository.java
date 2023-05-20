package ru.roslyackov.springboot.business.ropository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.roslyackov.springboot.business.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c where" +
    "(:title is null or :title='' " +
    " or lower(c.title) like lower(concat('%', :title, '%')))" +
    " and c.user.email=:email " +
    "order by c.title asc")
    List<Category> find(String title,String email);

    List<Category> findByUserEmailOrderByTitleAsc(String email);

    Optional<Category> findById(Long id);
}
