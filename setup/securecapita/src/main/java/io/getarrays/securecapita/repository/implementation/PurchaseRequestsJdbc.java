package io.getarrays.securecapita.repository.implementation;

import io.getarrays.securecapita.domain.PurchaseRequests;
import io.getarrays.securecapita.domain.User;
import io.getarrays.securecapita.exception.ApiException;
import io.getarrays.securecapita.query.PurchaseQuery;
import io.getarrays.securecapita.repository.PurchaseRequestsRepository;
import io.getarrays.securecapita.rowmapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import static java.util.Map.of;



@Repository
@RequiredArgsConstructor
@Slf4j

public class PurchaseRequestsJdbc implements PurchaseRequestsRepository<PurchaseRequests> {




    private final NamedParameterJdbcTemplate jdbc;



    RowMapper<PurchaseRequests>rowMapper=((rs, rowNum) -> {

        PurchaseRequests PurchaseRequests=new PurchaseRequests();
        PurchaseRequests purchaseRequests=new PurchaseRequests();
        purchaseRequests.setId(rs.getLong("id"));
        purchaseRequests.setProductName(rs.getString("productName"));
        purchaseRequests.setDate(rs.getDate(String.valueOf(rs.getDate("Date"))));
        purchaseRequests.setProductCode(rs.getString("productCode"));
        purchaseRequests.setQuantity(rs.getInt("quantity"));
        purchaseRequests.setReceiverEmail(rs.getString("receiverEmail"));
        return PurchaseRequests;

    });

    @Override
    public List<PurchaseRequests> list() {
        try {
            String query = "SELECT * FROM purchaseRequests";
            List<PurchaseRequests> purchaseRequests = jdbc.query(query,rowMapper) ;                        //query(query, new UserRowMapper());
            return purchaseRequests;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred while retrieving the list of users. Please try again.");
        }

    }


    @Override
    public PurchaseRequests create(PurchaseRequests purchaseRequests) {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = getSqlParameterSource(purchaseRequests);
        jdbc.update(PurchaseQuery.INSERT_PURCHASE_REQUEST_QUERY,parameters, holder);
        return purchaseRequests;
    }

    private SqlParameterSource getSqlParameterSource(PurchaseRequests purchaseRequests) {
        return new MapSqlParameterSource()
                //.addValue("userId", purchaseRequests.getUserId())
                .addValue("productName", purchaseRequests.getProductName())
                .addValue("date", purchaseRequests.getDate())
                .addValue("receiverEmail",purchaseRequests.getReceiverEmail())
                .addValue("signature", purchaseRequests.getSignature());
    }


    @Override
    public PurchaseRequests get(Long id) {
        try {

            return jdbc.queryForObject(PurchaseQuery.SELECT_PURCHASEREQUESTS_BY_ID_QUERY, of("id", id),rowMapper);

        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No PURCHASE REQUESTS found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void update(PurchaseRequests purchaseRequests, Long id) {

    }

//    @Override
//    public void update(PurchaseRequests purchaseRequests, Long id) {
//
//    }

//    @Override
//    public void update(PurchaseRequests purchaseRequests, Long id) {
//        try {
//
//            String UPDATE_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID = "UPDATE PURCHASEREQUEST SET productName=?,Date=?,productCode=? WHERE id = :purchaserequestsId";
//
//            jdbcTemplate.update(UPDATE_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID, purchaseRequests.getProductName(),purchaseRequests.getDate(),purchaseRequests.getProductCode(),id);
//            return;
//
//        }
//        catch (Exception exception) {
//            log.error(exception.getMessage());
//            throw new ApiException("An error occurred. Please try again.");
//        }
//
//    }



    @Override
    public boolean delete(Long id) {
        try {
            String DELETE_FROM_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID = "DELETE FROM PURCHASEREQUEST WHERE id = :purchaserequestwId";
            jdbc.update(DELETE_FROM_PURCHASEREQUESTS_BY_PURCHASEREQUEST_ID, Collections.singletonMap("purchaserequestsId", id));
            return true;
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }

    }

    @Override
    public void save(PurchaseRequests purchaseRequests) {



    }

    @Override
    public String saveImage(MultipartFile image) throws IOException {

        // Generate a unique file name or use a specific naming convention for the image
        String fileName = generateUniqueFileName(image.getOriginalFilename());

        // Specify the directory where you want to save the image
        String directory = "/path/to/image/directory";

        // Create the file path by combining the directory and file name
        String filePath = directory + "/" + fileName;

        // Save the image file to the specified path
        image.transferTo(new File(filePath));

        // Return the file path where the image is saved
        return filePath;
    }

    private String generateUniqueFileName(String originalFilename) {
// Get the file extension from the original file name
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Generate a unique file name using a combination of timestamp and a random number
        String uniqueFileName = System.currentTimeMillis() + "_" + new Random().nextInt(1000) + fileExtension;

        return uniqueFileName;


    }

    @Override
    public byte[] loadImage(String imagePath) throws IOException {
        return new byte[0];
    }

    @Override
    public void deleteImage(String imagePath) {

    }


}
