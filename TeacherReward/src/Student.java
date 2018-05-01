import java.util.*;

public class Student {
	Integer index;
	Integer score;
	Integer numCandy;
	
	public Student(int index, int score, int numCandy) {
		this.index = index; 
		this.score = score;
		this.numCandy = numCandy;
	}
	
	public int hashCode() {
		return index.hashCode() + score.hashCode();
	}
	
	public boolean equals(Object obj) {
		Student s = (Student) obj;
		return s.index.equals(index) && s.score.equals(score);
	}
	public String toString() {
		return Integer.toString(index) + " "  + Integer.toString(score) + " " + Integer.toString(numCandy);
		
	}
}
