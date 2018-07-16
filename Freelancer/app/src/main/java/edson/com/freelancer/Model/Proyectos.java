package edson.com.freelancer.Model;

/**
 * Created by Edson on 24/05/2018.
 */

public class Proyectos {

    private int id;
    private String name;
    private String description;
    private String category;
    private double price;
    private String date;

    public double getOwnerdId() {
        return ownerdId;
    }

    public void setOwnerdId(double ownerdId) {
        this.ownerdId = ownerdId;
    }

    public int getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(int freelancerId) {
        this.freelancerId = freelancerId;
    }

    private double ownerdId;
    private int freelancerId;


    public Proyectos() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
