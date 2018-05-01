import java.util.*;

public class UniqueID {
	String prevId;
	int k;
	public static void main(String[] args) {
		UniqueID UID = new UniqueID(3);
		System.out.println(UID.generateNewId());
	}
	public UniqueID(int k) {
		prevId = "999111";
		this.k = k;
	}
	
	public String generateNewId() {
		String currId = addOne(prevId, prevId.length() - 1);
		int i;
		while((i = detecKConsecutive(currId)) != -1) {
			currId = addOne(currId, i);
		}
		prevId = currId;
		return currId;
	}

	private int detecKConsecutive(String Id) {
		int slow = 0, fast = 0, count = 0;
		
		while(fast < Id.length()) {
			while(fast < Id.length() && Id.charAt(slow) == Id.charAt(fast)) {
				count++;
				if(count == k) {
					return fast;
				}
				fast++;
			}
			slow++;
			count = 0;
		}

		return -1;
	}

	private String addOne(String Id, int offset) {
		int i = offset;
		StringBuilder sb = new StringBuilder();
		sb.append(Id.substring(offset+1));
		
		while(i>=0 && toInt(Id.charAt(i)) + 1 == 10) {
			sb.insert(0, '0');
			i--;
		}
		if(i==-1) {
			sb.insert(0, '1');
		} else {
			sb.insert(0, (char)(Id.charAt(i) +1));
			sb.insert(0,  Id.substring(0, i));
		}
		
		return sb.toString();
	}

	private int toInt(char c) {
		return (int) c - '0';
	}
}
