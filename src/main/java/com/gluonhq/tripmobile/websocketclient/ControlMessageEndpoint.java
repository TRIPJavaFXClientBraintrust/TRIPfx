package com.gluonhq.tripmobile.websocketclient;

/**
 * Created by jamesweaver on 2/18/16.
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

/**
 *
 * @author mark
 */
public class ControlMessageEndpoint extends Endpoint {
  private Session session;

  @Override
  public void onOpen(Session sn, EndpointConfig ec) {
    System.out.println("In ControlMessageEndpoint#onOpen");
    this.session = sn;

    this.session.addMessageHandler(new MessageHandler.Whole<String>() {
      public void onMessage(String text) {
        System.out.println("Got your message (" + text + "). Thanks !");
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
      Logger.getLogger(ControlMessageEndpoint.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
