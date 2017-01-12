package midianet.pratico.repository;

import midianet.pratico.domain.Morador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends CrudRepository<Morador, Long> {

}