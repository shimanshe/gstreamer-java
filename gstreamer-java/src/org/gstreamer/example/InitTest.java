/* 
 * Copyright (c) 2007, 2008 Wayne Meissner
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

package org.gstreamer.example;

import org.gstreamer.Gst;
import org.gstreamer.Version;

import java.lang.reflect.Field;

/**
 *
 */
public class InitTest {
    
    /** Creates a new instance of GstInitTest */
    public InitTest() {
    }
    public static void main(String[] args) {
        args = Gst.init("foo", args);
        Version version = Gst.getVersion();
        System.out.printf("Gstreamer version %d.%d.%d%s initialized!",
                version.getMajor(), version.getMinor(), version.getMicro(),
                version.getNano() == 1 ? " (CVS)" : version.getNano() >= 2 ? " (Pre-release)" : "");
    }

    public static void addLibraryPath(String property, String libraryPath){
        String path = System.getProperty(property);
        try {
            if(path == null){
                path = "";
            }
            if (!path.contains(libraryPath)) {
                path = libraryPath + ";" + path;
                System.setProperty(property, path);
                final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
                sysPathsField.setAccessible(true);
                sysPathsField.set(null, null);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void initPath(){
        addLibraryPath("java.library.path", "E:\\gstreamer\\1.0\\mingw_x86_64\\bin");
        addLibraryPath("jna.library.path", "E:\\gstreamer\\1.0\\mingw_x86_64\\bin");
        addLibraryPath("path", "E:\\gstreamer\\1.0\\mingw_x86_64\\bin;E:\\gstreamer\\1.0\\mingw_x86_64\\lib\\gstreamer-1.0");
    }
}
