package com.gfg.sellercenter.attributes;

import static org.junit.Assert.*;

import com.gfg.sellercenter.attributes.entity.AttributeOption;
import com.gfg.sellercenter.attributes.entity.AttributeWithOption;
import com.gfg.sellercenter.attributes.entity.ProductWithAttributeInformation;
import com.gfg.sellercenter.attributes.reader.HttpReader;
import com.gfg.sellercenter.attributes.service.AttributeService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AttributeServiceTest {

    private static final String RESOURCES_PATH = "src/test/resources/";

    @Test
    public void successResponse() throws Exception {

        HttpReader readerMock = Mockito.mock(HttpReader.class);
        Mockito.when(
                        readerMock.getSerialNumberRequiredAttributesByProductIds(
                                new ArrayList<>(Arrays.asList(1, 2, 3))))
                .thenReturn(getFileContent("json/attributesRequireResponse.json"));
        
        AttributeService service = new AttributeService(readerMock);

        List<Integer> productIds = new ArrayList<>();

        productIds.add(1);
        productIds.add(2);
        productIds.add(3);

        Map<Integer, ProductWithAttributeInformation> expected = this.getExpectedSuccessResult();

        Map<Integer, ProductWithAttributeInformation> actual =
                service.getSerialNumberRequiredAttributesByProductIds(productIds);

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    private JSONArray getFileContent(String filename) throws IOException {
        return new JSONArray(new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + filename))));
    }

    @Test
    public void emptyResponse() throws Exception {

        HttpReader readerMock = Mockito.mock(HttpReader.class);
        Mockito.when(
                        readerMock.getSerialNumberRequiredAttributesByProductIds(
                                new ArrayList<>(Arrays.asList(1, 2, 3))))
                .thenReturn(getFileContent("json/emptyResponse.json"));

        AttributeService service = new AttributeService(readerMock);

        List<Integer> productIds = new ArrayList<>();

        productIds.add(1);
        productIds.add(2);
        productIds.add(3);

        Map<Integer, ProductWithAttributeInformation> expected = new HashMap<>();

        Map<Integer, ProductWithAttributeInformation> actual =
                service.getSerialNumberRequiredAttributesByProductIds(productIds);

        assertEquals(expected, actual);
    }

    private Map<Integer, ProductWithAttributeInformation> getExpectedSuccessResult() {

        Map<Integer, ProductWithAttributeInformation> expected = new HashMap<>();

        ProductWithAttributeInformation productOne =
                new ProductWithAttributeInformation(
                        1,
                        "test",
                        new JSONObject(
                                "{\"131\":\"fdsafsadfsadfdsaf\",\"136\":943,\"185\":1528,\"243\":\"0.00\",\"244\":\"0.00\",\"255\":\"0.00\",\"35\":2,\"62\":\"Midcom\",\"64\":\"...\",\"72\":\"10.00\",\"74\":308,\"76\":\"2\",\"77\":\"4\"}"),
                        new HashMap<>());

        ProductWithAttributeInformation productTwo =
                new ProductWithAttributeInformation(
                        2,
                        "adssadasdfadsf",
                        new JSONObject("{\"131\":\"asdfsafdsafdsafdsa\",\"74\":\"308\"}"),
                        new HashMap<>());

        AttributeWithOption attributeOne =
                new AttributeWithOption(35, "Supplier type", new HashMap<>());

        AttributeOption optionOne = new AttributeOption(1, "Direct");

        AttributeWithOption attributeTwo =
                new AttributeWithOption(74, "Type of package (shipment)", new HashMap<>());

        AttributeOption optionTwo = new AttributeOption(307, "Parcel");
        AttributeOption optionThree = new AttributeOption(308, "Carrier");

        attributeOne.getAttributeOptions().put(optionOne.getIdCatalogAttributeOption(), optionOne);
        attributeTwo.getAttributeOptions().put(optionTwo.getIdCatalogAttributeOption(), optionTwo);
        attributeTwo
                .getAttributeOptions()
                .put(optionThree.getIdCatalogAttributeOption(), optionThree);

        productOne.getAttribute().put(attributeOne.getIdCatalogAttribute(), attributeOne);
        productOne.getAttribute().put(attributeTwo.getIdCatalogAttribute(), attributeTwo);
        productTwo.getAttribute().put(attributeOne.getIdCatalogAttribute(), attributeOne);
        productTwo.getAttribute().put(attributeTwo.getIdCatalogAttribute(), attributeTwo);

        expected.put(productOne.getCatalogProductId(), productOne);
        expected.put(productTwo.getCatalogProductId(), productTwo);

        return expected;
    }
}
