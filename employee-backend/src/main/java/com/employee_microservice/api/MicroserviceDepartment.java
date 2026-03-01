package com.employee_microservice.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Component;

@Component
public class MicroserviceDepartment {

  private HttpClient client = HttpClient.newHttpClient();

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
