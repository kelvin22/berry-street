package au.org.berrystreet.familyfinder.api.controller

import au.org.berrystreet.familyfinder.api.domain.Family
import au.org.berrystreet.familyfinder.api.domain.Friend
import au.org.berrystreet.familyfinder.api.service.FamilyService
import au.org.berrystreet.familyfinder.api.service.FriendService
import org.springframework.web.bind.annotation.RequestParam

import static au.org.berrystreet.familyfinder.api.Constants.APPLICATION_JSON
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

import au.org.berrystreet.familyfinder.api.domain.Person
import au.org.berrystreet.familyfinder.api.service.PersonService
import au.org.berrystreet.familyfinder.api.service.Service
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.web.bind.annotation.RequestMethod.PUT

@RestController
@RequestMapping(value = '/people', produces = [APPLICATION_JSON])
@Api(value = '/people', description = 'the person API')
class People extends Controller<Person> {

    @Autowired
    PersonService service

    @Autowired
    FamilyService familyService
    
    @Autowired
    FriendService friendService

    @ApiOperation(value = '', notes = 'Creates a new `Person`', response = Person)
    @ApiResponses(value = [@ApiResponse(code = 405, message = 'Invalid input', response = Person)])
    @RequestMapping(
            value = '',
            consumes = [APPLICATION_JSON],
            method = POST)
    Person create( @ApiParam(value = '`Person` object to create', required = true) @RequestBody Person body) {
        super.create(body)
    }

    @ApiOperation(value = '', notes = 'list of all `Person`s that match the criteria provided')
    @ApiResponses(value = [@ApiResponse(code = 200, message = 'Successful response')])
    @RequestMapping(
            value = '',
            method = GET)
    Person[] list(
            @ApiParam(value = 'depth') @RequestParam(value = 'depth', required = true) int depth
    ) {
        super.list(depth) as Person[]
    }

    @ApiOperation(value = '', notes = 'Gets `Person` identified with `id`', response = Person)
    @ApiResponses(value = [@ApiResponse(code = 200, message = 'Successful response', response = Person)])
    @RequestMapping(
            value = '/{id}',
            method = GET)
    Person find(@ApiParam(value = 'ID of person to fetch', required = true) @PathVariable('id') Long id) {
        super.find(id)
    }

    @ApiOperation(value = '', notes = 'Updates an existing `Person`', response = Person)
    @ApiResponses(value = [
            @ApiResponse(code = 404, message = '`Person` not found', response = Person),
            @ApiResponse(code = 405, message = 'Invalid input', response = Person)])
    @RequestMapping(
            value = '/{id}',
            consumes = [APPLICATION_JSON],
            method = PUT)
    Person update(@ApiParam(value = 'ID of person to fetch', required = true) @PathVariable('id') Long id,
                  @ApiParam(value = '`Person` object to update', required = true) @RequestBody Person body) {
        super.update(id, body)
    }

    @ApiOperation(value = '', notes = 'Gets Family of `Person` identified with `id`', response = Person)
    @ApiResponses(value = [@ApiResponse(code = 200, message = 'Successful response', response = Person)])
    @RequestMapping(
            value = '/{id}/family',
            method = GET)
    List<Family> listFamily(@ApiParam(value = 'ID of person to fetch', required = true) @PathVariable('id') Long id) {
        (super.find(id) as Person).family
    }
    @RequestMapping(
            value = '/{id}/family',
            method = PUT)
    Person addFamily(@ApiParam(value = 'this person', required = true) @PathVariable('id') Long id,
                     @ApiParam(value = 'relationship', required = true) @RequestParam('relationship') String relationship,
                     @ApiParam(value = 'relative', required = true) @RequestParam('kinId') Long kinId) {
        Person kin = super.find(kinId) as Person
        Person person = super.find(id) as Person
        Family family = new Family(kin, person, relationship)
        familyService.repository.save(family)
        person
    }

    @ApiOperation(value = '', notes = 'Gets Friends of `Person` identified with `id`', response = Person)
    @ApiResponses(value = [@ApiResponse(code = 200, message = 'Successful response', response = Person)])
    @RequestMapping(
            value = '/{id}/friends',
            method = GET)
    List<Family> listFriends(@ApiParam(value = 'ID of person to fetch', required = true) @PathVariable('id') Long id) {
        (super.find(id) as Person).family
    }
    @RequestMapping(
            value = '/{id}/friends',
            method = PUT)
    Person addFriend(@ApiParam(value = 'this person', required = true) @PathVariable('id') Long id,
                     @ApiParam(value = 'relationship', required = true) @RequestParam('relationship') String relationship,
                     @ApiParam(value = 'friend', required = true) @RequestParam('friendId') Long friendId) {
        Person friend = super.find(friendId) as Person
        Person person = super.find(id) as Person
        Friend friendship = new Friend(friend, person, relationship)
        friendService.repository.save(friendship)
        person
    }

    @Override
    Service<Person> getService() { service }
}
