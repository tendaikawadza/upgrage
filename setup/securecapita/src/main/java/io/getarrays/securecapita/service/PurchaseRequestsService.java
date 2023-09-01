package io.getarrays.securecapita.service;

import io.getarrays.securecapita.domain.PurchaseRequests;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PurchaseRequestsService {


    PurchaseRequests createPurchaseRequest(PurchaseRequests purchaseRequests);

    List<PurchaseRequests> getAllPurchaseRequests();

    PurchaseRequests getPurchaseRequestById(Long id);

    boolean deletePurchaseRequests(Long id);

    void saveImage(PurchaseRequests purchaseRequests, MultipartFile image) throws IOException;

    byte[] loadImage(PurchaseRequests purchaseRequests) throws IOException;

    PurchaseRequests loadImage(Authentication authentication, MultipartFile image);
}