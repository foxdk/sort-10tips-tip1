/*
 * Copyright (c) 2016  Intellectual Reserve, Inc.  All rights reserved.
 */

package org.davefox;

import org.davefox.MyCisClient.MyCisClientException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Unit test for MyCisClient class.
 *
 * @author David Fox
 */
@Test
public class TestMyCisClient extends JerseyBaseTest {

  public void testCreateCisSession_normalFlow() {
    CisOauth2Resource.response = getNormalJsonResponse();
    MyCisClient client = new MyCisClient(this.getClient());

    String sessionId = client.createCisSession("http://localhost:9998", "some-dev-key", "areallygoodsecret");

    assertEquals(sessionId, "USYS848EE9A0A899CCFE1D1B371F0696087B_idses-int01.a.fsglobal.net");
  }

  public void testCreateCisSession_badRequestFlow() {
    CisOauth2Resource.response = 400;
    MyCisClient client = new MyCisClient(this.getClient());

    try {
      client.createCisSession("http://localhost:9998", "some-dev-key", "areallygoodsecret");
      fail("expected exception");
    }
    catch (MyCisClientException e) {
      assertEquals(e.getMessage(), "Remote call to create session failed: status=400");
    }
  }

  public void testCreateCisSession_badServerResponseFlow() {
    CisOauth2Resource.response = getBadJsonResponse();
    MyCisClient client = new MyCisClient(this.getClient());

    try {
      client.createCisSession("http://localhost:9998", "some-dev-key", "areallygoodsecret");
      fail("expected exception");
    }
    catch (MyCisClientException e) {
      assertEquals(e.getMessage(), "Bad response from server");
    }
  }


  private static String getNormalJsonResponse() {
    return "{\n" +
        "  \"token\": \"USYS848EE9A0A899CCFE1D1B371F0696087B_idses-int01.a.fsglobal.net\",\n" +
        "  \"access_token\": \"USYS848EE9A0A899CCFE1D1B371F0696087B_idses-int01.a.fsglobal.net\",\n" +
        "  \"token_type\": \"family_search\"\n" +
        "}\n";
  }

  private static String getBadJsonResponse() {
    return "{\n" +
        "  \"token\": \"USYS848EE9A0A899CCFE1D1B371F0696087B_idses-int01.a.fsglobal.net\",\n" +
        "  \"access_token\": \"USYS848EE9A0A899CCFE1D1B371F0696087B_idses-int01.a.fsglobal.net\",\n" +
        "  \"token_type\": \"family_search\"\n" +
        "\n";
  }
}