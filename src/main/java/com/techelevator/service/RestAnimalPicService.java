package com.techelevator.service;

import com.techelevator.model.AnimalPic;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
@Component
public class RestAnimalPicService implements AnimalPicService{
    private static final String API_PUG_URL = "https://dog.ceo/api/breed/pug/images/random";
    private static final String API_POM_URL = "https://dog.ceo/api/breed/pomeranian/images/random";
    private static final String API_MAINEC_URL = "https://api.thecatapi.com/v1/images/search?breed_ids=mcoo";
    private static final String API_ASH_URL = "https://api.thecatapi.com/v1/images/search?breed_ids=asho";

    private RestTemplate restTemplate = new RestTemplate();
    public AnimalPic getPugPic() throws RestClientResponseException {
        AnimalPic animalPic = restTemplate.getForObject(API_PUG_URL, AnimalPic.class);
        return animalPic;
    }

    public AnimalPic getPomPic() throws RestClientResponseException {
        AnimalPic animalPic = restTemplate.getForObject(API_POM_URL, AnimalPic.class);
        return animalPic;
    }

    public AnimalPic getMaineCPic() throws RestClientResponseException {
        AnimalPic animalPic = restTemplate.getForObject(API_MAINEC_URL, AnimalPic.class);
        return animalPic;
    }

    public AnimalPic getASHPic() throws RestClientResponseException {
        AnimalPic animalPic = restTemplate.getForObject(API_ASH_URL, AnimalPic.class);
        return animalPic;
    }


}
