package rj.training.rest.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
//@Table(name = "Movie")
public class Movie {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	//@Column(name = "name")
	@Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address synopsis;

    protected Movie() {
    }

    public Movie(Integer id, String name, Address synopsis) {
        super();
        this.id = id;
        this.name = name;
        this.synopsis = synopsis;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getSynopsis() {
        return synopsis;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSynopsis(Address synopsis) {
		this.synopsis = synopsis;
	}

	@Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return String.format("Movie[id=%d, name=%s, synopis=%s]",id, name, synopsis);
    }

}
