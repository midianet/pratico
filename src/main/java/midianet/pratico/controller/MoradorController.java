package midianet.pratico.controller;

import com.google.common.collect.Lists;
import midianet.pratico.domain.Morador;
import midianet.pratico.domain.Torre;
import midianet.pratico.repository.MoradorRepository;
import midianet.pratico.representation.MoradorRep;
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
@RequestMapping("/api/morador")
public class MoradorController {

    @Autowired
    private MoradorRepository repository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MoradorRep>> listAll() {
        final List<Morador> list = Lists.newArrayList(repository.findAll());
        if(list.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(toRep(list),HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MoradorRep> getById(@PathVariable("id") final Long id) {
        final Morador current = repository.findOne(id);
        if (current == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(toRep(current), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Void> create(@RequestBody final MoradorRep morador, final UriComponentsBuilder ucBuilder){
        morador.setId(null); //return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        final Morador current = toEntity(morador);
        repository.save(current);
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/morador/{id}").buildAndExpand(current.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<MoradorRep> update(@PathVariable("id") final Long id, @RequestBody final MoradorRep morador) {
        final Morador current = repository.findOne(id);
        if (current == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        current.setNome(morador.getNome());
        repository.save(current);
        return new ResponseEntity(toRep(current), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        final Morador current = repository.findOne(id);
        if (current == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private List<Morador> toEntity(final List<MoradorRep> list){
        final List<Morador> l = new ArrayList();
        list.forEach(e -> l.add(toEntity(e)));
        return l;
    }

    private List<MoradorRep> toRep(final List<Morador> list){
        final List<MoradorRep> l = new ArrayList();
        list.forEach(e -> l.add(toRep(e)));
        return l;
    }

    private Morador toEntity(final MoradorRep rep){
        final Morador entity = new Morador();
        entity.setId(rep.getId());
        entity.setNome(rep.getNome());
        entity.setCpf(rep.getCpf());
        entity.setApto(rep.getApto());
        entity.setAtivo(rep.getAtivo());
        entity.setTelegram(rep.getTelegram());
        entity.setTorre(new Torre(rep.getTorre().getId(),rep.getTorre().getNome()));
        return entity;
    }

    private MoradorRep toRep(final Morador entity){
        final MoradorRep rep = new MoradorRep();
        rep.setId(entity.getId());
        rep.setNome(entity.getNome());
        rep.setCpf(entity.getCpf());
        rep.setTelegram(entity.getTelegram());
        rep.setApto(entity.getApto());
        rep.setAtivo(entity.getAtivo());
        rep.setTorre(new TorreRep(entity.getTorre().getId(),entity.getTorre().getNome()));
        return rep;
    }

}