package jrcengine.Interface;

import java.util.Random;

public abstract class Abstract_Item extends Object_Normal {

	public static final int MAX_ITEM_UPGRADE_NUM = 3;

	public static final int ITEM_DAMAGE = 0;
	public static final int ITEM_HP = 1;
	public static final int ITEM_SPEED = 2;
	public static final int ITEM_ARMOR = 3;
	public static final int ITEM_ATTACK_SPEED = 4;
	public static final int ITEM_TOTAL_NUMBER = 5;

	public static final int ITEM_RANDOM_NORMAL = 20;
	public static final int ITEM_RANDOM_HIGH = 40;
	public static final int ITEM_RANDOM_ULTRA = 60;

	private boolean focus;

	private int tag;

	protected boolean isItemStateWindow;
	// ITEM_STATE

	protected boolean isItem;
	protected boolean isBag;
	protected boolean isAccessory;
	protected boolean isClothes;
	protected boolean isHelmets;
	protected boolean isShoes;
	protected boolean isWeapon;
	protected boolean isAllocationPosition;

	protected float[] option;
	protected float[] option_Plus;

	protected int slotNumber;
	protected int price;

	protected String item_Name;
	protected int item_Upgrade_Number;

	public Abstract_Item(String name_Item, int item_Image_Number,
			int item_Price, float center_X, float center_Y, float width) {
		super(item_Image_Number, center_X, center_Y, width, width);

		this.isItemStateWindow = false;
		this.focus = false;
		this.isAllocationPosition = false;
		this.isItem = false;
		this.isBag = false;
		this.isAccessory = false;
		this.isClothes = false;
		this.isHelmets = false;
		this.isShoes = false;
		this.isWeapon = false;

		this.item_Name = name_Item;
		this.option = new float[ITEM_TOTAL_NUMBER];
		this.option_Plus = new float[ITEM_TOTAL_NUMBER];

		for (int i = 0; i < ITEM_TOTAL_NUMBER; i++) {
			this.option[i] = this.option_Plus[i] = 0;
		}
		this.price = item_Price;

		item_Upgrade_Number = 0;
	}

	public Abstract_Item(String name_Item, int item_Image_Number, int item_Price) {
		super(item_Image_Number, 0, 0, 30, 30);

		this.focus = false;
		this.isAllocationPosition = false;
		this.isItem = false;
		this.isBag = false;
		this.isAccessory = false;
		this.isClothes = false;
		this.isHelmets = false;
		this.isShoes = false;
		this.isWeapon = false;

		this.item_Name = name_Item;
		this.option = new float[ITEM_TOTAL_NUMBER];
		this.option_Plus = new float[ITEM_TOTAL_NUMBER];

		for (int i = 0; i < ITEM_TOTAL_NUMBER; i++) {
			this.option[i] = this.option_Plus[i] = 0;
		}
		this.price = item_Price;

		item_Upgrade_Number = 0;
	}

	public boolean getFocus() {
		return this.focus;
	}

	public void setFocus(boolean set) {
		this.focus = set;
	}

	public void setItem_tag(int array_input) {
		this.tag = array_input;
	}

	public boolean getIsWeapon() {
		return this.isWeapon;
	}

	public boolean getIsHelmet() {
		return this.isHelmets;
	}

	public boolean getIsShoes() {
		return this.isShoes;
	}

	public boolean getIsAccessory() {
		return this.isAccessory;
	}

	public boolean getIsClothes() {
		return this.isClothes;
	}

	public boolean getIsBag() {
		return this.isBag;
	}

	public boolean getIsItem() {
		return this.isItem;
	}

	public float[] getOption() {
		return this.option;
	}

	public float[] getOption_Plus() {
		return this.option_Plus;
	}

	public void update(float deltaTime) {
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		stateTime += deltaTime;
	}

	public void setRandomOption() {
		float chance = 0;
		Random rand = new Random();
		chance = rand.nextFloat();

		if (0.2 <= chance) {
			int option;
			option = rand.nextInt(Abstract_Item.ITEM_TOTAL_NUMBER - 1);
			this.option_Plus[option] += (1 - chance)
					* rand.nextInt(Abstract_Item.ITEM_RANDOM_NORMAL);
		} else if (0.005 <= chance) {
			int option;
			for (int i = 0; i < 2; i++) {
				option = rand.nextInt(Abstract_Item.ITEM_TOTAL_NUMBER - 1);
				this.option_Plus[option] += (1 - chance)
						* rand.nextInt(Abstract_Item.ITEM_RANDOM_HIGH)
						+ Abstract_Item.ITEM_RANDOM_NORMAL;
			}
		} else if (0 <= chance) {
			int option;
			for (int i = 0; i < 3; i++) {
				option = rand.nextInt(Abstract_Item.ITEM_TOTAL_NUMBER - 1);
				this.option_Plus[option] += (1 - chance)
						* rand.nextInt(Abstract_Item.ITEM_RANDOM_ULTRA)
						+ Abstract_Item.ITEM_RANDOM_NORMAL;
			}
		}
	}

	public int getItem_tag() {
		return this.tag;
	}

	public int getSlotNumber() {
		return this.slotNumber;
	}

	public boolean getIsAllocationPosition() {
		return this.isAllocationPosition;
	}

	public void setIsAllocationPosition(boolean set) {
		this.isAllocationPosition = set;
	}

	public void setIsItemStateWindow(boolean set) {
		this.isItemStateWindow = set;
	}

	public boolean getIsItemStateWindow() {
		return this.isItemStateWindow;
	}

}
