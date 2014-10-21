package com.jorge.directory.app.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jorge.directory.app.domain.Contact;
import com.jorge.directory.app.repository.ContactRepository;


@RestController
@RequestMapping("/app")
public class ContactResource {

  private final Logger      log = LoggerFactory.getLogger(ContactResource.class);

  @Autowired
  private ContactRepository contactRepository;

  @RequestMapping(value = "/rest/contacts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public void create(@RequestBody Contact contact) {
    log.debug("REST request to save Contact : {}", contact);
    contactRepository.save(contact);
  }

  @RequestMapping(value = "/rest/contacts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Contact> getAll() {
    log.debug("REST request to get all Contacts");
    return contactRepository.findAll();
  }

  @RequestMapping(value = "/rest/contacts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Contact> get(@PathVariable Long id, HttpServletResponse response) {
    log.debug("REST request to get Contact : {}", id);
    Contact contact = contactRepository.findOne(id);
    if (contact == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(contact, HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/contacts/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete Contact : {}", id);
    contactRepository.delete(id);
  }
}
