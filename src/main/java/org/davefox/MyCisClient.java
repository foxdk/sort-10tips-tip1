/*
 * Copyright (c) 2016  Intellectual Reserve, Inc.  All rights reserved.
 */

package org.davefox;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A class which creates a CIS session and returns the CIS session ID.
 *
 * @author David Fox
 */
public class MyCisClient {

  private final Client client;

  public MyCisClient(Client client) {
    this.client = client;
  }

  public String createCisSession(String host, String clientId, String clientSecret) {
    String url = host + "/cis-web/oauth2/v3/token?grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;
    WebTarget web = client.target(url);
    Invocation.Builder builder = web.request(MediaType.APPLICATION_JSON_TYPE);
    Response response = builder.post(null);
    int status = response.getStatus();
    if (status == 200) {
      CisClientCredentials credentials;
      try {
        credentials = response.readEntity(CisClientCredentials.class);
      }
      catch (ProcessingException e) {
        throw new MyCisClientException("Bad response from server", e);
      }
      // return the session ID
      return credentials.getToken();
    }
    else {
      throw new MyCisClientException("Remote call to create session failed: status=" + status);
    }
  }

  public static class MyCisClientException extends RuntimeException {
    public MyCisClientException(String message) {
      super(message);
    }

    public MyCisClientException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}