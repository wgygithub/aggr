package org.example.wrapper;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author admin
 */
public class BufferingClientHttpResponseWrapper implements ClientHttpResponse {
  private final ClientHttpResponse response;
  private byte[] body;

  public BufferingClientHttpResponseWrapper(ClientHttpResponse response) {
    this.response = response;
  }

  @Override
  @NonNull
  public HttpStatusCode getStatusCode() throws IOException {
    return this.response.getStatusCode();
  }

  @NonNull
  @Override
  public String getStatusText() throws IOException {
    return this.response.getStatusText();
  }

  @NonNull
  @Override
  public HttpHeaders getHeaders() {
    return this.response.getHeaders();
  }

  @NonNull
  @Override
  public InputStream getBody() throws IOException {
    if (this.body == null) {
      this.body = StreamUtils.copyToByteArray(this.response.getBody());
    }
    return new ByteArrayInputStream(this.body);
  }

  @Override
  public void close() {
    this.response.close();
  }
}
