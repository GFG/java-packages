package com.gfg.oms.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.gfg.oms.Config;
import com.gfg.oms.Request;
import com.gfg.oms.consignment.request.CreateCommand;
import com.gfg.oms.consignment.request.CreateReturnCommand;
import com.gfg.oms.response.Error;
import com.gfg.oms.consignment.response.CreateConsignmentResponse;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.List;

@AllArgsConstructor
public class Client {
    @NotNull
    private Config config;

    @NotNull
    private ConnectionManager connectionManager;

    public CreateConsignmentResponse doCreateConsignmentRequest(CreateCommand createConsignmentRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RawResponse rawResponse = sendRequest(createConsignmentRequest);
        CreateConsignmentResponse response = new CreateConsignmentResponse();

        // case when request failed on lower layer
        if (rawResponse.getErrorMessage() != null) {
            response.addError(Error.generalError(rawResponse.getErrorMessage()));
            response.setIsSuccessful(false);
            return response;
        }

        // case when "message" field with error is in top level of response structure
        JsonNode jsonNode = mapper.readTree(rawResponse.getContent());
        if (jsonNode.hasNonNull("message") && jsonNode.get("message").isTextual()) {
            response.addError(Error.generalError(jsonNode.get("message").textValue()));
            response.setIsSuccessful(false);
            return response;
        }

        try {
            // handling normal error
            response = mapper.readValue(rawResponse.getContent(), CreateConsignmentResponse.class);
        } catch (MismatchedInputException e) {
            // handling error response with plain array of errors
            response.setErrors(mapper.readValue(rawResponse.getContent(), new TypeReference<List<Error>>(){}));
        }
        return response;
    }

    public CreateConsignmentResponse doCreateConsignmentReturnRequest(CreateReturnCommand createConsignmentReturnRequest) throws JsonProcessingException {
        return doCreateConsignmentRequest(createConsignmentReturnRequest);
    }

    public static Client getInstance(Config config) {
        return new Client(config, new ConnectionManager());
    }

    private RawResponse sendRequest(Request request) {
        request.setApiKey(config.getPassword());
        HttpURLConnection connection;

        try {
            connection = connectionManager.getConnection(request, new ObjectMapper(), config.getUrl());
        } catch (IOException e) {
            return new RawResponse(null, null, "Error during communication with OMS:" + e.getMessage());
        }

        try {
            connection.connect();
            InputStream inputStream = (connection.getResponseCode() == 200) ? connection.getInputStream() : connection.getErrorStream();
            return new RawResponse(
                    connection.getResponseCode(),
                    connectionManager.getResponseContent(inputStream),
                    null
            );
        } catch (IOException e) {
            return new RawResponse(null,  "", "Error during communication with OMS:" + e.getMessage());
        }
    }
}
