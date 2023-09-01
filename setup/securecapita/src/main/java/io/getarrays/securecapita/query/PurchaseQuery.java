package io.getarrays.securecapita.query;

public class PurchaseQuery {
    public static final String SELECT_PURCHASEREQUESTS_BY_ID_QUERY ="SELECT * FROM PurchaseRequest WHERE id = :id";
    public static final String INSERT_PURCHASE_REQUEST_QUERY = "INSERT INTO PurchaseRequest ( productName, date, receiverEmail, signature) VALUES ( :productName, :date, :receiverEmail, :signature)";

}
