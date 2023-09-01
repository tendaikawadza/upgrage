package io.getarrays.securecapita.repository;

import io.getarrays.securecapita.domain.PurchaseRequests;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PurchaseRequestsRepository <T extends PurchaseRequests>{
     List<T> list();
    T create(T data);

    T get(Long id);


   void update(T t,Long id);
    boolean delete(Long id);


    void save(PurchaseRequests purchaseRequests);
    // Save the image file and return the file path
    String saveImage(MultipartFile image) throws IOException;

    // Retrieve the image file by its path
    byte[] loadImage(String imagePath) throws IOException;

    // Delete the image file by its path
    void deleteImage(String imagePath);
}
