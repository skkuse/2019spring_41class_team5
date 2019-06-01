package edu.skku.dealistic.model;

import java.util.Objects;

/**
 * Website reference
 *
 * @author Junhyun Kim
 */
public class Reference {
    private Integer id;
    private String name;
    private String url;

    public Reference(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return id.equals(reference.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
