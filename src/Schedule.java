
public class Schedule {
	private String name;
	private String description;
	private int yStart;
	private int mStart;
	private int dStart;
	private int yEnd;
	private int mEnd;
	private int dEnd;
	private boolean onMonday;
	private boolean onTuesday;
	private boolean onWednesday;
	private boolean onThursday;
	private boolean onFriday;
	private boolean onSaturday;
	private boolean onSunday;
	private int delay;
	
	public Schedule(String n, String dsc, int yS, int mS, int dS, int yE, int mE, int dE, boolean onMo, boolean onTu, boolean onWe, boolean onTh, boolean onFr, boolean onSa, boolean onSu, int del) {
		name = n;
		description = dsc;
		yStart = yS;
		mStart = mS;
		dStart = dS;
		yEnd = yE;
		mEnd = mE;
		dEnd = dE;
		onMonday = onMo;
		onTuesday = onTu;
		onWednesday = onWe;
		onThursday = onTh;
		onFriday = onFr;
		onSaturday = onSa;
		onSunday = onSu;
		delay = del;
	}
	
	public Schedule() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getyStart() {
		return yStart;
	}
	public void setyStart(int yStart) {
		this.yStart = yStart;
	}
	public int getmStart() {
		return mStart;
	}
	public void setmStart(int mStart) {
		this.mStart = mStart;
	}
	public int getdStart() {
		return dStart;
	}
	public void setdStart(int dStart) {
		this.dStart = dStart;
	}
	public int getyEnd() {
		return yEnd;
	}
	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}
	public int getmEnd() {
		return mEnd;
	}
	public void setmEnd(int mEnd) {
		this.mEnd = mEnd;
	}
	public int getdEnd() {
		return dEnd;
	}
	public void setdEnd(int dEnd) {
		this.dEnd = dEnd;
	}

	public boolean isOnMonday() {
		return onMonday;
	}

	public void setOnMonday(boolean onMonday) {
		this.onMonday = onMonday;
	}

	public boolean isOnTuesday() {
		return onTuesday;
	}

	public void setOnTuesday(boolean onTuesday) {
		this.onTuesday = onTuesday;
	}

	public boolean isOnWednesday() {
		return onWednesday;
	}

	public void setOnWednesday(boolean onWednesday) {
		this.onWednesday = onWednesday;
	}

	public boolean isOnThursday() {
		return onThursday;
	}

	public void setOnThursday(boolean onThursday) {
		this.onThursday = onThursday;
	}

	public boolean isOnFriday() {
		return onFriday;
	}

	public void setOnFriday(boolean onFriday) {
		this.onFriday = onFriday;
	}

	public boolean isOnSaturday() {
		return onSaturday;
	}

	public void setOnSaturday(boolean onSaturday) {
		this.onSaturday = onSaturday;
	}

	public boolean isOnSunday() {
		return onSunday;
	}

	public void setOnSunday(boolean onSunday) {
		this.onSunday = onSunday;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
	
	
}
