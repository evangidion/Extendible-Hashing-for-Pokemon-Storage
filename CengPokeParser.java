import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CengPokeParser {

	public static ArrayList<CengPoke> parsePokeFile(String filename)
	{
		ArrayList<CengPoke> pokeList = new ArrayList<CengPoke>();

		// You need to parse the input file in order to use GUI tables.
		// TODO: Parse the input file, and convert them into CengPokes
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line;
			while((line = reader.readLine()) != null) {
				String[] arr = line.split("\\t");
				int key = Integer.parseInt(arr[1]);
				String name = arr[2];
				String power = arr[3];
				String type = arr[4];
				CengPoke poke = new CengPoke(key, name, power, type);
				pokeList.add(poke);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		return pokeList;
	}
	
	public static void startParsingCommandLine() throws IOException
	{
		// TODO: Start listening and parsing command line -System.in-.
		// There are 5 commands:
		// 1) quit : End the app. Print nothing, call nothing.
		// 2) add : Parse and create the poke, and call CengPokeKeeper.addPoke(newlyCreatedPoke).
		// 3) search : Parse the pokeKey, and call CengPokeKeeper.searchPoke(parsedKey).
		// 4) delete: Parse the pokeKey, and call CengPokeKeeper.removePoke(parsedKey).
		// 5) print : Print the whole hash table with the corresponding buckets, call CengPokeKeeper.printEverything().

		// Commands (quit, add, search, print) are case-insensitive.
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] arr = line.split("\\t");
				if(arr[0].equalsIgnoreCase("add")) {
					int key = Integer.valueOf(arr[1]);
					String name = arr[2];
					String power = arr[3];
					String type = arr[4];
					CengPoke poke = new CengPoke(key,name,power,type);
					CengPokeKeeper.addPoke(poke);
				}
				else if(arr[0].equalsIgnoreCase("search")) {
					Integer key = Integer.valueOf(arr[1]);
					CengPokeKeeper.searchPoke(key);
				}
				else if(arr[0].equalsIgnoreCase("delete")) {
					Integer key = Integer.valueOf(arr[1]);
					CengPokeKeeper.deletePoke(key);
				}
				else if(arr[0].equalsIgnoreCase("print")) {
					CengPokeKeeper.printEverything();
				}
				else if(arr[0].equalsIgnoreCase("quit")) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}
}
