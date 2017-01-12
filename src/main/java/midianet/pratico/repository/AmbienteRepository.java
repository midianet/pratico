package midianet.pratico.repository;

import midianet.pratico.domain.Ambiente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbienteRepository extends CrudRepository<Ambiente, Integer> {

}