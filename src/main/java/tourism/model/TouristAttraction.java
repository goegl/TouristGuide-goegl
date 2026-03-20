package tourism.model;

import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private String cityOld;
    private List<String> tags;

    private int id;
    private List<Tag> tagList;
    private City city;

    private int city_id;
    private List<Integer> tagIds;

    public TouristAttraction(String name, String description, String city, List<String> tags) {
        this.name = name;
        this.description = description;
        this.cityOld = city;
        this.tags = tags;
    }
    public TouristAttraction(int attractionId,String name, String description, int city_id) {
        this.id = attractionId;
        this.name = name;
        this.description = description;
        this.city_id = city_id;
    }

    public TouristAttraction(){}

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

    public String getCityOld() {
        return cityOld;
    }

    public void setCityOld(String city) {
        this.cityOld = city;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
