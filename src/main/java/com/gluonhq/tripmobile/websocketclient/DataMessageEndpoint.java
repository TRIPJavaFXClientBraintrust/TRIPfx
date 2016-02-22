package com.gluonhq.tripmobile.websocketclient;

/**
 * Created by jamesweaver on 2/18/16.
 */

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gluonhq.tripmobile.model.Reading;
import com.gluonhq.tripmobile.model.ReadingConsumer;

/**
 *
 * @author mark
 */
public class DataMessageEndpoint extends Endpoint {
  private Session session;
  private ReadingConsumer readingConsumer;


  public DataMessageEndpoint(ReadingConsumer readingConsumer) {
    this.readingConsumer = readingConsumer;
  }

  @Override
  public void onOpen(Session sn, EndpointConfig ec) {
    System.out.println("In DataMessageEndpoint#onOpen");
    this.session = sn;

    //TODO: Investgate why lambda version of the following didn't work:
    this.session.addMessageHandler(new MessageHandler.Whole<String>() {
      public void onMessage(String jsonReading) {
        try {
          ObjectMapper mapper = new ObjectMapper();
          Reading reading = mapper.readValue(jsonReading, Reading.class);
          System.out.println("New reading: " + reading.toString());
          readingConsumer.setReading(reading);

        }
        catch (Exception e) {
          System.out.println("Exception: " + e.getLocalizedMessage());
        }
      }
    });
  }

//    @Override
//    public void onMessage() {
//
//    }

  public void sendMessage(String message) {
    try {
      System.out.println("sendMessage message = " + message);
      this.session.getBasicRemote().sendText(message);
    } catch (IOException ex) {
      Logger.getLogger(DataMessageEndpoint.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
