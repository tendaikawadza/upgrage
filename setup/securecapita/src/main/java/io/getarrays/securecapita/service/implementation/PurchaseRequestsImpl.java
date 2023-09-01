package io.getarrays.securecapita.service.implementation;

import io.getarrays.securecapita.domain.PurchaseRequests;
import io.getarrays.securecapita.repository.PurchaseRequestsRepository;
import io.getarrays.securecapita.service.PurchaseRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseRequestsImpl implements PurchaseRequestsService {
    private final PurchaseRequestsRepository purchaseRequestsRepository;

    //delete requests
    public boolean deletePurchaseRequest(Long id ){

        return purchaseRequestsRepository.delete(id);

    }


    @Override
    public PurchaseRequests createPurchaseRequest(PurchaseRequests purchaseRequests) {

        return purchaseRequestsRepository.create(purchaseRequests);


    }

    public void saveImage(PurchaseRequests purchaseRequests, MultipartFile image) throws IOException {
        // Generate a unique file name or use a specific naming convention for the image
        String fileName = generateUniqueFileName(image.getOriginalFilename());

        // Specify the directory where you want to save the image
        String directory = "/path/to/image/directory";

        // Create the file path by combining the directory and file name
        String filePath = directory + "/" + fileName;

        // Save the image file to the specified path
        image.transferTo(new File(filePath));

        // Update the purchaseRequests object with the file path or any relevant information
        purchaseRequests.setImagePath(filePath);

        // Save the updated purchaseRequests object to your data store (e.g., database)
        purchaseRequestsRepository.save(purchaseRequests);
    }

    private String generateUniqueFileName(String originalFilename) {
        // Get the current timestamp
        long timestamp = System.currentTimeMillis();

        // Generate a random string
        String randomString = UUID.randomUUID().toString();

        // Extract the file extension from the original filename
        String fileExtension = getFileExtension(originalFilename);

        // Combine the timestamp, random string, and file extension to create a unique filename
        String uniqueFileName = timestamp + "_" + randomString + "." + fileExtension;

        return uniqueFileName;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";

    }


    @Override
    public byte[] loadImage(PurchaseRequests purchaseRequests) throws IOException {
        return new byte[0];
    }



    @Override
    public PurchaseRequests loadImage(Authentication authentication, MultipartFile image) {
        PurchaseRequests purchaseRequests = new PurchaseRequests();

        // Generate a unique file name for the image
        String fileName = generateUniqueFileName(image.getOriginalFilename());

        try {
            // Specify the directory where you want to save the image
            String directoryPath = "path/to/image/directory/";

            // Create the directory if it doesn't exist
            Files.createDirectories(Path.of(directoryPath));

            // Define the file path where you want to save the image
            String filePath = directoryPath + fileName;

            // Copy the input stream from the MultipartFile to the specified file path
            Files.copy(image.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);

            // Set the image URL or file path to the purchaseRequests object
            purchaseRequests.setImageUrl(filePath);

        } catch (IOException e) {
            // Handle any exceptions that occur during the image loading process
            e.printStackTrace();
            // Optionally, you can throw an exception or handle the error in an appropriate way

            // Return null to indicate an error occurred while loading the image
            return null;
        }

        // Return the updated PurchaseRequests object
        return purchaseRequests;
    }

    @Override
    public List<PurchaseRequests> getAllPurchaseRequests() {
        return purchaseRequestsRepository.list();
    }

    @Override
    public PurchaseRequests getPurchaseRequestById(Long id) {
        return (PurchaseRequests) purchaseRequestsRepository.get(id);
    }

    @Override
    public boolean deletePurchaseRequests(Long id) {
        return purchaseRequestsRepository.delete(id);
    }
}
