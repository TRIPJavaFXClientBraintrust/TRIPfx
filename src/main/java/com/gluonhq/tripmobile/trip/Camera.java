/*
 * The MIT License
 *
 * Copyright 2014, 2015 Mark A. Heckler
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.gluonhq.tripmobile.trip;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

/**
 *
 * @author Mark Heckler (mark.heckler@gmail.com, @mkheck)
 */
public class Camera implements Runnable {

    private URL url = null;
    private URLConnection con = null;
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
    private final BooleanProperty hasImage = new SimpleBooleanProperty(true);
    
    public Camera(String ipCam){
        try {
            url = new URL(ipCam + "/?action=snapshot");
        } catch (MalformedURLException ex) {
            hasImage.set(false);
        }
        System.out.println("Starting camera at " + url.toExternalForm());
    }
    
    @Override
    public void run() {
        try {
            con = url.openConnection();
            con.setConnectTimeout(3000);
            con.setReadTimeout(10000);
          
            final InputStream in = con.getInputStream();
            if (in != null){
                image.set(new Image(in));
                hasImage.set(true);
                in.close();
            }
        } catch (SocketTimeoutException ex) {
//            System.out.println("Exception, camera timed out: " + ex.getLocalizedMessage());
            hasImage.set(false);
        } catch (IOException ex) {
//            System.out.println("Exception converting image: " + ex.getLocalizedMessage());
            hasImage.set(false);
        }
        
    }

    public ObservableValue<Image> getImageProperty() {
        return image;
    }
    
    public Image getImage() {
        return image.get();
    }
    
    public ObservableValue<Boolean> getHasImageProperty() {
        return hasImage;
    }
    
}
