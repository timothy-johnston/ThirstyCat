package entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Drink {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int drinksToday;
	private Calendar drinkTime;
	
	public Drink(int drinksToday) {
		this.drinksToday = drinksToday;
	}

	public Long getId() {
		return id;
	}

	public int getDrinksToday() {
		return drinksToday;
	}

	public void setDrinksToday(int drinksToday) {
		this.drinksToday = drinksToday;
	}

	public Calendar getDrinkTime() {
		return drinkTime;
	}

	public void setDrinkTime(Calendar drinkTime) {
		this.drinkTime = drinkTime;
	}
	
}
