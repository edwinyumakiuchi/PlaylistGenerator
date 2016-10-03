import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Executor {

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter an artist: ");
		String inputArtist = reader.nextLine();
		
		// String inputArtist = "Gorillaz";
		
		// System.out.println("inputArtist = " + inputArtist);
		
		Artist lastfmArtist = new Artist(inputArtist);
		Artist firstArtist = lastfmArtist;
		Artist[] similarArtists = new Artist[20];
		
		int similarArtistIndex = 0;
		
		for (int i = 0; i < similarArtists.length; i++) {
			
			lastfmArtist.setSimilarArtist();
			firstArtist.setAdvancedSimilarArtist(lastfmArtist.similarArtist, i);
			// similarArtists[i] = lastfmArtist;
			
			if (firstArtist.similarArtist[similarArtistIndex] != null)
				lastfmArtist = new Artist(firstArtist.similarArtist[similarArtistIndex++].name);
		}
		
		firstArtist.sortPlaylist();
		firstArtist.printPlaylist();
	}

}
