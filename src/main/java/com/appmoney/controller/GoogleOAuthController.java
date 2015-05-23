package com.appmoney.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appmoney.security.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class GoogleOAuthController {

  private static final String GOOGLE_OAUTH_ENDPOINT = "https://accounts.google.com/o/oauth2/auth";
  private static final String GOOGLE_TOKEN_ENDPOINT = "https://www.googleapis.com/oauth2/v3/token";
  private static final String GOOGLE_EMAIL_ENDPOINT = "https://www.googleapis.com/plus/v1/people/me";

  @Autowired
  HttpClient httpClient;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  TokenService tokenService;

  @Value("${oauth.google.clientId}")
  String clientId;

  @Value("${oauth.google.clientSecret}")
  String clientSecret;

  @Value("${oauth.google.redirectUri}")
  String redirectUri;

  @Value("${oauth.google.scopes}")
  String scopes;

  @RequestMapping(method=RequestMethod.GET, value="/authenticate/google")
  public String redirectToGoogle() throws UnsupportedEncodingException {
    StringBuffer uri = new StringBuffer("redirect:");
    uri.append(GOOGLE_OAUTH_ENDPOINT);
    uri.append("?");
    uri.append("response_type=code");
    uri.append("&");
    uri.append("scope=");
    uri.append(URLEncoder.encode(scopes, "utf8"));
    uri.append("&");
    uri.append("client_id=");
    uri.append(URLEncoder.encode(clientId, "utf8"));
    uri.append("&");
    uri.append("redirect_uri=");
    uri.append(URLEncoder.encode(redirectUri, "utf8"));
    return uri.toString();
  }

  @RequestMapping(method=RequestMethod.GET, value="/oauth2callback")
  public Map<String, Object> receiveRedirect(String code) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("email", getUserEmail(getToken(code)));
    return model;
  }

  private String getUserEmail(String token) throws Exception {
    HttpGet get = new HttpGet(GOOGLE_EMAIL_ENDPOINT);
    get.addHeader("Authorization", String.format("Bearer %s", token));

    HttpResponse response = httpClient.execute(get);
    JsonNode node = objectMapper.readTree(response.getEntity().getContent());
    for (JsonNode email : node.get("emails")) {
      if (email.get("type").asText().equals("account")) {
        return email.get("value").asText();
      }
    }

    throw new RuntimeException("Can't find user email.");
  }

  private String getToken(String code) throws Exception {
    List<NameValuePair> formParams = new ArrayList<>();
    formParams.add(new BasicNameValuePair("code", code));
    formParams.add(new BasicNameValuePair("client_id", clientId));
    formParams.add(new BasicNameValuePair("client_secret", clientSecret));
    formParams.add(new BasicNameValuePair("redirect_uri", redirectUri));
    formParams.add(new BasicNameValuePair("grant_type", "authorization_code"));

    HttpPost post = new HttpPost(GOOGLE_TOKEN_ENDPOINT);
    post.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
    HttpResponse response = httpClient.execute(post);

    JsonNode node = objectMapper.readTree(response.getEntity().getContent());
    return node.get("access_token").asText();
  }

}
