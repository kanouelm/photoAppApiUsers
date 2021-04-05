package de.kanoune.photoappapiusers.services;

import de.kanoune.photoappapiusers.model.api.UserDTO;
import de.kanoune.photoappapiusers.model.entities.UserVO;
import de.kanoune.photoappapiusers.model.feignClient.AlbumsServiceClient;
import de.kanoune.photoappapiusers.model.rest.response.AlbumResponse;
import de.kanoune.photoappapiusers.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //private  RestTemplate restTemplate;
    private final Environment environment;
    private final AlbumsServiceClient albumsServiceClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,Environment environment, AlbumsServiceClient albumsServiceClient) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.albumsServiceClient = albumsServiceClient;
        this.environment = environment;
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

    @Override
    public UserDTO getUserDetailsByUserId(String userId) {

        UserVO userVO = userRepository.findByUserId(userId);

        if(userVO == null) throw new UsernameNotFoundException("User not found");

        UserDTO userDto = new ModelMapper().map(userVO, UserDTO.class);
        /*  // this is for the RestTemplate client
        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);

        ResponseEntity<List<AlbumResponse>> albumsListResponse = restTemplate.exchange(
                albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponse>>() {
                });

        List<AlbumResponse> albumsList = albumsListResponse.getBody();
        */
       log.info("Before calling albums Microservice");
        List<AlbumResponse> albumsList = albumsServiceClient.getAlbums(userId);
        log.info("After calling albums Microservice");


        userDto.setAlbumResponseList(albumsList);

        return userDto;
    }

}
