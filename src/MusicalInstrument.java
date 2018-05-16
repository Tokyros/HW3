import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public abstract class MusicalInstrument implements InstrumentFunc{
    private Number price;
    private String brand;

    public MusicalInstrument(String brand, Number price){
        setBrand(brand);
        setPrice(price);
    }

    public MusicalInstrument(Scanner scanner){
        String brand;

        try {
            if (scanner.hasNextInt()){
                setPrice(scanner.nextInt());
            } else {
                setPrice(scanner.nextDouble());
            }
        }catch (InputMismatchException ex){
            throw new InputMismatchException("Price not found!");
        }
        scanner.nextLine();
        brand = scanner.nextLine();
        setBrand(brand);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        if (price.doubleValue() > 0)
            this.price = price;
        else
            throw new InputMismatchException("Price must be a positive number!");

    }


    protected boolean isValidType(String[] typeArr, String material){
        for(int i = 0; i < typeArr.length ; i++) {
            if (material.equals(typeArr[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof MusicalInstrument))
            return false;

        MusicalInstrument otherInstrument = (MusicalInstrument) o;

        return getPrice().doubleValue() == otherInstrument.getPrice().doubleValue() && getBrand().equals(otherInstrument.getBrand());
    }

    @Override
    public String toString() {
        String stringToFormat = "%-8s %-9s| Price: " + (getPrice() instanceof Integer ? "%7d," : "%7.2f,");
        return String.format(stringToFormat, getBrand(), getClass().getCanonicalName(), getPrice());
    }

    @Override
    public int compareTo(MusicalInstrument o) {
        return compareTo(o.getBrand(), o.getPrice());
    }

    public int compareTo(String brand, Number price){
        int brandCompare = this.getBrand().compareTo(brand);
        if (brandCompare != 0) return brandCompare;

        double v = this.getPrice().doubleValue() - price.doubleValue();
        if (v < 0) return -1;
        if (v == 0) return 0;
        return 1;
    }
}
