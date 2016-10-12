/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */

package org.davefox;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is used to hold the client credentials response received from the cis-web oauth2 call.
 * The token is the authenticated session id.
 *
 * @author David Fox
 */
@XmlRootElement
public class CisClientCredentials {
  @SuppressWarnings("unused")
  private String token;  // set by JSON object deserializer

  public String getToken() {
    return token;
  }
}