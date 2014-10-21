package com.jorge.directory.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorge.directory.app.domain.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

  Contact findById(long id);

  List<Contact> findByName(String name);
}
