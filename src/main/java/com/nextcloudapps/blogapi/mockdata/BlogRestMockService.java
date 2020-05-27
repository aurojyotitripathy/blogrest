package com.nextcloudapps.blogapi.mockdata;

import com.nextcloudapps.blogapi.model.role.Role;
import com.nextcloudapps.blogapi.model.role.RoleName;
import com.nextcloudapps.blogapi.payload.ApiResponse;
import com.nextcloudapps.blogapi.payload.JwtAuthenticationResponse;
import com.nextcloudapps.blogapi.payload.LoginRequest;
import com.nextcloudapps.blogapi.payload.SignUpRequest;
import com.nextcloudapps.blogapi.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BlogRestMockService {

    private Logger logger = LoggerFactory.getLogger(BlogRestMockService.class);

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private RestTemplate signInRestTemplate;

    @Autowired
    private RestTemplate signUpRestTemplate;

    @Autowired
    private RoleRepository roleRepository;

    public void loadAllRoles(){
        Optional<Role> adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
        if (!adminRole.isPresent()) {
            Role role = new Role();
            role.setName(RoleName.ROLE_ADMIN);
            roleRepository.save(role);
        }
        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_USER);
        if (!userRole.isPresent()) {
            Role role = new Role();
            role.setName(RoleName.ROLE_USER);
            roleRepository.save(role);
        }
    }
    public boolean signUp(MockUsers mockUser){
        boolean status=false;
        try {
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setEmail(mockUser.getEmail());
            signUpRequest.setUsername(mockUser.getUserName());
            signUpRequest.setFirstName(mockUser.getFirstName());
            signUpRequest.setLastName(mockUser.getLastName());
            signUpRequest.setPassword("dev@707");
            HttpEntity<SignUpRequest> request = new HttpEntity<>(signUpRequest);
            ResponseEntity<ApiResponse> signup = signInRestTemplate.postForEntity(appUrl + "/api/auth/signup", signUpRequest, ApiResponse.class);
            status=true;
        } catch (HttpClientErrorException ex) {
            int statusCode = ex.getStatusCode().value();
            if(ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
                logger.error("Email Id - {} message:: {} ",new Object[]{mockUser.getEmail(),ex.getResponseBodyAsString()});
            }
        } catch (Exception e){
            logger.error("*************Error occurred*********",e);
        }
        return status;
    }

    public  JwtAuthenticationResponse signIn(MockUsers mockUser){
        JwtAuthenticationResponse res=null;
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setPassword("dev@707");
            loginRequest.setUsernameOrEmail(mockUser.getEmail());
            HttpEntity<LoginRequest> loginRequestHttpEntity = new HttpEntity<>(loginRequest);
            ResponseEntity<JwtAuthenticationResponse> signin = signUpRestTemplate.postForEntity(appUrl + "/api/auth/signin", loginRequestHttpEntity, JwtAuthenticationResponse.class);
            res=signin.getBody();
        } catch (HttpClientErrorException ex) {
            int statusCode = ex.getStatusCode().value();
            if(ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
                logger.error("Email Id - {} message:: {} ",new Object[]{mockUser.getEmail(),ex.getResponseBodyAsString()});
            }
        } catch (Exception e){
            logger.error("*************Error occurred*********",e);
        }
        return res;
    }
}
