package de.kanoune.photoappapiusers.services;

import de.kanoune.photoappapiusers.model.api.UserDTO;
import de.kanoune.photoappapiusers.model.entities.UserVO;
import de.kanoune.photoappapiusers.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserVO userVO = modelMapper.map(userDetails, UserVO.class);
        userVO.setEncryptedPassword("test");
        userRepository.save(userVO);

        UserDTO returnedValue = modelMapper.map(userVO, UserDTO.class);

        return returnedValue;
    }
}
