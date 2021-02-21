package de.kanoune.photoappapiusers.repositories;

import de.kanoune.photoappapiusers.model.entities.UserVO;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserVO, Long> {

    UserVO findByEmail(String email);
}
