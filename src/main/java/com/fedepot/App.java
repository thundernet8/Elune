package com.fedepot;

import com.fedepot.Razor;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        Razor razor = Razor.self();

        razor.listen("0.0.0.0", 9000);
        razor.start(App.class, args);

        System.out.println( "App started" );
    }
}
