import java.util.ArrayList;
import java.lang.Math;

public class CengHashTable {

	public Integer prefixLength;
	public int hashLength = (int)(Math.log(CengPokeKeeper.getHashMod()) / Math.log(2));
	public ArrayList<CengHashRow> rows;


	public CengHashTable()
	{
		// TODO: Create a hash table with only 1 row.
		this.rows = new ArrayList<CengHashRow>();
		this.prefixLength = 0;
		CengHashRow tmp = new CengHashRow("0");
		this.rows.add(tmp);
	}

	public void deletePoke(Integer pokeKey)
	{
		// TODO: Empty Implementation

		Integer hashValueOfPokeKey = pokeKey % CengPokeKeeper.getHashMod();
		String hashPrefix = String.format("%"+Integer.toString(hashLength)+"s",Integer.toBinaryString(hashValueOfPokeKey)).replace(' ','0');
		Integer rowIndex;
		if(prefixLength == 0) {
			rowIndex = 0;
		}
		else {
			String hashPre = hashPrefix.substring(0, prefixLength);
			rowIndex = Integer.parseInt(hashPre,2);
		}
		for(int k = 0; k < Math.pow(2,this.prefixLength - this.rows.get(rowIndex).getBucket().getHashPrefix()); k++) {
			CengHashRow it = this.rows.get(k + rowIndex);
			CengBucket bucket = it.getBucket();
			for(int i = 0; i < bucket.pokeCount(); i++) {
				CengPoke poket = bucket.pokeAtIndex(i);
				if(poket.pokeKey() == pokeKey) {
					bucket.pokes.remove(i);
					break;
				}
			}
		}
		Integer count = 0;
		for(int i = 0; i < Math.pow(2,this.prefixLength - this.rows.get(rowIndex).getBucket().getHashPrefix()); i++) {
			CengHashRow it2 = this.rows.get(i + rowIndex);
			CengBucket bucket2 = it2.getBucket();
			if(bucket2.pokeCount() == 0 && !bucket2.isVisited()) {
				count++;
				bucket2.isVisited = true;
			}
		}
		System.out.println("\"delete\": {");
		System.out.println("\t\"emptyBucketNum\": " + Integer.toString(count));
		System.out.println("}");
	}

	public void addPoke(CengPoke poke)
	{			
		// TODO: Empty Implementation
		if(prefixLength == 0) {
			CengHashRow it = this.rows.get(0);
			if(it.getBucket().pokeCount() == CengPokeKeeper.getBucketSize()) {
				this.prefixLength++;
				CengHashRow nOne = new CengHashRow("1");
				it.setHashPrefix("0");
				it.getBucket().prefixLength++;
				for(int k = 0; k < it.getBucket().pokeCount(); k++) {
					CengPoke cPoke = it.getBucket().pokeAtIndex(k);
					Integer hashOfPoke = cPoke.hashValue();
					String hashPrefix = String.format("%"+Integer.toString(hashLength)+"s",Integer.toBinaryString(hashOfPoke)).replace(' ','0');
					String hashPre = hashPrefix.substring(0, prefixLength);
					if(hashPre.equalsIgnoreCase("1")) {
						it.getBucket().pokes.remove(cPoke);
						nOne.getBucket().addPoke(cPoke);
					}
				}
				this.rows.add(nOne);
				addPoke(poke);
			}
			else {
				it.getBucket().addPoke(poke);
			}
		}
		else {
			String hashPrefix = String.format("%"+Integer.toString(hashLength)+"s",Integer.toBinaryString(poke.hashValue())).replace(' ','0');
			String hashPre = hashPrefix.substring(0,prefixLength);
			Integer rowIndex = Integer.parseInt(hashPre,2);
			CengHashRow it = this.rows.get(rowIndex);
			if(it.getBucket().pokeCount() != CengPokeKeeper.getBucketSize()) {
				it.getBucket().addPoke(poke);
			}
			else {
				if(this.prefixLength == it.getBucket().getHashPrefix()) {
					this.prefixLength++;
					extend(rowIndex);
					addPoke(poke);
				}
				else {
					Integer preLen = it.getBucket().getHashPrefix();
					String buckPre = it.hashPrefix().substring(0,preLen);
					for(int j = 0; j < this.rows.size(); j++) {
						if((rows.get(j).hashPrefix().substring(0, preLen)).equalsIgnoreCase(buckPre)) {
							it.getBucket().prefixLength++;
							CengBucket nOne = new CengBucket(it.getBucket().getHashPrefix());
							for(int i = j; i < (Math.pow(2,this.prefixLength - preLen) + j); i++) {
								if(i < j + (Math.pow(2,this.prefixLength - preLen) / 2)) {
									this.rows.get(i).setBucket(it.getBucket());
								}
								else {
									this.rows.get(i).setBucket(nOne);
								}
							}
							int a = rows.get(j).getBucket().pokeCount();
							for(int k = 0; k < a; k++) {
								CengPoke m = rows.get(j).getBucket().pokeAtIndex(0);
								rows.get(j).getBucket().pokes.remove(m);
								addPoke(m);
							}
							addPoke(poke);
							break;
						}
					}
				}
			}
		}
	}
	
