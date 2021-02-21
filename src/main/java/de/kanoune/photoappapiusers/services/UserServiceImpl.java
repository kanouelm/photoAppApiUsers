package de.kanoune.photoappapiusers.services;

import de.kanoune.photoappapiusers.model.api.UserDTO;
import de.kanoune.photoappapiusers.model.entities.UserVO;
import de.kanoune.photoappapiusers.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserVO userVO = modelMapper.map(userDetails, UserVO.class);

        userRepository.save(userVO);

        UserDTO returnedValue = modelMapper.map(userVO, UserDTO.class);

        return returnedValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserVO userVO = userRepository.findByEmail(username);

        if(userVO == null) throw new UsernameNotFoundException(username);

        return new User(userVO.getEmail(), userVO.getEncryptedPassword(), true, true, true,true, new ArrayList<>());
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {

        UserVO userVO = userRepository.findByEmail(email);

        if(userVO == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userVO, UserDTO.class);
    }

}
