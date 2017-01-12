package midianet.pratico.repository;

import midianet.pratico.domain.Torre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorreRepository extends CrudRepository<Torre, Integer> {

}
