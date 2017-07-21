package org.weilabs;

import org.weilabs.archiver.BZip2;
import org.weilabs.archiver.GZip;
import org.weilabs.archiver.Tar;
import org.weilabs.archiver.Zip;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String[] a = {"a","b","1"};
        List lst1 = new ArrayList<String>();
        lst1.add("d");
        Collections.addAll(lst1,a);
        System.out.println( lst1);

        Set<String> set1 = new HashSet<String>();
        set1.add("d");
        Collections.addAll(set1,a);
        System.out.println( set1);


        System.out.println(0 + 2*2);


    }
}
