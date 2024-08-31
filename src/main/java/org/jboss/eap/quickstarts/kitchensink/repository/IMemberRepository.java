package org.jboss.eap.quickstarts.kitchensink.repository;

import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMemberRepository extends MongoRepository<Member, String> {
    Member findByEmail(String email);

    List<Member> findAllByOrderByNameAsc();
}