	public void searchPoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
		Boolean found = false;
		Boolean first = true;
		System.out.println("\"search\": {");
		Integer hashValueOfPokeKey = pokeKey % CengPokeKeeper.getHashMod();
		String hashPrefix = String.format("%"+Integer.toString(hashLength)+"s",Integer.toBinaryString(hashValueOfPokeKey)).replace(' ','0');
		Integer rowIndex;
		if(prefixLength == 0) {
			rowIndex = 0;
		}
		else {
			String hashPre = hashPrefix.substring(0, prefixLength);
			rowIndex = Integer.parseInt(hashPre,2);
		}
		for(int k = 0; k < Math.pow(2,this.prefixLength - this.rows.get(rowIndex).getBucket().getHashPrefix()); k++) {
			CengHashRow it = this.rows.get(k + rowIndex);
			CengBucket bucket = it.getBucket();
			for(int i = 0; i < bucket.pokeCount(); i++) {
				CengPoke poket = bucket.pokeAtIndex(i);
				if(poket.pokeKey() == pokeKey) {
					found = true;
					if(!first) {
						System.out.print(",\n");
					}
					System.out.println("\t\"row\": {");
					System.out.println("\t\t\"hashPref\": " + it.hashPrefix() + ",");
					System.out.println("\t\t\"bucket\": {");
					System.out.println("\t\t\t\"hashLength\": " + Integer.toString(bucket.getHashPrefix()) + ",");
					System.out.println("\t\t\t\"pokes\": [");
					for(int j = 0; j < bucket.pokeCount(); j++) {
						CengPoke poke = bucket.pokeAtIndex(j);
						System.out.println("\t\t\t\t\"poke\": {");
						System.out.println("\t\t\t\t\t\"hash\": " + String.format("%"+Integer.toString(hashLength)+"s",Integer.toBinaryString(poke.hashValue())).replace(' ','0') + ",");
						System.out.println("\t\t\t\t\t\"pokeKey\": " + Integer.toString(poke.pokeKey()) + ",");
						System.out.println("\t\t\t\t\t\"pokeName\": " + poke.pokeName() + ",");
						System.out.println("\t\t\t\t\t\"pokePower\": " + poke.pokePower() + ",");
						System.out.println("\t\t\t\t\t\"pokeType\": " + poke.pokeType());
						if(j != bucket.pokeCount()-1) {
							System.out.println("\t\t\t\t},");
						}
						else {
							System.out.println("\t\t\t\t}");
						}
					}
					System.out.println("\t\t\t]");
					System.out.println("\t\t}");
					System.out.print("\t}");
					first = false;
				}
			}
		}
		if(found) {
			System.out.println("\n}");
		}
		else {
			System.out.println("}");
		}

	}
	
	public void print()
	{
		// TODO: Empty Implementation
		System.out.println("\"table\": {");
		for(int i = 0; i < rowCount(); i++) {
			System.out.println("\t\"row\": {");
			CengHashRow row = this.rows.get(i);
			System.out.println("\t\t\"hashPref\": " + row.hashPrefix() + ",");
			System.out.println("\t\t\"bucket\": {");
			CengBucket bucket = row.getBucket();
			System.out.println("\t\t\t\"hashLength\": " + Integer.toString(bucket.getHashPrefix()) +",");
			System.out.println("\t\t\t\"pokes\": [");
			for(int j = 0; j < bucket.pokeCount(); j++) {
				CengPoke poke = bucket.pokeAtIndex(j);
				System.out.println("\t\t\t\t\"poke\": {");
				System.out.println("\t\t\t\t\t\"hash\": " + String.format("%"+Integer.toString(hashLength)+"s",Integer.toBinaryString(poke.hashValue())).replace(' ','0') + ",");
				System.out.println("\t\t\t\t\t\"pokeKey\": " + Integer.toString(poke.pokeKey()) + ",");
				System.out.println("\t\t\t\t\t\"pokeName\": " + poke.pokeName() + ",");
				System.out.println("\t\t\t\t\t\"pokePower\": " + poke.pokePower() + ",");
				System.out.println("\t\t\t\t\t\"pokeType\": " + poke.pokeType());
				if(j != bucket.pokeCount()-1) {
					System.out.println("\t\t\t\t},");
				}
				else {
					System.out.println("\t\t\t\t}");
				}

			}
			System.out.println("\t\t\t]");
			System.out.println("\t\t}");
			if(i != rowCount()-1) {
				System.out.println("\t},");
			}
			else {
				System.out.println("\t}");
			}
		}
		System.out.println("}");
	}

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public int prefixBitCount()
	{
		// TODO: Return table's hash prefix length.
		return prefixLength;
	}
	
	public int rowCount()
	{
		// TODO: Return the count of HashRows in table.
		return rows.size();
	}
	
	public CengHashRow rowAtIndex(int index)
	{
		// TODO: Return corresponding hashRow at index.
		return this.rows.get(index);
	}

	public void extend(int sIndex) {
		for(int i = 0; i < Math.pow(2,prefixLength); i += 2) {
			if(i == (sIndex*2)) {
				CengHashRow nRow = this.rows.get(i);
				String oldPrefix = nRow.hashPrefix();
				CengHashRow nOne = new CengHashRow(oldPrefix+"1");
				nRow.setHashPrefix(oldPrefix+"0");
				for(int j = 0; j < nRow.getBucket().pokeCount(); j++) {
					CengPoke cPoke = nRow.getBucket().pokeAtIndex(j);
					Integer hashOfPoke = cPoke.hashValue();
					String hashPre = String.format("%"+Integer.toString(hashLength)+"s",Integer.toBinaryString(hashOfPoke)).replace(' ','0');
					String hashPrePoke = hashPre.substring(0, prefixLength);
					if(hashPrePoke.equalsIgnoreCase(oldPrefix + "1")) {
						nOne.getBucket().addPoke(cPoke);
						nRow.getBucket().pokes.remove(cPoke);
						j--;
					}
				}
				nRow.getBucket().prefixLength++;
				nOne.getBucket().prefixLength = prefixLength;
				this.rows.add(i+1,nOne);
			}
			else {
				CengHashRow nRow = this.rows.get(i);
				CengHashRow nOne = new CengHashRow(nRow.hashPrefix()+"1");
				nOne.setBucket(nRow.getBucket());
				this.rows.add(i+1,nOne);
				nRow.setHashPrefix(nRow.hashPrefix()+"0");
			}
		}
	}
	
	// Own Methods
}
