import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

public class Artist implements Comparable<Artist>{

	public String name = "";
	public Artist[] similarArtist = new Artist[20];
	public Artist[] advancedSimilarArtist = new Artist[400];
	public int advancedSimilarArtistIndex = 0;
	public Artist[] playlist = new Artist[400];
	public int playlistIndex = 0;
	public int scrobble = 0;
	public int value = 0;
	
	public Artist(String startName) throws UnsupportedEncodingException, IOException {
		
		name = startName;
		
		this.setScrobble();
	}
	
	public void setSimilarArtist() throws UnsupportedEncodingException, IOException {
		
		// System.out.println(name);
		
		String formattedName = java.net.URLEncoder.encode(name.replace(" ", "+"), "UTF-8");
		// String result = java.net.URLEncoder.encode("Äppelknyckarjazz", "UTF-8");
		
		// System.out.println("http://www.last.fm/music/" + name + "/+similar");
		
		URL url = new URL("http://www.last.fm/music/" + formattedName + "/+similar");
		
		// System.out.println(url.toString());
		
    	int artistNum = 0;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {

		    	if (line.contains("Image for '")) {
			    	
		    		// System.out.println(line.substring(0, line.length() - 2).substring(48));
		    		
		    		line = line.replaceAll("&amp;", "&");
		    		line = line.replaceAll("&#39;", "'");
		    		
		    		similarArtist[artistNum++] = new Artist(line.substring(0, line.length() - 2).substring(48));
		    	}
		    }
		}
		
		// System.out.println(similarArtist.length);
		
		/* System.out.println(name);
		
		for (int i = 0; i < similarArtist.length - 1; i++) {
			
			System.out.println(similarArtist[i].name);
		}
		
		System.out.println("==========================================="); */
	}
	
	public void setAdvancedSimilarArtist(Artist[] similarArtistList, int indexOfHeaderArtist) throws UnsupportedEncodingException, IOException {
		
		if (indexOfHeaderArtist == 0)
			advancedSimilarArtist[advancedSimilarArtistIndex++] = new Artist(this.name);
		else 
			advancedSimilarArtist[advancedSimilarArtistIndex++] = new Artist(this.similarArtist[indexOfHeaderArtist-1].name);
		
		System.out.println(advancedSimilarArtist[advancedSimilarArtistIndex-1].name);
		
		for (int i = 0; i < similarArtistList.length - 1; i++) {
			
			// System.out.println("advancedSimilarArtistIndex = " + advancedSimilarArtistIndex);
			advancedSimilarArtist[advancedSimilarArtistIndex++] = new Artist(similarArtistList[i].name);
			System.out.println(advancedSimilarArtist[advancedSimilarArtistIndex-1].name);
		}
		
		System.out.println("===========================================");
	}
	
	public void sortPlaylist() {
		
		// Arrays.sort(advancedSimilarArtist);
		
		for (int i = 0; i < advancedSimilarArtist.length; i++) {
			
			boolean artistInserted = false;
			
			for (int j = 0; j < playlistIndex; j++) {
				
				if (playlist[j].name.equals(advancedSimilarArtist[i].name)) {
				
					playlist[j].value += (advancedSimilarArtist[i].scrobble + 1);
					artistInserted = true;
				}
			}
			
			if (!artistInserted) {
				playlist[playlistIndex] = advancedSimilarArtist[i];
				playlist[playlistIndex++].value = advancedSimilarArtist[i].scrobble + 1;
			}
			
			
			/* if (!Arrays.asList(playlist).contains(advancedSimilarArtist[i])) {
				
				playlist[playlistIndex] = advancedSimilarArtist[i];
				playlist[playlistIndex++].value = advancedSimilarArtist[i].scrobble + 1;
			}
			else {
				
				playlist[Arrays.asList(playlist).indexOf(advancedSimilarArtist[i])].value += advancedSimilarArtist[i].scrobble + 1; 
			} */
		}
		
		Arrays.sort(playlist, new Comparator<Artist>() {
			
			public int compare(Artist o1, Artist o2) {
				
				if (o1 == null && o2 == null)
					return 0;
				else if (o1 == null)
					return 1;
				else if (o2 == null)
					return -1;
				else return o2.value - o1.value;
			}
		});
	}
	
	public void printAdvancedSimilarArtist() {
		
		for (int i = 0; i < advancedSimilarArtist.length; i++)
			System.out.println(advancedSimilarArtist[i].name + " " + advancedSimilarArtist[i].scrobble);
	}
	
	public void printPlaylist() {
		
		for (int i = 0; i < playlistIndex; i++)
			System.out.println(playlist[i].name + " " + playlist[i].value);
	}
	
	public void setScrobble() throws UnsupportedEncodingException, IOException {
		
		String formattedName = java.net.URLEncoder.encode(name.replace(" ", "+"), "UTF-8");
		
		URL url = new URL("http://www.last.fm/user/edwinyumakiuchi/library/music/" + formattedName);
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {

		    	if (line.contains("<h2 class=\"metadata-title\">Scrobbles</h2>")) {
			    	
		    		line = reader.readLine();
		    		
		    		scrobble = Integer.parseInt(line.substring(0, line.length() - 4).substring(56));
		    		// System.out.println("setScrobble: line = " + line.substring(0, line.length() - 4).substring(56));
		    		
		    		break;
		    	}
		    }
		}
	}

	@Override
	public int compareTo(Artist o) {
		
		int returnValue = 0;
		
		if (o == null)
			returnValue = 1;
		else if (this == null)
			returnValue = -1;
		else returnValue = o.value - this.value;
		
		return returnValue;
	}
	
	/* public boolean equals(Artist o) {
		
		if (o.name.equals(name))
			return true;
		else return false;
	} */
}
