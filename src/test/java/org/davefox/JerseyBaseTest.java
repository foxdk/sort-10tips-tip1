/*
 * Copyright (c) 2016  Intellectual Reserve, Inc.  All rights reserved.
 */

package org.davefox;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;

/**
 * Base test for testing code which makes Jersey calls.
 *
 * @author David Fox
 */
public class JerseyBaseTest extends JerseyTestNg.ContainerPerClassTest {

  @Path("cis-web/oauth2/v3/token")
  public static class CisOauth2Resource {
    static Object response = null;    // set this from the test to the desired endpoint response

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String getOauth2Token(
        @QueryParam("grant_type") String grantType,
        @QueryParam("client_id") String clientId,
        @QueryParam("client_secret") String clientSecret) {

      if (response instanceof Integer) {
        throw new WebApplicationException((Integer) response);
      }
      else {
        return (String) response;
      }
    }
  }

  @Override
  protected Application configure() {
    return new ResourceConfig(
        CisOauth2Resource.class
    );
  }

  @Override
  protected void configureClient(ClientConfig config) {
    config.register(new JerseyUnknownFieldsMappingProvider());
  }

  @Provider
  static class JerseyUnknownFieldsMappingProvider implements ContextResolver<ObjectMapper> {
    public ObjectMapper getContext(Class<?> type) {
      return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
  }
}