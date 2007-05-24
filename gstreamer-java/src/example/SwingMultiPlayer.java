/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import org.gstreamer.GMainLoop;
import org.gstreamer.Gst;
import org.gstreamer.swing.GstVideoPlayer;

/**
 *
 */
public class SwingMultiPlayer {
    
    /** Creates a new instance of SwingPlayer */
    public SwingMultiPlayer() {
    }
    public static void main(String[] args) {
        //System.setProperty("sun.java2d.opengl", "True");
        
        args = Gst.init("Swing Player", args);
        if (args.length < 1) {
            System.err.println("Usage: SwingPlayer <filename>");
            System.exit(1);
        }
        final File[] files = new File[args.length];
        for (int i = 0; i < args.length; ++i) {
            files[i] = new File(args[i]);
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame window = new JFrame("Swing Video Player");
                JDesktopPane panel = new JDesktopPane();
                window.add(panel);
                for (int i = 0; i < files.length; ++i) {
                    File file = files[i];
                    JInternalFrame frame = new JInternalFrame(file.getName());
                    frame.setResizable(true);
                    frame.setClosable(true);
                    frame.setIconifiable(true);
                    frame.setMaximizable(true);
                    
                    frame.setLocation(i * 100, i * 100);
                    final GstVideoPlayer player = new GstVideoPlayer(file);
                    player.setPreferredSize(new Dimension(640, 480));
                    player.setControlsVisible(true);
                    frame.add(player, BorderLayout.CENTER);
                    frame.pack();
                    panel.add(frame);
                    frame.setVisible(true);
                    Gst.invokeLater(new Runnable() {
                        public void run() {
                            player.play();
                        }
                    });
                    
                }
                window.setPreferredSize(new Dimension(1024, 768));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.pack();
                window.setVisible(true);
            }
        });
        new GMainLoop().run();
    }
}