import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Executor {

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter an artist: ");
		String inputArtist = reader.nextLine();
		
		// System.out.println("inputArtist = " + inputArtist);
		
		Artist iTunesArtist = new Artist(inputArtist);
		
		iTunesArtist.setSimilarArtist();
	}

}
