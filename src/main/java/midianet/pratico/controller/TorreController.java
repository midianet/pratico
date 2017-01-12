package midianet.pratico.controller;

import com.google.common.collect.Lists;
import midianet.pratico.domain.Torre;
import midianet.pratico.repository.TorreRepository;
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
@RequestMapping("/api/torre")
public class TorreController {

	@Autowired
	private TorreRepository repository;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TorreRep>> listAll() {
		final List<Torre> list = Lists.newArrayList(repository.findAll());
		if(list.isEmpty()){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity(toRep(list),HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TorreRep> getById(@PathVariable("id") final Integer id) {
		final Torre current = repository.findOne(id);
		if (current == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(toRep(current), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<Void> create(@RequestBody final TorreRep torre, final UriComponentsBuilder ucBuilder){
		torre.setId(null); //return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		final Torre current = toEntity(torre);
		repository.save(current);
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/torre/{id}").buildAndExpand(current.getId()).toUri());
		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<TorreRep> update(@PathVariable("id") final Integer id, @RequestBody final TorreRep torre) {
		final Torre current = repository.findOne(id);
		if (current == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		current.setNome(torre.getNome());
		repository.save(current);
		return new ResponseEntity(toRep(current), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable("id") final Integer id) {
		final Torre current = repository.findOne(id);
		if (current == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		repository.delete(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	private List<Torre> toEntity(final List<TorreRep> list){
		final List<Torre> l = new ArrayList();
		list.forEach(e -> l.add(toEntity(e)));
		return l;
	}

	private List<TorreRep> toRep(final List<Torre> list){
		final List<TorreRep> l = new ArrayList();
		list.forEach(e -> l.add(toRep(e)));
		return l;
	}

	private Torre toEntity(final TorreRep rep){
		return new Torre(rep.getId(),rep.getNome());
	}

	private TorreRep toRep(final Torre entity){
		return new TorreRep(entity.getId(),entity.getNome());
	}

}

//	@RequestMapping(value = "/user/", method = RequestMethod.GET)
//	public ResponseEntity<List<User>> listAllUsers() {
//		List<User> users = userService.findAllUsers();
//		if(users.isEmpty()){
//			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//		}
//		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
//	}
//
//	//-------------------Retrieve Single User--------------------------------------------------------
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
//		System.out.println("Fetching User with id " + id);
//		User user = userService.findById(id);
//		if (user == null) {
//			System.out.println("User with id " + id + " not found");
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<User>(user, HttpStatus.OK);
//	}
//
//	//-------------------Create a User--------------------------------------------------------
//	@RequestMapping(value = "/user/", method = RequestMethod.POST)
//	public ResponseEntity<Void> createUser(@RequestBody User user,    UriComponentsBuilder ucBuilder) {
//		System.out.println("Creating User " + user.getName());
//		if (userService.isUserExist(user)) {
//			System.out.println("A User with name " + user.getName() + " already exist");
//			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
//		}
//		userService.saveUser(user);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
//		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//	}
//
//	//------------------- Update a User --------------------------------------------------------
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
//	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
//		System.out.println("Updating User " + id);
//		User currentUser = userService.findById(id);
//		if (currentUser==null) {
//			System.out.println("User with id " + id + " not found");
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		}
//		currentUser.setName(user.getName());
//		currentUser.setAge(user.getAge());
//		currentUser.setSalary(user.getSalary());
//		userService.updateUser(currentUser);
//		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
//	}
//
//	//------------------- Delete a User --------------------------------------------------------
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
//	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
//		System.out.println("Fetching & Deleting User with id " + id);
//		User user = userService.findById(id);
//		if (user == null) {
//			System.out.println("Unable to delete. User with id " + id + " not found");
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		}
//		userService.deleteUserById(id);
//		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//	}
//
//	//------------------- Delete All Users --------------------------------------------------------
//	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
//	public ResponseEntity<User> deleteAllUsers() {
//		System.out.println("Deleting All Users");
//		userService.deleteAllUsers();
//		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//	}