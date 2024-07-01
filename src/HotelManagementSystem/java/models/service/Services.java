package models.service;

// TODO: make prices real
public enum Services {
    CLEAN_ROOM(1),
    CHARGE(2),
    BABY_KIT(2),
    TOWEL_REPLACE(1),
    EXTRA_BLANKETS(1);

    private final double price;

    Services(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        String[] words = name().split("_");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(word.charAt(0)).append(word.substring(1).toLowerCase()).append(" ");
        }

        return result.toString().trim();
    }
}
