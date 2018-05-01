import java.util.*;

public class UniqueIdAlphaNumeric {
	private final TrieNode root;
	String prevId;
	public static void main(String[] args) {
		String[] words = {"ABC", "ABB"};
		UniqueIdAlphaNumeric UID = new UniqueIdAlphaNumeric(words);
		System.out.println(UID.generateNewId());
	}
	
	public UniqueIdAlphaNumeric(String[] words) {
		root = new TrieNode();
		prevId = "ABC99Z";
		for(String word : words) {
			insertInTrie(word);
		}
	}
	
	public String generateNewId() {
		String currId = addOne(prevId, prevId.length() - 1);
		int i;
		while((i = detectReservedWord(currId)) != -1) {
			currId = addOne(currId, i);
		}
		prevId = currId;
		return currId;
	}
	
	private String addOne(String Id, int offset) {
		int i = offset;
		StringBuilder sb = new StringBuilder();
		sb.append(Id.substring(offset+1));
		
		while(i>=0 && addOneHelper(Id.charAt(i)) == (char)('Z' + 1)) {
			sb.insert(0, '0');
			i--;
		}
		if(i==-1) {
			sb.insert(0, '1');
		} else {
			sb.insert(0, addOneHelper(Id.charAt(i)));
			sb.insert(0,  Id.substring(0, i));
		}
		
		return sb.toString();
	}

	private char addOneHelper(char c) {
		if(c == '9')
			return 'A';
		
		return (char)(c + 1);
	}

	private int detectReservedWord(String Id) {
		for(int i=0; i<Id.length(); i++) {
			TrieNode current = root;
			for(int j=i; j<Id.length(); j++) {
				char ch = Id.charAt(j);
				TrieNode node = current.children.get(ch);
				if(node == null)
					break;
				if(node.isEnd)
					return j - 1;
				current = node;
			}
		}
		return -1;
	}

	public void insertInTrie(String word) {
		TrieNode current = root;
		for(int i=0; i < word.length(); i++) {
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			if(node == null) {
				node = new TrieNode();
				current.children.put(ch,  node);
			}
			current = node;
		}
		current.isEnd = true;
	}
	
	class TrieNode {
		public Map<Character, TrieNode> children;
		public boolean isEnd;
		
		public TrieNode() {
			children = new HashMap<>();
			isEnd = false;
		}
	}

}

