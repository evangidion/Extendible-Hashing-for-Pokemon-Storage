import java.util.ArrayList;

public class CengBucketList {

	public ArrayList<CengBucket> bucketList;

	public CengBucketList()
	{
		// TODO: Empty Implementation
		this.bucketList = new ArrayList<CengBucket>();
	}

	public void deletePoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
	}

	public void addPoke(CengPoke poke)
	{
		// TODO: Empty Implementation
	}
	
	public void searchPoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
	}
	
	public void print()
	{
		// TODO: Empty Implementation
	}
	
	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public int bucketCount()
	{
		// TODO: Return all bucket count.
		return bucketList.size();
	}
	
	public CengBucket bucketAtIndex(int index)
	{
		// TODO: Return corresponding bucket at index.
		return bucketList.get(index);
	}
	
	// Own Methods
}
