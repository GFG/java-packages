package com.gfg.sellercenter.seller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.sellercenter.seller.infra.HttpReader;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SellerReaderService implements SellerReaderServiceInterface {
    @NotNull
    @NotEmpty
    private final String hostUrl;

    @NotNull
    private final HttpReader jsonReader;

    private static final String API_PATH = "/api/sellers/v1";

    @Override
    public Map<Integer, Seller> getByIds(List<Integer> sellerIds) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String listOfIds = sellerIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        String requestUrl = hostUrl + API_PATH + String.format("?seller_ids=[%s]", listOfIds);

        Seller[] sellers = mapper.readValue(jsonReader.readRemote(requestUrl), Seller[].class);
        Map<Integer, Seller> result = new HashMap<>();
        for (Seller seller: sellers) {
            result.put(seller.getId(), seller);
        }
        return result;
    }

    @Override
    public Seller getById(Integer sellerId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String requestUrl = hostUrl + API_PATH + "/" + sellerId.toString();
        return mapper.readValue(jsonReader.readRemote(requestUrl), Seller.class);
    }

    public static SellerReaderService getInstance(String hostUrl) {
        return new SellerReaderService(hostUrl, new HttpReader());
    }
}
