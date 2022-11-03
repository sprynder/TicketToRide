
public class Ticket {

	private int value;
	private City start, end;
	private boolean completed;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public City getStart() {
		return start;
	}

	public void setStart(City start) {
		this.start = start;
	}

	public City getEnd() {
		return end;
	}

	public void setEnd(City end) {
		this.end = end;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
