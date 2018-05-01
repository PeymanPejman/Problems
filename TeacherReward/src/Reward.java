import java.util.*;
import java.util.stream.Stream;

public class Reward {
	Map<Student, List<Student>> adjacency;
	Integer[] scores;
	Integer[] candies;
	List<Student> localMins;
	Map<Integer, Student> students;

	public static void main(String[] args) {
		Integer[] scores = {5, 4, 1, 2, 3, 10, 13, 11, 3};
		Reward r = new Reward(scores);
		r.rewardStudents();
		System.out.println(Arrays.toString(r.candies));
		System.out.println(Stream.of(r.candies).mapToInt(i -> i).sum());
	}
	
	public Reward(Integer[] scores) {
		adjacency = new HashMap<>();
		this.scores = scores;
		candies = new Integer[scores.length];
		localMins = new ArrayList<>();
		students = new HashMap<>();
		createStudents();
		buildAdjecencyGraph();
	}

	private void createStudents() {
		for(int i=0; i<scores.length; i++) {
			students.put(i, new Student(i, scores[i], 0));
		}
	}

	public void rewardStudents() {
		for(Student s : localMins) {
			awardCandyDFS(s, 0);
		}
		 populateCandiesArray();
	}
	
	
	private void awardCandyDFS(Student s, int candy) {
		s.numCandy = Math.max(s.numCandy, candy +1);
		
		for(Student next : adjacency.get(s)) {
			awardCandyDFS(next, s.numCandy);
		}
	}

	private void buildAdjecencyGraph() {		
		for(int i=0; i<scores.length; i++) {
			
			Student s = students.get(i);

			if(!adjacency.containsKey(s)) {
				adjacency.put(s,  new ArrayList<>());
			}
			
			if(i>0 && scores[i-1] > scores[i]) {
				adjacency.get(s).add(students.get(i-1));
			}
			if(i<scores.length-1 && scores[i+1] > scores[i]) {
				adjacency.get(s).add(students.get(i+1));
			}
			
			if(localMin(i)) {
				localMins.add(s);
			}
		}
	}
	
	private boolean localMin(int i) {
		if(i==0)
			return scores[i] < scores[i+1];
		if(i==scores.length-1)
			return scores[i] < scores[i-1];
		
		return scores[i]<scores[i+1] && scores[i]<scores[i-1];
	}
	
	private void populateCandiesArray() {	
		for(Map.Entry<Student, List<Student>> entry : adjacency.entrySet()) {
			candies[entry.getKey().index] = entry.getKey().numCandy;
		}
	}
}
