import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class Artist {

	String name;
	public String[] similarArtist;
	public String[] advancedSimilarArtist;
	public String[] playlist; /* requires user input */
	
	public Artist(String startName) {
		
		name = startName;
		similarArtist = new String[20];
	}
	
	public Artist(String startName, String[] startSimilarArtist, String[] startAdvancedSimilarArtist, String[] startPlaylist) {
		
		name = startName;
		similarArtist = startSimilarArtist;
		advancedSimilarArtist = startAdvancedSimilarArtist;
		playlist = startPlaylist;
	}
	
	public void setSimilarArtist() throws UnsupportedEncodingException, IOException {
		
		// System.out.println(name);
		
		name.replace(" ", "+");
		
		// System.out.println("http://www.last.fm/music/" + name + "/+similar");
		
		URL url = new URL("http://www.last.fm/music/" + name + "/+similar");
		
		// System.out.println(url.toString());
		
    	int artistNum = 0;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {

		    	if (line.contains("Image for '")) {
			    	
		    		// System.out.println(line.substring(0, line.length() - 2).substring(48));
		    		
		    		similarArtist[artistNum++] = new String(line.substring(0, line.length() - 2).substring(48));
		    	}
		    }
		}
		
		// System.out.println(similarArtist.length);
		
		for (int i = 0; i < similarArtist.length - 1; i++) {
			
			System.out.println(similarArtist[i]);
		}
	}
}
