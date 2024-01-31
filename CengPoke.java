public class CengPoke {
	
	private Integer pokeKey;
	
	private String pokeName;
	private String pokePower;
	private String pokeType;

	private Integer hashValue;
	
	public CengPoke(Integer pokeKey, String pokeName, String pokePower, String pokeType)
	{
		this.pokeKey = pokeKey;
		this.pokeName = pokeName;
		this.pokePower = pokePower;
		this.pokeType = pokeType;
		setHashValue(CengPokeKeeper.getHashMod());
	}
	
	// Getters
	
	public Integer pokeKey()
	{
		return pokeKey;
	}
	public String pokeName()
	{
		return pokeName;
	}
	public String pokePower()
	{
		return pokePower;
	}
	public String pokeType()
	{
		return pokeType;
	}
		
	// GUI method - do not modify
	public String fullName()
	{
		return "" + pokeKey() + "\t" + pokeName() + "\t" + pokePower() + "\t" + pokeType;
	}
		
	// Own Methods

	public Integer hashValue() {
		return hashValue;
	}

	public void setHashValue(int hashMod)  {
		hashValue = this.pokeKey % hashMod;
	}
}
