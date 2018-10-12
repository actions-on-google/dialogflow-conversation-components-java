/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.test.MockRequestBuilder;
import com.google.api.services.actions_fulfillment.v2.model.RichResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import org.junit.Test;


public class ConversationComponentsAppTest {

  private static String fromFile(String fileName) throws IOException {
    Path absolutePath = Paths.get("src", "test", "resources",
        fileName);
    return new String(Files.readAllBytes(absolutePath));
  }

  @Test
  public void testWelcome() throws Exception {
    ConversationComponentsApp app = new ConversationComponentsApp();
    MockRequestBuilder rb = MockRequestBuilder.PreBuilt.welcome(
        "Default Welcome Intent", true);
    ActionRequest request = rb.build();

    CompletableFuture<ActionResponse> future = app.welcome(request);

    ActionResponse response = future.get();
    assertTrue(response.getExpectUserResponse());
    assertEquals(2, response.getRichResponse().getItems().size());
    assertNotNull(response.getRichResponse().getSuggestions());
  }

  @Test
  public void testByeResponse() throws Exception {
    ConversationComponentsApp app = new ConversationComponentsApp();
    MockRequestBuilder rb = new MockRequestBuilder();
    ActionRequest request = rb.setIntent("bye response").build();

    CompletableFuture<ActionResponse> future = app.byeResponse(request);

    ActionResponse response = future.get();
    assertFalse(response.getExpectUserResponse());
    assertEquals(1, response.getRichResponse().getItems().size());
    assertNull(response.getRichResponse().getSuggestions());
  }

  @Test
  public void testBasicCard() throws Exception {
    ConversationComponentsApp app = new ConversationComponentsApp();
    MockRequestBuilder requestBuilder = new MockRequestBuilder();
    ActionRequest request = requestBuilder.setIntent("basic card").build();

    CompletableFuture<ActionResponse> future = app.basicCard(request);

    ActionResponse response = future.get();
    RichResponse richResponse = response.getRichResponse();

    assertTrue(response.getExpectUserResponse());
    assertEquals(2, richResponse.getItems().size());
    assertNotNull(richResponse.getItems().get(1).getBasicCard());
    assertNotNull(richResponse.getItems().get(1).getBasicCard().getImage());
    assertNull(response.getHelperIntent());
    assertNotNull(response.getRichResponse().getSuggestions());
  }

  @Test
  public void testMediaResponse() throws Exception {
    ConversationComponentsApp app = new ConversationComponentsApp();
    MockRequestBuilder requestBuilder = new MockRequestBuilder();
    ActionRequest request = requestBuilder.setIntent("media response").build();

    CompletableFuture<ActionResponse> future = app.mediaResponse(request);

    ActionResponse response = future.get();
    RichResponse richResponse = response.getRichResponse();

    assertTrue(response.getExpectUserResponse());
    assertEquals(2, richResponse.getItems().size());
    assertNotNull(richResponse.getItems().get(1).getMediaResponse());
    assertNotNull(
        richResponse.getItems().get(1).getMediaResponse()
            .getMediaObjects().get(0).getIcon());
    assertNull(response.getHelperIntent());
    assertNotNull(response.getRichResponse().getSuggestions());
  }

  @Test
  public void testMediaResponse_NoMediaSupport() throws Exception {
    ConversationComponentsApp app = new ConversationComponentsApp();
    MockRequestBuilder requestBuilder = new MockRequestBuilder();
    // Tests with device that does not support media action.
    ActionRequest request = requestBuilder
        .setIntent("media response")
        .setMediaOutput(false)
        .build();

    CompletableFuture<ActionResponse> future = app.mediaResponse(request);

    ActionResponse response = future.get();
    RichResponse richResponse = response.getRichResponse();
    assertTrue(response.getExpectUserResponse());
    assertEquals(1, richResponse.getItems().size());
  }

  @Test
  public void testMediaResponseStatus() throws Exception {
    ConversationComponentsApp app = new ConversationComponentsApp();
    String status = "FINISHED";
    MockRequestBuilder requestBuilder = MockRequestBuilder.PreBuilt.mediaPlaybackStatus(
        status, "handleMediaStatusEvent", true);
    ActionRequest request = requestBuilder.build();

    CompletableFuture<ActionResponse> future = app.handleMediaStatusEvent(request);

    ActionResponse response = future.get();
    RichResponse richResponse = response.getRichResponse();
    assertTrue(
        richResponse.getItems().get(0).getSimpleResponse().getTextToSpeech().contains(status));
  }
}
