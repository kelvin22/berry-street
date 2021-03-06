package au.org.berrystreet.familyfinder.api.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.neo4j.ogm.annotation.EndNode
import org.neo4j.ogm.annotation.RelationshipEntity
import org.neo4j.ogm.annotation.StartNode
import org.springframework.stereotype.Component

@ApiModel
@Component
@RelationshipEntity(type = 'SUBJECT')
class Subject extends Entity {
    @StartNode Person person
    @EndNode Case aCase
    @ApiModelProperty String date

    Subject() { }

    Subject(Person person, Case aCase, String date) {
        this.person = person
        this.aCase = aCase
        this.date = date
        this.aCase.subjects.add(this)
    }
}
