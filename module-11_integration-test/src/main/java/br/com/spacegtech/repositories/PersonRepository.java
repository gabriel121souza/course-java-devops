package br.com.spacegtech.repositories;

import br.com.spacegtech.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
    @Query("select p from Person p where p.firstName = ?1 and p.lastName= ?2")
    Person findByJPQL(String firstName, String lastName);

    @Query("select p from Person p where p.firstName =:firstName and p.lastName=:lastName")
    Person findByJPQLNameParemeters(@Param("firstName") String first,
                                    @Param("lastName") String lastName);

    @Query(value = "select * from person p where p.first_name = ?1 and p.last_name= ?2", nativeQuery = true)
    Person findByNativeQuery(String firstName, String lastName);

    @Query(value = "select * from person p where p.first_name =:firstName and p.last_name=:lastName", nativeQuery = true)
    Person findByNativeSQLWithNameParameters(@Param("firstName") String first,
                             @Param("lastName") String lastName);



}