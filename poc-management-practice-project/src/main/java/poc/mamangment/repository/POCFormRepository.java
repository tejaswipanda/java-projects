package poc.mamangment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poc.mamangment.model.POCForm;

@Repository
public interface POCFormRepository extends JpaRepository<POCForm, Long> {

}
