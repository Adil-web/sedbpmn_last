package kz.kazgisa.data.repositories;

import kz.kazgisa.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
