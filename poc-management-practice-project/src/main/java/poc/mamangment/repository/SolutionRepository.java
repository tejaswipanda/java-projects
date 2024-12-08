package poc.mamangment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poc.mamangment.model.POCForm;
import poc.mamangment.model.Solution;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, String> {

}
