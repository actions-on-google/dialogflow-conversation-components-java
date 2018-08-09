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

import com.google.actions.api.*;
import com.google.actions.api.Capability;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.systemintent.SelectionCarousel;
import com.google.actions.api.response.systemintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConversationComponentsApp extends DialogflowApp {

  private static final String IMG_URL_AOG =
          "https://developers.google.com/actions/images/badges" +
                  "/XPM_BADGING_GoogleAssistant_VER.png";
  private static final String IMG_URL_GOOGLE_HOME =
          "https://lh3.googleusercontent.com/Nu3a6F80WfixUqf_ec_vgXy_" +
                  "c0-0r4VLJRXjVFF_X_CIilEu8B9fT35qyTEj_PEsKw";
  private static final String IMG_URL_GOOGLE_PIXEL =
          "https://storage.googleapis.com/madebygoog/v1" +
                  "/Pixel/Pixel_ColorPicker/Pixel_Device_Angled_Black-720w.png";
  private static final String IMG_URL_MEDIA =
          "http://storage.googleapis.com/automotive-media/album_art.jpg";
  private static final String MEDIA_SOURCE =
          "http://storage.googleapis.com/automotive-media/Jazz_In_Paris.mp3";

  private static final String[] IMAGES = new String[]{
          IMG_URL_AOG, IMG_URL_GOOGLE_HOME, IMG_URL_GOOGLE_PIXEL};

  private static final String[] SUGGESTIONS = new String[]{
          "Basic Card",
          "Browse Carousel",
          "Carousel",
          "List",
          "Media",
          "Table Card"};

  @ForIntent("Default Welcome Intent")
  public CompletableFuture<ActionResponse> welcome(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    responseBuilder
            .add(new SimpleResponse()
                    .setDisplayText("Hello there")
                    .setTextToSpeech("Hi there!"))
            .add(new SimpleResponse()
                    .setTextToSpeech("I can show you basic cards, lists and " +
                            " carousels as well as suggestions on your phone.")
                    .setDisplayText("I can show you basic cards, lists and " +
                            " carousels as well as suggestions"))
            .addSuggestions(SUGGESTIONS);

    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("normal ask")
  public CompletableFuture<ActionResponse> normalAsk(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    ActionResponse response = responseBuilder
            .add("Ask me to show you a list, carousel, or basic card.")
            .build();
    return CompletableFuture.completedFuture(response);
  }

  @ForIntent("basic card")
  public CompletableFuture<ActionResponse> basicCard(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return CompletableFuture.completedFuture(
              responseBuilder.add("Sorry, try this on a device with a screen" +
                      " select the phone surface in the simulator").build());
    }

    Button learnMoreButton = new Button()
            .setTitle("This is a button")
            .setOpenUrlAction(new OpenUrlAction()
                    .setUrl("https://assistant.google.com"));
    List<Button> buttons = new ArrayList<>();
    buttons.add(learnMoreButton);
    String text = "This is a basic card. Text in a basic card can include" +
            " \"quotes\", *emphasis*, _italics_, **strong**, __bold__ and " +
            " ***bold italic*** or ___strong emphasis___ as well as other " +
            " things like line  \nbreaks";
    responseBuilder
            .add("This is the first simple response for a basic card.")
            .add(new BasicCard()
                    .setTitle("Title: This is a title")
                    .setSubtitle("This is a subtitle")
                    .setFormattedText(text)
                    .setImage(new Image()
                            .setUrl(IMG_URL_AOG)
                            .setAccessibilityText("Image alt text"))
                    .setButtons(buttons))
            .addSuggestions(SUGGESTIONS);

    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("list")
  public CompletableFuture<ActionResponse> selectionList(
          ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return CompletableFuture.completedFuture(
              responseBuilder.add("Sorry, try this on a device with a screen" +
                      " select the phone surface in the simulator").build());
    }

    List<ListSelectListItem> items = new ArrayList<>();
    ListSelectListItem item;
    for (int i = 0; i < 3; i++) {
      item = new ListSelectListItem();
      item.setTitle("Item #" + (i + 1))
              .setDescription("Description of Item #" + (i + 1))
              .setImage(new Image()
                      .setUrl(IMAGES[i])
                      .setAccessibilityText("Image alt text"))
              .setOptionInfo(new OptionInfo()
                      .setKey(String.valueOf(i + 1)));
      items.add(item);
    }

    responseBuilder
            .add("This is the first simple response for a list.")
            .add(new SelectionList().setTitle("List title").setItems(items))
            .addSuggestions(SUGGESTIONS);

    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("carousel")
  public CompletableFuture<ActionResponse> selectionCaorusel(
          ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return CompletableFuture.completedFuture(
              responseBuilder.add("Sorry, try this on a device with a screen" +
                      " select the phone surface in the simulator").build());
    }

    List<CarouselSelectCarouselItem> items = new ArrayList<>();
    CarouselSelectCarouselItem item;
    for (int i = 0; i < 3; i++) {
      item = new CarouselSelectCarouselItem();
      item.setTitle("Item #" + (i + 1))
              .setDescription("Description of Item #" + (i + 1))
              .setImage(new Image()
                      .setUrl(IMAGES[i])
                      .setAccessibilityText("Image alt text"))
              .setOptionInfo(new OptionInfo()
                      .setKey(String.valueOf(i + 1)));
      items.add(item);
    }

    responseBuilder
            .add("This is the first simple response for a selection carousel.")
            .addSuggestions(SUGGESTIONS)
            .add(new SelectionCarousel().setItems(items));

    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("browse carousel")
  public CompletableFuture<ActionResponse> browseCarousel(
          ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return CompletableFuture.completedFuture(
              responseBuilder.add("Sorry, try this on a device with a screen" +
                      " select the phone surface in the simulator").build());
    }

    String url = "https://www.google.com";

    List<CarouselBrowseItem> items = new ArrayList<>();
    CarouselBrowseItem item;
    for (int i = 0; i < 3; i++) {
      item = new CarouselBrowseItem();
      item.setTitle("Item #" + (i + 1));
      item.setDescription("Description of Item #" + (i + 1));
      item.setOpenUrlAction(new OpenUrlAction().setUrl(url));
      item.setImage(new Image().setUrl(IMAGES[i])
              .setAccessibilityText("Image alt text"));
      item.setFooter("Footer for Item #" + (i + 1));
      items.add(item);
    }

    responseBuilder
            .add("This is an example of a browse carousel.")
            .addSuggestions(SUGGESTIONS)
            .add(new CarouselBrowse().setItems(items));

    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("item selected")
  public CompletableFuture<ActionResponse> itemSelected(
          ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    String selectedItem = request.getArgument("OPTION").getTextValue();
    responseBuilder
            .add("You selected: " + selectedItem)
            .addSuggestions(SUGGESTIONS);
    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("media response")
  public CompletableFuture<ActionResponse> mediaResponse(
          ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    if (!request.hasCapability(Capability.MEDIA_RESPONSE_AUDIO.getValue())) {
      return CompletableFuture.completedFuture(
              responseBuilder.add("Sorry, this device does not support " +
                      "audio playback").build());
    }

    List<MediaObject> mediaObjects = new ArrayList<>();
    mediaObjects.add(new MediaObject()
            .setName("Jazz in Paris")
            .setDescription("A funky Jazz tune")
            .setContentUrl(MEDIA_SOURCE)
            .setIcon(new Image()
                    .setUrl(IMG_URL_MEDIA)
                    .setAccessibilityText("Media icon")));
    responseBuilder
            .add("This is the sample for a media response")
            .addSuggestions(SUGGESTIONS)
            .add(new MediaResponse()
                    .setMediaObjects(mediaObjects)
                    .setMediaType("AUDIO"));
    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("media status")
  public CompletableFuture<ActionResponse> handleMediaStatusEvent(
          ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    Argument mediaStatus = request.getArgument("MEDIA_STATUS");
    String status = "Unknown";
    if (mediaStatus != null && mediaStatus.getExtension() != null) {
      status = (String) mediaStatus.getExtension().get("status");
    }
    responseBuilder
            .add("Media status received - " + status)
            .addSuggestions(SUGGESTIONS);

    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("table builder")
  public CompletableFuture<ActionResponse> tableCard(
          ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return CompletableFuture.completedFuture(
              responseBuilder.add("Sorry, try this on a device with a screen" +
                      " select the phone surface in the simulator").build());
    }

    List<TableCardColumnProperties> columnProperties = new ArrayList<>();
    columnProperties.add(new TableCardColumnProperties()
            .setHeader("Column #1"));
    columnProperties.add(new TableCardColumnProperties()
            .setHeader("Column #2"));
    columnProperties.add(new TableCardColumnProperties()
            .setHeader("Column #3"));

    List<TableCardRow> rows = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      List<TableCardCell> cells = new ArrayList<>();
      for (int j = 0; j < 3; j++) {
        cells.add(new TableCardCell().setText("Cell #" + (j + 1)));
      }
      rows.add(new TableCardRow().setCells(cells));
    }

    TableCard table = new TableCard()
            .setTitle("Table card title")
            .setSubtitle("Table card subtitle")
            .setColumnProperties(columnProperties)
            .setRows(rows);

    responseBuilder
            .add("This is an example of Table card")
            .add(table)
            .addSuggestions(SUGGESTIONS);
    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("normal bye")
  public CompletableFuture<ActionResponse> normalBye(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    responseBuilder
            .add("Ok see you later.")
            .endConversation();
    return CompletableFuture.completedFuture(responseBuilder.build());
  }

  @ForIntent("bye response")
  public CompletableFuture<ActionResponse> byeResponse(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder();
    responseBuilder
            .add(new SimpleResponse()
                    .setDisplayText("OK see you later")
                    .setTextToSpeech("Okay see you later"))
            .endConversation();
    return CompletableFuture.completedFuture(responseBuilder.build());
  }
}
