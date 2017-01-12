package midianet.pratico.controller;

import com.google.common.collect.Lists;
import midianet.pratico.domain.Ambiente;
import midianet.pratico.domain.Torre;
import midianet.pratico.repository.AmbienteRepository;
import midianet.pratico.representation.AmbienteRep;
import midianet.pratico.representation.TorreRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ambiente")
public class AmbienteController {

    @Autowired
    private AmbienteRepository repository;
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AmbienteRep>> listAll() {
        final List<Ambiente> list = Lists.newArrayList(repository.findAll());
        if(list.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(toRep(list),HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmbienteRep> getById(@PathVariable("id") final Integer id) {
        final Ambiente current = repository.findOne(id);
        if (current == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(toRep(current), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Void> create(@RequestBody final AmbienteRep ambiente, final UriComponentsBuilder ucBuilder){
        ambiente.setId(null); //return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        final Ambiente current = toEntity(ambiente);
        repository.save(current);
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/ambiente/{id}").buildAndExpand(current.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<AmbienteRep> update(@PathVariable("id") final Integer id, @RequestBody final AmbienteRep ambiente) {
        final Ambiente current = repository.findOne(id);
        if (current == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        current.setNome(ambiente.getNome());
        repository.save(current);
        return new ResponseEntity(toRep(current), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
        final Ambiente current = repository.findOne(id);
        if (current == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private List<Ambiente> toEntity(final List<AmbienteRep> list){
        final List<Ambiente> l = new ArrayList();
        list.forEach(e -> l.add(toEntity(e)));
        return l;
    }

    private List<AmbienteRep> toRep(final List<Ambiente> list){
        final List<AmbienteRep> l = new ArrayList();
        list.forEach(e -> l.add(toRep(e)));
        return l;
    }

    private Ambiente toEntity(final AmbienteRep rep){
        return new Ambiente(rep.getId(),rep.getNome());
    }

    private AmbienteRep toRep(final Ambiente entity){
        return new AmbienteRep(entity.getId(),entity.getNome());
    }

}