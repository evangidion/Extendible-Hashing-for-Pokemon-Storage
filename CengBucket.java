import java.util.ArrayList;

public class CengBucket {

	// GUI-Based Methods
	// These methods are required by GUI to work properly.

	public int prefixLength;
	public Boolean isVisited;
    public ArrayList<CengPoke> pokes;

	public CengBucket(int prefixLength) {
		this.prefixLength = prefixLength;
		this.pokes = new ArrayList<CengPoke>();
		this.isVisited = false;
	}

	public void addPoke(CengPoke poke) {
		this.pokes.add(poke);
	}

	public void removePoke(Integer index) {
		this.pokes.remove(index);
	}

	public int pokeCount()
	{
		// TODO: Return the pokemon count in the bucket.
		return this.pokes.size();
	}
	
	public CengPoke pokeAtIndex(int index)
	{
		// TODO: Return the corresponding pokemon at the index.
		return this.pokes.get(index);
	}
	
	public int getHashPrefix()
	{
		// TODO: Return hash prefix length.
		return this.prefixLength;
	}
	
	public Boolean isVisited()
	{
		// TODO: Return whether the bucket is found while searching.
		return this.isVisited;
	}
	
	// Own Methods
}
