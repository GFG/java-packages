package com.gfg.sellercenter.seller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SellerReaderServiceInterface {
    Map<Integer, Seller> getByIds(List<Integer> sellerIds) throws IOException;
    Seller getById(Integer sellerId) throws IOException;
}
