package au.org.berrystreet.familyfinder.api.domain

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.voodoodyne.jackson.jsog.JSOGGenerator
import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@JsonIdentityInfo(generator=JSOGGenerator.class)
@NodeEntity
class Person {

    @GraphId Long id;

    String name;

//    private int born;
//
//    @Relationship(type = "ACTED_IN")
//    private List<Movie> movies;
//
//    public Person() { }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getBorn() {
//        return born;
//    }
//
//    public List<Movie> getMovies() {
//        return movies;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setBorn(int born) {
//        this.born = born;
//    }
//
//    public void setMovies(List<Movie> movies) {
//        this.movies = movies;
//    }
}
