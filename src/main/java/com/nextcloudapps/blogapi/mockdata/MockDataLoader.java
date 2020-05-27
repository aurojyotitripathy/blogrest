package com.nextcloudapps.blogapi.mockdata;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.nextcloudapps.blogapi.model.role.Role;
import com.nextcloudapps.blogapi.model.role.RoleName;
import com.nextcloudapps.blogapi.model.user.User;
import com.nextcloudapps.blogapi.payload.*;
import com.nextcloudapps.blogapi.repository.RoleRepository;
import com.nextcloudapps.blogapi.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/api/mockdata")
public class MockDataLoader {

    private Logger logger = LoggerFactory.getLogger(MockDataLoader.class);

    @Autowired
    private BlogRestMockService blogRestMockService;







    @GetMapping
    public ResponseEntity<String> init() {
        Random random = new Random();
        blogRestMockService.loadAllRoles();
        List<MockUsers> mockUsers = loadObjectList(MockUsers.class, "classpath:mockdata.csv");
        for (MockUsers mockUser : mockUsers) {
            blogRestMockService.signUp(mockUser);
            JwtAuthenticationResponse jwtAuthenticationResponse=blogRestMockService.signIn(mockUser);
            if(jwtAuthenticationResponse!=null){
                logger.info("Email Id - {} :: Access key Bearer {} ", new Object[]{mockUser.getEmail(), jwtAuthenticationResponse.getAccessToken()});
            }
        }
        return new ResponseEntity<String>("Loaded...", HttpStatus.OK);
    }

    public <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            //File file = new ClassPathResource(fileName).getFile();
            File file = ResourceUtils.getFile(fileName);
            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            logger.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }

   /* public void init(){

User user=new User();
            user.setUsername(mockUser.getUserName());
            user.setPassword("dev@707");
            user.setFirstName(mockUser.getFirstName());
            user.setLastName(mockUser.getLastName());
            user.setEmail(mockUser.getEmail());
            user.setPhone(mockUser.getPhoneNumber());
            userService.addUser(user);
            if(random.nextInt(2)==1){
                userService.giveAdmin(user.getUsername());
            }

        Role roleAdmin=new Role();
        roleAdmin.setName(RoleName.ROLE_ADMIN);

        Role roleUser=new Role();
        roleAdmin.setName(RoleName.ROLE_USER);
         List<String> categoriesName=Arrays.asList("");
        for(String categoryName:categoriesName){
            Category category=new Category();
            category.setName(categoryName);
            categoryService.addCategory(category,)
        }

        Geo geo=new Geo();
        geo.setLat(mockUser.getLatitude());
        geo.setLng(mockUser.getLongitude());
        Address address=new Address();
        address.setCity(mockUser.getCity());
        address.setStreet(mockUser.getCity());
        address.setSuite(mockUser.getSuite());
        address.setStreet(mockUser.getStreet());
        address.setZipcode(mockUser.getZipcode());
        address.setGeo(geo);

        Company company=new Company();
        company.setBs(mockUser.getBs());
        company.setCatchPhrase(mockUser.getCatchPharse());
        company.setName(mockUser.getCompanyName());

        user.setCompany(company);
        user.setAddress(address);

        //album-photos
        int randomAlbums=random.nextInt(5);
        List<String> titles= Arrays.asList("","","","","","");
        List<String> thumbnailUrls= Arrays.asList("","","","","","");
        for(int i=1;i<=randomAlbums;i++){
            Photo photo1=new Photo();
            photo1.setThumbnailUrl(thumbnailUrls.get(i));
            photo1.setUrl(thumbnailUrls.get(i));
            photo1.setTitle(titles.get(i));

            Photo photo2=new Photo();
            photo2.setThumbnailUrl(thumbnailUrls.get(i));
            photo2.setUrl(thumbnailUrls.get(i));
            photo2.setTitle(titles.get(i));

            List<Photo> photos=new ArrayList<>();
            photos.add(photo1);
            photos.add(photo2);

            Album album=new Album();
            album.setTitle(titles.get(i));
            album.setUser(user);
            album.setPhoto(photos);
        }

        //post-comments
        int randomPost=random.nextInt(20);
        for(int i=1;i<=randomPost;i++){
            Post post=new Post();
            post.setBody("");
            post.setCategory();
            post.setTitle();
            post.setUser(user);
        }
    }*/
}
