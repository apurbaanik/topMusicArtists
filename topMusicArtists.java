/**
 * @author Anik Barua
 * @since 10-02-2020
 * @version 2.0
 *
 * Description: Lab #3 - This java program print outs an organized data of music
 * artists in alphabetical order by Artist's name from Spotify's Top 200 Artists
 * CSV dataset. It uses LinkedList to store the data, removes duplicates, and
 * sorts the artist's name in ascending order.
 */

import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class topMusicArtists { // class started

    public static void main(String[] args) throws Exception { //main method

        PrintStream report = new PrintStream("ArtistsSorted-WeekOf08302020.txt");
        // This output file will contain the data in ascending order by Artist name.

        //I am using the Multi-Dimensinal array from lab 2 to read the data from CSV.
        int row = 200;
        int column = 5; //Row and Column number for multi-dimensinal array

        String[][] array = new String[row][column];
        //Multi-Dimensinal array that will contain readings(Artist's data) from the csv file.

        //Read in the csv file part 
        try {
            Scanner sc = new Scanner(new File("spotify_music.csv"));
            for (int i = 0; i < row; i++) {
                String[] line = sc.nextLine().split(","); // Splits words by "," from each line
                for (int j = 0; j < column; j++) {
                    array[i][j] = line[j];
                }
            }
            sc.close(); //Scanner closed
        } catch (FileNotFoundException e) {
            e.getMessage();
        } // End of try and catch block

        TopStreamingArtists artistList = new TopStreamingArtists();
        //Creates a linkedlist of artistList that will hold artist objects

        //Using for loop, artists linkedlist is reading the data from the Multi-Dimensinal Array.
        for (int i = 0; i < row; i++) {
            // Create a Artist Object
            Artist artist = new Artist(Integer.parseInt(array[i][0]), array[i][1], array[i][2],
                    Integer.parseInt(array[i][3]), array[i][4]);
            // Add the Artist in Artist LinkedList
            artistList.add(artist);
        }

        artistList.removeDuplicates(); //Using a method from the LinkedList class,
        // it removes the duplicates from the list. 

        artistList.sort(); //Using a method from LinkedList class, it sorts the data 
        //in ascending order by Artists name.

        artistList.displayList(report); //Prints out the report in the output file.
    }
}

/*
This is a LinkedList class for the Artist's object. It uses the LinkedList class
from the Java library. It contains general LinkedList methods and I added some 
new methods for sort, print, and to remove duplicates. 
 */
class TopStreamingArtists { //Class started

    private LinkedList<Artist> artistList; //LinkedList of Artist object

    public TopStreamingArtists() {
        artistList = new LinkedList(); //Instantiates a linkedlist with new keyword
    }

    public void add(Artist x) {
        artistList.add(x); //Adds Artist objects in the linkedlist
    }

    public void remove() {
        artistList.remove(); //Removes the last Artist object from the linkedlist
    }

    public LinkedList<Artist> getLinkedList() {
        return artistList;  //Returns the full linkedlist
    }

    public int size() {
        return artistList.size(); //Returns the size of linkedlist
    }

    /*
    Since linkedlist is a type of collection, I am using the collections library sort
    method to sort my Artist's LinkedList in ascending order. To use the collections.sort
    method, I implemented the comparable interface in my Artist class and created a 
    compareTo method so that it can sort by comparing the name of Artists. 
     */
    public void sort() {
        //Collections.sort() method from the java.util.Collections class
        Collections.sort(artistList);
    }

    /*
    This removeDuplicates method removes duplicates from the LinkedList. 
     */
    public void removeDuplicates() {

        // Using LinkedHashMap map because it will not read any duplicates, and I can
        // change the map back to LinkedList and assign the values to my current LinkedList.
        LinkedHashMap<String, Artist> map = new LinkedHashMap<>();

        //Using this for loop, the map uses the name of the artist's as a key and the 
        // Artist list as a value. 
        for (int i = 0; i < 200; i++) {
            map.put(artistList.get(i).getName(), artistList.get(i));
        }
        // Assigning the map values to my artistList. Now my LinkedList doesn't
        //contain any duplicates. 
        this.artistList = new LinkedList<>(map.values());
    }

    // Prints out the report in sorted artist name with no duplicates
    public void displayList(PrintStream ps) {
        int count = 0; //To keep track of how many artist's are in my list
        Iterator x = artistList.iterator(); //Using an Iterator to loop through my linkedlist
        while (x.hasNext()) {
            count++;
            ps.println(count + " " + x.next().toString());
            ps.println();
        }
    }
} //end of TopStreamingArtists class

/*
Artist class takes all the information of an artist and creates an Artist object. 
It implements a comparable method so that it can compare the artists with their names 
and sort it in asecending order. 
 */
class Artist implements Comparable { //Class started

    //The csv files contains the position, artist's name, track name, total streams and the url.
    private int position, streams;
    private String artist, trackName, url;

    // Constructor takes the postion, song name, artist's name, total streams, and the url
    public Artist(int position, String trackName, String artist, int streams, String url) {
        this.position = position;
        this.trackName = trackName;
        this.artist = artist;
        this.streams = streams;
        this.url = url;
    }

    // Returns the name of the artist.
    public String getName() {
        return this.artist;
    }

    // Returns a string with all the data of the artists. 
    @Override
    public String toString() {
        return "Artist Name: " + this.artist + ", Position: " + this.position
                + ", Track Name: " + this.trackName + ", Streams: " + this.streams
                + ", URL: " + this.url;
    }

    // This compareTo methods allows the Collection.sort from the linkedlist class,
    // to sort the list based on the artist's name. 
    @Override
    public int compareTo(Object obj) { //Takes an object of type Object
        Artist name = (Artist) obj; // Casting the object to Artist class. 
        int x = this.artist.compareTo(name.artist); //Comparing the names
        return x;
    }
}// end of Artist class