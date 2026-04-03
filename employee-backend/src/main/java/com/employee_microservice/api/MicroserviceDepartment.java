package com.employee_microservice.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Component;

/**
 * This component handles the communication with the Department microservice.
 * It serves as a client to verify the availability or existence of data
 * through HTTP requests.
 */
@Component
public class MicroserviceDepartment {

    private HttpClient client = HttpClient.newHttpClient();

    /**
     * Sends a GET request to a specified URI to validate the department data.
     * * @param uri The full web address (Uniform Resource Identifier) to be
     * queried.
     * 
     * @return {@code true} if the server returns a successful 200 OK status;
     *         {@code false} otherwise.
     * @throws IOException          If an I/O error occurs when sending or
     *                              receiving.
     * @throws InterruptedException If the operation is interrupted.
     */
    public boolean dataMicroserviceDepartment(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .version(Version.HTTP_1_1)
                .GET()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return false;
        } else {
            return true;
        }
    }
}
