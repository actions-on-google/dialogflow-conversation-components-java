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

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SelectionCarousel;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowse;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowseItem;
import com.google.api.services.actions_fulfillment.v2.model.CarouselSelectCarouselItem;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.ListSelectListItem;
import com.google.api.services.actions_fulfillment.v2.model.MediaObject;
import com.google.api.services.actions_fulfillment.v2.model.MediaResponse;
import com.google.api.services.actions_fulfillment.v2.model.OpenUrlAction;
import com.google.api.services.actions_fulfillment.v2.model.OptionInfo;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;
import com.google.api.services.actions_fulfillment.v2.model.TableCard;
import com.google.api.services.actions_fulfillment.v2.model.TableCardCell;
import com.google.api.services.actions_fulfillment.v2.model.TableCardColumnProperties;
import com.google.api.services.actions_fulfillment.v2.model.TableCardRow;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConversationComponentsApp extends DialogflowApp {

  // Note: Do not store any state as an instance variable.
  // It is ok to have final variables where the variable is assigned a value in
  // the constructor but remains unchanged. This is required to ensure thread-
  // safety as the entry point (ActionServlet) instances may
  // be reused by the server.

  private static final String IMG_URL_AOG = "https://storage.googleapis.com/actionsresources/logo_assistant_2x_64dp.png";
  private static final String IMG_URL_GOOGLE_PAY = "https://storage.googleapis.com/actionsresources/logo_pay_64dp.png";
  private static final String IMG_URL_GOOGLE_HOME = "https://lh3.googleusercontent.com/Nu3a6F80WfixUqf_ec_vgXy_c0-0r4VLJRXjVFF_X_CIilEu8B9fT35qyTEj_PEsKw";
  private static final String IMG_URL_GOOGLE_PIXEL = "https://storage.googleapis.com/madebygoog/v1/Pixel/Pixel_ColorPicker/Pixel_Device_Angled_Black-720w.png";
  private static final String IMG_URL_MEDIA = "http://storage.googleapis.com/automotive-media/album_art.jpg";
  private static final String MEDIA_SOURCE = "http://storage.googleapis.com/automotive-media/Jazz_In_Paris.mp3";

  private static final String[] IMAGES =
      new String[]{IMG_URL_AOG, IMG_URL_GOOGLE_PAY, IMG_URL_GOOGLE_HOME, IMG_URL_GOOGLE_PIXEL};

  private static final String[] SUGGESTIONS =
      new String[]{"Basic Card", "Browse Carousel", "Carousel", "List", "Media", "Table Card"};

  @ForIntent("Default Welcome Intent")
  public ActionResponse welcome(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    responseBuilder
        .add(
            new SimpleResponse()
                .setDisplayText(rb.getString("welcome_msg_1"))
                .setTextToSpeech(rb.getString("welcome_msg_2")))
        .add(
            new SimpleResponse()
                .setTextToSpeech(rb.getString("welcome_more_1"))
                .setDisplayText(rb.getString("welcome_more_2")))
        .addSuggestions(SUGGESTIONS);

    return responseBuilder.build();
  }

  @ForIntent("normal ask")
  public ActionResponse normalAsk(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    return responseBuilder.add(rb.getString("normal_ask_text")).build();
  }

  @ForIntent("basic card")
  public ActionResponse basicCard(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return responseBuilder.add(rb.getString("msg_no_screen")).build();
    }

    Button learnMoreButton =
        new Button()
            .setTitle(rb.getString("basic_card_button_text"))
            .setOpenUrlAction(new OpenUrlAction().setUrl("https://assistant.google.com"));
    List<Button> buttons = new ArrayList<>();
    buttons.add(learnMoreButton);
    String text = rb.getString("basic_card_text");
    responseBuilder
        .add(rb.getString("basic_card_response"))
        .add(
            new BasicCard()
                .setTitle(rb.getString("basic_card_title"))
                .setSubtitle(rb.getString("basic_card_sub_title"))
                .setFormattedText(text)
                .setImage(
                    new Image()
                        .setUrl(IMG_URL_AOG)
                        .setAccessibilityText(rb.getString("basic_card_alt_text")))
                .setButtons(buttons))
        .addSuggestions(SUGGESTIONS);

    return responseBuilder.build();
  }

  @ForIntent("list")
  public ActionResponse selectionList(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return responseBuilder.add(rb.getString("msg_no_screen")).build();
    }

    List<ListSelectListItem> items = new ArrayList<>();
    ListSelectListItem item;
    for (int i = 0; i < 4; i++) {
      item = new ListSelectListItem();
      item.setTitle(getMsg(rb, "list_item_title", i + 1))
          .setDescription(getMsg(rb, "list_item_desc", i + 1))
          .setImage(
              new Image()
                  .setUrl(IMAGES[i])
                  .setAccessibilityText(rb.getString("list_image_alt_text")))
          .setOptionInfo(new OptionInfo().setKey(String.valueOf(i + 1)));
      items.add(item);
    }

    responseBuilder
        .add(rb.getString("list_response_title"))
        .add(new SelectionList().setTitle("List title").setItems(items))
        .addSuggestions(SUGGESTIONS);

    return responseBuilder.build();
  }

  @ForIntent("carousel")
  public ActionResponse selectionCaorusel(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return responseBuilder.add(rb.getString("msg_no_screen")).build();
    }

    List<CarouselSelectCarouselItem> items = new ArrayList<>();
    CarouselSelectCarouselItem item;
    for (int i = 0; i < 4; i++) {
      item = new CarouselSelectCarouselItem();
      item.setTitle(getMsg(rb, "list_item_title", i + 1))
          .setDescription(getMsg(rb, "list_item_desc", i + 1))
          .setImage(
              new Image()
                  .setUrl(IMAGES[i])
                  .setAccessibilityText(rb.getString("list_image_alt_text")))
          .setOptionInfo(new OptionInfo().setKey(String.valueOf(i + 1)));
      items.add(item);
    }

    responseBuilder
        .add(rb.getString("selection_carousel_response_title"))
        .addSuggestions(SUGGESTIONS)
        .add(new SelectionCarousel().setItems(items));

    return responseBuilder.build();
  }

  @ForIntent("browse carousel")
  public ActionResponse browseCarousel(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return responseBuilder.add(rb.getString("msg_no_screen")).build();
    }

    if (!request.hasCapability(Capability.WEB_BROWSER.getValue())) {
      responseBuilder
              .add(rb.getString("msg_no_browser"))
              .addSuggestions(SUGGESTIONS);
      return responseBuilder.build();
    }

    String url = "https://www.google.com";

    List<CarouselBrowseItem> items = new ArrayList<>();
    CarouselBrowseItem item;
    for (int i = 0; i < 4; i++) {
      item = new CarouselBrowseItem();
      item.setTitle(getMsg(rb, "list_item_title", i + 1));
      item.setDescription(getMsg(rb, "list_item_desc", i + 1));
      item.setOpenUrlAction(new OpenUrlAction().setUrl(url));
      item.setImage(
          new Image().setUrl(IMAGES[i]).setAccessibilityText(rb.getString("list_image_alt_text")));
      item.setFooter(getMsg(rb, "list_item_footer", i + 1));
      items.add(item);
    }

    responseBuilder
        .add(rb.getString("browse_carousel_response"))
        .addSuggestions(SUGGESTIONS)
        .add(new CarouselBrowse().setItems(items));

    return responseBuilder.build();
  }

  @ForIntent("item selected")
  public ActionResponse itemSelected(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    String selectedItem = request.getSelectedOption();
    responseBuilder.add(getMsg(rb, "item_selected", selectedItem)).addSuggestions(SUGGESTIONS);
    return responseBuilder.build();
  }

  @ForIntent("media response")
  public ActionResponse mediaResponse(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    if (!request.hasCapability(Capability.MEDIA_RESPONSE_AUDIO.getValue())) {
      return responseBuilder.add(rb.getString("msg_no_media")).build();
    }

    List<MediaObject> mediaObjects = new ArrayList<>();
    mediaObjects.add(
        new MediaObject()
            .setName(rb.getString("media_name"))
            .setDescription(rb.getString("media_desc"))
            .setContentUrl(MEDIA_SOURCE)
            .setIcon(
                new Image()
                    .setUrl(IMG_URL_MEDIA)
                    .setAccessibilityText(rb.getString("media_image_alt_text"))));
    responseBuilder
        .add(rb.getString("media_response"))
        .addSuggestions(SUGGESTIONS)
        .add(new MediaResponse().setMediaObjects(mediaObjects).setMediaType("AUDIO"));
    return responseBuilder.build();
  }

  @ForIntent("media status")
  public ActionResponse handleMediaStatusEvent(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    String status = request.getMediaStatus();
    if (status == null) {
      status = "Unknown";
    }
    responseBuilder.add(getMsg(rb, "media_status_received", status)).addSuggestions(SUGGESTIONS);

    return responseBuilder.build();
  }

  @ForIntent("table builder")
  public ActionResponse tableCard(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
      return responseBuilder.add(rb.getString("msg_no_screen")).build();
    }

    List<TableCardColumnProperties> columnProperties = new ArrayList<>();
    columnProperties.add(new TableCardColumnProperties().setHeader(rb.getString("table_col_1")));
    columnProperties.add(new TableCardColumnProperties().setHeader(rb.getString("table_col_2")));
    columnProperties.add(new TableCardColumnProperties().setHeader(rb.getString("table_col_3")));

    List<TableCardRow> rows = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      List<TableCardCell> cells = new ArrayList<>();
      for (int j = 0; j < 3; j++) {
        cells.add(new TableCardCell().setText(getMsg(rb, "table_cell_value", (i + 1))));
      }
      rows.add(new TableCardRow().setCells(cells));
    }

    TableCard table =
        new TableCard()
            .setTitle(rb.getString("table_title"))
            .setSubtitle(rb.getString("table_subtitle"))
            .setColumnProperties(columnProperties)
            .setRows(rows);

    responseBuilder.add(rb.getString("table_response")).add(table).addSuggestions(SUGGESTIONS);
    return responseBuilder.build();
  }

  @ForIntent("normal bye")
  public ActionResponse normalBye(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    responseBuilder.add(rb.getString("bye_display_text")).endConversation();
    return responseBuilder.build();
  }

  @ForIntent("bye response")
  public ActionResponse byeResponse(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources", request.getLocale());
    responseBuilder
        .add(
            new SimpleResponse()
                .setDisplayText(rb.getString("bye_display_text"))
                .setTextToSpeech(rb.getString("bye_tts")))
        .endConversation();
    return responseBuilder.build();
  }

  private String getMsg(ResourceBundle rb, String key, Object... args) {
    return MessageFormat.format(rb.getString(key), args);
  }
}
